package com.project.oriobook.modules.product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.responses.ProductResponse;
import com.project.oriobook.modules.product.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Tag(name = "products")
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    // private final ElasticProductRepository productElasticRepository;
    private final ProductService productService;
    private final ElasticsearchClient elasticClient;

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ProductResponse> getAllProducts(@ParameterObject @ModelAttribute FindAllProductQueryDTO query) {
        Page<Product> productsList = productService.getAllProducts(query);
        // elasticClient.bulk(b -> b.index("products").add(objectMapper.valueToTree(productsList.getContent())));

        modelMapper.typeMap(Product.class, ProductResponse.class);
        Page<ProductResponse> productResponse = productsList.map(product ->
                modelMapper.map(product, ProductResponse.class));
        //
        return new PageResponse<>(productResponse);
    }

    @GetMapping("/custom")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ProductResponse> getAllProductsCustom(@ParameterObject @ModelAttribute FindAllProductQueryDTO query)
            throws Exception {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit());
        int from = (int) pageRequest.getOffset();
        int size = pageRequest.getPageSize();

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("products")
                .query(q -> q
                        .matchAll(m -> m)
                )
                .from(from)
                .size(size)
                .build();

        try {
            SearchResponse<ObjectNode> response = elasticClient.search(searchRequest, ObjectNode.class);

            PageResponse<ProductResponse> pageResponse = new PageResponse<>();
            pageResponse.setResponseForElastic(response, query.getPage(), query.getLimit(), ProductResponse.class);

            return pageResponse;
        } catch (IOException e) {
            throw new CommonException.GetElasticData("getProducts");
        }
    }

    @PutMapping("/sync-elastic")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    public Boolean syncElastic() throws Exception {
        Page<Product> products = productService.getAllProducts(null);

        try {
            DeleteByQueryRequest deleteRequest = DeleteByQueryRequest.of(d -> d
                    .index("products")
                    .query(q -> q.matchAll(m -> m))
            );
            elasticClient.deleteByQuery(deleteRequest);

            // for (Product product : products.getContent()) {
            //     IndexRequest<Product> request = new IndexRequest.Builder<Product>()
            //             .index("products")
            //             .id(String.valueOf(product.getId()))
            //             .document(product)
            //             .build();
            //     elasticClient.index(request);
            // }
            // return true;

            BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();

            for (Product product : products.getContent()) {
                BulkOperation bulkOperation = BulkOperation.of(op -> op
                        .index(idx -> idx
                                .index("products")
                                .id(String.valueOf(product.getId()))
                                .document(product)
                        )
                );

                bulkRequestBuilder.operations(bulkOperation);
            }

            BulkResponse bulkResponse = elasticClient.bulk(bulkRequestBuilder.build());

            return !bulkResponse.errors();
        } catch (Exception e) {
            throw new CommonException.SyncElasticData("SyncProducts");
        }
    }

    private static BulkRequest.Builder getBuilder(Page<Product> products) throws JsonProcessingException {
        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();
        ObjectMapper objectMapper = new ObjectMapper();

        Product product = products.getContent().get(0);
        String jsonString = objectMapper.writeValueAsString(product);

        bulkRequestBuilder.operations(op -> op
                .index(idx -> idx
                        .index("products")
                        .id(String.valueOf(product.getId()))
                        .document(JsonData.of(jsonString))
                )
        );
        return bulkRequestBuilder;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable String id) throws Exception {
        Product product = productService.getProductById(id);
        return modelMapper.map(product, ProductResponse.class);
    }

    @PostMapping("")
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean createProduct(@Valid @RequestBody CreateProductDTO productDTO, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Product newProduct = productService.createProduct(productDTO);
        return newProduct != null;
    }

    @PutMapping("/{id}")
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public Boolean updateProduct(@PathVariable String id, @Valid @RequestBody CreateProductDTO productDTO, BindingResult result)
            throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Product updatedProduct = productService.updateProduct(id, productDTO);
        return updatedProduct != null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteProduct(@PathVariable String id) throws Exception {
        productService.deleteProduct(id);
        return true;
    }
}

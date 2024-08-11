package com.project.oriobook.modules.product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.ElasticIndexConst;
import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.common.utils.ElasticUtil;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.elastic.services.ElasticService;
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
import java.time.format.DateTimeFormatter;

@RestController
@Tag(name = "products")
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ElasticsearchClient elasticClient;
    private final ElasticService elasticService;

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ProductResponse> getAllProducts(@ParameterObject @ModelAttribute FindAllProductQueryDTO query) {
        Page<Product> productsList = productService.getAllProducts(query);

        modelMapper.typeMap(Product.class, ProductResponse.class);
        Page<ProductResponse> productResponse = productsList.map(product ->
            modelMapper.map(product, ProductResponse.class));

        return new PageResponse<>(productResponse);
    }

    @GetMapping("/custom")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ProductResponse> getAllProductsCustom(@ParameterObject @ModelAttribute FindAllProductQueryDTO query)
        throws Exception {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit());
        int from = (int) pageRequest.getOffset();
        int size = pageRequest.getPageSize();

        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
            .index(ElasticIndexConst.PRODUCTS)
            .from(from)
            .size(size);

        BoolQuery.Builder boolQueryBuilder = ElasticUtil.generateBoolBaseQuery(query);

        // Add match query only if productName is not null
        if (query.getProductName() != null) {
            boolQueryBuilder.must(m -> m
                .wildcard(wc -> wc
                    .field("name")
                    .value("*" + query.getProductName() + "*")
                )
            );
        }

        // BoolQuery.Builder filterQueryBuilder = new BoolQuery.Builder();
        if (query.getCategoryId() != null) {
            TermQuery termQuery = new TermQuery.Builder()
                .field("categoryNode.id.keyword")
                .value(query.getCategoryId())
                .build();

            boolQueryBuilder.filter(f -> f
                .term(termQuery));
        }

        if (query.getAuthorId() != null) {
            TermQuery termQuery = new TermQuery.Builder()
                .field("authorNode.id.keyword")
                .value(query.getAuthorId())
                .build();

            boolQueryBuilder.filter(f -> f
                .term(termQuery));
        }

        // Add sort query only if sortByPrice is not null
        if (query.getSortByPrice() != null) {
            searchRequestBuilder.sort(s -> s
                .field(f -> f
                    .field("price")
                    .order(query.getSortByPrice() == CommonEnum.SortEnum.ASC ? SortOrder.Asc : SortOrder.Desc)
                )
            );
        }

        // Not do rating sort yet (apply for FindAllProductQueryDTO)
        if (query.getSortByRating() != null) {
            searchRequestBuilder.sort(s -> s
                .field(f -> f
                    .field("rating")
                    .order(query.getSortByRating() == CommonEnum.SortEnum.ASC ? SortOrder.Asc : SortOrder.Desc)
                )
            );
        }

        // Build query for the search request
        searchRequestBuilder
            .query(q -> q.bool(boolQueryBuilder.build()));

        SearchResponse<ObjectNode> searchResponse = ElasticUtil.searchRequest(searchRequestBuilder,
            elasticClient, ElasticIndexConst.PRODUCTS);

        PageResponse<ProductResponse> pageResponse = new PageResponse<>();
        pageResponse.setResponseForElastic(searchResponse, query.getPage(), query.getLimit(), ProductResponse.class);

        return pageResponse;
    }

    @PutMapping("/sync-elastic")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = RoleConst.OP_ADMIN)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    public Boolean syncElastic() throws Exception {
        Page<Product> products = productService.getAllProducts(null);

        return elasticService.syncDataToElastic(products, ElasticIndexConst.PRODUCTS);
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
        if (result.hasErrors()) {
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
    public Boolean updateProduct(@PathVariable String id, @Valid @RequestBody CreateProductDTO productDTO,
                                 BindingResult result)
        throws Exception {
        if (result.hasErrors()) {
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

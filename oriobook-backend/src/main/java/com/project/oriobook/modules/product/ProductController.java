package com.project.oriobook.modules.product;

import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.responses.GetProductResponse;
import com.project.oriobook.modules.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<GetProductResponse> getAllProducts(@ParameterObject @ModelAttribute FindAllProductQueryDTO query) {
        // PageResponse<Product> productsList = productService.getAllProducts(query);
        // return productsList;
        // PageRequest pageRequest = new PageRequest();
        Page<Product> productsList = productService.getAllProducts(query);

        modelMapper.typeMap(Product.class, GetProductResponse.class);
        // modelMapper.typeMap()
                // .addMappings(mapper -> {
                //     mapper.skip(GetProductResponse::setCategoryNode);
                // });
        Page<GetProductResponse> productResponse = productsList.map(product -> {
            GetProductResponse response = modelMapper.map(product, GetProductResponse.class);
            response.setCategoryNode(modelMapper.map(product.getCategoryNode(), GetProductResponse.Category.class));
            return response;
        });

        // List<GetProductResponse> productResponse = productsList.getData()
        //         .stream()
        //         .map(product -> modelMapper.map(product, GetProductResponse.class)).toList();

        return new PageResponse<>(productResponse);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean createProduct(@Valid @RequestBody CreateProductDTO productDTO, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Product newProduct = productService.createProduct(productDTO);
        return newProduct != null;
    }
}

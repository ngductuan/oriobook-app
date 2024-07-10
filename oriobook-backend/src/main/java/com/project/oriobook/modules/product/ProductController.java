package com.project.oriobook.modules.product;

import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.responses.ProductResponse;
import com.project.oriobook.modules.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ProductResponse> getAllProducts(@ParameterObject @ModelAttribute FindAllProductQueryDTO query) {
        Page<Product> productsList = productService.getAllProducts(query);

        modelMapper.typeMap(Product.class, ProductResponse.class);

        Page<ProductResponse> productResponse = productsList.map(product -> {
            ProductResponse response = modelMapper.map(product, ProductResponse.class);
            response.setCategoryNode(modelMapper.map(product.getCategoryNode(), ProductResponse.Category.class));
            return response;
        });

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

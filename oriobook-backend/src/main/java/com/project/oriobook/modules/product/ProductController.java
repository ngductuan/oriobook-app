package com.project.oriobook.modules.product;

import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.ProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.responses.ProductResponse;
import com.project.oriobook.modules.product.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "products")
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
        Page<ProductResponse> productResponse = productsList.map(product ->
                modelMapper.map(product, ProductResponse.class));

        return new PageResponse<>(productResponse);
    }

    String test () {
        System.out.println("test");
        return "test";
    }

    @PostMapping("")
    @PreAuthorize(RoleConst.ROLE_ADMIN_USER)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Product newProduct = productService.createProduct(productDTO);
        return newProduct != null;
    }

    @PutMapping("/{id}")
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public Boolean updateProduct(@PathVariable String id, @Valid @RequestBody ProductDTO productDTO, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Product updatedProduct = productService.updateProduct(id, productDTO);
        return updatedProduct != null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteProduct(@PathVariable String id) throws Exception {
        productService.deleteProduct(id);
        return true;
    }
}

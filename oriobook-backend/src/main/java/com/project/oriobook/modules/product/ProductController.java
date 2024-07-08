package com.project.oriobook.modules.product;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.CategoryException;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.catalog.CatalogException;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK) // @ParameterObject
    public PageResponse<Product> getAllProducts(@ModelAttribute FindAllProductQueryDTO query) {
        PageResponse<Product> productsList = productService.getAllProducts(query);
        return productsList;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@Valid @RequestBody CreateProductDTO productDTO, BindingResult result) throws Exception {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO cannot be null");
        }

        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        if(productDTO.getName().equals("abc")){
            throw new CategoryException.NotFound();
        }

        return CommonConst.SUCCESS_REQUEST;
    }
}

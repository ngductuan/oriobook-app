package com.project.oriobook.modules.product;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.CreateProductDTO;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String createProduct(@Valid @RequestBody CreateProductDTO product, BindingResult result) throws ValidationException{
        if(result.hasErrors()) {
            // List<String> errorMessages = result.getFieldErrors()
            //         .stream()
            //         .map(FieldError::getDefaultMessage)
            //         .toList();
            // return ResponseEntity.badRequest().body(errorMessages).toString();
            throw new ValidationException(result);
        }



        return CommonConst.SUCCESS_REQUEST;
    }
}

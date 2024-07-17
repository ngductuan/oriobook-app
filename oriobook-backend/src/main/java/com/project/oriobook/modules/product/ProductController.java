package com.project.oriobook.modules.product;

import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.dto.ProductDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.responses.ProductResponse;
import com.project.oriobook.modules.product.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ProductResponse> getAllProducts(@ParameterObject @ModelAttribute FindAllProductQueryDTO query) {
        Page<Product> productsList = productService.getAllProducts(query);

        modelMapper.typeMap(Product.class, ProductResponse.class);
        Page<ProductResponse> productResponse = productsList.map(product ->
                modelMapper.map(product, ProductResponse.class));

        redisTemplate.opsForValue().set("test1", new Product());
        return new PageResponse<>(productResponse);
    }

    @PostMapping("")
    @PreAuthorize(RoleConst.ROLE_ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        Product newProduct = productService.createProduct(productDTO);
        String testStr = (String) redisTemplate.opsForValue().get("test1");
        return cacheProduct(newProduct);
    }

    @CachePut(value = "productCache", key = "#product.id")
    public Product cacheProduct(Product product) {
        return product;
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

package com.project.oriobook.modules.product;

import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.services.ProductService;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<Product> getAllProducts(@ModelAttribute FindAllProductQueryDTO query) {
        System.out.println("query " + query);
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit());
        query.setPage(2);
        PageResponse<Product> productsList = productService.getAllProducts(query, pageRequest);
        return productsList;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return product;
    }
}

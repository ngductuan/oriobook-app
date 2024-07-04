package com.project.oriobook.modules.product;

import com.project.oriobook.modules.product.entity.Product;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    @GetMapping("")
    public ResponseEntity<Product> getAllProducts() {
        Product product = new Product();
        return ResponseEntity.ok(product);
    }
}

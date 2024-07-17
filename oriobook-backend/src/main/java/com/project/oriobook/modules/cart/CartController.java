package com.project.oriobook.modules.cart;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "carts")
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {

    @PutMapping("/add/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @CachePut(value = "productCache", key = "#product.id")
    public Boolean addProductToCart(@PathVariable String productId) {
        return true;
    }
}

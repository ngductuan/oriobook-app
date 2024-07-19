package com.project.oriobook.modules.cart;

import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.modules.cart.entities.CartItem;
import com.project.oriobook.modules.cart.services.CartRedisService;
import com.project.oriobook.modules.user.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "carts")
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartRedisService cartRedisService;

    @GetMapping("")
    @PreAuthorize(RoleConst.ROLE_ADMIN_USER)
    @ResponseStatus(HttpStatus.OK)
    public List<CartItem> getCart(@AuthenticationPrincipal User userDetails) throws Exception {
        // return cartRedisService.getCart(userDetails.getId());
        return cartRedisService.getCart(userDetails.getId());
    }

    @PutMapping("/adjust/{productId}")
    @PreAuthorize(RoleConst.ROLE_ADMIN_USER)
    @ResponseStatus(HttpStatus.OK)
    public Boolean addProductToCart(@PathVariable String productId,
                                    @RequestParam CommonEnum.AdjustCartEnum adjustMode,
                                    @AuthenticationPrincipal User userDetails) throws Exception {
        boolean result = cartRedisService.adjustProductToCart(userDetails.getId(), productId, adjustMode);
        return result;
    }
}

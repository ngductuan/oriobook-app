package com.project.oriobook.modules.cart;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.utils.CommonUtil;
import com.project.oriobook.modules.cart.entities.CartRedisItem;
import com.project.oriobook.modules.cart.responses.CartResponse;
import com.project.oriobook.modules.cart.services.CartRedisService;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.services.ProductService;
import com.project.oriobook.modules.user.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ProductService productService;

    private final ModelMapper modelMapper;

    @GetMapping("")
    @Operation(summary = RoleConst.OP_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCart(@AuthenticationPrincipal User userDetails) throws Exception {
        List<CartRedisItem> cartItemsList = cartRedisService.getCart(userDetails.getId());

        CartResponse cart = new CartResponse();
        double totalPrice = 0;
        int totalQuantity = 0;
        for (CartRedisItem cartItem : cartItemsList) {
            // Map product to CartItem
            Product product = productService.getProductById(cartItem.getProductId());
            CartResponse.CartItem cartItemResponse = modelMapper.map(product, CartResponse.CartItem.class);

            double itemTotalPrice = (cartItemResponse.getPrice() * cartItem.getQuantity());
            int quantity = cartItem.getQuantity();

            // Set cart item response
            cartItemResponse.setQuantity(quantity);
            cartItemResponse.setItemTotalPrice(CommonUtil.roundPrice(itemTotalPrice));

            // Calculate total price and quantity
            totalPrice += itemTotalPrice;
            totalQuantity += quantity;

            // Add to cart
            cart.getData().add(cartItemResponse);
            cart.setTotalQuantity(totalQuantity);
            cart.setTotalPrice(CommonUtil.roundPrice(totalPrice));
        }

        return cart;
    }

    @GetMapping("/total-quantity")
    @Operation(summary = RoleConst.OP_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    public int getTotalQuantity(@AuthenticationPrincipal User userDetails) throws Exception {
        List<CartRedisItem> cart = cartRedisService.getCart(userDetails.getId());
        int totalQuantity = cart.stream().mapToInt(CartRedisItem::getQuantity).sum();
        return totalQuantity;
    }

    @PutMapping("/adjust/{productId}")
    @Operation(summary = RoleConst.OP_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_USER)
    @ResponseStatus(HttpStatus.OK)
    public Boolean addProductToCart(@PathVariable String productId,
                                    @RequestParam CommonEnum.AdjustCartEnum adjustMode,
                                    @AuthenticationPrincipal User userDetails) throws Exception {
        boolean result = cartRedisService.adjustProductToCart(userDetails.getId(), productId, adjustMode);
        return result;
    }
}

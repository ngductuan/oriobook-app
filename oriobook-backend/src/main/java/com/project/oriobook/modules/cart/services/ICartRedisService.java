package com.project.oriobook.modules.cart.services;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.modules.cart.entities.Cart;
import com.project.oriobook.modules.cart.entities.CartRedisItem;

import java.util.List;

public interface ICartRedisService {
    Cart getCart(String userId) throws Exception;
    boolean adjustProductToCart(String userId, String productId, CommonEnum.AdjustCartEnum mode) throws Exception;
}

package com.project.oriobook.modules.cart.services;

import com.project.oriobook.common.enums.CommonEnum;

public interface ICartRedisService {
    boolean adjustProductToCart(String userId, String productId, CommonEnum.AdjustCartEnum mode) throws Exception;
}

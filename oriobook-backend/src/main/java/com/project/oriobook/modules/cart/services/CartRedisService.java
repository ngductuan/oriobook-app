package com.project.oriobook.modules.cart.services;

import com.project.oriobook.common.constants.RedisConst;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.exceptions.CartException;
import com.project.oriobook.common.utils.MapperUtil;
import com.project.oriobook.modules.cart.entities.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartRedisService implements ICartRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MapperUtil mapperUtil;

    @Override
    public boolean adjustProductToCart(String userId, String productId, CommonEnum.AdjustCartEnum mode) throws Exception {
        String cacheString = String.format(RedisConst.CART_CACHE, userId, productId);
        boolean isExist = redisTemplate.hasKey(cacheString) == Boolean.TRUE;
        CartItem cartItem = new CartItem(productId, 1); // for add first

        if (!isExist) {
            if (mode != CommonEnum.AdjustCartEnum.ADD) {
                throw new CartException.InvalidAction();
            }
            cartItem = new CartItem(productId, 1);
        } else {
            cartItem = (CartItem) redisTemplate.opsForHash().get(cacheString, productId);
        }

        System.out.println("cartItem: " + cartItem);
        if(cartItem == null) {
            throw new CartException.ItemNotExists();
        }

        if (mode == CommonEnum.AdjustCartEnum.ADD) {
            if (isExist) {
                // If the key was already present, increment the value by 1
                cartItem.addItem();
            }
            redisTemplate.opsForHash().put(cacheString, productId, mapperUtil.convertObjectToMap(cartItem));

        } else if (mode == CommonEnum.AdjustCartEnum.SUBTRACT) {
            int quantity = cartItem.getCount();
            if (quantity > 1) {
                redisTemplate.opsForHash().increment(cacheString, productId, -1);
            } else {
                redisTemplate.opsForHash().delete(cacheString, productId);
            }
        } else if (mode == CommonEnum.AdjustCartEnum.DELETE) {
            redisTemplate.opsForHash().delete(cacheString, productId);
        }
        return true;
    }
}

package com.project.oriobook.modules.cart.services;

import com.project.oriobook.common.constants.RedisConst;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.exceptions.CartException;
import com.project.oriobook.common.utils.MapperUtil;
import com.project.oriobook.common.utils.RedisUtil;
import com.project.oriobook.modules.cart.entities.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartRedisService implements ICartRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MapperUtil mapperUtil;
    private final RedisUtil redisUtil;

    @Override
    public boolean adjustProductToCart(String userId, String productId, CommonEnum.AdjustCartEnum mode) throws Exception {
        String cacheString = String.format(RedisConst.CART_SET_CACHE, userId, productId);
        String getCacheStr = String.format(RedisConst.CART_GET_CACHE, userId);
        boolean isExist = redisTemplate.hasKey(cacheString) == Boolean.TRUE;
        CartItem cartItem;

        if (!isExist) {
            // for ADD mode
            if (mode != CommonEnum.AdjustCartEnum.ADD) {
                throw new CartException.InvalidAction();
            }
            // add new item
            cartItem = new CartItem(productId, 1);
        } else {
            // for all mode
            // cartItem = (CartItem) redisTemplate.opsForHash().get(cacheString, "productId");
            List<Object> test = List.of("productId", "count");
            List<Object> str = redisTemplate.opsForHash().multiGet(cacheString, test);
            CartItem cartItem1 = redisUtil.convertToCartItem(str, test);

            // CartItem map = (CartItem) redisTemplate.opsForHash().get(cacheString, productId);
            // CartItem map2 = (CartItem) redisTemplate.opsForHash().get(getCacheStr, productId);
            // CartItem map2 = mapperUtil.convertJsonToObject(
            //         , CartItem.class
            // );
            // CartItem map1 = (CartItem) redisTemplate.opsForHash().get(cacheString, productId);
            // CartItem map2 = mapperUtil.convertJsonToObject(
            //         (String) redisTemplate.opsForHash().get(cacheString, "productId"),
            //         CartItem.class);
            System.out.println("cartItem: ");
        }

        // // If error in getting data
        // if (cartItem == null) {
        //     throw new CartException.ItemNotFound();
        // }
        //
        // if (mode == CommonEnum.AdjustCartEnum.ADD) {
        //     if (isExist) {
        //         // If the key was already present, increment the value by 1
        //         redisTemplate.opsForHash().put(cacheString, "count", cartItem.getCount() + 1);
        //     } else {
        //         Map<String, Object> map = mapperUtil.convertObjectToMap(cartItem);
        //         redisTemplate.opsForHash().putAll(cacheString, map);
        //     }
        //     // for(Map.Entry<String, Object> entry : map.entrySet()) {
        //     //     System.out.println(entry.getKey() + " : " + entry.getValue());
        //     // }
        //
        // } else if (mode == CommonEnum.AdjustCartEnum.SUBTRACT) {
        //     int quantity = cartItem.getCount();
        //     if (quantity > 1) {
        //         redisTemplate.opsForHash().increment(cacheString, productId, -1);
        //     } else {
        //         redisTemplate.opsForHash().delete(cacheString, productId);
        //     }
        // } else if (mode == CommonEnum.AdjustCartEnum.DELETE) {
        //     redisTemplate.opsForHash().delete(cacheString, productId);
        // }
        return true;
    }
}

package com.project.oriobook.modules.cart.services;

import com.project.oriobook.common.constants.RedisConst;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.exceptions.CartException;
import com.project.oriobook.common.utils.MapperUtil;
import com.project.oriobook.common.utils.RedisUtil;
import com.project.oriobook.common.utils.ValidationUtil;
import com.project.oriobook.modules.cart.entities.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartRedisService implements ICartRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MapperUtil mapperUtil;
    private final RedisUtil redisUtil;

    @Override
    public List<CartItem> getCart(String userId) throws Exception {
        String cachePattern = String.format(RedisConst.CART_GET_CACHE, userId);
        // boolean isExist = redisTemplate.hasKey(cacheString) == Boolean.TRUE;
        Set<String> keys = redisTemplate.keys(cachePattern);
        List<String> test = List.of("productId", "quantity");

        if(!ValidationUtil.diffNullOrEmptyList(keys)) return List.of();

        // Map<String, List<Object>> cartItems = keys.stream().collect(
        //         Collectors.toMap(
        //                 key -> key,
        //                 key -> {
        //                     String cacheString = String.format(RedisConst.CART_SET_CACHE, userId, key);
        //                     return redisTemplate.opsForHash().multiGet(cacheString, Collections.singleton(test));
        //                 }
        //         )
        // );
        try{
            // Map<String, List<Object>> cartItems2 = keys.stream()
            //         .collect(Collectors.toMap(
            //                 key -> key,
            //                 key -> redisTemplate.opsForList().range(key, 0, -1)
            //         ));
            // Map<String, Map<String, Object>> cartItems3 = keys.stream()
            //         .collect(Collectors.toMap(
            //                 key -> key,
            //                 key -> redisTemplate.opsForHash().multiGet(key, Collections.singleton(test))
            //         ));
            // Map<String, CartItem> cartItems4 = keys.stream()
            //         .collect(Collectors.toMap(
            //                 key -> key,
            //                 key -> {
            //                     // return redisTemplate.opsForHash().get(key, "quantity");
            //                     return (CartItem) ;
            //                 }
            //         ));

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CartItem> cartItems = new ArrayList<>();
        for(String key : keys){
            Map<Object, Object> test2 = redisTemplate.opsForHash().entries(key);
            cartItems.add(redisUtil.convertRedisToObject(test2, CartItem.class));
        }
        return cartItems;

    }

    @Override
    public boolean adjustProductToCart(String userId, String productId, CommonEnum.AdjustCartEnum mode) throws Exception {
        String cacheString = String.format(RedisConst.CART_SET_CACHE, userId, productId);
        boolean isExist = redisTemplate.hasKey(cacheString) == Boolean.TRUE;
        Integer prodQuantity = 0;
        String prodQuantityName = "quantity";

        if (!isExist) {
            if (mode != CommonEnum.AdjustCartEnum.ADD) {
                throw new CartException.InvalidAction();
            }
        } else {
            prodQuantity = (Integer) redisTemplate.opsForHash().get(cacheString, prodQuantityName);
        }

        if (mode == CommonEnum.AdjustCartEnum.ADD) {
            if (isExist) {
                // If the key was already present, increment the value by 1
                redisTemplate.opsForHash().increment(cacheString, prodQuantityName, 1);
            } else {
                CartItem cartItem = new CartItem(productId, 1);
                Map<String, Object> map = mapperUtil.convertObjectToMap(cartItem);
                redisTemplate.opsForHash().putAll(cacheString, map);
            }

        } else if (mode == CommonEnum.AdjustCartEnum.SUBTRACT) {
            if(prodQuantity == null) throw new CartException.InvalidAction();

            if (prodQuantity > 1) {
                redisTemplate.opsForHash().increment(cacheString, prodQuantityName, -1);
            } else {
                redisTemplate.delete(cacheString);
            }

        } else if (mode == CommonEnum.AdjustCartEnum.DELETE) {
            if(prodQuantity == null) throw new CartException.InvalidAction();

            redisTemplate.delete(cacheString);
        }
        return true;
    }
}

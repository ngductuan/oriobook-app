package com.project.oriobook.modules.cart.services;

import com.project.oriobook.common.constants.RedisConst;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.exceptions.CartException;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.common.utils.MapperUtil;
import com.project.oriobook.common.utils.RedisUtil;
import com.project.oriobook.modules.auth.services.AuthService;
import com.project.oriobook.modules.cart.entities.CartRedisItem;
import com.project.oriobook.modules.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartRedisService implements ICartRedisService {
    private final ProductService productService;

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisUtil redisUtil;
    private static final Logger logger = LoggerFactory.getLogger(CartRedisService.class);

    @Override
    public List<CartRedisItem> getCart(String userId) throws Exception {
        String cachePattern = String.format(RedisConst.CART_GET_CACHE, userId);
        Set<String> keys = redisTemplate.keys(cachePattern);

        if(keys == null) throw new CommonException.ConvertRedisData("getCart");

        List<CartRedisItem> redisItems = new ArrayList<>();
        for (String key : keys){
            Map<Object, Object> entry = redisTemplate.opsForHash().entries(key);
            redisItems.add(redisUtil.convertRedisToObject(entry, CartRedisItem.class));
        }

        return redisItems;
    }

    @Override
    public boolean adjustProductToCart(String userId, String productId, CommonEnum.AdjustCartEnum mode) throws Exception {
        // Check valid product id
        productService.getProductById(productId);

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
                CartRedisItem cartItem = new CartRedisItem(cacheString, productId, 1);
                Map<String, Object> map = MapperUtil.convertObjectToMap(cartItem);
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

    @Override
    public void deleteCart(String userId) throws Exception {
        String cachePattern = String.format(RedisConst.CART_GET_CACHE, userId);
        Set<String> keys = redisTemplate.keys(cachePattern);

        if(keys == null) throw new CommonException.ConvertRedisData("deleteCart");

        for (String key : keys){
            redisTemplate.delete(key);
        }
    }
}

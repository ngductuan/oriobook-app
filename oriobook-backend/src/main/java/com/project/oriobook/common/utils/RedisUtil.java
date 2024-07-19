package com.project.oriobook.common.utils;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.modules.cart.entities.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final MapperUtil mapperUtil;

    // public CartItem convertToCartItem(List<Object, Object> entries) throws Exception {
    //     if (values == null || keys == null || values.size() != keys.size()) {
    //         throw new CommonException.ConvertRedisData("(CartItem)" + CommonConst.REDIS_CONVERT_DATA);
    //     }
    //
    //     Map<String, Object> map = new HashMap<>();
    //     for (int i = 0; i < keys.size(); i++) {
    //         map.put((String) keys.get(i), values.get(i));
    //     }
    //
    //     return mapperUtil.convertMapToObject(map, CartItem.class);
    // }

    public <T> T convertRedisToObject(Map<Object, Object> entries, Class<T> className) throws Exception {
        if (entries == null) {
            throw new CommonException.ConvertRedisData(CommonConst.REDIS_CONVERT_DATA);
        }

        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            map.put((String) entry.getKey(), entry.getValue());
        }

        T aliasItem = mapperUtil.convertMapToObject(map, className);

        return aliasItem;
    }
}

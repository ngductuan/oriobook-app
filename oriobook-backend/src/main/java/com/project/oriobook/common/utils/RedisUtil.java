package com.project.oriobook.common.utils;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final MapperUtil mapperUtil;

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

package com.project.oriobook.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.oriobook.common.exceptions.CommonException;

public class KafkaUtil {
    public static <T> T convertObjectToMessage(Object kafkaObject, Class<T> entityClass) throws Exception {
        try {
            JsonNode userAfterNode = MapperUtil.objectMapper.convertValue(kafkaObject, JsonNode.class);

            JsonNode payload = userAfterNode.get("payload");
            if (payload != null) {
                JsonNode afterNode = payload.get("after");
                if (afterNode != null) {
                    T coreData = MapperUtil.objectMapper.treeToValue(afterNode, entityClass);

                    return coreData;
                } else {
                    System.out.println("No 'after' data available.");
                }
            } else {
                System.out.println("No 'payload' data available.");
            }
        } catch (Exception e) {
            throw new CommonException.GetKafkaData("GetKafkaData (" + entityClass.getName() + ")");
        }
        return null;
    }
}

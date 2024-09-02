package com.project.oriobook.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.oriobook.common.exceptions.CommonException;

public class KafkaUtil {
    public static <T> T convertObjectToMessage(Object kafkaObject, Class<T> entityClass) throws Exception {
        try {
            JsonNode userAfterNode = MapperUtil.objectMapper.convertValue(kafkaObject, JsonNode.class);
            JsonNode payload = userAfterNode.get("payload");

            if (payload == null) {
                throw new CommonException.GetKafkaData("NoPayloadAvailable (" + entityClass.getName() + ")");
            }

            JsonNode afterNode = payload.get("after");
            if (afterNode != null && !afterNode.isNull()) {
                T coreData = MapperUtil.objectMapper.treeToValue(afterNode, entityClass);

                return coreData;
            } else {
                System.out.println("No 'after' data available.");
                // for delete operation
                return null;
            }
        } catch (Exception e) {
            throw new CommonException.GetKafkaData("GetKafkaData (" + entityClass.getName() + ")");
        }
    }
}

package com.project.oriobook.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.oriobook.common.constants.KafkaConst;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.core.message.base.DMLMessageBase;

public class KafkaUtil {
    public static <T extends DMLMessageBase> T convertObjectToDMLMessage(Object kafkaObject, Class<T> entityClass)
        throws Exception {
        try {
            JsonNode userAfterNode = MapperUtil.objectMapper.convertValue(kafkaObject, JsonNode.class);
            JsonNode payload = userAfterNode.get("payload");

            if (ValidationUtil.isNullJsonNode(payload)) {
                throw new CommonException.GetKafkaData("NoPayloadAvailable (" + entityClass.getName() + ")");
            }

            JsonNode opNode = payload.get("op");
            String operation = "";

            if(!ValidationUtil.isNullJsonNode(opNode)) {
                String op = opNode.asText();
                if (!ValidationUtil.isNullOrBlankString(op)) {
                    operation = op;
                }
            }

            if(operation.equals(KafkaConst.DELETE_OP)){
                JsonNode idNode = payload.get("before").get("id");
                if (!ValidationUtil.isNullJsonNode(idNode)) {
                    String id = idNode.asText();
                    T instance = DMLMessageBase.createMessage(operation, entityClass);
                    instance.setId(id);

                    return instance;
                }
            } else {
                JsonNode afterNode = payload.get("after");
                if (!ValidationUtil.isNullJsonNode(afterNode)) {
                    T changedData = MapperUtil.objectMapper.treeToValue(afterNode, entityClass);
                    changedData.setOperation(operation);
                    return changedData;
                }
            }

            System.out.println("No 'after' data available.");
            return null;
        } catch (Exception e) {
            throw new CommonException.GetKafkaData("GetKafkaData (" + entityClass.getName() + ")");
        }
    }
}

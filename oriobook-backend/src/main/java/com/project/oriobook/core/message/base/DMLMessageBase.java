package com.project.oriobook.core.message.base;

import com.project.oriobook.common.constants.KafkaConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DMLMessageBase extends MessageBase{
    String operation = KafkaConst.SELECT_OP;

    public static <T extends DMLMessageBase> T createMessage(String operation, Class<T> clazz) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();
        instance.setOperation(operation);
        return instance;
    }
}

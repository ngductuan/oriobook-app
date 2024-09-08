package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class CommonException {
    // Redis
    public static class ConvertRedisData extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_GET_REDIS_DATA;
        private static final String message = ErrorMessage.get(code);

        public ConvertRedisData(String message) {
            super(500, ConvertRedisData.message, new ErrorDetails(code.toString(), message));
        }
    }

    // Elastic
    public static class GetElasticData extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_GET_ELASTIC_DATA;
        private static final String message = ErrorMessage.get(code);

        public GetElasticData(String message) {
            super(500, GetElasticData.message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class ElasticData extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_SYNC_ELASTIC_DATA;
        private static final String message = ErrorMessage.get(code);

        public ElasticData(String message) {
            super(500, ElasticData.message, new ErrorDetails(code.toString(), message));
        }
    }

    // Kafka
    public static class GetKafkaData extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_GET_KAFKA_DATA;
        private static final String message = ErrorMessage.get(code);

        public GetKafkaData(String message) {
            super(500, GetKafkaData.message, new ErrorDetails(code.toString(), message));
        }
    }
}

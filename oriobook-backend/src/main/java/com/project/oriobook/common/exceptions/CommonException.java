package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.enums.ErrorCodeEnum;
import com.project.oriobook.common.enums.ErrorMessage;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import com.project.oriobook.common.exceptions.base.LogicExceptionBase;

public class CommonException {
    public static class ConvertRedisData extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_GET_REDIS_DATA;
        private static final String message = ErrorMessage.get(code);

        public ConvertRedisData(String message) {
            super(500, ConvertRedisData.message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class GetElasticData extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_GET_ELASTIC_DATA;
        private static final String message = ErrorMessage.get(code);

        public GetElasticData(String message) {
            super(500, GetElasticData.message, new ErrorDetails(code.toString(), message));
        }
    }

    public static class SyncElasticData extends LogicExceptionBase {
        private static final ErrorCodeEnum code = ErrorCodeEnum.COMMON_SYNC_ELASTIC_DATA;
        private static final String message = ErrorMessage.get(code);

        public SyncElasticData(String message) {
            super(500, SyncElasticData.message, new ErrorDetails(code.toString(), message));
        }
    }
}

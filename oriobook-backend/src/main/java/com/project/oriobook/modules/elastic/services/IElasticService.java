package com.project.oriobook.modules.elastic.services;

import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.core.message.base.MessageBase;
import org.springframework.data.domain.Page;

public interface IElasticService {
    <T extends BaseEntity> boolean syncDataToElastic(Page<T> dataPage, String index) throws Exception;
    <T extends BaseEntity> void insertDataToElastic(T data, String index) throws Exception;
    <T extends BaseEntity> void updateDataToElastic(T data, String index) throws Exception;
    <T extends BaseEntity> void deleteDataFromElastic(T data, String index) throws Exception;
}

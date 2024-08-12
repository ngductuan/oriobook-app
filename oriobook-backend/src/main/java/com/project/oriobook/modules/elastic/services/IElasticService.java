package com.project.oriobook.modules.elastic.services;

import com.project.oriobook.core.entity.base.BaseEntity;
import org.springframework.data.domain.Page;

public interface IElasticService {
    <T extends BaseEntity> boolean syncDataToElastic(Page<T> dataPage, String index) throws Exception;
}

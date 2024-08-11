package com.project.oriobook.modules.elastic.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.product.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticService implements IElasticService{
    private final ElasticsearchClient elasticClient;

    @Override
    public <T extends BaseEntity> boolean syncDataToElastic(Page<T> dataPage, String index) throws Exception {
        try {
            DeleteByQueryRequest deleteRequest = DeleteByQueryRequest.of(d -> d
                    .index(index)
                    .query(q -> q.matchAll(m -> m))
            );
            elasticClient.deleteByQuery(deleteRequest);

            BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();

            for (T item : dataPage.getContent()) {
                BulkOperation bulkOperation = BulkOperation.of(op -> op
                        .index(idx -> idx
                                .index(index)
                                .id(String.valueOf(item.getId()))
                                .document(item)
                        )
                );

                bulkRequestBuilder.operations(bulkOperation);
            }

            BulkResponse bulkResponse = elasticClient.bulk(bulkRequestBuilder.build());

            return !bulkResponse.errors();
        } catch (Exception e) {
            throw new CommonException.SyncElasticData("SyncElastic (" + index + ")");
        }
    }
}

package com.project.oriobook.modules.elastic.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.core.message.base.MessageBase;
import com.project.oriobook.modules.author.services.AuthorService;
import com.project.oriobook.modules.category.services.CategoryService;
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

    @Override
    public <T extends BaseEntity> void insertDataToElastic(T data, String index) throws Exception {
        try {
            String documentId = String.valueOf(data.getId());

            IndexRequest<T> indexRequest = IndexRequest.of(i -> i
                .index(index)
                .id(documentId)
                .document(data)
            );

            IndexResponse indexResponse = elasticClient.index(indexRequest);

            if (indexResponse.result() == Result.Created) {
                System.out.println("Document inserted successfully in index: " + index);
            } else {
                throw new CommonException.SyncElasticData("Failed to insert document in index: " + index);
            }

        } catch (Exception e) {
            throw new CommonException.SyncElasticData("Insert failed for index: " + index + ", " +
                "Document ID: " + data.getId());
        }
    }

    @Override
    public <T extends BaseEntity> void updateDataToElastic(T data, String index) throws Exception {
        try {
            String documentId = String.valueOf(data.getId());

            UpdateRequest<T, T> updateRequest = UpdateRequest.of(u -> u
                .index(index)
                .id(documentId)
                .doc(data)
            );

            UpdateResponse<T> updateResponse = elasticClient.update(updateRequest, data.getClass());

            if (updateResponse.result() == Result.NotFound) {
                throw new CommonException.SyncElasticData("Document not found in index: " + index);
            }

            System.out.println("Successfully updated document in index: " + index);
        } catch (Exception e) {
            System.err.println("Update failed for index: " + index + ", Document ID: " + data.getId());
            throw new CommonException.SyncElasticData("Update failed for index: " + index + ", " +
                "Document ID: " + data.getId());
        }
    }

    @Override
    public <T extends BaseEntity> void deleteDataFromElastic(T data, String index) throws Exception {
        try {
            String documentId = String.valueOf(data.getId());

            DeleteRequest deleteRequest = DeleteRequest.of(d -> d
                .index(index)
                .id(documentId)
            );

            DeleteResponse deleteResponse = elasticClient.delete(deleteRequest);

            if (deleteResponse.result() == Result.NotFound) {
                throw new CommonException.SyncElasticData("Document not found in index: " + index);
            }

            System.out.println("Successfully deleted document from index: " + index);

        } catch (Exception e) {
            throw new CommonException.SyncElasticData("Delete failed for index: " + index + ", " +
                "Document ID: " + data.getId());
        }
    }
}

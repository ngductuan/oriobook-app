package com.project.oriobook.common.utils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.ElasticIndexConst;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.core.pagination.base.QueryFilterBase;
import com.project.oriobook.modules.product.responses.ProductResponse;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ElasticUtil {
    public static BoolQuery.Builder generateBoolBaseQuery(QueryFilterBase query) {
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        // Add range queries only if startDate or endDate are not null
        if (query.getStartDate() != null || query.getEndDate() != null) {
            RangeQuery.Builder rangeQueryBuilder = QueryBuilders.range().field("createdAt");

            if (query.getStartDate() != null) {
                rangeQueryBuilder.gte(JsonData.of(query.getStartDate().format(DateTimeFormatter
                    .ofPattern(CommonConst.DATE_TIME_FORMAT_PATTERN))));
            }
            if (query.getEndDate() != null) {
                rangeQueryBuilder.lte(JsonData.of(query.getEndDate().format(DateTimeFormatter
                    .ofPattern(CommonConst.DATE_TIME_FORMAT_PATTERN))));
            }

            boolQueryBuilder.filter(Query.of(q -> q.range(rangeQueryBuilder.build())));
        }
        return boolQueryBuilder;
    }

    public static <T> PageResponse<T> generatePageResponse(SearchRequest.Builder searchRequestBuilder,
        ElasticsearchClient client, QueryFilterBase query, String elasticIndex, Class<T> targetClass) throws Exception {

        SearchRequest searchRequest = searchRequestBuilder.build();

        try {
            SearchResponse<ObjectNode> response = client.search(searchRequest, ObjectNode.class);

            PageResponse<T> pageResponse = new PageResponse<>();
            pageResponse.setResponseForElastic(response, query.getPage(), query.getLimit(), targetClass);

            return pageResponse;
        } catch (IOException e) {
            throw new CommonException.GetElasticData("GetElasticData: (" + elasticIndex + ")");
        }
    }
}

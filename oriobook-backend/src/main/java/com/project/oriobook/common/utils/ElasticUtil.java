package com.project.oriobook.common.utils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
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
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.common.exceptions.CommonException;
import com.project.oriobook.core.pagination.base.QueryFilterBase;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ElasticUtil {
    public static SearchRequest.Builder generateSearchRequestBuilder(QueryFilterBase query, String elasticIndex) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit());
        int from = (int) pageRequest.getOffset();
        int size = pageRequest.getPageSize();

        SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
            .index(elasticIndex)
            .from(from)
            .size(size);

        if (!ValidationUtil.isNullOrBlankString(query.getSortByDate())) {
            searchRequestBuilder.sort(s -> s
                .field(f -> f
                    .field("createdAt").order(ElasticUtil.getSortOrder(query.getSortByDate()))
                )
            );
        }

        return searchRequestBuilder;
    }

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

    public static SearchResponse<ObjectNode> searchRequest(SearchRequest.Builder searchRequestBuilder,
        ElasticsearchClient client, String elasticIndex) throws Exception {

        SearchRequest searchRequest = searchRequestBuilder.build();

        try {
            return client.search(searchRequest, ObjectNode.class);
        } catch (IOException e) {
            throw new CommonException.GetElasticData("GetElasticData: (" + elasticIndex + ")");
        }
    }

    public static SortOrder getSortOrder(CommonEnum.SortEnum sortBy) {
        return sortBy.equals(CommonEnum.SortEnum.ASC) ? SortOrder.Asc : SortOrder.Desc;
    }
}

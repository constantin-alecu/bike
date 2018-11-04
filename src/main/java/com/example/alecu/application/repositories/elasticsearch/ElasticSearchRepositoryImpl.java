package com.example.alecu.application.repositories.elasticsearch;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticSearchRepositoryImpl implements ElasticSearchRepository {

    @Autowired
    RestHighLevelClient client;

    @Override
    public void index(String jsonDocument, String indexName, String indexType, String id) {
        IndexRequest request = new IndexRequest(indexName, indexType, id);
        request.source(jsonDocument, XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> search(String text, String field, String indexName) {
        List<String> result = new ArrayList<>();

        SearchRequest searchRequest = new SearchRequest(indexName);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery(field, text));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchHits hits = searchResponse.getHits();

        List<SearchHit> searchHits = Arrays.asList(hits.getHits());

        searchHits.forEach( hit -> result.add(hit.getId()));

        return result;
    }
}

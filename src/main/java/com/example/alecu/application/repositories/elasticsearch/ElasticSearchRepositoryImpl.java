package com.example.alecu.application.repositories.elasticsearch;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
}

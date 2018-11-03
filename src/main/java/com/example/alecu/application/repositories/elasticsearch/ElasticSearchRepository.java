package com.example.alecu.application.repositories.elasticsearch;

public interface ElasticSearchRepository {

    void index(String jsonDocument, String indexName, String indexType, String id);

}

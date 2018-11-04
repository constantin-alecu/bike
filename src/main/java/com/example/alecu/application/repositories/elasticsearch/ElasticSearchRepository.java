package com.example.alecu.application.repositories.elasticsearch;

import java.util.List;

public interface ElasticSearchRepository {

    void index(String jsonDocument, String indexName, String indexType, String id);

    List<String> search(String text, String field, String indexName);

}

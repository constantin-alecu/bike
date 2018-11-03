package com.example.alecu.application.repositories.elasticsearch.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchClientFactory.class);

    @Bean
    public RestHighLevelClient init(@Value("${elastic.search.host}") String elasticSearchHost,
                                    @Value("${elastic.search.port}") Integer elasticSearchPort ){
        LOGGER.info("Creating rest elasticsearchclient configured to: " + elasticSearchHost + elasticSearchPort);
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elasticSearchHost, elasticSearchPort, "http")));
    }
}

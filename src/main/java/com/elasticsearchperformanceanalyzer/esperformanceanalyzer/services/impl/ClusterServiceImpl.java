package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.impl;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.ClusterService;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yashasvi
 */

@Service
@Log4j2
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    RestHighLevelClient elasticsearchClient;

    @Autowired
    String clusterInfo;

    @Override
    public ResponseEntity<Object> checkTestIndexCount() {

        try {
            SearchRequest searchRequest = new SearchRequest("test1234");

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(50);
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            long totalCount = searchResponse.getHits().getTotalHits().value;
            return new ResponseEntity<>(totalCount, HttpStatus.ACCEPTED);
        }
        catch (Exception e){
             log.error(e);
        }
            return null;

    }

    @Override
    public ResponseEntity<Object> getClusterInfo() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.getForEntity(clusterInfo, Object.class);
        return new ResponseEntity<>(response.getBody(),HttpStatus.ACCEPTED);
    }
}

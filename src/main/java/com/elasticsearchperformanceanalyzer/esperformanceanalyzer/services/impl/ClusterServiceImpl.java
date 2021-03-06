package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.impl;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.IndexCountRequest;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.ClusterService;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

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
    public ResponseEntity<Object> checkTestIndexCount(IndexCountRequest indexCountRequest) {

        try {
            SearchRequest searchRequest = new SearchRequest(indexCountRequest.getIndexName());

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(0);
            sourceBuilder.trackTotalHits(true);
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("Processing Index Count");

            Instant initialInstant = Instant.now();
            long initialTimeStampMillis = initialInstant.toEpochMilli();

            long totalCount = searchResponse.getHits().getTotalHits().value;

            Instant finalInstant = Instant.now();
            long finalTimeStampMillis = finalInstant.toEpochMilli();

            log.info("Total Number of Documents in the index is : " + totalCount);
            log.info("Total Time taken to fetch the documents is : " + (finalTimeStampMillis - initialTimeStampMillis) + " ms.");
            return new ResponseEntity<>(totalCount, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error(e);
        }
        return null;

    }

    @Override
    public ResponseEntity<Object> getClusterInfo() {

        try {

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Object> response = restTemplate.getForEntity(clusterInfo, Object.class);

            log.info("status code value = " + response.getStatusCodeValue());
            log.info("status code = " + response.getStatusCode());

            if (response.getStatusCodeValue() != 200) {
                log.info("Cluster is not responding, please check cluster monitorings");
            }
            return new ResponseEntity<>(response.getBody(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error(e);
            log.info("Cluster is not responding, please check cluster monitorings");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

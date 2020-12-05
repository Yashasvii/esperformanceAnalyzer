package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.impl;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.ClusterService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.cluster.ClusterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yashasvi
 */

@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    RestHighLevelClient elasticsearchClient;

    @Autowired
    String clusterInfo;

    @Value("${elasticsearch.host}")
    public String host;

    @Value("${elasticsearch.port}")
    public int port;

    @Override
    public ResponseEntity<Object> checkHealth() {

        ClusterHealthRequest request = new ClusterHealthRequest();
        return new ResponseEntity<>(request.getDescription(), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Object> getClusterInfo() {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.getForEntity(clusterInfo, Object.class);
        return new ResponseEntity<>(response.getBody(),HttpStatus.ACCEPTED);
    }
}

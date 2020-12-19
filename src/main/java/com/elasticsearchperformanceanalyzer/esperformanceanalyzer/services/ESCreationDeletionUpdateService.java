package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESCreationRequest;
import org.springframework.http.ResponseEntity;

public interface ESCreationDeletionUpdateService {

    public ResponseEntity<Object> createElasticSearchCluster(ESCreationRequest esCreationRequest);
}

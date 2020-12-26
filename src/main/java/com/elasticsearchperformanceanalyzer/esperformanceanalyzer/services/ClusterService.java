package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.IndexCountRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author yashasvi
 */
public interface ClusterService {
    ResponseEntity<Object> checkTestIndexCount(IndexCountRequest indexCountRequest);
    ResponseEntity<Object> getClusterInfo();
}

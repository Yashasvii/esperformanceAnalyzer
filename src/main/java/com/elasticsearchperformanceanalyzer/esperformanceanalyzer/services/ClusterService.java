package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services;

import org.springframework.http.ResponseEntity;

/**
 * @author yashasvi
 */
public interface ClusterService {
    ResponseEntity<Object> checkTestIndexCount();
    ResponseEntity<Object> getClusterInfo();
}

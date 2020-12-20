package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESSearchRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author yashasvi
 */
public interface SearchService {

    ResponseEntity<Object> searchQuery(ESSearchRequest esSearchRequest);
}

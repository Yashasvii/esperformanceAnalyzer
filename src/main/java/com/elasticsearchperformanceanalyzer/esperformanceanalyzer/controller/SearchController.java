package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.controller;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.constants.PathConstants;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESSearchRequest;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yashasvi
 */

@RestController
@RequestMapping(PathConstants.SEARCH)
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping(path = PathConstants.SEARCH_QUERY)
    public ResponseEntity<Object> searchQuery(@RequestBody ESSearchRequest esSearchRequest) {
        return searchService.searchQuery(esSearchRequest);
    }

}

package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.controller;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.constants.PathConstants;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.IndexCountRequest;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is to test if ES is up and running
 */

/**
 * @author yashasvi
 */
@RestController
@RequestMapping(PathConstants.CLUSTERINFO)
public class ESClusterController {

    @Autowired
    ClusterService clusterService;

    @PostMapping(path = PathConstants.CHECK_TEST_INDEX_COUNT)
    public ResponseEntity<Object> checkTestIndexCount(@RequestBody IndexCountRequest indexCountRequest) {
        return clusterService.checkTestIndexCount(indexCountRequest);
    }

    @GetMapping(path = "")
    public ResponseEntity<Object> getClusterInfo() {
        return clusterService.getClusterInfo();
    }


}

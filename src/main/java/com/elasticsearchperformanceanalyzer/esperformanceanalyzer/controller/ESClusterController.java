package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.controller;

/**
 * @author yashasvi
 */

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.constants.PathConstants;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is to test if ES is up and running
 */

@RestController
@RequestMapping(PathConstants.CLUSTERINFO)
public class ESClusterController {

    @Autowired
    ClusterService clusterService;

    @GetMapping(path = PathConstants.CHECK_HEALTH)
    public ResponseEntity<Object> checkHealth() {
        return clusterService.checkHealth();
    }

    @GetMapping(path = "")
    public ResponseEntity<Object> getClusterInfo() {
        return clusterService.getClusterInfo();
    }




}

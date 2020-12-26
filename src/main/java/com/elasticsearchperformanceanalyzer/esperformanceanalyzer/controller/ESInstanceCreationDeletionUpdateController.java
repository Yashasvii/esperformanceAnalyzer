package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.controller;


import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.constants.PathConstants;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESCreationUpdateDeleteRequest;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.ESCreationDeletionUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author yashasvi
 */
@RestController
@RequestMapping(PathConstants.CLUSTER_SETTINGS)
public class ESInstanceCreationDeletionUpdateController {

    @Autowired
    ESCreationDeletionUpdateService esCreationDeletionUpdateService;

    @PostMapping(path = PathConstants.CREATE_ELASTICSEARCH_CLUSTER)
    public ResponseEntity<Object> createElasticsearchCluster(@RequestBody ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest) {
        return esCreationDeletionUpdateService.createDomain(esCreationUpdateDeleteRequest);
    }

    @PostMapping(path = PathConstants.UPDATE_ELASTICSEARCH_CLUSTER)
    public ResponseEntity<Object> updateElasticsearchCluster(@RequestBody ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest) {
        return esCreationDeletionUpdateService.updateDomain(esCreationUpdateDeleteRequest);
    }

    @PostMapping(path = PathConstants.DELETE_ELASTICSEARCH_CLUSTER)
    public ResponseEntity<Object> deleteElasticsearchCluster(@RequestBody ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest) {
        return esCreationDeletionUpdateService.deleteDomain(esCreationUpdateDeleteRequest);
    }

    @PostMapping(path = PathConstants.GET_PROCESSING_STATUS)
    public ResponseEntity<Object> getProcessingStatus(@RequestBody ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest) {
        return esCreationDeletionUpdateService.getProcessingStatus(esCreationUpdateDeleteRequest);
    }



}

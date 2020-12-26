package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESCreationUpdateDeleteRequest;
import org.springframework.http.ResponseEntity;


/**
 * @author yashasvi
 */
public interface ESCreationDeletionUpdateService {

    ResponseEntity<Object> createDomain(ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest);

    ResponseEntity<Object> updateDomain(ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest);

    ResponseEntity<Object> deleteDomain(ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest);

    ResponseEntity<Object> getProcessingStatus(ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest);
}

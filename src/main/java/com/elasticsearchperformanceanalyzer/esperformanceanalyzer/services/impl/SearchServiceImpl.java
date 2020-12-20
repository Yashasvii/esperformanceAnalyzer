package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.impl;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESSearchRequest;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.SearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yashasvi
 */


@Service
@Log4j2
public class SearchServiceImpl implements SearchService {

    @Autowired
    String clusterInfo;

    @Override
    public ResponseEntity<Object> searchQuery(ESSearchRequest esSearchRequest) {


   try {

        RestTemplate restTemplate = new RestTemplate();
        int times = (esSearchRequest.getTimes() != null)?esSearchRequest.getTimes():1;
        for(int i=0;i<times; i++) {
            ResponseEntity<String> response =  restTemplate.postForEntity(clusterInfo, esSearchRequest.getQuery(), String.class);
            System.out.println("response = " + response);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
   catch (Exception e) {
       log.error(e);
       return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }

   }

}

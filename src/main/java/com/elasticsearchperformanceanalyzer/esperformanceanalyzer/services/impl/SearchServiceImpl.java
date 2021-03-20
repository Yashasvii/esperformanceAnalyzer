package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.impl;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESSearchRequest;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.MyHttpCallable;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.SearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        log.info("Index Name = " + esSearchRequest.getIndexName());
        log.info("Query = " + esSearchRequest.getQuery());
        log.info("Total Search Count = " + esSearchRequest.getTotalTimes());
        log.info("Number of Concurrent Threads = " + esSearchRequest.getConcurrencyTimes());


        try {

            Instant initialInstant = Instant.now();
            long initialTimeStampMillis = initialInstant.toEpochMilli();


            ExecutorService executor = Executors.newFixedThreadPool((esSearchRequest.getConcurrencyTimes() != null) ? esSearchRequest.getConcurrencyTimes() : 10);
            Callable<ResponseEntity<String>> callable = new MyHttpCallable(clusterInfo, esSearchRequest);

            for (int i = 0; i < ((esSearchRequest.getTotalTimes() != null) ? esSearchRequest.getTotalTimes() : 10); i++) {

                Future<ResponseEntity<String>> future = executor.submit(callable);
                // System.out.println("future = " + future.get().toString());
            }

            executor.shutdown();

            Instant finalInstant = Instant.now();
            long finalTimeStampMillis = finalInstant.toEpochMilli();

            log.info("Total Time taken for search : " + (finalTimeStampMillis - initialTimeStampMillis) + " ms.");

            return new ResponseEntity<>(HttpStatus.ACCEPTED);


        } catch (Exception e) {
            log.error("Exception", e);
        }


        return null;


    }

}

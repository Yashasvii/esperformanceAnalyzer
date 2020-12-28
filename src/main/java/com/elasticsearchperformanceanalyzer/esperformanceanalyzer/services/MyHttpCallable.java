package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services;

import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESSearchRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author yashasvi
 */

@Data
@Log4j2
public class MyHttpCallable implements Callable<ResponseEntity<String> > {



    private String clusterInfo;
    private ESSearchRequest esSearchRequest;

    public MyHttpCallable(String clusterInfo, ESSearchRequest esSearchRequest) {
        this.clusterInfo = clusterInfo;
        this.esSearchRequest = esSearchRequest;
    }

    @Override
    public ResponseEntity<String>  call()  {

        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> requestMap = mapper.readValue( esSearchRequest.getQuery(), new TypeReference<>() {});
            return restTemplate.postForEntity(clusterInfo + "/" + (esSearchRequest.getIndexName() == null?"":esSearchRequest.getIndexName() + "/_search"), requestMap, String.class);
        }
        catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
}

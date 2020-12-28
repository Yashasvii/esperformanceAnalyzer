package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request;

import lombok.Data;

/**
 * @author yashasvi
 */

@Data
public class ESSearchRequest {

    Integer totalTimes;
    String query;
    Integer concurrencyTimes;
    String indexName;

}

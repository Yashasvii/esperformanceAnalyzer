package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request;


import lombok.Data;

/**
 * @author yashasvi
 */
@Data
public class ESCreationUpdateDeleteRequest {

    String domainName;
    //    Integer masterCount;
    String esVersion;
    //    Boolean dedicatedMasterEnabled;
    String instanceType;
    Integer instanceCount;

}

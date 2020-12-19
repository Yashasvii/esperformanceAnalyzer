package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request;


import lombok.Data;

@Data
public class ESCreationRequest {

    String domainName;
//    Integer masterCount;
    String esVersion;
//    Boolean dedicatedMasterEnabled;
    String instanceType;
    Integer instanceCount;

}

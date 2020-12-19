package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.impl;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticsearch.AWSElasticsearch;
import com.amazonaws.services.elasticsearch.AWSElasticsearchClientBuilder;
import com.amazonaws.services.elasticsearch.model.*;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESCreationRequest;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.ESCreationDeletionUpdateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class ESCreationDeletionUpdateServiceImpl implements ESCreationDeletionUpdateService {

    @Override
    public ResponseEntity<Object> createElasticSearchCluster(ESCreationRequest esCreationRequest) {


        // Build the client using the default credentials chain.
        // You can use the AWS CLI and run `aws configure` to set access key, secret
        // key, and default region.
        final AWSElasticsearch client = AWSElasticsearchClientBuilder
                .standard()
                // Unnecessary, but lets you use a region different than your default.
                .withRegion(Regions.AP_SOUTH_1)
                // Unnecessary, but if desired, you can use a different provider chain.
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        // Create a new domain
        String createdDomainResponse = createDomain(client, esCreationRequest);

        return new ResponseEntity<>(createdDomainResponse, HttpStatus.ACCEPTED);


    }

    /**
     * Creates an Amazon Elasticsearch Service domain with the specified options.
     * Some options require other AWS resources, such as an Amazon Cognito user pool
     * and identity pool, whereas others require just an instance type or instance
     * count.
     *
     * @param client
     *            The AWSElasticsearch client to use for the requests to Amazon
     *            Elasticsearch Service
     * @param esCreationRequest
     *            The name of the domain you want to create
     */
    private static String createDomain(final AWSElasticsearch client, ESCreationRequest esCreationRequest) {

        // Create the request and set the desired configuration options
        CreateElasticsearchDomainRequest createRequest = new CreateElasticsearchDomainRequest()
                .withDomainName((esCreationRequest.getDomainName() != null)? esCreationRequest.getDomainName(): "test-domain")
                .withElasticsearchVersion((esCreationRequest.getEsVersion() != null)? esCreationRequest.getEsVersion(): "7.9")
                .withElasticsearchClusterConfig(new ElasticsearchClusterConfig()
                        .withDedicatedMasterEnabled(false)
//                        .withDedicatedMasterCount(3)
//                        // Small, inexpensive instance types for testing. Not recommended for production
//                        // domains.
//                        .withDedicatedMasterType("t2.small.elasticsearch")
                        .withInstanceType((esCreationRequest.getInstanceType() != null)? esCreationRequest.getInstanceType(): "t2.small.elasticsearch")
                        .withInstanceCount((esCreationRequest.getInstanceType() != null)? esCreationRequest.getInstanceCount(): 5))
                // Many instance types require EBS storage.
                .withEBSOptions(new EBSOptions()
                        .withEBSEnabled(true)
                        .withVolumeSize(10)
                        .withVolumeType(VolumeType.Gp2));
        // You can uncomment this line and add your account ID, a user name, and the
        // domain name to add an access policy.
        // .withAccessPolicies("{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"arn:aws:iam::123456789012:user/user-name\"]},\"Action\":[\"es:*\"],\"Resource\":\"arn:aws:es:region:123456789012:domain/domain-name/*\"}]}")
//                .withNodeToNodeEncryptionOptions(new NodeToNodeEncryptionOptions()
//                        .withEnabled(true));

        // Make the request.
        System.out.println("Sending domain creation request...");
        CreateElasticsearchDomainResult createResponse = client.createElasticsearchDomain(createRequest);
        System.out.println("Domain creation response from Amazon Elasticsearch Service:");
        System.out.println(createResponse.getDomainStatus().toString());
        return createResponse.getDomainStatus().toString();
    }

}

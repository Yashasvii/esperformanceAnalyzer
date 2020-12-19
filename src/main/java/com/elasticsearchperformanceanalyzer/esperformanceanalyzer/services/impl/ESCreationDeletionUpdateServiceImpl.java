package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.impl;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticsearch.AWSElasticsearch;
import com.amazonaws.services.elasticsearch.AWSElasticsearchClientBuilder;
import com.amazonaws.services.elasticsearch.model.*;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.request.ESCreationUpdateDeleteRequest;
import com.elasticsearchperformanceanalyzer.esperformanceanalyzer.services.ESCreationDeletionUpdateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author yashasvi
 */

@Service
@Log4j2
public class ESCreationDeletionUpdateServiceImpl implements ESCreationDeletionUpdateService {

    /**
     * Creates an Amazon Elasticsearch Service domain with the specified options.
     * Some options require other AWS resources, such as an Amazon Cognito user pool
     * and identity pool, whereas others require just an instance type or instance
     * count.
     * AWSElasticsearch client to use for the requests to Amazon
     *            Elasticsearch Service
     * @param esCreationUpdateDeleteRequest
     *            The name of the domain you want to create
     */


    /**
     * @author Yashasvi
     */

    @Override
    public ResponseEntity<Object> createDomain(ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest) {

        // Create the request and set the desired configuration options
        CreateElasticsearchDomainRequest createRequest = new CreateElasticsearchDomainRequest()
                .withDomainName((esCreationUpdateDeleteRequest.getDomainName() != null) ? esCreationUpdateDeleteRequest.getDomainName() : "test-domain")
                .withElasticsearchVersion((esCreationUpdateDeleteRequest.getEsVersion() != null) ? esCreationUpdateDeleteRequest.getEsVersion() : "7.9")
                .withElasticsearchClusterConfig(new ElasticsearchClusterConfig()
                        .withDedicatedMasterEnabled(false)
//                        .withDedicatedMasterCount(3)
//                        // Small, inexpensive instance types for testing. Not recommended for production
//                        // domains.
//                        .withDedicatedMasterType("t2.small.elasticsearch")
                        .withInstanceType((esCreationUpdateDeleteRequest.getInstanceType() != null) ? esCreationUpdateDeleteRequest.getInstanceType() : "t2.small.elasticsearch")
                        .withInstanceCount((esCreationUpdateDeleteRequest.getInstanceCount() != null) ? esCreationUpdateDeleteRequest.getInstanceCount() : 5))
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
        CreateElasticsearchDomainResult createResponse = getClient().createElasticsearchDomain(createRequest);
        System.out.println("Domain creation response from Amazon Elasticsearch Service:");
        System.out.println(createResponse.getDomainStatus().toString());
        return new ResponseEntity<>(createResponse.getDomainStatus().toString(), HttpStatus.ACCEPTED);
    }


    /**
     * Updates the configuration of an Amazon Elasticsearch Service domain with the
     * specified options. Some options require other AWS resources, such as an
     * Amazon Cognito user pool and identity pool, whereas others require just an
     * instance type or instance count.
     */
    @Override
    public ResponseEntity<Object> updateDomain(ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest) {

        try {
            // Updates the domain to use three data instances instead of five.
            // You can uncomment the Cognito lines and fill in the strings to enable Cognito
            // authentication for Kibana.
            final UpdateElasticsearchDomainConfigRequest updateRequest = new UpdateElasticsearchDomainConfigRequest()
                    .withDomainName((esCreationUpdateDeleteRequest.getDomainName() != null) ? esCreationUpdateDeleteRequest.getDomainName() : "test-domain")
                    // .withCognitoOptions(new CognitoOptions()
                    // .withEnabled(true)
                    // .withUserPoolId("user-pool-id")
                    // .withIdentityPoolId("identity-pool-id")
                    // .withRoleArn("role-arn"))
                    .withElasticsearchClusterConfig(new ElasticsearchClusterConfig()
                            .withInstanceCount((esCreationUpdateDeleteRequest.getInstanceCount() != null) ? esCreationUpdateDeleteRequest.getInstanceCount() : 5));

            System.out.println("Sending domain update request...");
            final UpdateElasticsearchDomainConfigResult updateResponse = getClient()
                    .updateElasticsearchDomainConfig(updateRequest);
            System.out.println("Domain update response from Amazon Elasticsearch Service:");
            System.out.println(updateResponse.toString());
            return new ResponseEntity<>(updateResponse.toString(), HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException e) {
            System.out.println("Domain not found. Please check the domain name.");
            return new ResponseEntity<>("Domain not found. Please check the domain name.", HttpStatus.BAD_REQUEST);
        }


    }

    /**
     * Deletes an Amazon Elasticsearch Service domain. Deleting a domain can take
     * several minutes.
     */

    @Override
    public ResponseEntity<Object> deleteDomain(ESCreationUpdateDeleteRequest esCreationUpdateDeleteRequest) {
        try {
            final DeleteElasticsearchDomainRequest deleteRequest = new DeleteElasticsearchDomainRequest()
                    .withDomainName((esCreationUpdateDeleteRequest.getDomainName() != null) ? esCreationUpdateDeleteRequest.getDomainName() : "test-domain");

            System.out.println("Sending domain deletion request...");
            final DeleteElasticsearchDomainResult deleteResponse = getClient().deleteElasticsearchDomain(deleteRequest);
            System.out.println("Domain deletion response from Amazon Elasticsearch Service:");
            System.out.println(deleteResponse.toString());
            return new ResponseEntity<>(deleteResponse.toString(), HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException e) {
            System.out.println("Domain not found. Please check the domain name.");
            return new ResponseEntity<>("Domain not found. Please check the domain name.", HttpStatus.BAD_REQUEST);
        }
    }


    private AWSElasticsearch getClient() {

        // Build the client using the default credentials chain.
        // You can use the AWS CLI and run `aws configure` to set access key, secret
        // key, and default region.
        return AWSElasticsearchClientBuilder
                .standard()
                // Unnecessary, but lets you use a region different than your default.
                .withRegion(Regions.AP_SOUTH_1)
                // Unnecessary, but if desired, you can use a different provider chain.
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

}

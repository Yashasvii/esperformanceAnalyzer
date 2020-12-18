package com.elasticsearchperformanceanalyzer.esperformanceanalyzer.configuration;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yashasvi
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    public String host;

    @Value("${elasticsearch.port}")
    public int port;

    @Value("${elasticsearch.username}")
    public String username;

    @Value("${elasticsearch.password}")
    public String password;

    @Bean
    public RestHighLevelClient elasticsearchClient() {

        return new RestHighLevelClient(
                RestClient.builder(
                        HttpHost.create("https://search-es-test-hfvxnaeq6toidr6qahbh25ojje.ap-south-1.es.amazonaws.com")));
    }

    @Bean
    public String clusterInfo() {
        return  "https://search-es-test-hfvxnaeq6toidr6qahbh25ojje.ap-south-1.es.amazonaws.com/";
    }


//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//
//        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "http"))
//                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
//
//        return new RestHighLevelClient(builder);
//    }
//
//    @Bean
//    public String clusterInfo() {
//        return "http://"+ host + ":"+port;
//    }

}

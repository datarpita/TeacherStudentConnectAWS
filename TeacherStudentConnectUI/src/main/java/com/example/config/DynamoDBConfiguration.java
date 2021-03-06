package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class DynamoDBConfiguration {
	
	@Value("${aws.region}")
	private String awsRegion;
	
	@Value("${access-key}")
	private String awsAccessKey;
	
	@Value("${secret-key}")
	private String awsSecretKey;
	
	@Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }
	
	@Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return buildAmazonDynamoDB();
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
    	String dynamoDBEndpoint = "dynamodb." + awsRegion + ".amazonaws.com";
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                dynamoDBEndpoint,
                                awsRegion
                        )
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        awsAccessKey,
                                        awsSecretKey
                                )
                        )
                )
                .build();
    }
}

package com.example.lambda;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class Test {
	
	public static void main(String[] args) {
		String region = "ap-south-1";
		System.out.println("In constructor - TeacherRepository: "+ region);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		RegionUtils.getRegion(region);
        client.setRegion(RegionUtils.getRegion(region));
        DynamoDBMapper mapper = new DynamoDBMapper(client); 
	}

}

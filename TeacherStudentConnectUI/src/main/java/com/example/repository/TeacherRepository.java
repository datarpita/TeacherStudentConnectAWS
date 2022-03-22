package com.example.repository;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.example.entity.Teacher;

@Repository
public class TeacherRepository {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	@Value("${table.name}")
	private String tableName;
	
	@Value("${save.teacher.url}")
	private String saveTeacherURL;



	@Autowired private AmazonDynamoDB amazonDynamoDB;


	public Teacher saveTeacher(Teacher teacher) {
		//dynamoDBMapper.save(teacher);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Teacher> responseEntity = restTemplate.postForEntity(saveTeacherURL, teacher, Teacher.class);
		teacher = responseEntity.getBody();
		return teacher;
	}


	public ArrayList<Teacher> listTeachers(){

		ArrayList<Teacher> teacherList = new ArrayList<>();
		ScanResult result = null;
		do {
			ScanRequest scanRequest = new ScanRequest().withTableName(tableName); 
			if(result != null){
				scanRequest.setExclusiveStartKey(result.getLastEvaluatedKey());
	        }
			result = amazonDynamoDB.scan(scanRequest); 
			
			
			for (Map<String, AttributeValue> item :	result.getItems()){ 
				System.out.println(item); 
				Teacher t = new Teacher();
				t.setTeacherid(item.get("teacherid").getS());
				t.setName(item.get("name").getS());
				t.setSchool(item.get("school").getS());
				ArrayList<String> subjectList = new ArrayList<>();
				item.get("subjects").getL().forEach(s -> {
					subjectList.add(s.getS());
				});				
				t.setSubjects(subjectList);	
				teacherList.add(t);
			}
		}while(result.getLastEvaluatedKey() != null);
		return teacherList;
	}
	
	
	public Teacher loadTeacherById(String teacherId) {
		Teacher teacherObj = dynamoDBMapper.load(Teacher.class, teacherId);
		return teacherObj;
	}

}

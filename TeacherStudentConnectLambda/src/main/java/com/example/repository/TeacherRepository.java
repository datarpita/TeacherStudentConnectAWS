package com.example.repository;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.entity.Teacher;


public class TeacherRepository {

	private static DynamoDBMapper mapper;
	//private DynamoDB dynamoDb;
	
	
	public TeacherRepository() {
		String region = System.getenv("LAMBDA_REGION");
		System.out.println("In constructor - TeacherRepository: "+ region);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(RegionUtils.getRegion(region));
        mapper = new DynamoDBMapper(client);     
	}

	

	public Teacher saveTeacher(Teacher teacher) {		
		System.out.println("In saveTeacher: teacher:::" + teacher);		
		mapper.save(teacher);
		return teacher;
	}


	/*
	 * public ArrayList<Teacher> listTeachers(){
	 * 
	 * ArrayList<Teacher> teacherList = new ArrayList<>(); ScanResult result = null;
	 * do { ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
	 * if(result != null){
	 * scanRequest.setExclusiveStartKey(result.getLastEvaluatedKey()); } result =
	 * amazonDynamoDB.scan(scanRequest);
	 * 
	 * 
	 * for (Map<String, AttributeValue> item : result.getItems()){
	 * System.out.println(item); Teacher t = new Teacher();
	 * t.setTeacherid(item.get("teacherid").getS());
	 * t.setName(item.get("name").getS()); t.setSchool(item.get("school").getS());
	 * ArrayList<String> subjectList = new ArrayList<>();
	 * item.get("subjects").getL().forEach(s -> { subjectList.add(s.getS()); });
	 * t.setSubjects(subjectList); teacherList.add(t); }
	 * }while(result.getLastEvaluatedKey() != null); return teacherList; }
	 */
	
	
	/*
	 * public Teacher loadTeacherById(String teacherId) { Teacher teacherObj =
	 * dynamoDBMapper.load(Teacher.class, teacherId); return teacherObj; }
	 */
}

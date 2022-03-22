package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.entity.Student;
import com.example.entity.Teacher;

@Repository
public class StudentRepository {

	@Autowired
    private DynamoDBMapper dynamoDBMapper;


	public Student saveStudent(Student student, Teacher teacherObj) {
	    List<Student> studentList = null;
	    if (teacherObj.getStudents() == null) {
	      studentList = new ArrayList<>();
	    } else {
	      studentList = teacherObj.getStudents();
	    } 
	    studentList.add(student);
	    teacherObj.setStudents(studentList);
	    this.dynamoDBMapper.save(teacherObj);
	    return student;
	  }

    public Student getStudentById(String studentId) {
        return dynamoDBMapper.load(Student.class, studentId);
    }

    public String deleteStudent(String studentId) {
    	Student emp = dynamoDBMapper.load(Student.class, studentId);
        dynamoDBMapper.delete(emp);
        return "student Deleted!";
    }

    public String updateStudent(String studentId, Student student) {
        dynamoDBMapper.save(student,
                new DynamoDBSaveExpression()
        .withExpectedEntry("studentId",
                new ExpectedAttributeValue(
                        new AttributeValue().withS(studentId)
                )));
        return studentId;
    }
	
}

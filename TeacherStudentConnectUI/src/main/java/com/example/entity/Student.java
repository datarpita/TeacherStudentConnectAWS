package com.example.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Student {
  @DynamoDBAttribute
  private String studentRollNo;
  
  @DynamoDBAttribute
  private String name;
  
  @DynamoDBAttribute
  private String dob;
  
  @DynamoDBAttribute
  private String gender;
  
  public Student() {}
  
  public Student(String studentRollNo, String name, String dob, String gender) {
    this.studentRollNo = studentRollNo;
    this.name = name;
    this.dob = dob;
    this.gender = gender;
  }
  
  public String getStudentRollNo() {
    return this.studentRollNo;
  }
  
  public void setStudentRollNo(String studentRollNo) {
    this.studentRollNo = studentRollNo;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDob() {
    return this.dob;
  }
  
  public void setDob(String dob) {
    this.dob = dob;
  }
  
  public String getGender() {
    return this.gender;
  }
  
  public void setGender(String gender) {
    this.gender = gender;
  }
  
  public String toString() {
    return "Student [studentRollNo=" + this.studentRollNo + ", name=" + this.name + ", dob=" + this.dob + ", gender=" + this.gender + "]";
  }
}
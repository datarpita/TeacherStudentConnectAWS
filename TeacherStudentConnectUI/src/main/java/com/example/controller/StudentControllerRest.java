package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Student;
import com.example.repository.StudentRepository;

@RestController
public class StudentControllerRest {
	
	@Autowired
	private StudentRepository studRepo;
	
	
	@PostMapping("/submitstudent")
    public Student saveStudent(@RequestBody Student student) {
        return studRepo.saveStudent(student);
    }


}

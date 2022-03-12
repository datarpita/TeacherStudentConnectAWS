package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Qualification;
import com.example.entity.Student;
import com.example.repository.StudentRepository;

@Controller
public class StudentController {
	
	@Autowired
	StudentRepository studRepo;
	
	@Value("${qualification.lambda.url}")
	private String qualLambdaURL;
	
	@GetMapping("/showstudent")
	public String showAddStudentPage(Model model) {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Qualification> responseEntity = restTemplate.getForEntity(qualLambdaURL, Qualification.class);
		Qualification qualObj = responseEntity.getBody();
		//Qualification qualObj = null;
		if (qualObj != null) {
			model.addAttribute("qual", qualObj.getBusinessstream());
			System.out.println("Response received:::"+qualObj.getBusinessstream());
		} else
			model.addAttribute("qual", "");
		Student newStudentObj = new Student();
		model.addAttribute("newstudent", newStudentObj);
		return "addstudent";
	}
}

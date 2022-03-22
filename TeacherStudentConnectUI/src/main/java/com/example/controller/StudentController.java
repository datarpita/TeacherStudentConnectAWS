package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Qualification;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.repository.StudentRepository;
import com.example.repository.TeacherRepository;

@Controller
public class StudentController {
	
	@Autowired
	StudentRepository studRepo;
	
	@Autowired
	  private TeacherRepository teacherRepo;
	
	@Value("${qualification.lambda.url}")
	private String qualLambdaURL;
	
	@GetMapping({"/showstudent"})
	  public String showAddStudentPage(@RequestParam String teacherid, Model model, HttpServletRequest request) {
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Qualification> qualResponseEntity = restTemplate.getForEntity(qualLambdaURL, Qualification.class);
	    Qualification qualObj = qualResponseEntity.getBody();
	    if (qualObj != null) {
	      model.addAttribute("qual", qualObj.getBusinessstream());
	      System.out.println("Response received:::" + qualObj.getBusinessstream());
	    } else {
	      model.addAttribute("qual", "");
	    } 
	    Student newStudentObj = new Student();
	    model.addAttribute("newstudent", newStudentObj);
	    HttpSession httpSession = request.getSession();
	    httpSession.setAttribute("teacherid", teacherid);
	    return "addstudent";
	  }
	
	@PostMapping({"/submitstudent"})
	  public String submitStudentPage(@ModelAttribute Student newStudentObj, HttpServletRequest request, Model model) {
	    String teacherid = (String)request.getSession().getAttribute("teacherid");
	    Teacher teacherObj = this.teacherRepo.loadTeacherById(teacherid);
	    this.studRepo.saveStudent(newStudentObj, teacherObj);
	    model.addAttribute("newstudent", newStudentObj);
	    return "addstudent";
	  }
}

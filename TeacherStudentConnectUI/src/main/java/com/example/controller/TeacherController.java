package com.example.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.Teacher;
import com.example.repository.TeacherRepository;

@Controller
public class TeacherController {
	
	@Value("${save.teacher.url}")
	private String qualLambdaURL;
	
	@Autowired
	private TeacherRepository teacherRepo; 
	
	@GetMapping("/showteacher")
	public String showAddTeacherPage(Model model) {
		Teacher newTeacherObj = new Teacher();
		model.addAttribute("newteacher", newTeacherObj);
		model.addAttribute("isAdded", "false");
		return "addteacher";
	}

	
	@PostMapping("/submitteacher")
	public String submitTeacherPage(@ModelAttribute Teacher newteacher, Model model) {
		System.out.println("New teacher: " + newteacher);
		teacherRepo.saveTeacher(newteacher);
		model.addAttribute("newteacher", newteacher);
		model.addAttribute("isAdded", "true");
		return "addteacher";
	}
	
	@GetMapping("/listteacher")
	public String listAllTeachers(Model model) {
		
		ArrayList<Teacher> teacherList = teacherRepo.listTeachers();
		model.addAttribute("teacherlist", teacherList);
		return "listteacher";
	}
}

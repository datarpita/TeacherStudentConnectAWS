package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Inputs;

@Controller
public class QualificationController {

	@GetMapping("/")
	public String showIndex(Model model) {
		Inputs inputObj = new Inputs();
		model.addAttribute(inputObj);
		return "index";
	}
	
	
	@PostMapping("/addsession")
	public String addSessionDetails(Inputs inputsObj) {		
		System.out.println("Session id received:"+ inputsObj.getSessionid());
		System.out.println("Session name received:"+ inputsObj.getSessionname());
		return "index";
	}
	
	@GetMapping("/getqual")
	public String getQual(Model model) {
		String url="https://jdegury2fb.execute-api.ap-south-1.amazonaws.com/dev/getbs";
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		System.out.println("Response received:::"+response);
		model.addAttribute("qual", response);
		return "addstudent";
	}
	
	
	
}

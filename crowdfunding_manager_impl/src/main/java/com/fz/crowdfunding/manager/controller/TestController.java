package com.fz.crowdfunding.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fz.crowdfunding.manager.service.TestService;

@Controller
public class TestController {
	@Autowired
	public TestService testService;
	
	@RequestMapping(value="/test")
	public String test(){
		System.out.println("TestController");
		testService.insert();
		return "success";
	}

}

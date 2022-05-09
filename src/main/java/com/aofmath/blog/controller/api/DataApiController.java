package com.aofmath.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aofmath.blog.service.DataService;

@RestController
public class DataApiController {
	
	@Autowired
	private DataService dataService;
	
	@GetMapping("/data/restDeInfo")
	public String restDeInfo() throws Exception{
		return dataService.restDeInfo();
	}
}

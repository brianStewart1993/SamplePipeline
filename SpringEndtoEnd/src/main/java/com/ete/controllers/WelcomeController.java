package com.ete.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class WelcomeController {

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@ApiOperation(value = "getGreeting", nickname = "getGreeting")
	 @ApiResponses(value = {
		        @ApiResponse(code = 500, message = "Server error"),
		        @ApiResponse(code = 200, message = "Successful retrieval",
		           responseContainer = "List") })
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}

}
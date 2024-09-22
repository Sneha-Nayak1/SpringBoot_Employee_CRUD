package com.example.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.example.demo.Exception.ResourceNotFoundException;


@ControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleException(ResourceNotFoundException exception){
		String mesg=exception.getMessage();
		return new ResponseEntity<String>(mesg,HttpStatus.BAD_REQUEST);
	}

}

package com.employee.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.domain.ResponseMessage;

@ControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler
	public ResponseMessage handleValidationExceptions(MethodArgumentNotValidException ex) {
		ResponseMessage responseError = new ResponseMessage();
		Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
		responseError.setStatuscode(HttpStatus.BAD_REQUEST.value());
		responseError.setMessage(errors.toString());
		return responseError;
	}
}

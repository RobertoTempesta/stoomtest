package com.roberto.stoomtest.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.roberto.stoomtest.services.exceptions.EntityNotFound;
import com.roberto.stoomtest.services.exceptions.GeoAPIException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(EntityNotFound.class)
	public ResponseEntity<StandardError> entityNotFound(EntityNotFound ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Entity not found!");
		err.setMessage(ex.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(GeoAPIException.class)
	public ResponseEntity<StandardError> geoAPIException(GeoAPIException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Service unavailable!");
		err.setMessage(ex.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Validation exception");
		err.setMessage(ex.getMessage());
		err.setPath(request.getRequestURI());
		
		for (FieldError f : ex.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(err);
	}
}

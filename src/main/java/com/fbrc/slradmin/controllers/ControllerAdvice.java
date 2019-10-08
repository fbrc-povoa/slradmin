package com.fbrc.slradmin.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fbrc.slradmin.exceptions.DMSAException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(DMSAException.class)
	public String globalException(Model model, DMSAException ex) {
		return "DMSANotFound";
	}
}

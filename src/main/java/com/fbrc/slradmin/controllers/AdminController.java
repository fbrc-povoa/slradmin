package com.fbrc.slradmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.fbrc.slradmin.dtos.OptSelected;
import com.fbrc.slradmin.services.CollectionService;

@Controller
public class AdminController {
	
	private CollectionService collectionService;
	
	@Autowired
	public AdminController(CollectionService adminService) {
		this.collectionService = adminService;
	}
	
	@PostMapping("/admin/collection")
	public String admin(Model model, OptSelected opt) {
		
		model.addAttribute("connection_address", collectionService.getCurrentAddress());
		model.addAttribute("collectionName", opt.getValue());
		model.addAttribute("collections", collectionService.getCurrentCollectionsName());
		return "admin";
	}

}

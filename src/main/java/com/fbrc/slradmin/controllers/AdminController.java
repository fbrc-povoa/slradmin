package com.fbrc.slradmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fbrc.slradmin.dtos.CollectionNameSelected;
import com.fbrc.slradmin.dtos.NewCollection;
import com.fbrc.slradmin.services.CollectionService;

@Controller
public class AdminController {
	
	private CollectionService collectionService;
	
	@Autowired
	public AdminController(CollectionService adminService) {
		this.collectionService = adminService;
	}
	
	@PostMapping("/admin/collection")
	public String admin(Model model, CollectionNameSelected opt) {
		
		collectionService.setCurrentCollectionNameAdmin(opt.getValue());
		
		model.addAttribute("connection_address", collectionService.getCurrentAddress());
		model.addAttribute("collectionName", collectionService.getCurrentCollectionNameAdmin());
		model.addAttribute("optSelected", new CollectionNameSelected());
		model.addAttribute("collectionsName", collectionService.getCurrentCollectionsName());
		return "admin";
	}
	
	@GetMapping("/admin/collection/new")
	public String collectionNew(Model model) {
		model.addAttribute("connection_address", collectionService.getCurrentAddress());
		model.addAttribute("collectionName", collectionService.getCurrentCollectionNameAdmin());
		model.addAttribute("optSelected", new CollectionNameSelected());
		model.addAttribute("collectionsName", collectionService.getCurrentCollectionsName());
		model.addAttribute("newCollection", new NewCollection());
		return "/collectionNew";
	}
	
	@GetMapping("/admin/collection/value/add")
	public String add(Model model){
		
		model.addAttribute("fields", collectionService.getCurrentCollectionFields());
		
		model.addAttribute("connection_address", collectionService.getCurrentAddress());
		model.addAttribute("collectionName", collectionService.getCurrentCollectionNameAdmin());
		model.addAttribute("optSelected", new CollectionNameSelected());
		model.addAttribute("collectionsName", collectionService.getCurrentCollectionsName());
		model.addAttribute("newCollection", new NewCollection());
		return "admin-add";
	}
	
}

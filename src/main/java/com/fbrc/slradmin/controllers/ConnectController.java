package com.fbrc.slradmin.controllers;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fbrc.slradmin.dtos.ConnectionDto;
import com.fbrc.slradmin.dtos.CollectionNameSelected;
import com.fbrc.slradmin.services.CollectionService;
import com.fbrc.slradmin.services.ConnectionService;

@Controller
public class ConnectController {
	
	private ConnectionDto dto;
	private ConnectionService connectionService;
	private CollectionService collectionService;
	
	@Autowired
	public ConnectController(ConnectionDto dto, //
			ConnectionService connectionService, //
			CollectionService collectionService ) {
		this.dto=dto;
		this.connectionService=connectionService;
		this.collectionService=collectionService;
	}

	@GetMapping("/connect")
	public String connect(Model model) {
		model.addAttribute("connection", dto);
		return "connect";
	}
	
	@PostMapping("/connect")
	public String connect(Model model, ConnectionDto connection) throws JSONException {
		
		if(connectionService.start(connection)) {
			model.addAttribute("connection_address", connection.getAddress());
			model.addAttribute("collectionsName", collectionService.collectionsName());
			model.addAttribute("optSelected", new CollectionNameSelected());
			return "connected";
		} else {
			model.addAttribute("connection", dto);
			return "connect";
		}
		
	}
}

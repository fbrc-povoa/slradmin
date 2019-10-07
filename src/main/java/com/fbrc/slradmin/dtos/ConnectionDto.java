package com.fbrc.slradmin.dtos;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConnectionDto {
	
	public ConnectionDto() {}

	private String address;
}

package com.cinematheater.dto;

public class LoginResponseDTO {

private String message;
private String token;
private Integer userId;
	public LoginResponseDTO(String message,String token,Integer userId) {
		this.message = message;
		this.token = token;
		this.userId = userId;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getToken() {
		return token;
	}
	
	public Integer getUserId() {
		return userId;
	}
	

}

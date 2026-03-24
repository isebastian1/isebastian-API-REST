package com.chakray.isebastian.models;

import jakarta.validation.constraints.NotNull;

//Clase para el Login
public class Login {
	//Atributos
	@NotNull
	private String username;
	@NotNull
	private String password;
	
	//Constructor	
	public Login(){
		
	}
	public Login(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

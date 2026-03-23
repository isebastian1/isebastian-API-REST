package com.chakray.isebastian.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

//Clase para el Login
public class Login {
	//Atributos
	@JsonProperty("tax_id")
	@NotNull
	private String tax_id;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull
	private String password;
	
	//Constructor
	public Login(){}
	
	public Login(String tax_id, String password) {
		this.setTax_id(tax_id);
		this.setPassword(password);
	}

	public String getTax_id() {
		return tax_id;
	}

	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

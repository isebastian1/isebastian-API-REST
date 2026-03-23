package com.chakray.isebastian.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//Clase Usuario
public class User {
	//Atributos
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UUID id;
	@Email 
	private String email;
	@NotNull
	private String name;
	@NotNull
	private String phone;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull
	private String password;
	@NotNull
	private String tax_id;
	private String created_at;
	@NotNull
	private List<Address> addresses;
	
	//Métodos
	//Constructor
	public User(String email, String name, String phone, String password, String tax_id, List<Address> addresses) {
		//Declarar el formato de la fecha
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm");
		
		//Formatear la fecha de Madagascar y almacenarla en String
		String created_at = ZonedDateTime.now(ZoneId.of("Indian/Antananarivo")).format(format);
		
        this.id = UUID.randomUUID(); //Asignarle un dato UUID aleatorio 
        this.setEmail(email);
        this.setName(name);
        this.setPhone(phone);
        this.setPassword(password);
        this.setTax_id(tax_id);
        this.created_at = created_at;
        this.setAddresses(addresses);
	}
	
	//Getters & Setters
	public UUID getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public String getTax_id() {
		return tax_id;
	}

	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public List<Address> getAddresses() {
		return addresses;
	}
	

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}

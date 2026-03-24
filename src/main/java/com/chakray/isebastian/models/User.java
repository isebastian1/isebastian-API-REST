package com.chakray.isebastian.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

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
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String created_at;
	@NotNull
	private List<Address> addresses;
	
	//Métodos
	//Constructor
	public User(String email, String name, String phone, String password, String tax_id, List<Address> addresses) {
		//Declarar el formato de la fecha
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm");	
		
		//Declarar el parametro para el tax_id usando una expresión regular
		Pattern patternT = Pattern.compile("^[A-Z]{4}[0-9]{6}[A-Z][0-9]{2}$");
		//Declarar un boolean para verificar si el tax_id cumple con la expresión regular
		boolean matcherT = patternT.matcher(tax_id).find();

		//Si no cumple, enviar error
		if(!matcherT) throw new RuntimeException("El formato del tax_id es incorrecto, debe ser AAAA010203A00");
	
		//Declarar el parametro para el phone usando una expresión regular
		Pattern patternP = Pattern.compile("^(\\+[0-9]{2})[0-9]{10}$|^[0-9]{10}$");
		//Declarar un boolean para verificar si el phone cumple con la expresión regular
		boolean matcherP = patternP.matcher(phone).find();
		
		//Si no cumple, enviar error
		if(!matcherP) throw new RuntimeException("El formato del phone es incorrecto, puede ser +121234567890 1234567890");
		
        this.id = UUID.randomUUID(); //Asignarle un dato UUID aleatorio 
        this.setEmail(email);
        this.setName(name);
        this.setPhone(phone);
        this.setPassword(password);
        this.setTax_id(tax_id);
        //Formatear la fecha de Madagascar y almacenarla en String
        this.created_at = ZonedDateTime.now(ZoneId.of("Indian/Antananarivo")).format(format);;
        this.setAddresses(addresses);
	}
	
	public User() {}

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

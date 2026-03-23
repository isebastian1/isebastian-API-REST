package com.chakray.isebastian.models;

import com.fasterxml.jackson.annotation.JsonProperty;

//Clase Domicilio
public class Address {
	//Atributos
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer id;
	private String name;
	private String street;
	private String country_code;
	
	//Métodos
	//Constructor
	public Address(Integer id, String name, String street, String country_code) {
		this.setId(id);
		this.setName(name);
		this.setStreet(street);
		this.setCountry_code(country_code);
	}
	
	//Getters & Setters
	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
}

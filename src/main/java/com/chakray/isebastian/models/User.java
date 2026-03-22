package com.chakray.isebastian.models;

import java.util.UUID;
import java.time.Instant;

//Clase Usuario
public class User {
	//Atributos
	private UUID id;
	private String email;
	private String name;
	private String phone;
	private String password;
	private String tax_id;
	private Instant created_at;
	private Address[] addresses;
	
	//Métodos
	//Constructor
	public User(UUID id, String email, String name, String phone, String password, String tax_id, Address[] addresses) {
        this.setId(id);
        this.setEmail(email);
        this.setName(name);
        this.setPhone(phone);
        this.setPassword(password);
        this.setTax_id(tax_id);
        this.setCreated_at(Instant.now()); //Obtener la fecha y hora actual
        this.setAddresses(addresses);
	}
	
	//Getters & Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	private void setPassword(String password) {
		this.password = password;
	}
	
	//En privado para no mostrarse en el JSON
	private String getPassword() {
		return password;
	}

	public String getTax_id() {
		return tax_id;
	}

	public void setTax_id(String tax_id) {
		this.tax_id = tax_id;
	}

	public Instant getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Instant created_at) {
		this.created_at = created_at;
	}

	public Address[] getAddresses() {
		return addresses;
	}

	public void setAddresses(Address[] addresses) {
		this.addresses = addresses;
	}
}

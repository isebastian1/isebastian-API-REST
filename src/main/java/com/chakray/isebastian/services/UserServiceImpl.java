package com.chakray.isebastian.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chakray.isebastian.interfaces.UserService;
import com.chakray.isebastian.models.FilterAttribute;
import com.chakray.isebastian.models.User;

import jakarta.annotation.PostConstruct;

//Servicio de usuarios, implementación
@Service
public class UserServiceImpl implements UserService {
	
	//Declarar el Array de objetos de la clase Usuario
	private final ArrayList<User> users = new ArrayList<>();

	//Método para llenar el Array
	@PostConstruct //Se ejecutará después de iniciar el programa
	private void fillUsers() {
		users.add(new User(UUID.randomUUID(), "zuser@email.com", "Mateo", "44555", "contraseña", "AMARR", null));
		users.add(new User(UUID.randomUUID(), "auser@email.com", "Juan", "77555", "contraseña", "AJRR", null));
		users.add(new User(UUID.randomUUID(), "uuser@email.com", "Pedro", "11555", "contraseña", "EPRR", null));
	}
	
	//Método para obtener todos los usuarios
	@Override
	public List<User> getUsers() {
		return users;
	}

	//Método para obtener todos los usuarios ordenados
	@Override
	public List<User> getUsersSortedBy(FilterAttribute sortedBy) {
		switch(sortedBy) {
			case created_at -> users.sort(Comparator.comparing(User::getCreated_at));
			case email -> users.sort(Comparator.comparing(User::getEmail));
			case id -> users.sort(Comparator.comparing(User::getId));
			case name -> users.sort(Comparator.comparing(User::getName));
			case phone -> users.sort(Comparator.comparing(User::getPhone));
			case tax_id -> users.sort(Comparator.comparing(User::getTax_id));
			default -> getUsers();
		}
		return users;
	}
}

package com.chakray.isebastian.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chakray.isebastian.interfaces.UserService;
import com.chakray.isebastian.models.FilterAttribute;
import com.chakray.isebastian.models.FilterOperator;
import com.chakray.isebastian.models.User;

import jakarta.annotation.PostConstruct;

//Servicio de usuarios, implementación
@Service
public class UserServiceImpl implements UserService {
	
	//Declarar el Array de objetos de la clase Usuario
	private final List<User> users = new ArrayList<>();

	//Método para llenar el Array
	@PostConstruct //Se ejecutará después de iniciar el programa
	private void fillUsers() {
		users.add(new User("zuser@gmail.com", "Mateo Rodriguez", "44555", "contraseña", "AMARR", null));
		users.add(new User("auser@email.com", "Juan Hernandez", "77555", "contraseña", "AJRR", null));
		users.add(new User("uuser@yahoo.com", "Pedro García", "11555", "contraseña", "EPRR", null));
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
			default -> {
				return users;
			}
		}
		return users;
	}
	
	//Método para obtener todos los usuarios filtrados
	@Override
	public List<User> getUsersFilter(String filter, String operator, String value) {		
		//Convertir el String Operator en la constante del Enum Class
		FilterOperator operatorAux = FilterOperator.valueOf(operator);
		
		//Switch del Enum class (FilterOperator) para obtener los usuarios que cumplan con el filtro
		return switch(operatorAux) {
			//Se invoca un stream para aplicar el filter después se elige el atributo llamando a la función
			//selectAttribute, posteriormente se elige el operador y se le envía el parametro value,
			//finalmente se convierte el stream a una lista.
			case co -> users.stream().filter(user -> selectAttribute(filter, user).contains(value)).toList();
			case eq -> users.stream().filter(user -> selectAttribute(filter, user).equals(value)).toList();
			case ew -> users.stream().filter(user -> selectAttribute(filter, user).endsWith(value)).toList();
			case sw -> users.stream().filter(user -> selectAttribute(filter, user).startsWith(value)).toList();		
			default -> Collections.emptyList();
		};
	}
	
	//Función para elegir el atributo de acuerdo al parametro Filter recibido
	public String selectAttribute(String filter, User user) {
		//Convertir el String Operator en la constante del Enum Class
		FilterAttribute filterAux = FilterAttribute.valueOf(filter);
		
		//Switch del Enum class (FilterAttribute) para obtener el atributo de la clase para el filtro
		//Se retorna el valor obtenido
		return switch(filterAux) {
			case created_at -> user.getCreated_at(); //Por ejemplo, si el Filter es created_at se obtiene su getter
			case email -> user.getEmail();
			case id -> user.getId().toString();
			case name -> user.getName();
			case phone -> user.getPhone();
			case tax_id -> user.getTax_id();
			default -> "";
		};
	}

	@Override
	public User createUser(User user) {
		//Crear un nuevo objeto con los atributos recibidos (se instancia la clase para validaciones)
		User userAux = new User(user.getEmail(), user.getName(), user.getPhone(), user.getPassword(), 
				user.getTax_id(), user.getAddresses());
		//Añadir el usuario al Array
		users.add(userAux);
		
		//Retornar el último elemento añadido al arreglo
		return users.getLast();
	}
	
	
}

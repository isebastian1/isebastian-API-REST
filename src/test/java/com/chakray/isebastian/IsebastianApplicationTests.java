package com.chakray.isebastian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.chakray.isebastian.interfaces.UserService;
import com.chakray.isebastian.models.Address;
import com.chakray.isebastian.models.FilterAttribute;
import com.chakray.isebastian.models.Login;
import com.chakray.isebastian.models.User;

@SpringBootTest
class IsebastianApplicationTests {
	@Autowired
	UserService userServ;

	@Test
	void contextLoads() {
	}
	
	//Test para comprobar que si se hace el guardado de nuevos usuarios
	@Test
	void createUser() {
		//Declarar un objeto de la clase address y guardarlo en una lista
		Address address = new Address(0, "Home", "Nothing Street", "SP");
		List<Address> addressList =  new ArrayList<>();
		addressList.add(address);
		
		//Crear un objeto de la clase User con atributos
		User user = new User("alice@gmail.com", "Alice", "+342211478952", "Nad4_queda", "JEAN001085D40", addressList);
		
		//Invocar el método para crear nuevos usuarios
		User newUserRegistred = userServ.createUser(user);
		
		//Verificar que el resultado del servicio no sea nulo
		assertNotNull(newUserRegistred);
		//Verificar que los parametros se hayan guardado
		assertEquals(user.getName(), newUserRegistred.getName());
		assertEquals(user.getTax_id(), newUserRegistred.getTax_id());
	}

	//Test para comprobar que si se ordenan los usuarios
	@Test
	void getUsersSortedBy(){	
		//Invocar el método para obtener usuarios ordenados de acuerdo al parametro sortedBy
		List<User> users = userServ.getUsersSortedBy(FilterAttribute.name);
		
		//Verificar que el primer nombre sea Harry
		assertEquals("Harry", users.getFirst().getName());
	}
	
	//Test para comprobar que si se filtran los usuarios
	@Test
	void getUsersFilter() {		
		//Invocar el método para obtener usuarios filtrados por atributos
		List<User> users = userServ.getUsersFilter("name", "sw","J");
		
		//Verificar que el filtro haya funcionado
		assertEquals("Juan", users.getFirst().getName());
	}
	
	//Test para comprobar que si se actualizan los usuarios
	@Test
	void updateUser() {
		//Buscar en el servicio el primer usuario devuelto para editarlo
		User userToUpdate = userServ.getUsers().getFirst();
		
		//Verificar que el resultado no sea nulo
		assertNotNull(userToUpdate);
		
		//Crear un objeto de la clase User con atributos
		User user = new User("alice@gmail.com", "Alice", "+342211478952", "Nad4_queda", "JEAN001085D45", Collections.emptyList());
		//Invocar el método para actualizar usuarios
		User updatedUser = userServ.updateUser(user,userToUpdate.getId().toString());
		
		//Verificar que el resultado del servicio no sea nulo
		assertNotNull(updatedUser);
		//Comparar el nombre modificado
		assertEquals("Alice", updatedUser.getName());
	}
	
	//Test para comprobar que si se eliminan los usuarios
	@Test
	void deleteUser() {
		//Obtener el tamaño inicial del arreglo
		int initialSize = userServ.getUsers().size();
		
		//Buscar en el servicio el primer usuario devuelto para eliminarlo
		User userToDelete = userServ.getUsers().getFirst();
		
		//Verificar que el resultado no sea nulo
		assertNotNull(userToDelete);
		
		//Invocar el método para eliminar usuarios
		userServ.deleteUser(userToDelete.getId().toString());
		
		//Obtener el tamaño final del arreglo
		int finalSize = userServ.getUsers().size();
		
		//Verificar que el tamaño haya cambiado
		assertEquals(initialSize-1, finalSize);
	}
	
	@Test
	void login() {
		//Crear una instancia de la clase Objeto, usando los datos de Simón
		Login loginData = new Login("PESI200100J30", "C0ntr4s3n4");	
		
		//Invocar el método del Login
		Boolean success = userServ.loginValidation(loginData);
		
		//Verificar que el login haya sido exitoso (true)
		assertEquals(true, success);
	}
}

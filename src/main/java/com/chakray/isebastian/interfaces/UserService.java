package com.chakray.isebastian.interfaces;

import java.util.List;

import com.chakray.isebastian.models.FilterAttribute;
import com.chakray.isebastian.models.Login;
import com.chakray.isebastian.models.User;

//Interface para el Servicio de Usuarios
public interface UserService {
	//Obtener todos los usuarios
	public List<User> getUsers();
	
	//Obtener usuarios ordenados de acuerdo al atributo sortedBy
	public List<User> getUsersSortedBy(FilterAttribute sortedBy);
	
	//Obtener usuarios filtrados de acuerdo al atributo Filter
	public List<User> getUsersFilter(String filter, String operator, String value);

	//Crear un nuevo usuario
	public User createUser(User user);
	
	//Modificar un usuario de acuerdo con el ID recibido
	public User updateUser(User user, String id);
	
	//Eliminar el usuario del ID recibido
	public void deleteUser(String id);
	
	//Login
	public boolean loginValidation(Login loginData);
}

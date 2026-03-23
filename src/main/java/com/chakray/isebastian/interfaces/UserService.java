package com.chakray.isebastian.interfaces;

import java.util.List;

import com.chakray.isebastian.models.FilterAttribute;
import com.chakray.isebastian.models.User;

//Interface para el Servicio de Usuarios
public interface UserService {
	//Obtener todos los usuarios
	public List<User> getUsers();
	
	//Obtener usuarios ordenados de acuerdo al atributo sortedBy
	public List<User> getUsersSortedBy(FilterAttribute sortedBy);
}

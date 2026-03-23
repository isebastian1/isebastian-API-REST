package com.chakray.isebastian.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chakray.isebastian.interfaces.UserService;
import com.chakray.isebastian.models.FilterAttribute;
import com.chakray.isebastian.models.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

//Controlador
@RestController
@CrossOrigin
public class UserController {
	//Inyectar servicio
	@Autowired
    private UserService userSrv;
		
	//Endpoint para obtener todos los usuarios y obtenerlos ordenados usando el parametro sortedBy
	@Tag(name = "Users Sorted By")
	@Operation(summary = "Get All User Sorted By Specific Attribute",
	description = "Return a list of users stored in the array sorted by the attribute in the query parameter sortedBy")
	@GetMapping(name = "/users", params = "!filter")
	public ResponseEntity<List<User>> getUsers(@RequestParam (required = false) FilterAttribute sortedBy){
		//Devolver un Response OK y enviar el parametro sortedBy
		if(sortedBy != null) return ResponseEntity.ok(userSrv.getUsersSortedBy(sortedBy));
		//Devolver un Response OK y traer todos los usuarios
		else return ResponseEntity.ok(userSrv.getUsers());
    }
	
	//Endpoint para filtrar los Usuarios con el parametro Filter
	@Tag(name = "Users Filter")
	@Operation(summary = "Get All User Filter By Specific Attribute",
	description = "Return a list of users stored in the array filtered by the attribute in the query parameter filter")
	@GetMapping("/users")
	public ResponseEntity<?> getUsersFilter(@Parameter(example = "name+co+z") @RequestParam String filter){
    	if(filter != null) {
    		//Devolver un Response OK
    		try {
    			//Separar el string recibido usando los signos (+)
        		String[] fil = filter.split("\\+");
        		
        		//Devolver un Response Bad Request si el arreglo tiene mas de 3 elementos
        		if (fil.length > 3) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        				.body("{\"error\": \"El parámetro Filter esta escrito incorrectamente\"}");
        		//Devolver un Response OK y traer todos los usuarios que cumplan el filtro
        		else return ResponseEntity.ok(userSrv.getUsersFilter(fil [0], fil[1], fil[2]));
    		}catch(Exception e) {
    			//Devolver un Response Bad Request si hubo errores
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        				.body("{\"error\": \"Ocurrió un error al filtrar los Usuarios\"}");
    		}    		
    	} else {
    		//Devolver un Response Bad Request
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("{\"error\": \"El parametro filter es nulo o esta vacío\"}");
    	}
    }
	
	//Endpoint para crear nuevos Usuarios
	@Tag(name = "Create User")
	@Operation(summary = "Create a new User", description = "Store a new user in the array")
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user){
		try {
			//Devolver un Response OK y mostrar el usuario nuevo
			return ResponseEntity.ok(userSrv.createUser(user));
		}catch(Exception e) {
			//Devolver un Response Bad Request si hubo errores
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("{\"error\": \"Ocurrió un error al guardar el Usuario\"}");
		}   
	}
	
	//Endpoint para modificar Usuarios mediante el campo ID
	@Tag(name = "Update User")
	@Operation(summary = "Update a User Attribute or Attributes by ID", description = "Update an user attribute by Id")
	@PatchMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user, @PathVariable String id){
		try {
			//Devolver un Response OK y mostrar el usuario modificado
			return ResponseEntity.ok(userSrv.updateUser(user, id));
		}catch(Exception e) {
			//Devolver un Response Bad Request si hubo errores
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("{\"error\": \"No existe un usuario con este ID\"}");
		}   
	}
	
	//Endpoint para eliminar Usuarios mediante el campo ID
	@Tag(name = "Delete User")
	@Operation(summary = "Delete a User by ID", description = "Remove an user from the array by Id")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id){
		try {
			//Devolver un Response Vacio
			userSrv.deleteUser(id);
			return ResponseEntity.noContent().build();
		}catch(Exception e) {
			//Devolver un Response Bad Request si hubo errores
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body("{\"error\": \"Ocurrió un error al eliminar el Usuario\"}");
		}   
	}
}

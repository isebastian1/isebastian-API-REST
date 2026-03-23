package com.chakray.isebastian.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chakray.isebastian.interfaces.UserService;
import com.chakray.isebastian.models.FilterAttribute;
import com.chakray.isebastian.models.User;

//Controlador
@RestController
@CrossOrigin
public class UserController {
	//Inyectar servicio
	@Autowired
    private UserService userSrv;
		
	//Endpoint para obtener todos los usuarios
    @GetMapping("/users")
	public ResponseEntity<List<User>> users(@RequestParam (required = false) FilterAttribute sortedBy){
    	if(sortedBy != null) {
    		//Devolver un Response OK
    		return ResponseEntity.ok(userSrv.getUsersSortedBy(sortedBy));
    	} else {
    		//Devolver un Response OK
    		return ResponseEntity.ok(userSrv.getUsers());
    	}
    }
}

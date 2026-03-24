package com.chakray.isebastian.controllers;

import java.lang.ModuleLayer.Controller;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.chakray.isebastian.interfaces.UserService;
import com.chakray.isebastian.models.Login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

//Controlador Login
@RestController
@CrossOrigin
public class LoginController {
	//Inyectar servicio
	@Autowired
	private UserService userSrv;
	
	//Declarar una variable map para los errores
	private Map<String, String> error = new HashMap<>();
	
	//Logger
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	//Endpoint para obtener todos los usuarios y obtenerlos ordenados usando el parametro sortedBy
	@Tag(name = "Login")
	@Operation(summary = "Login with TaxID and Password", description = "for user authentication.")
	@PostMapping("/login")
	public ResponseEntity<?> loginAuth(@Valid @RequestBody (required = true) Login loginData){
		log.info("Se accedió al Endpoint - Login");
		try {
			//Devolver Response Entity Ok si los datos son correctos
			Boolean success = userSrv.loginValidation(loginData);
			
			if (success) return ResponseEntity.ok(success);
			else {
				error.put("error", "Contraseña incorrecta");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	    				.body(error);
			}
		}catch(Exception e) {
			//En caso de error, devolver Response Entity BadRequest con el error
			error.put("error", "Error en el Login: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(error);
		}	
    }
}

package com.chakray.isebastian.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.chakray.isebastian.interfaces.EncryptorService;

import jakarta.annotation.PostConstruct;

@Service
public class EncryptorServiceImpl implements EncryptorService {
	//Declarar llave secreta para ser usada
	private static String secretKey = "";
	
	@PostConstruct //Se ejecuta al iniciar el programa
	private void generateSecretKey() {
		//Generar una llave usando Secure Random 32 byte = 256 bits
		byte[] key = new byte[32]; 
		new SecureRandom().nextBytes(key);
		//Asignar valor a la llave secreta
		secretKey = Base64.getEncoder().encodeToString(key);
	}

	//Método para obtener la llave secreta
	private SecretKey getSecretKey() {
		//Decodificar los caracteres
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		//Retornar objeto SecretKeySpec, recibe los parametros llave secreta y el método AES
		return new SecretKeySpec(decodedKey, "AES");
	}
	
	//Método para encriptar las contraseñas
	@Override
	public String encrypt(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//Declarar objeto de la clase Cipher
		Cipher cipher = Cipher.getInstance("AES");
		//Inicializar el objeto con la llave secreta, en el modo encriptar
		cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
		//Encriptar la contraseña y guardarla en una nueva variable
		byte[] encryptedPassword = cipher.doFinal(password.getBytes());
		//Retornar un String con la contraseña codificada
		return Base64.getEncoder().encodeToString(encryptedPassword);
	}
	
	//Método para desencriptar las contraseñas
	@Override
	public String decrypt(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//Declarar objeto de la clase Cipher
		Cipher cipher = Cipher.getInstance("AES");
		//Inicializar el objeto con la llave secreta, en el modo encriptar
		cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
		//Decodificar la contraseña
		byte[] decodedPasswoord = Base64.getDecoder().decode(password);
		//Desencriptar la contraseña que antes fue decodificada
		byte[] decryptedPassword = cipher.doFinal(decodedPasswoord);
		//Retornar un String con la contraseña codificada
		return decryptedPassword.toString();
	}
}

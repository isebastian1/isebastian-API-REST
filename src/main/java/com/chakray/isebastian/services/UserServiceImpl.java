package com.chakray.isebastian.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chakray.isebastian.interfaces.EncryptorService;
import com.chakray.isebastian.interfaces.UserService;
import com.chakray.isebastian.models.Address;
import com.chakray.isebastian.models.FilterAttribute;
import com.chakray.isebastian.models.FilterOperator;
import com.chakray.isebastian.models.Login;
import com.chakray.isebastian.models.User;

import jakarta.annotation.PostConstruct;

//Servicio de usuarios, implementación
@Service
public class UserServiceImpl implements UserService {
	
	//Declarar el Array de objetos de la clase Usuario
	private final List<User> users = new ArrayList<>();
	
	@Autowired
	private EncryptorService encryptorSrv;

	//Método para llenar el Array
	@PostConstruct //Se ejecutará después de iniciar el programa
	private void fillUsers() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		List<Address> genericAddress = new ArrayList<>();
		genericAddress.add(new Address(genericAddress.size()+1, "Home", "Munson Street", "MX"));
		genericAddress.add(new Address(genericAddress.size()+1, "Job", "Sanders Street", "MX"));
		
		users.add(new User("zuser@gmail.com", "Harry", "44555", encryptorSrv.encrypt("contraseña"), "AMARR", genericAddress));
		users.add(new User("auser@email.com", "Juan", "77555", encryptorSrv.encrypt("H0l4Mund0"), "AJRR", genericAddress));
		users.add(new User("uuser@yahoo.com", "Simón", "11555", encryptorSrv.encrypt("C0ntr4s3n4"), "EPRR", genericAddress));
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
			default -> throw new RuntimeException("Atributo incorrecto");
		}
		return users;
	}
	
	//Método para obtener todos los usuarios filtrados
	@Override
	public List<User> getUsersFilter(String filter, String operator, String value) {		
		//Declarar variable del Enum Class
		FilterOperator operatorAux;
		
		//Convertir el String Operator en la constante del Enum Class
		try {
			operatorAux = FilterOperator.valueOf(operator);
		}catch(Exception e) {
			throw new RuntimeException("Operador incorrecto, debería ser co, eq, ew, sw");
		}
		
		//Switch del Enum class (FilterOperator) para obtener los usuarios que cumplan con el filtro
		return switch(operatorAux) {
			//Se invoca un stream para aplicar el filter después se elige el atributo llamando a la función
			//selectAttribute, posteriormente se elige el operador y se le envía el parametro value,
			//finalmente se convierte el stream a una lista.
			case co -> users.stream().filter(user -> selectAttribute(filter, user).contains(value)).toList();
			case eq -> users.stream().filter(user -> selectAttribute(filter, user).equals(value)).toList();
			case ew -> users.stream().filter(user -> selectAttribute(filter, user).endsWith(value)).toList();
			case sw -> users.stream().filter(user -> selectAttribute(filter, user).startsWith(value)).toList();		
			default -> throw new RuntimeException("Operador incorrecto");
		};
	}
	
	//Función para elegir el atributo de acuerdo al parametro Filter recibido
	public String selectAttribute(String filter, User user) {
		//Declarar variable del Enum Class
		FilterAttribute filterAux;
		
		//Convertir el String Operator en la constante del Enum Class
		try {
			filterAux = FilterAttribute.valueOf(filter);
		}catch(Exception e) {
			//En caso de error, retornar mensaje
			throw new RuntimeException("Atributo incorrecto, debería ser created_at, email, id, name, phone, tax_id");
		}
		
		//Switch del Enum class (FilterAttribute) para obtener el atributo de la clase para el filtro
		//Se retorna el valor obtenido
		return switch(filterAux) {
			case created_at -> user.getCreated_at(); //Por ejemplo, si el Filter es created_at se obtiene su getter
			case email -> user.getEmail();
			case id -> user.getId().toString();
			case name -> user.getName();
			case phone -> user.getPhone();
			case tax_id -> user.getTax_id();
		};
				
	}

	//Método para crear nuevos usuarios
	@Override
	public User createUser(User user) {
		//Crear un nuevo objeto con los atributos recibidos (se instancia la clase para validaciones)
		//Se llama el método para llenar la lista de domicilios, para asignar un ID incrementable
		try {
			User userAux = new User(user.getEmail(), user.getName(), user.getPhone(), encryptorSrv.encrypt(user.getPassword()), 
					user.getTax_id(), fillAddressList(user.getAddresses()));
			//Añadir el usuario al Array
			users.add(userAux);
		}catch(Exception e) {
			//En caso de error, retornar mensaje
			throw new RuntimeException("Error al almacenar la contraseña");
		}
		
		//Retornar el último elemento añadido al arreglo
		return users.getLast();
	}
	
	//Función para llenar la lista de domicilios
	public List<Address> fillAddressList(List<Address> addressList){
		
		//Instanciar una nueva lista
		List<Address> newAddress = new ArrayList<>();
		
		//Recorrer la lista recibida en el parametro y asignar sus valores
		addressList.forEach(address ->
			newAddress.add( //Asignar un ID incrementable
					new Address(newAddress.size()+1, address.getName(), address.getStreet(), address.getCountry_code())
					)
		);
		
		return newAddress;
	}

	//Método para actualizar los datos de un usuario
	@Override
	public User updateUser(User user, String id) {
		//Se convierte el ID recibido en UUID
		UUID idAux = UUID.fromString(id);
		//Mediante stream se busca el User que tenga el mismo ID, si no se devuelve error
		User update = users.stream().filter(u -> u.getId().equals(idAux)).findFirst().orElseThrow();
				
		//Actualización de los campos
		update.setEmail(user.getEmail());
		update.setName(user.getName());
		update.setPhone(user.getPhone());
		update.setTax_id(user.getTax_id());	
		update.setAddresses(user.getAddresses());
		
		//Si la contraseña no esta vacía o no es nula, se actualiza
		if(!user.getPassword().isBlank() || !user.getPassword().equals(null)) {
			try {
				//Enviar contraseña modificada para encriptarla
				String encryptPassword = encryptorSrv.encrypt(user.getPassword());
				//Actualizar el atributo con la contraseña encriptada
				update.setPassword(encryptPassword);
			}catch(Exception e) {
				throw new RuntimeException("Error al encriptar contraseña");
			}
		}
				
		//Retornar el objeto Usuario actualizado
		return update;
	}

	@Override
	public void deleteUser(String id) {
		//Se convierte el ID recibido en UUID
		UUID idAux = UUID.fromString(id);
		//Mediante stream se busca el User que tenga el mismo ID, si no se devuelve error
		User delete = users.stream().filter(u -> u.getId().equals(idAux)).findFirst().orElseThrow();
		
		//Eliminar el usuario
		users.remove(delete);
	}
	
	
	@Override
	public boolean loginValidation(Login loginData){		
		User loger = users.stream().filter(u -> u.getTax_id().equals(loginData.getTax_id())).findFirst().orElseThrow();
		System.out.print(loger.getName());
		try {
			String correctPassword = encryptorSrv.decrypt(loger.getPassword());
			return correctPassword.equals(loginData.getPassword());
		}catch(Exception e) {
			return false;
		}		
	}
}

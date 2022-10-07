package dominio.webapp.controller;

import java.util.List;

import dominio.repository.RepoUsuarios;
import dominio.usuario.Usuario;

public class AdminController {

	 private static AdminController instance = null;
	    
	 private AdminController(){

	    }
	    
	 public static AdminController getInstance() {
		 if (instance == null) {
	    	instance = new AdminController();
	    }
	    	return instance;
	 }
	 
	 public List<Usuario> getInfoUser(String nombreUser) {
		 //Esto es vulnerable por SQL Injection, con solo poner 
		 // "' or 1=1 or login LIKE '"
		 List<Usuario> users = RepoUsuarios.getInstance().getUsuariosConCondicion(nombreUser);
		 return users;
	 }
}

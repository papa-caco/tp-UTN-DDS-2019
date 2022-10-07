package dominio.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dominio.repository.RepoUsuarios;
import dominio.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

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
	 
	 public static ModelAndView show(Request req, Response res){
		if(req.session().attribute("username")!=null) {
				res.redirect("/login-volver");
				return null;
		}
		return new ModelAndView(null, "panel-admin.html");
	}
	 
	public static ModelAndView volverHome(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();
		res.redirect("/usuario");
		return new ModelAndView(model, "/usuarioInicio.hbs");
	}
	
	 public static ModelAndView getInfoUser(Request req, Response res) {
		 //Esto es vulnerable por SQL Injection, con solo poner 
		 // "' or 1=1 or login LIKE '"
		 String nombreUser = req.queryParams("nombreUser");
		 Map<String, Object> model = new HashMap<>();
		 if(nombreUser==null) {
			return new ModelAndView(model, "panel-admin.html");
		 }
		 List<Usuario> users = RepoUsuarios.getInstance().getUsuariosConCondicion(nombreUser);
		 model.put("users", users);
		return new ModelAndView(model, "panel-admin.html");
	 }
}

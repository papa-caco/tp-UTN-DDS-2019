package dominio.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import dominio.repository.*;
import dominio.usuario.*;

public class LoginController {

	public static ModelAndView show(Request req, Response res){
		if(req.session().attribute("username")!=null) {
				res.redirect("/usuario");
				return null;
		}
		return new ModelAndView(null, "home/login.html");
	}
	
	public static ModelAndView mostrarPreguntaSeguridad(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();
		return new ModelAndView(model, "home/preguntaDeSeguridad.html");
	}
	
	public static ModelAndView confirmarPreguntaSeguridad(Request req, Response res) {
		String user = req.queryParams("usuarioID");
		String pregSeguridad = req.queryParams("pregSeguridad");
		
		Usuario currentUser = RepoUsuarios.getInstance().buscarPorLogin(user);
		Map<String, Object> model = new HashMap<>();
		if(currentUser!= null && currentUser.getPregSeguridad().equals(pregSeguridad)) {
			//PASAMOS A CAMBIAR SU CONTRASEÑA
			res.cookie("user", user);
			return new ModelAndView(model, "home/cambiarContrasenia.html");
		}
		//ERROR, NO ES LA MISMA PREGUNTA DE SEGURIDAD
		return new ModelAndView(model, "home/errorPreguntaSeguridad.hbs");
	}
	
	public static ModelAndView cambiarPassword(Request req, Response res) {
		String user = req.cookie("user");
		String password1 = req.queryParams("password1");
		String password2 = req.queryParams("password2");
		Map<String, Object> model = new HashMap<>();
		Usuario currentUser = RepoUsuarios.getInstance().buscarPorLogin(user);
		if(currentUser!= null && password1.equals(password2)) {
			currentUser.setPassword(password2);
			RepoUsuarios.getInstance().actualizarUsuario(currentUser);
			
			return new ModelAndView(model, "home/exitoCambiarContrasenia.hbs");
		}
	
		//ERROR, NO ES LA MISMA PREGUNTA DE SEGURIDAD
		return new ModelAndView(model, "home/errorCambiarContrasenia.hbs");
	}
	
	
	
	
	public static ModelAndView login(Request req, Response res) {
		String login = req.queryParams("usuarioID");
		String pass = req.queryParams("password");
		Usuario currentUser = RepoUsuarios.getInstance().buscarPorLogin(login);

		if(currentUser == null) {
			System.out.println("Usuario y/o contrasena existente: " + req.queryParams("usuarioID").toString());
			return new ModelAndView(null, "home/errorSesion.hbs");
		}

		if( ValidadorSesion.validarSesionUsuario(currentUser, login, pass)) {
			req.session().attribute("user", currentUser);
			req.session().attribute("currPageListPrendas", 0);
			req.session().attribute("currPageListPrendasPlacard", 0);
			req.session().attribute("currentPageEventos", 0);
			req.session().attribute("currentPageProximos", 0);
			req.session().attribute("currentPageHistoricos", 0);
			req.session().attribute("currentPageCalificados", 0);
			req.session().attribute("currentPageSugerencias", 0);
			Map<String, Object> model = new HashMap<>();
		//	res.redirect("/usuario");
			if(currentUser.getEsAdmin().equals(0)) {
				return new ModelAndView(model, "/usuario.html");
			} 
			return new ModelAndView(model, "/usuarioAdmin.html");
		}
		return new ModelAndView(currentUser, "home/errorSesion.hbs");
	}
	
	public static ModelAndView salir(Request req, Response res) {
		req.session().removeAttribute("user");
		res.redirect("/");
		return null;
	}
	
}	



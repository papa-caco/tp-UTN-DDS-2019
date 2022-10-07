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
			res.redirect("/usuario");
			return new ModelAndView(model, "/usuarioInicio.hbs");
		}
		return new ModelAndView(currentUser, "home/errorSesion.hbs");
	}
	
	public static ModelAndView salir(Request req, Response res) {
		req.session().removeAttribute("user");
		res.redirect("/");
		return null;
	}
	
}	



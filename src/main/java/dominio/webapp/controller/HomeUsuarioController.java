package dominio.webapp.controller;

import java.util.HashMap;
//import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import dominio.excepciones.*;
import dominio.placard.Placard;
import dominio.prenda.*;
import dominio.repository.*;
import dominio.usuario.Usuario;
import dominio.webapp.request.RequestPrenda;

public class HomeUsuarioController {
	
    private static HomeUsuarioController instance = null;
    
    private HomeUsuarioController(){

    }
    
    public static HomeUsuarioController getInstance() {
    	if (instance == null) {
    		instance = new HomeUsuarioController();
    	}
    		return instance;
    }
	
    public ModelAndView show(Request req, Response res){
    	Usuario currentUser = (Usuario)req.session().attribute("user");
    	
    	if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("userName", currentUser.getUserName());
		model.put("login", currentUser.getLogin());
		return new ModelAndView(model, "/usuarioInicio.hbs");
	}
    
    public ModelAndView showAdmin(Request req, Response res){
    	Usuario currentUser = (Usuario)req.session().attribute("user");
    	
    	if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("userName", currentUser.getUserName());
		model.put("login", currentUser.getLogin());
		return new ModelAndView(model, "/usuarioInicioAdmin.hbs");
	}

	public ModelAndView listPlacards(Request request, Response response) {
		Usuario currentUser = (Usuario)request.session().attribute("user");
		
		if (currentUser == null) {
			response.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		List<Placard> placardsUsuario = currentUser.getPlacards();
		Map<String, Object> model = new HashMap<>();
		model.put("userName", currentUser.getUserName());
		model.put("placards", placardsUsuario);
		return new ModelAndView(model, "/placards.hbs");
	}
	
	public ModelAndView mostrarAltaPrenda(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user");
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		Map<String, Object> model = new HashMap<>();
		model.put("userName",currentUser.getUserName());
		model.put("placardsUsuario", currentUser.getPlacards());
		model.put("categoriasTipos", RepoCategorias.getInstance().getCategorias());
		model.put("usosPrendas", RepoUsoPrendas.getInstance().getUsos());
		model.put("tiposPrendas", RepoTipos.getInstance().getTipos());
		model.put("telasPrendas", RepoTelas.getInstance().getTelas());
		model.put("colorPrendas", RepoColores.getInstance().getColores());
		return new ModelAndView(model, "/formAltaPrenda.html");
	}
	
	public ModelAndView altaPrenda(Request request, Response response) throws RoperoFullException, SinPrendasException {
		Usuario currentUser = (Usuario)request.session().attribute("user");
		Prenda prendita = RepoPrendas.getInstance().buscoPrendaNombre(request.queryParams("nombre").toString());
		if (prendita != null) {
			Map<String, String> model = new HashMap<>();
			model.put("message", "Prenda ya existente, intente con otro nombre");
			response.status(200);
			return new ModelAndView(model, "/cargaPrendaError.hbs");
		}
		try {
			RequestPrenda prendaReq = new RequestPrenda();
			prendaReq.setNombre(request.queryParams("nombre").toString());  
			prendaReq.setTipo(request.queryParams("tipo").toString());
			prendaReq.setCategoria(request.queryParams("categoria").toString());
			prendaReq.setUsoPrenda(request.queryParams("usoPrenda").toString());
			prendaReq.setTela(request.queryParams("material").toString());
			prendaReq.setColorBase(request.queryParams("deColor1").toString());
			prendaReq.setColorSecundario(request.queryParams("deColor").toString());
			prendita = prendaReq.getPrendaFromThis();
			System.out.println("Total Prendas actual : " + RepoPrendas.getInstance().getPrendas().size());
			RepoPrendas.getInstance().agregarPrenda(prendita);
			Placard placard = RepoPlacards.getInstance().buscoPlacardNombre(request.queryParams("pnombre").toString());
			placard.addPrenda(prendita, currentUser);
			RepoPlacards.getInstance().actualizarPlacard(placard);
			if ((! prendita.esPrendaComplementaria()) && (! RepoSugerencias.getInstance().contieneSugerenciaConPrendaDePlacard(placard.getIdPlacard()))) {
				System.out.println(RepoAtuendos.getInstance().buscoAtuendosPlacard(placard).size() + " Atuendos para borrar");
				RepoAtuendos.getInstance().eliminarAtuendosPlacard(placard);
				RepoAtuendos.getInstance().agregarAtuendosPlacard(placard);
				System.out.println(placard.getPrendas().size() + " Prendas en " + placard.getNombre());
				System.out.println(RepoAtuendos.getInstance().buscoAtuendosPlacard(placard).size() + " Atuendos nuevos generados de " + placard.getNombre());
			}
			System.out.println("Nuevo total prendas: " + RepoPrendas.getInstance().getPrendas().size());
			response.status(201);
			return new ModelAndView(null, "/cargaPrendaOK.hbs");
		} catch (RoperoFullException e) {
			Map<String, String> model = new HashMap<>();
			model.put("message", "Hubo un error al cargar la  prenda");
			response.status(200);
			return new ModelAndView(model, "/cargaPrendaError.hbs");
		}
	}
}

package dominio.webapp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import dominio.eventos.Planificador;
import dominio.placard.*;
import dominio.prenda.Prenda;
import dominio.repository.*;
import dominio.usuario.Usuario;
import dominio.webapp.request.*;


public class SugerenciasController {
	
    private static SugerenciasController instance = null;
    
    private SugerenciasController() {

    }
    
    public static SugerenciasController getInstance() {
    	if (instance == null) {
    		instance = new SugerenciasController();
    	}
    		return instance;
    }

	public ModelAndView mostrarAccionesSugerencia(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		Integer idSugerencia = Integer.parseInt(req.params("idSugerencia"));
		Integer  idEventoActual = Integer.parseInt(req.queryParams("idEvt"));
		Map<String, Object> model = new HashMap<>();
		Sugerencia sugerencia = RepoSugerencias.getInstance().buscoSugerenciaPorId(idSugerencia);
		String estadoSugerencia = sugerencia.getEstado().name();
		double caloriasAtuendo = sugerencia.getAtuendo().caloriasBasicas();
		List<Prenda> prendasAtuendo = sugerencia.getAtuendo().getPrendasAtuendo();
		List<RequestPrenda> reqPrendasAtuendo = prendasAtuendo.stream().map(prenda -> new RequestPrenda(prenda)).collect(Collectors.toList());
		model.put("usuarioActual",currentUser.getUserName());
		model.put("idEvento", idEventoActual);
		model.put("idSugerencia", idSugerencia);
		model.put("estado", estadoSugerencia);
		model.put("calorias", caloriasAtuendo);
		model.put("dateTimeActual", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		model.put("prendasAtuendo", reqPrendasAtuendo);
		return new ModelAndView(model, "/accionesSugerencia.hbs");
	}
	
	public ModelAndView aceptarSugerencia(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		int idSugerencia = Integer.parseInt(req.params("idSugerencia"));
		Integer  idEventoActual = Integer.parseInt(req.queryParams("idEvt"));
		Planificador.getInstance().aceptarSugerenciaEventoPlanificado(idEventoActual, idSugerencia);
		Map<String, Object> model = new HashMap<>();
		model.put("idSugerencia", idSugerencia);
		model.put("idEvento", idEventoActual);
		if (Planificador.getInstance().consultarEventoPlanificadoPorId(idEventoActual).getSugerenciaElegida().getIdSugerencia() == idSugerencia) {
			res.status(201);
			return new ModelAndView(model, "/aceptarSugerenciaEventoOK.hbs");
		}
		else {
			res.status(200);
			return new ModelAndView(model, "/aceptarSugerenciaEventoError.hbs");
		}
		
	}
	
	public ModelAndView rechazarSugerencia(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		int idSugerencia = Integer.parseInt(req.params("idSugerencia"));
		Integer  idEventoActual = Integer.parseInt(req.queryParams("idEvt"));
		Planificador.getInstance().rechazaSugerenciaEventoPlanificado(idEventoActual, idSugerencia);
		Map<String, Object> model = new HashMap<>();
		model.put("idSugerencia", idSugerencia);
		model.put("idEvento", idEventoActual);
		if (RepoSugerencias.getInstance().buscoSugerenciaPorId(idSugerencia).getEstado().ordinal() == EstadoSugerencia.RECHAZADA.ordinal()) {
			res.status(201);
			return new ModelAndView(model, "/rechazarSugerenciaEventoOK.hbs");
		}
		else {
			res.status(200);
			return new ModelAndView(model, "/rechazarSugerenciaEventoError.hbs");
		}
		
	}
	
	public ModelAndView calificarSugerencia(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		Integer  idEventoActual = Integer.parseInt(req.queryParams("idEvt"));
		Map<String, Object> model = new HashMap<>();
		model.put("idEvento", idEventoActual);
		
		if (!req.params("idSugerencia").equals("Sin Seleccionar")) {
			int idSugerencia = Integer.parseInt(req.params("idSugerencia"));
			Sugerencia sugerencia = RepoSugerencias.getInstance().buscoSugerenciaPorId(idSugerencia);
			RequestCalificacion reqCalificacion = new RequestCalificacion();
			System.out.println(req.queryParams("sensacionGlobal")+req.queryParams("sensacionCabeza")+req.queryParams("sensacionCuello")+req.queryParams("sensacionManos"));
			reqCalificacion.setUsuario(currentUser.getUserName());
			reqCalificacion.setSensacionGlobal(req.queryParams("sensacionGlobal").toString());
			reqCalificacion.setSensacionCabeza(req.queryParams("sensacionCabeza"));
			reqCalificacion.setSensacionCuello(req.queryParams("sensacionCuello"));
			reqCalificacion.setSensacionManos(req.queryParams("sensacionManos"));
			CalificacionSugerencia calificacion = reqCalificacion.getCalificacionFromThis();
			sugerencia.setCalificacion(calificacion);
			RepoSugerencias.getInstance().actualizarSugerencia(sugerencia);
			int idCalificacion = calificacion.getIdCalificacion();
			model.put("idSugerencia", idSugerencia);
			model.put("idCalificacion", idCalificacion);
			model.put("username", currentUser.getUserName());
			if (sugerencia.getCalificacion().getIdCalificacion() == idCalificacion) {
				res.status(201);
				return new ModelAndView(model, "/calificarSugerenciaOK.hbs");
			}
			else {
				res.status(200);
				return new ModelAndView(model, "/calificarSugerenciaError.hbs");
			}		
		}
		else {
			res.status(200);
			return new ModelAndView(model, "/calificarSugerenciaError.hbs");
		}
	}

}
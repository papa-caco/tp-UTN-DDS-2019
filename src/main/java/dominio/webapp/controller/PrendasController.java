package dominio.webapp.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import dominio.placard.Placard;
import dominio.prenda.Prenda;
import dominio.repository.*;
import dominio.usuario.Usuario;
import dominio.utilities.ordenadores.OrdenadorPrendasPorTipo;
import dominio.webapp.request.RequestPrenda;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PrendasController {
	
    private static PrendasController instance = null;
    
    private PrendasController(){
    }
    
    public static PrendasController getInstance() {
    	if (instance == null) {
    		instance = new PrendasController();
    	}
    		return instance;
    }
	
	public ModelAndView listPrendasTipo(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user");
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		List<Prenda> prendas = RepoPrendas.getInstance().getPrendas();
        List<Prenda> ropas = null;
        if (req.queryParams("tipo") == null || req.queryParams("tipo").isEmpty()) {
        	ropas = prendas;
        } 
        else {        
        	ropas = prendas.stream().filter(pr -> 
        	pr.getTipo().getTipo().equals(req.queryParams("tipo"))).collect(Collectors.toList());
        }
        String secuencia = req.queryParams("page");
		Collections.sort(ropas,new OrdenadorPrendasPorTipo());
		int ultElem = ropas.size();
		List<Prenda> paginaPrendas = new LinkedList<Prenda>();
		Map<String, Object> model = new HashMap<>();
		
		int offset = 1;
		if (req.queryParams("cantidad") != null) {
			offset = Integer.parseInt(req.queryParams("cantidad"));
		}
		
		if ((secuencia == null) || ((! secuencia.equals("first")) && (! secuencia.equals("next")) && (! secuencia.equals("prev")))) {
			paginaPrendas = ropas;
		} else {
			if (secuencia.equals("first")) {
				req.session().attribute("currPageListPrendas", 0);
				if (offset < ultElem) {
					paginaPrendas = ropas.subList(0,offset);
				} else {
				paginaPrendas = ropas.subList(0,ultElem);
				}
			}
			if (secuencia.equals("next")) {
				if ( ((Integer)req.session().attribute("currPageListPrendas") + 1) * offset < ultElem ) {
					req.session().attribute("currPageListPrendas", (Integer)req.session().attribute("currPageListPrendas")+1);
				}
				int primero = (Integer)req.session().attribute("currPageListPrendas") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem) {
					if (ultimo < ultElem) {
						paginaPrendas = ropas.subList(primero,ultimo);
					} else {
						paginaPrendas = ropas.subList(primero,ultElem);
					}
				} else {
					req.session().attribute("currPageListPrendas", 0);
					paginaPrendas = ropas;
				}
			} 
			if (secuencia.equals("prev")) {
				if ((Integer)req.session().attribute("currPageListPrendas") > 0 ) {
					req.session().attribute("currPageListPrendas", (Integer)req.session().attribute("currPageListPrendas")-1);;
				}
				int primero = (Integer)req.session().attribute("currPageListPrendas") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem && ultimo < ultElem) {
					paginaPrendas = ropas.subList(primero,ultimo);
				} else {
					req.session().attribute("currPageListPrendas", 0);
					paginaPrendas = ropas;
				}
			}
			float resultado = (float)ultElem / offset;
			float sinDecimales = (int)ultElem /offset;
			if (resultado > sinDecimales) {
				model.put("totalPaginas", (int)(sinDecimales + 1));
			} else {
				model.put("totalPaginas", (int)sinDecimales);
			}
			model.put("paginaActual", (Integer)req.session().attribute("currPageListPrendas") + 1);
		}
		
		List<RequestPrenda> reqPaginaPrendas = paginaPrendas.stream().map(pr -> new RequestPrenda(pr)).collect(Collectors.toList());
		model.put("userName", currentUser.getUserName());
		model.put("totalPrendas", ultElem);
		model.put("listadoReqPrendas", reqPaginaPrendas);
		model.put("cantPagina", reqPaginaPrendas.size());
        res.status(200);
        return new ModelAndView(model, "ropas.hbs");
    }
    
    public ModelAndView listPrendasPlacard(Request req, Response res) {
    	Usuario currentUser = (Usuario)req.session().attribute("user");
    	
    	if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		String nombrePlacard = req.queryParams("nombrePlacard");
	   	Placard unPlacard = RepoPlacards.getInstance().buscoPlacardNombre(nombrePlacard);
    	List<Prenda> prendas = unPlacard.getPrendas();
        List<Prenda> ropas = null;
        if (req.queryParams("tipo") == null || req.queryParams("tipo").isEmpty()) {
        	ropas = prendas;
        } 
        else {        
        	ropas = prendas.stream().filter(pr -> pr.getTipo().getTipo().equals(req.queryParams("tipo"))).collect(Collectors.toList());
        }
        String secuencia = req.queryParams("page");
		Collections.sort(ropas,new OrdenadorPrendasPorTipo());
		int ultElem = ropas.size();
		List<Prenda> paginaPrendas = new LinkedList<Prenda>();
		Map<String, Object> model = new HashMap<>();
	
		int offset = 1;
		if (req.queryParams("cantidad") != null) {
			offset = Integer.parseInt(req.queryParams("cantidad"));
		}
		
		if ((secuencia == null) || ((! secuencia.equals("first")) && (! secuencia.equals("next")) && (! secuencia.equals("prev")))) {
			paginaPrendas = ropas;
		} else {
			if (secuencia.equals("first")) {
				req.session().attribute("currPageListPrendasPlacard", 0);
				if (offset < ultElem) {
					paginaPrendas = ropas.subList(0,offset);
				} else {
				paginaPrendas = ropas.subList(0,ultElem);
				}
			}
			if (secuencia.equals("next")) {
				if ( ((Integer)req.session().attribute("currPageListPrendasPlacard") + 1) * offset < ultElem ) {
					req.session().attribute("currPageListPrendasPlacard", (Integer)req.session().attribute("currPageListPrendasPlacard") + 1);
				}
				int primero = (Integer)req.session().attribute("currPageListPrendasPlacard") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem) {
					if (ultimo < ultElem) {
						paginaPrendas = ropas.subList(primero,ultimo);
					} else {
						paginaPrendas = ropas.subList(primero,ultElem);
					}
				} else {
					req.session().attribute("currPageListPrendasPlacard", 0);
					paginaPrendas = ropas;
				}
			}
			if (secuencia.equals("prev")) {
				if ((Integer)req.session().attribute("currPageListPrendasPlacard") > 0 ) {
					req.session().attribute("currPageListPrendasPlacard", (Integer)req.session().attribute("currPageListPrendasPlacard") - 1);
				}
				int primero = (Integer)req.session().attribute("currPageListPrendasPlacard") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem && ultimo < ultElem) {
					paginaPrendas = ropas.subList(primero,ultimo);
				} else {
					req.session().attribute("currPageListPrendasPlacard", 0);
					paginaPrendas = ropas;
				}
			}
			float resultado = (float)ultElem / offset;
			float sinDecimales = (int)ultElem /offset;
			if (resultado > sinDecimales) {
				model.put("totalPaginas", (int)(sinDecimales + 1));
			} else {
				model.put("totalPaginas", (int)sinDecimales);
			}
			model.put("paginaActual", (Integer)req.session().attribute("currPageListPrendasPlacard") + 1);
		}
		
		List<RequestPrenda> reqPaginaPrendas = paginaPrendas.stream().map(pr -> new RequestPrenda(pr)).collect(Collectors.toList());
		model.put("usuario", currentUser.getUserName());
		model.put("totalPrendas", ultElem);
		model.put("listadoReqPrendas", reqPaginaPrendas);
		model.put("cantPagina", reqPaginaPrendas.size());
     	int cantAtuendos = RepoAtuendos.getInstance().buscoAtuendosPlacard(unPlacard).size();
    	model.put("cantAtuendos", cantAtuendos);
        model.put("nombrePlacard", nombrePlacard);
        model.put("listadoReqPrendas", reqPaginaPrendas);
        res.status(200);
        return new ModelAndView(model, "prendasPlacard.hbs");
    }
    
}
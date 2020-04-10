package dominio.webapp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import spark.ModelAndView;
import spark.Request;
import spark.Response;


import dominio.eventos.*;
import dominio.excepciones.*;
import dominio.placard.CalificacionSugerencia;
import dominio.placard.Recomendador;
import dominio.placard.Sugerencia;
import dominio.prenda.Prenda;
import dominio.repository.*;
import dominio.usuario.Usuario;
import dominio.utilities.ordenadores.OrdenadorEventosPorAntiguo;
import dominio.utilities.ordenadores.OrdenadorEventosPorReciente;
import dominio.webapp.request.*;

public class EventosController {
	
    private static EventosController instance = null;
    
    private EventosController(){

    }
    
    public static EventosController getInstance() {
    	if (instance == null) {
    		instance = new EventosController();
    	}
    		return instance;
    }

	public ModelAndView showGestionEventos(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user");
		
		if (currentUser == null) {
		res.status(500);
		return new ModelAndView(null, "/error500-1.hbs");
		}
	
		String secuencia = req.queryParams("page");
		List<Evento> eventosUsuario = Planificador.getInstance().getEventosUsuario(currentUser.getIdUsuario());
		Collections.sort(eventosUsuario,new OrdenadorEventosPorAntiguo());
		int ultElem = eventosUsuario.size();
		List<Evento> paginaEventos = new LinkedList<Evento>();
		Map<String, Object> model = new HashMap<>();
		
		int offset = 1;
		if (req.queryParams("cantidad") != null) {
			offset = Integer.parseInt(req.queryParams("cantidad"));
		}
		
		if ((secuencia == null) || ((! secuencia.equals("first")) && (! secuencia.equals("next")) && (! secuencia.equals("prev")))) {
			paginaEventos = eventosUsuario;
		} else {
			if (secuencia.equals("first")) {
				req.session().attribute("currentPageEventos", 0);
				if (offset < ultElem) {
					paginaEventos = eventosUsuario.subList(0,offset);
				} else {
				paginaEventos = eventosUsuario.subList(0,ultElem);
				}
			}
			if (secuencia.equals("next")) {
				if ( ((Integer)req.session().attribute("currentPageEventos") + 1) * offset < ultElem ) {
					req.session().attribute("currentPageEventos",(Integer)req.session().attribute("currentPageEventos") + 1);
				}
				int primero = (Integer)req.session().attribute("currentPageEventos") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem) {
					if (ultimo < ultElem) {
						paginaEventos = eventosUsuario.subList(primero,ultimo);
					} else {
						paginaEventos = eventosUsuario.subList(primero,ultElem);
					}
					
				} else {
					req.session().attribute("currentPageEventos", 0);
					paginaEventos = eventosUsuario;
				}
			}
			if (secuencia.equals("prev")) {
				if ((Integer)req.session().attribute("currentPageEventos") > 0 ) {
					req.session().attribute("currentPageEventos",(Integer)req.session().attribute("currentPageEventos") - 1);
				}
				int primero = (Integer)req.session().attribute("currentPageEventos") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem && ultimo < ultElem) {
					paginaEventos = eventosUsuario.subList(primero,ultimo);
				} else {
					req.session().attribute("currentPageEventos", 0);
					paginaEventos = eventosUsuario;
				}
			}
			float resultado = (float)ultElem / offset;
			float sinDecimales = (int)ultElem /offset;
			if (resultado > sinDecimales) {
				model.put("totalPaginas", (int)(sinDecimales + 1));
			} else {
				model.put("totalPaginas", (int)sinDecimales);
			}
			model.put("paginaActual", (Integer)req.session().attribute("currentPageEventos") + 1);
			System.out.println("Mostrando hasta " + offset + " items de la pagina nro:" + ((Integer)req.session().attribute("currentPageEventos") + 1));
		}
		
		List<RequestEvento> reqEventosUsuario = paginaEventos.stream().map(ev -> new RequestEvento(ev)).collect(Collectors.toList());
		
		model.put("eventosUsuario", reqEventosUsuario);
		model.put("cantPagina", reqEventosUsuario.size());
		model.put("totalEventos", ultElem);
		model.put("usuarioActual",currentUser.getUserName());
		model.put("dateTimeActual", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		
		return new ModelAndView(model, "/eventosInicio.hbs");
	}
	
	public ModelAndView mostrarAltaEvento(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user");
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		Map<String, Object> model = new HashMap<>();
		model.put("auxiliares",RepoAuxiliares.getInstance().getValores());
		model.put("caracteres", RepoUsoPrendas.getInstance().getUsos());
		model.put("ubicaciones", RepoUbicaciones.getInstance().getUbicaciones());
		model.put("unidadesPeriodo",RepoEscalaTiempo.getInstance().getUnidadesTiempo());
		return new ModelAndView(model, "/formAltaEvento.html");
	}
	
	public ModelAndView altaEvento(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user");
		Map<String, String> model = new HashMap<>();
		RequestEvento reqEvento = new RequestEvento();
		reqEvento.setFechaHoraEvento(req.queryParams("fechaHoraEvento").toString());
		reqEvento.setUbicacion(req.queryParams("ubicacion").toString());
		reqEvento.setActividad(req.queryParams("actividad").toString());
		reqEvento.setCaracter(req.queryParams("caracter").toString());
		if (! req.queryParams("cantidadTiempo").equals("")) {
			reqEvento.setCantidadTiempo(Integer.parseInt(req.queryParams("cantidadTiempo").toString()));
		}
		reqEvento.setUnidad(req.queryParams("unidadTiempo").toString());
		reqEvento.setUserName(currentUser.getUserName());
		reqEvento.setRepetitivo(req.queryParams("repetitivo").toString());
		if (req.queryParams("repetitivo").toString().equals("SI") && (! req.queryParams("cantidadPeriodo").equals("")) ) {
			reqEvento.setCantidadPeriodo(Integer.parseInt(req.queryParams("cantidadPeriodo").toString()));
			reqEvento.setUnidadPeriodo(req.queryParams("unidadPeriodo").toString());
			reqEvento.setFechaHoraFin(req.queryParams("fechaHoraFin").toString());
			Planificacion plan = reqEvento.getPlanificacionfromThis();
			System.out.println("Nuevo evento repetitivo - Id: " + plan.getIdEvento());
		} else {
			EventoPlanificado planificado = reqEvento.getEventoPlanificadofromThis();
			System.out.println("Nuevo evento planficado - Id: " + planificado.getIdEvento());
		}
		res.status(200);
		return new ModelAndView(model, "/cargaEventoOK.hbs");
	}
	
	public ModelAndView mostrarProximosEventos(Request req, Response res) throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		Usuario currentUser = (Usuario)req.session().attribute("user");
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		List<EventoPlanificado> eventosUsuario = Planificador.getInstance().eventosProximos(currentUser);
		Collections.sort(eventosUsuario,new OrdenadorEventosPorAntiguo());
		String secuencia = req.queryParams("page");
		int ultElem = eventosUsuario.size();
		List<EventoPlanificado> paginaEventos = new LinkedList<>();
		Map<String, Object> model = new HashMap<>();
		
		int offset = 1;
		if (req.queryParams("cantidad") != null) {
			offset = Integer.parseInt(req.queryParams("cantidad"));
		}
		
		if ((secuencia == null) || ((! secuencia.equals("first")) && (! secuencia.equals("next")) && (! secuencia.equals("prev")))) {
			paginaEventos = eventosUsuario;
		} else {
			if (secuencia.equals("first")) {
				req.session().attribute("currentPageProximos", 0);
				if (offset < ultElem) {
					paginaEventos = eventosUsuario.subList(0,offset);
				} else {
				paginaEventos = eventosUsuario.subList(0,ultElem);
				}
			}
			if (secuencia.equals("next")) {
				if ( ((Integer)req.session().attribute("currentPageProximos") + 1) * offset < ultElem ) {
					req.session().attribute("currentPageProximos",(Integer)req.session().attribute("currentPageProximos") + 1);
				}
				int primero = (Integer)req.session().attribute("currentPageProximos") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem) {
					if (ultimo < ultElem) {
						paginaEventos = eventosUsuario.subList(primero,ultimo);
					} else {
						paginaEventos = eventosUsuario.subList(primero,ultElem);
					}
				} 
				else {
					req.session().attribute("currentPageProximos", 0);
					paginaEventos = eventosUsuario;
				}
			}
			if (secuencia.equals("prev")) {
				if ((Integer)req.session().attribute("currentPageProximos") > 0 ) {
					req.session().attribute("currentPageProximos",(Integer)req.session().attribute("currentPageProximos") - 1);
				}
				int primero = (Integer)req.session().attribute("currentPageProximos") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem && ultimo < ultElem) {
					paginaEventos = eventosUsuario.subList(primero,ultimo);
				} else {
					req.session().attribute("currentPageProximos", 0);
					paginaEventos = eventosUsuario;
				}
			}
			float resultado = (float)ultElem / offset;
			float sinDecimales = (int)ultElem /offset;
			if (resultado > sinDecimales) {
				model.put("totalPaginas", (int)(sinDecimales + 1));
			} else {
				model.put("totalPaginas", (int)sinDecimales);
			}
			model.put("paginaActual", (Integer)req.session().attribute("currentPageProximos") + 1);
			System.out.println("Mostrando hasta " + offset + " items de la pagina nro:" + ((Integer)req.session().attribute("currentPageProximos") + 1));
		}
		List<RequestEvento> reqEventosProximosUsuario = paginaEventos.stream().map(ev -> new RequestEvento(ev)).collect(Collectors.toList());
		model.put("eventosProximosUsuario", reqEventosProximosUsuario);
		model.put("cantPagina", reqEventosProximosUsuario.size());
		model.put("totalEventos", ultElem);
		model.put("usuarioActual",currentUser.getUserName());
		model.put("dateTimeActual", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		res.status(200);
		return new ModelAndView(model, "/eventosProximos.hbs");
	}
	
	public ModelAndView mostrarEventosHistoricos(Request req, Response res) throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		List<EventoHistorico> historicosUsuario = RepoEventosHistoricos.getInstance().consultarEventosHistoricosUsuario(currentUser);
		List<EventoHistorico> sinCalificar = historicosUsuario.stream().filter(ev -> ev.sinCalificacion()).collect(Collectors.toList());
		Collections.sort(sinCalificar,new OrdenadorEventosPorReciente());
		String secuencia = req.queryParams("page");
		int ultElem = sinCalificar.size();
		List<EventoHistorico> paginaEventos = new LinkedList<>();
		Map<String, Object> model = new HashMap<>();
		
		int offset = 1;
		if (req.queryParams("cantidad") != null) {
			offset = Integer.parseInt(req.queryParams("cantidad"));
		}
		
		if ((secuencia == null) || ((! secuencia.equals("first")) && (! secuencia.equals("next")) && (! secuencia.equals("prev")))) {
			paginaEventos = sinCalificar;
		} else {
			if (secuencia.equals("first")) {
				req.session().attribute("currentPageHistoricos", 0);
				if (offset < ultElem) {
					paginaEventos = sinCalificar.subList(0,offset);
				} else {
				paginaEventos = sinCalificar.subList(0,ultElem);
				}
			}
			if (secuencia.equals("next")) {
				if ( ((Integer)req.session().attribute("currentPageHistoricos") + 1) * offset < ultElem ) {
					req.session().attribute("currentPageHistoricos", (Integer)req.session().attribute("currentPageHistoricos") + 1);
				}
				int primero = (Integer)req.session().attribute("currentPageHistoricos") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem) {
					if (ultimo < ultElem) {
						paginaEventos = sinCalificar.subList(primero,ultimo);
					} else {
						paginaEventos = sinCalificar.subList(primero,ultElem);
					}
				}
				else {
					req.session().attribute("currentPageHistoricos", 0);
					paginaEventos = sinCalificar;
				}
			}
			if (secuencia.equals("prev")) {
				if ((Integer)req.session().attribute("currentPageHistoricos") > 0 ) {
					req.session().attribute("currentPageHistoricos", (Integer)req.session().attribute("currentPageHistoricos") - 1);
				}
				int primero = (Integer)req.session().attribute("currentPageHistoricos") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem && ultimo < ultElem) {
					paginaEventos = sinCalificar.subList(primero,ultimo);
				} else {
					req.session().attribute("currentPageHistoricos", 0);
					paginaEventos = sinCalificar;
				}
			}
			float resultado = (float)ultElem / offset;
			float sinDecimales = (int)ultElem /offset;
			if (resultado > sinDecimales) {
				model.put("totalPaginas", (int)(sinDecimales + 1));
			} else {
				model.put("totalPaginas", (int)sinDecimales);
			}
			model.put("paginaActual", (Integer)req.session().attribute("currentPageHistoricos") + 1);
			System.out.println("Mostrando hasta " + offset + " items de la pagina nro:" + ((Integer)req.session().attribute("currentPageHistoricos") +1));
		}
		List<RequestEvento> reqHistoricosUsuario = paginaEventos.stream().map(ev -> new RequestEvento(ev)).collect(Collectors.toList());
		model.put("eventosHistoricosUsuario", reqHistoricosUsuario);
		model.put("cantPagina", reqHistoricosUsuario.size());
		model.put("totalEventos", ultElem);
		model.put("usuarioActual",currentUser.getUserName());
		model.put("dateTimeActual", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		res.status(200);
		return new ModelAndView(model, "/eventosHistoricos.hbs");
	}
	
	public ModelAndView mostrarHistoricosCalificados(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		List<EventoHistorico> historicosUsuario = RepoEventosHistoricos.getInstance().consultarEventosHistoricosUsuario(currentUser);
		List<EventoHistorico> conSugerencia = historicosUsuario.stream().filter(ev -> ev.getSugerenciaElegida() != null ).collect(Collectors.toList());
		List<EventoHistorico> conCalificacion = conSugerencia.stream().filter(ev -> ev.tieneCalificacion()).collect(Collectors.toList());
		Collections.sort(conCalificacion,new OrdenadorEventosPorReciente());
		String secuencia = req.queryParams("page");
		int ultElem = conCalificacion.size();
		List<EventoHistorico> paginaEventos = new LinkedList<>();
		Map<String, Object> model = new HashMap<>();
		
		int offset = 1;
		if (req.queryParams("cantidad") != null) {
			offset = Integer.parseInt(req.queryParams("cantidad"));
		}
		
		if ((secuencia == null) || ((! secuencia.equals("first")) && (! secuencia.equals("next")) && (! secuencia.equals("prev")))) {
			paginaEventos = conCalificacion;
		} else {
			if (secuencia.equals("first")) {
				req.session().attribute("currentPageCalificados", 0);
				if (offset < ultElem) {
					paginaEventos = conCalificacion.subList(0,offset);
				} else {
					paginaEventos = conCalificacion.subList(0,ultElem);
				}
			}
			if (secuencia.equals("next")) {
				if ( ((Integer)req.session().attribute("currentPageCalificados") + 1) * offset < ultElem ) {
					req.session().attribute("currentPageCalificados", (Integer)req.session().attribute("currentPageCalificados") + 1);
				}
				int primero = (Integer)req.session().attribute("currentPageCalificados") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem) {
					if (ultimo < ultElem) {
						paginaEventos = conCalificacion.subList(primero,ultimo);
					} else {
						paginaEventos = conCalificacion.subList(primero,ultElem);
					}
				}
				else {
					req.session().attribute("currentPageCalificados", 0);
					paginaEventos = conCalificacion;
				}
			}
			if (secuencia.equals("prev")) {
				if ((Integer)req.session().attribute("currentPageCalificados") > 0 ) {
					req.session().attribute("currentPageCalificados", (Integer)req.session().attribute("currentPageCalificados") -1 );
				}
				int primero = (Integer)req.session().attribute("currentPageCalificados") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem && ultimo < ultElem) {
					paginaEventos = conCalificacion.subList(primero,ultimo);
				} else {
					req.session().attribute("currentPageCalificados", 0);
					paginaEventos = conCalificacion;
				}
			}
			float resultado = (float)ultElem / offset;
			float sinDecimales = (int)ultElem /offset;
			if (resultado > sinDecimales) {
				model.put("totalPaginas", (int)(sinDecimales + 1));
			} else {
				model.put("totalPaginas", (int)sinDecimales);
			}
			model.put("paginaActual", (Integer)req.session().attribute("currentPageCalificados") + 1);
			System.out.println("Mostrando hasta " + offset + " items de la pagina nro:" + ((Integer)req.session().attribute("currentPageCalificados") +1));
		}
		List<RequestEvtHistCalificado> reqHistoricosCalificados = paginaEventos.stream().map(ev -> new RequestEvtHistCalificado(ev)).collect(Collectors.toList());
		model.put("totalEventos", ultElem);
		model.put("usuarioActual",currentUser.getUserName());
		model.put("dateTimeActual", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		model.put("reqHistoricosCalificados", reqHistoricosCalificados);
		model.put("cantPagina", reqHistoricosCalificados.size());
		res.status(200);
		return new ModelAndView(model, "/historicosCalificados.hbs");
	}
	
	public ModelAndView consultarSugerenciasEvento(Request req, Response res) throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		Integer idEvt = Integer.parseInt(req.params("idEvento"));
		String secuencia = req.queryParams("page");
		EventoPlanificado event = Planificador.getInstance().consultarEventoPlanificadoPorId(idEvt);
		List<RequestEvento> reqEventos = Lists.newArrayList(new RequestEvento(event));
		List<Sugerencia> sugerencias = event.getSugerencias();
		List<Sugerencia> sugerenciasEvento = sugerencias.stream().filter(suge -> suge.getEstado().ordinal() != 1).collect(Collectors.toList());
		List<Sugerencia> paginaSugerencias = new LinkedList<>();
		Integer ultElem = sugerenciasEvento.size();
		Map<String, Object> model = new HashMap<>();
		int offset = 1;
		if (req.queryParams("cantidad") != null) {
			offset = Integer.parseInt(req.queryParams("cantidad"));
		}
		if ((secuencia == null) || ((! secuencia.equals("first")) && (! secuencia.equals("next")) && (! secuencia.equals("prev")))) {
			paginaSugerencias = sugerenciasEvento;
		} 
		else {
			if (secuencia.equals("first")) {
				req.session().attribute("currentPageSugerencias", 0);
				if (offset < ultElem) {
					paginaSugerencias = sugerenciasEvento.subList(0,offset);
				} else {
					paginaSugerencias = sugerenciasEvento;
				}
			}
			if (secuencia.equals("next")) {
				if ( ((Integer)req.session().attribute("currentPageSugerencias") + 1) * offset < ultElem ) {
					req.session().attribute("currentPageSugerencias", (Integer)req.session().attribute("currentPageSugerencias") + 1);
				}
				int primero = (Integer)req.session().attribute("currentPageSugerencias") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem) {
					if (ultimo < ultElem) {
						paginaSugerencias = sugerenciasEvento.subList(primero,ultimo);
					}
					else {
						paginaSugerencias = sugerenciasEvento.subList(primero,ultElem);
					}
				}
				else {
					req.session().attribute("currentPageSugerencias", 0);
					paginaSugerencias = sugerenciasEvento;
				}
			}
			if (secuencia.equals("prev")) {
				if ((Integer)req.session().attribute("currentPageSugerencias") > 0 ) {
					req.session().attribute("currentPageSugerencias", (Integer)req.session().attribute("currentPageSugerencias") - 1);
				}
				int primero = (Integer)req.session().attribute("currentPageSugerencias") * offset;
				int ultimo = primero + offset;
				if (primero < ultElem && ultimo < ultElem) {
					paginaSugerencias = sugerenciasEvento.subList(primero,ultimo);
				} 
				else {
					req.session().attribute("currentPageSugerencias", 0);
					paginaSugerencias = sugerenciasEvento;
				}
			}
			float resultado = (float)ultElem / offset;
			float sinDecimales = (int)ultElem /offset;
			if (resultado > sinDecimales) {
				model.put("totalPaginas", (int)(sinDecimales + 1));
			} else {
				model.put("totalPaginas", (int)sinDecimales);
			}
			model.put("paginaActual", (Integer)req.session().attribute("currentPageSugerencias") + 1);
			System.out.println("Mostrando hasta " + offset + " items de la pagina nro:" + ((Integer)req.session().attribute("currentPageSugerencias") +1));
		}
		List<RequestSugerencia> reqSugerencias = paginaSugerencias.stream().map(suge -> new RequestSugerencia(suge)).collect(Collectors.toList());
		model.put("usuarioActual",currentUser.getUserName());
		model.put("dateTimeActual", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		model.put("idEvento", idEvt.toString());
		model.put("tempMax", Recomendador.getInstance().getTempMaximaPronosticada(event));
		model.put("tempMin", Recomendador.getInstance().getTempMinimaPronosticada(event));
		model.put("precipit", Recomendador.getInstance().getProbabilidadPrecipitacion(event));
		model.put("reqEventos", reqEventos);
		model.put("reqSugerenciasEvento", reqSugerencias);
		model.put("cantPagina", reqSugerencias.size());
		model.put("cantSuge", ultElem);
		res.status(200);
		return new ModelAndView(model, "/listSugerenciasEvento.hbs");
	}
	
	public ModelAndView anularSeleccionSugerencia(Request req, Response res) throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		Integer idEvt = Integer.parseInt(req.params("idEvento"));
		Planificador.getInstance().deshacerEleccionSugerencia(idEvt);
		Map<String, Object> model = new HashMap<>();
		model.put("idEvento", idEvt.toString());
		if (Planificador.getInstance().consultarEventoPlanificadoPorId(idEvt).getSugerenciaElegida() == null) {
				res.status(201);
				return new ModelAndView(model, "/borrarSugerenciaEventoOK.hbs");
		}	else {
				res.status(200);
				model.put("idSugerencia", Planificador.getInstance().consultarEventoPlanificadoPorId(idEvt).getSugerenciaElegida().getIdSugerencia());
				return new ModelAndView(model, "/borrarSugerenciaEventoError.hbs");
		}
		
	}
	
	public ModelAndView mostrarCalificarSugerencia(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		Integer idEvt = Integer.parseInt(req.params("idEvento"));
		Map<String, Object> model = new HashMap<>();
		EventoHistorico evento = RepoEventosHistoricos.getInstance().consultarEventoHistoricoPorId(idEvt);
		Sugerencia sugerencia = evento.getSugerenciaElegida();
		String idSugerencia = "Sin Seleccionar";
		String estadoSugerencia = "Sin Estado";
		double caloriasAtuendo = 0.0;
		String idCalificacion = "Sin Calificar";
		RequestCalificacion reqCalificacion = new RequestCalificacion("",0,"","","","","");
		List<RequestPrenda> reqPrendasAtuendo = new LinkedList<>();
		if (evento.getSugerenciaElegida() != null) {
			idSugerencia = sugerencia.getIdSugerencia().toString();
			estadoSugerencia = evento.getSugerenciaElegida().getEstado().name();
			caloriasAtuendo = sugerencia.getAtuendo().caloriasBasicas();
			List<Prenda> prendasAtuendo = sugerencia.getAtuendo().getPrendasAtuendo();
			reqPrendasAtuendo.addAll(Lists.newArrayList(prendasAtuendo.stream().map(prenda -> new RequestPrenda(prenda)).collect(Collectors.toList())));
			if (sugerencia.getCalificacion() != null ) {
				idCalificacion = sugerencia.getCalificacion().getIdCalificacion().toString();
				reqCalificacion = new RequestCalificacion(sugerencia.getCalificacion());
			}
		}
		model.put("usuarioActual",currentUser.getUserName());
		model.put("dateTimeActual", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		model.put("idEvento",idEvt);
		model.put("fechaHoraEvento",evento.getFechaHoraEvento().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		model.put("ubicacion",evento.getUbicacion());
		model.put("actividad",evento.getActividad());
		model.put("estadoEvento",evento.getEstado().name());
		model.put("caracter",evento.getCaracterVestimenta().name());
		model.put("idSugerencia", idSugerencia);
		model.put("estadoSugerencia", estadoSugerencia);
		model.put("calorias", caloriasAtuendo);
		model.put("idCalificacion",idCalificacion);
		model.put("sensacionGlobal", reqCalificacion.getSensacionGlobal());
		model.put("sensacionCabeza", reqCalificacion.getSensacionCabeza());
		model.put("sensacionCuello", reqCalificacion.getSensacionCuello());
		model.put("sensacionManos", reqCalificacion.getSensacionManos());
		model.put("prendasAtuendo", reqPrendasAtuendo);
		model.put("valoresTermicos", RepoValoresTermicos.getInstance().getValoresTermicos());
		res.status(200);
		return new ModelAndView(model, "/mostrarCalificarSugerencia.hbs");
	}
	
	public ModelAndView mostrarActualizarCalificacion(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		Integer idEvt = Integer.parseInt(req.params("idEvento"));
		Map<String, Object> model = new HashMap<>();
		EventoHistorico evento = RepoEventosHistoricos.getInstance().consultarEventoHistoricoPorId(idEvt);
		Sugerencia sugerencia = evento.getSugerenciaElegida();
		String idSugerencia = "Sin Seleccionar";
		String estadoSugerencia = "Sin Estado";
		double caloriasAtuendo = 0.0;
		String idCalificacion = "Sin Calificar";
		RequestCalificacion reqCalificacion = new RequestCalificacion("",0,"","","","","");
		List<RequestPrenda> reqPrendasAtuendo = new LinkedList<>();
		if (evento.getSugerenciaElegida() != null) {
			idSugerencia = sugerencia.getIdSugerencia().toString();
			estadoSugerencia = evento.getSugerenciaElegida().getEstado().name();
			caloriasAtuendo = sugerencia.getAtuendo().caloriasBasicas();
			List<Prenda> prendasAtuendo = sugerencia.getAtuendo().getPrendasAtuendo();
			reqPrendasAtuendo.addAll(Lists.newArrayList(prendasAtuendo.stream().map(prenda -> new RequestPrenda(prenda)).collect(Collectors.toList())));
			if (sugerencia.getCalificacion() != null ) {
				idCalificacion = sugerencia.getCalificacion().getIdCalificacion().toString();
				reqCalificacion = new RequestCalificacion(sugerencia.getCalificacion());
			}
		}
		model.put("usuarioActual",currentUser.getUserName());
		model.put("dateTimeActual", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		model.put("idEvento",idEvt);
		model.put("fechaHoraEvento",evento.getFechaHoraEvento().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm:ss")));
		model.put("ubicacion",evento.getUbicacion());
		model.put("actividad",evento.getActividad());
		model.put("estadoEvento",evento.getEstado().name());
		model.put("caracter",evento.getCaracterVestimenta().name());
		model.put("idSugerencia", idSugerencia);
		model.put("estadoSugerencia", estadoSugerencia);
		model.put("calorias", caloriasAtuendo);
		model.put("idCalificacion",idCalificacion);
		model.put("sensacionGlobal", reqCalificacion.getSensacionGlobal());
		model.put("sensacionCabeza", reqCalificacion.getSensacionCabeza());
		model.put("sensacionCuello", reqCalificacion.getSensacionCuello());
		model.put("sensacionManos", reqCalificacion.getSensacionManos());
		model.put("prendasAtuendo", reqPrendasAtuendo);
		model.put("valoresTermicos", RepoValoresTermicos.getInstance().getValoresTermicos());
		res.status(200);
		return new ModelAndView(model, "/mostrarActualizarCalificacion.hbs");
	}
	
	public ModelAndView actualizarCalificacion(Request req, Response res) {
		Usuario currentUser = (Usuario)req.session().attribute("user"); 
		
		if (currentUser == null) {
			res.status(500);
			return new ModelAndView(null, "/error500-1.hbs");
		}
		
		int idEvento = Integer.parseInt(req.params("idEvento"));
		EventoHistorico evento = RepoEventosHistoricos.getInstance().consultarEventoHistoricoPorId(idEvento);
		Sugerencia sugerencia = evento.getSugerenciaElegida();
		RequestCalificacion reqCalificacion = new RequestCalificacion();
		reqCalificacion.setUsuario(currentUser.getUserName());
		reqCalificacion.setSensacionGlobal(req.queryParams("sensacionGlobal").toString());
		reqCalificacion.setSensacionCabeza(req.queryParams("sensacionCabeza"));
		reqCalificacion.setSensacionCuello(req.queryParams("sensacionCuello"));
		reqCalificacion.setSensacionManos(req.queryParams("sensacionManos"));
		CalificacionSugerencia calificacion = reqCalificacion.getCalificacionFromThis();
		sugerencia.setCalificacion(calificacion);
		RepoSugerencias.getInstance().actualizarSugerencia(sugerencia);
		int idCalificacion = calificacion.getIdCalificacion();
		Map<String, Object> model = new HashMap<>();
		model.put("idSugerencia", sugerencia.getIdSugerencia());
		model.put("idCalificacion", idCalificacion);
		model.put("idEvento", idEvento);
		model.put("username", currentUser.getUserName());
		if (sugerencia.getCalificacion().getIdCalificacion() == idCalificacion) {
			res.status(201);
			return new ModelAndView(model, "/actualizarCalificacionOk.hbs");
		}
		else {
			res.status(200);
			return new ModelAndView(model, "/actualizarCalificacionError.hbs");
		}
	}
}
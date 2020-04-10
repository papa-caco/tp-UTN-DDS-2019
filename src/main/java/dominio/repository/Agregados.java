package dominio.repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.common.collect.Lists;

import dominio.eventos.*;
import dominio.placard.*;
import dominio.excepciones.SinDatosClimaException;
import dominio.excepciones.SinPrendasException;
import dominio.excepciones.SinSenderActivoException;
import dominio.prenda.Atuendo;
import dominio.prenda.Capa;
import dominio.prenda.CaracterPrenda;
import dominio.prenda.Categoria;
import dominio.prenda.Superior;
import dominio.prenda.ValorTermico;
import dominio.servicios.ProveedorPronostico;
import dominio.servicios.ServicioNotificador;
import dominio.servicios.ServicioPronostico;
import dominio.servicios.notificacion.NotificationSender1;
import dominio.servicios.notificacion.NotificationSender2;
import dominio.servicios.pronostico.accuweather.CiudadPronostico;
import dominio.servicios.pronostico.accuweather.LocationKeysAccu;
import dominio.servicios.pronostico.accuweather.ProveedorPronosticoAccu;
import dominio.servicios.pronostico.accuweather.RecuperadorJsonAccuMock;
import dominio.usuario.Usuario;
import dominio.webapp.request.Auxiliar;

public class Agregados {
	
	public static void main(String[] args) throws SinPrendasException, SinSenderActivoException, SinDatosClimaException, IOException {
		//EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
		//EntityManager entityManager = entityManagerFactory.createEntityManager();
		//ServicioNotificador.getInstance().setSenders(Lists.newArrayList(NotificationSender1.getInstance(),NotificationSender2.getInstance()));
		
		/*Recordatorio reminder1 = RepoReminders.getInstance().obtenerRecordatorio(1,EscalaTiempo.DIAS);
		Recordatorio reminder2 = RepoReminders.getInstance().obtenerRecordatorio(3, EscalaTiempo.HORAS);
		Recordatorio reminder3 = RepoReminders.getInstance().obtenerRecordatorio(25,EscalaTiempo.MINUTOS);
		Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-09T00:30:00","Avellaneda","Ver a Racing","jose",reminder1));
		Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-09T13:01:00","Olivos","Tomar medicamento","jose",reminder3));
		Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-08T16:01:00","Mar Del Plata","Playa, sombrilla y Mate","jose",reminder3));
		Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-10T01:17:00","Cordoba","Viajar a Bs.As.","juan",reminder3));
		Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-08T23:50:00","Moron","Visitar Bodega","juan",reminder2));
		Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-10T01:01:00","Ushuaia","Visitar una pinguinera","juan",reminder1));
		
		
		Planificador.getInstance().aceptarSugerenciaEventoPlanificado(5229,5240);
		Planificador.getInstance().aceptarSugerenciaEventoPlanificado(5230,5820);
		Planificador.getInstance().aceptarSugerenciaEventoPlanificado(5231,5900);
		Planificador.getInstance().aceptarSugerenciaEventoPlanificado(5232,5980);
		Planificador.getInstance().aceptarSugerenciaEventoPlanificado(5233,6199);
		Planificador.getInstance().aceptarSugerenciaEventoPlanificado(5234,5295);
		
		Usuario jose = RepoUsuarios.getInstance().buscarPorLogin("jose");
		CalificacionSugerencia calif = RepoCalificaciones.getInstance().obtengoCalificacion(jose, ValorTermico.APROPIADA, ValorTermico.FRIA, ValorTermico.APROPIADA, ValorTermico.FRIA);
		RepoEventosHistoricos.getInstance().setFeedbackSugerenciaEvento(6475, calif);
		
		
		//Planificador.getInstance().cargarSubeventosPlanificaciones();
		
		Recordatorio reminder = RepoReminders.getInstance().obtenerRecordatorio(5,EscalaTiempo.MINUTOS);
		Planificador.getInstance().cargarPlanificacion(new Planificacion("2020-02-08T14:20:00","Olivos",
				"Tomar 1 litro de agua...","juan","2020-02-08T18:19:00",EscalaTiempo.HORAS,2,reminder));*/
		//Usuario jose = RepoUsuarios.getInstance().buscarPorLogin("jose");
		// jose.setCriterioVestimenta();
		//System.out.println("Hay " + Planificador.getInstance().getEventosUsuario(86).size() + " distintos eventos para Jose.");
		
		/*		
		Recordatorio reminder = RepoReminders.getInstance().obtenerRecordatorio(5,EscalaTiempo.MINUTOS);
		Planificador.getInstance().cargarPlanificacion(new Planificacion("2020-02-08T23:20:00","Olivos", CaracterPrenda.DEPORTIVO,
				"Tomar 1 litro de agua...","juan","2020-02-09T11:19:00",EscalaTiempo.HORAS,2,reminder));
		
		Recordatorio reminder2 = RepoReminders.getInstance().obtenerRecordatorio(1, EscalaTiempo.HORAS);
		Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-08T23:55:00","Avellaneda", CaracterPrenda.DEPORTIVO,"Ver partido de La Academia en el Cilindro.","jose",reminder2));*/
		//Planificador.getInstance().aceptarSugerenciaEventoPlanificado(13063,13337);
		//RepoUsoPrendas.getInstance().getUsos().get(0).name();
		//Planificador.getInstance().eliminarSugerenciasEvento(5229);
		
		System.out.println(RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("camisa", RepoCapas.getInstance().obtengoIntermedia(15.0), Categoria.TORSO).getIdTipo());
		System.out.println(RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("cuellera", RepoCapas.getInstance().obtengoSuperior(27.0), Categoria.CUELLO).getIdTipo());
		System.out.println(RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("gorra", RepoCapas.getInstance().obtengoSuperior(15.0), Categoria.CABEZA).getIdTipo());
		System.out.println(RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("guantes", RepoCapas.getInstance().obtengoSuperior(25.0), Categoria.MANO).getIdTipo());
		/*Planificador.getInstance().aceptarSugerenciaEventoPlanificado(21670,21672);
		 ProveedorPronosticoAccu proveePronost = ProveedorPronosticoAccu.getInstance(RecuperadorJsonAccuMock.getInstance("./src/varios/jsonAccu/"), LocationKeysAccu.getInstance()); //<<SINGLETON>>
	        List<ProveedorPronostico> providers = new LinkedList<ProveedorPronostico>(); 
	        providers.add(proveePronost);
	        //CriterioSeleccionAtuendos criterioVentilado = new CriterioRefrescar();
	        //CriterioSeleccionAtuendos criterioCasual = new CriterioMasCasual();
	        ServicioNotificador.getInstance().setSenders(Lists.newArrayList(NotificationSender1.getInstance(),NotificationSender2.getInstance()));
	        ServicioPronostico.getInstance().setProveedores(Lists.newArrayList(proveePronost));
	        RecuperadorJsonAccuMock.getInstance("./src/varios/jsonAccu/");
	        System.out.println(ServicioPronostico.getInstance().getpronosticoCiudadFecha("Avellaneda", LocalDateTime.now().plusDays(1)).getTempMax());
	        Planificador.getInstance().generarSugerenciasParaEventoPlanificado(23257);
	        System.out.println(Recomendador.getInstance().entregarSugerenciasEvento(LocalDateTime.now(), "Avellaneda" , RepoUsuarios.getInstance().buscarPorLogin("juan")).size()+" sugerencias");
		//System.out.println("Hay " + RepoSugerencias.getInstance().buscoSugerenciaPorId(21725).getAtuendo().caloriasBasicas());*/
		ProveedorPronosticoAccu proveePronost = ProveedorPronosticoAccu.getInstance(RecuperadorJsonAccuMock.getInstance("./src/varios/jsonAccu/"), LocationKeysAccu.getInstance()); //<<SINGLETON>>
        List<ProveedorPronostico> providers = new LinkedList<>(); 
        providers.addAll(Lists.newArrayList(proveePronost));
        ServicioPronostico.getInstance().setProveedores(Lists.newArrayList(proveePronost));
        
		}
		
	}


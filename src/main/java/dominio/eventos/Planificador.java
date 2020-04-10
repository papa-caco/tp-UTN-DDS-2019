package dominio.eventos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import javax.persistence.PersistenceException;

import dominio.excepciones.*;
import dominio.placard.*;
import dominio.prenda.CaracterPrenda;
import dominio.repository.RepoEventosHistoricos;
import dominio.repository.RepoSugerencias;
import dominio.servicios.ServicioNotificador;
import dominio.usuario.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PUBLIC)
public class Planificador implements WithGlobalEntityManager {
	
	private static Planificador INSTANCE = null;
	
	private Planificador() {
		super();
	}
	
	public static Planificador getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new Planificador();
		}
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public List<EventoPlanificado> getEventosPlanificados() {
		return entityManager().createQuery("from EventoPlanificado").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Planificacion> getPlanificaciones() {
		return entityManager().createQuery("from Planificacion").getResultList();
	}
	
	public void agregarEventoPlanificado(EventoPlanificado event) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(event).getIdEvento();
			event.setIdEvento(id);
			entityManager().getTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
	}
	
	public void eliminarEventoPlanificado(EventoPlanificado evento) {
		Integer idEvt = evento.getIdEvento();
		this.eliminarSugerenciasEvento(idEvt);
		EventoPlanificado evt = this.consultarEventoPlanificadoPorId(idEvt);
		
		try {
			entityManager().getTransaction().begin();
    		if (evt.getFeedbackSugerencia() != null) {
    			evt.setFeedbackSugerencia(null);
    		}
    		if (evt.getSugerenciaElegida() != null) {
    			evt.setSugerenciaElegida(null);
    		}
    		if (evt.getRecordatorio() != null) {
    			evt.setRecordatorio(null);
    		}
    		if (!evt.getSugerencias().isEmpty()) {
    			evt.getSugerencias().clear();
    		}
    		if (evt.getUsuario() != null) {
    			evt.setUsuario(null);
    		}
       		entityManager().remove(evt);
		    entityManager().getTransaction().commit();
		    
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
	}
	
	public void actualizarEventoPlanificado(EventoPlanificado event) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(event).getIdEvento();
			event.setIdEvento(id);
			entityManager().getTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
	}
	
	public void actualizarPlanificacion(Planificacion plan) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(plan).getIdEvento();
			plan.setIdEvento(id);
			entityManager().getTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
	}
	
	public void cargarPlanificacion(Planificacion plan) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(plan).getIdEvento();
			plan.setIdEvento(id);
			entityManager().getTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
	}
	
	public void aceptarSugerenciaEventoPlanificado(int idEvt, int idSugerencia) {
		EventoPlanificado event = this.consultarEventoPlanificadoPorId(idEvt);
		Sugerencia suge = RepoSugerencias.getInstance().buscoSugerenciaPorId(idSugerencia);
		event.getSugerencias().remove(suge);
		event.setSugerenciaElegida(suge);
		this.actualizarEventoPlanificado(event);
		suge.setEstado(EstadoSugerencia.ACEPTADA);
		RepoSugerencias.getInstance().actualizarSugerencia(suge);
	}

	public void rechazaSugerenciaEventoPlanificado(int idEvt, int idSuge) {
		EventoPlanificado event = this.consultarEventoPlanificadoPorId(idEvt);
		if (event.getSugerenciaElegida() == null || event.getSugerenciaElegida().getIdSugerencia() != idSuge) {
			Sugerencia sugerencia = event.getSugerencias().stream().filter(suge -> suge.getIdSugerencia() == idSuge).collect(Collectors.toList()).get(0);
			sugerencia.setEstado(EstadoSugerencia.RECHAZADA);
			RepoSugerencias.getInstance().actualizarSugerencia(sugerencia);
		}
		else {
			Sugerencia sugerencia = event.getSugerenciaElegida();
			sugerencia.setEstado(EstadoSugerencia.RECHAZADA);
			event.setSugerenciaElegida(null);
			RepoSugerencias.getInstance().actualizarSugerencia(sugerencia);
			this.actualizarEventoPlanificado(event);
		}
	}
	
	public void deshacerEleccionSugerencia(int idEvento) {
		EventoPlanificado evento = this.consultarEventoPlanificadoPorId(idEvento);
		evento.getSugerencias().add(evento.getSugerenciaElegida());
		evento.setSugerenciaElegida(null);
		this.actualizarEventoPlanificado(evento);
	}
	
	public List<EventoPlanificado> eventosProximos(Usuario user) throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		this.actualizarEstadoEventos();
		List<EventoPlanificado> eventosUsuario = this.consultarEventosPlanificadosUsuario(user);
		return eventosUsuario.stream().filter(ev1->ev1.estaProximo()).collect(Collectors.toList());
	}
	
	public List<Evento> getEventosUsuario(int idUser) {
		@SuppressWarnings("unchecked")
		List<Evento> eventos = entityManager().createQuery("from Evento where Tipo_Evento <> 'Planificacion'").getResultList();
		return eventos.stream().filter(ev -> ev.getUsuario().getIdUsuario() == idUser).collect(Collectors.toList());
	}
	
	public EventoPlanificado consultarEventoPlanificadoPorId(int idEvt) {
		return this.getEventosPlanificados().stream().filter(ev -> ev.getIdEvento() == idEvt).collect(Collectors.toList()).get(0);
	}
	
	public Planificacion consultarPlanificacionesPorId(int idPlan) {
		return this.getPlanificaciones().stream().filter(plan -> plan.getIdEvento() == idPlan).collect(Collectors.toList()).get(0);
	}
	
	public List<EventoPlanificado> consultarEventosPlanificadosUsuario(Usuario user) {
		@SuppressWarnings("unchecked")
		List<EventoPlanificado> planificados = entityManager().createQuery("from Evento where Tipo_Evento ='EventoPlanificado' ").getResultList();
		return planificados.stream().filter(ev -> ev.getUsuario().getIdUsuario() == user.getIdUsuario()).collect(Collectors.toList());
	}
	
	public void actualizarEstadoEventos() throws IOException, SinDatosClimaException, SinSenderActivoException, SinPrendasException {
		this.cambiarEventosAProximo();
		this.procesarEventosVencidos();
		this.generarSugerenciasParaEventosPlanificdos();
	}

	public void cargarSubeventosPlanificaciones() {
		List<Planificacion> planes = this.getPlanificaciones();
		if (! planes.isEmpty()) {
			planes.stream().forEach( plan -> {
				if (plan.getEstado() == EstadoEvento.PENDIENTE) {
					int j = this.cantEventosRepetidos(plan);
					System.out.println("cant. repe: " + j);
					long periodo = plan.getUnidadPeriodo().multiplicadorSegundos() * plan.getCantidadPeriodo();
					long timeInicial = plan.getFechaHoraEvento().toEpochSecond(ZoneOffset.UTC);
					try {
						entityManager().getTransaction().begin();
						for (int i=0; i>=0 && i <= j; i++) {
							long tiempoAdicionado = periodo * i;
							LocalDateTime fechaSubEvento = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeInicial + tiempoAdicionado),ZoneOffset.UTC);
							EventoPlanificado subEvento = new EventoPlanificado(fechaSubEvento, plan.getUbicacion(), plan.getCaracterVestimenta(), plan.getActividad(),plan.getUsuario(),plan.getRecordatorio());
							Integer idSubEvt = entityManager().merge(subEvento).getIdEvento();
							subEvento.setIdEvento(idSubEvt);
						}
						entityManager().getTransaction().commit();
						plan.setEstado(EstadoEvento.PROCESADO);
						this.actualizarPlanificacion(plan);
					} 
					catch (PersistenceException e) {
						e.printStackTrace();
						entityManager().getTransaction().rollback();
						throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
					}
					finally {
						entityManager().close();
					}
				}	
			});
		}
			else { System.out.println("No se encontraron eventos repetitivos para procesar");
		}
	}
	
	public void mostrarCantidadEventosEnBaseDeDatos() {
		@SuppressWarnings("unchecked")
		List<EventoPlanificado> planificados = entityManager().createQuery("from Evento where Tipo_Evento = 'EventoPlanificado'").getResultList();
		@SuppressWarnings("unchecked")
		List<EventoPlanificado> repetitivos = entityManager().createQuery("from Evento where Tipo_Evento = 'Planificacion'").getResultList();
	    System.out.println("Hay " + planificados.size() + " eventos planificados cargados en el sistema.");
	    System.out.println("Hay " + repetitivos.size() + " eventos repetitivos cargados en el sistema.");
	}
	
	public EventoPlanificado obtengoEventoPlanificado(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad, String usuario, Recordatorio reminder) {
		@SuppressWarnings("unchecked")
		List<EventoPlanificado> planificados = entityManager().createQuery("from Evento where Tipo_Evento = 'EventoPlanificado'").getResultList();
		List<EventoPlanificado> seleccionados = planificados.stream().filter(ev -> ev.getFechaHoraEvento().format(DateTimeFormatter.ISO_DATE_TIME).equals(dateTime) 
				&&  ev.getUbicacion().equals(ubicacion) && ev.getActividad().equals(actividad) && ev.getUsuario().getUserName().equals(usuario) 
				&& ev.getRecordatorio().getIdRecordatorio() == reminder.getIdRecordatorio() && ev.getCaracterVestimenta().getIdUso() == caracter.getIdUso()).collect(Collectors.toList());
		if (! seleccionados.isEmpty()) {
			return seleccionados.get(0);
		} else {
			EventoPlanificado planificado = new EventoPlanificado(dateTime, ubicacion, caracter, actividad , usuario, reminder);
			this.agregarEventoPlanificado(planificado);
			return planificado;
		}
	}
	
	public Planificacion obtengoPlanificacion(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad, String usuario,
			String fechaHoraFin, EscalaTiempo unitPeriodo, int qtyPeriodo, Recordatorio reminder) {
		@SuppressWarnings("unchecked")
		List<Planificacion> planificaciones = entityManager().createQuery("from Evento where Tipo_Evento = 'Planificacion'").getResultList();
		List<Planificacion> planes = planificaciones.stream().filter(ev -> ev.getFechaHoraEvento().format(DateTimeFormatter.ISO_DATE_TIME).equals(dateTime) 
				&&  ev.getUbicacion().equals(ubicacion) && ev.getActividad().equals(actividad) && ev.getUsuario().getUserName().equals(usuario) 
				&& ev.getRecordatorio().getIdRecordatorio() == reminder.getIdRecordatorio() && ev.getCaracterVestimenta().getIdUso() == caracter.getIdUso()
				&& ev.getCantidadPeriodo() == qtyPeriodo && ev.getUnidadPeriodo().getIdEscalaTiempo() == unitPeriodo.getIdEscalaTiempo()
				&& ev.getFechaHoraFin().format(DateTimeFormatter.ISO_DATE_TIME).equals(fechaHoraFin)).collect(Collectors.toList());
		if (! planes.isEmpty()) {
			return planes.get(0);
		} else {
			Planificacion plan = new Planificacion(dateTime, ubicacion, caracter, actividad, usuario, fechaHoraFin, unitPeriodo, qtyPeriodo, reminder);
			this.cargarPlanificacion(plan);
			return plan;
		}
	}

	private void actualizarEventosPlanificados(List<EventoPlanificado> eventos) {
		if (eventos.size() != 0) {
		try {
			entityManager().getTransaction().begin();
			for(int i = 0; i < eventos.size(); i = i + 1) {
	    		EventoPlanificado evt = eventos.get(i);
	    		Integer id = entityManager().merge(evt).getIdEvento();
				evt.setIdEvento(id);
	    	}
			entityManager().getTransaction().commit();
		} 
		catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
		}
	}

private void generarSugerenciasParaEventosPlanificdos() throws SinDatosClimaException, IOException, SinPrendasException {
		List<EventoPlanificado> noVencidos = this.getEventosPlanificados().stream().filter(ev -> ev.getEstado() != EstadoEvento.VENCIDO).collect(Collectors.toList());
		List<Integer> ids = noVencidos.stream().map(ev -> ev.getIdEvento()).collect(Collectors.toList());
		ids.stream().forEach(id-> {
		try { this.generarSugerenciasParaEventoPlanificado(id);
		} catch (SinDatosClimaException exception) {
			exception.printStackTrace();
		} catch (IOException iOe) {
			iOe.printStackTrace();
		} catch (SinPrendasException pe) {
			pe.printStackTrace();
		}
		});
	}
	
	public void generarSugerenciasParaEventoPlanificado(int idEvt) throws SinDatosClimaException, IOException, SinPrendasException {
		EventoPlanificado event = this.consultarEventoPlanificadoPorId(idEvt);
		if (event.getSugerencias().isEmpty() && (event.getEstado() == EstadoEvento.PENDIENTE || event.getEstado() == EstadoEvento.PROXIMO)) {
			List<Sugerencia> suggestions = Recomendador.getInstance().entregarSugerenciasEvento(event);
			System.out.println("Sugerencias generadas: " + suggestions.size()  + ", para el evento Id: " + event.getIdEvento());
			RepoSugerencias.getInstance().agregarSugerencias(suggestions);
			event.setSugerencias(suggestions);
			event.setEstado(EstadoEvento.PROCESADO);
			this.actualizarEventoPlanificado(event);
		}
	}
	
	private void eliminarSugerenciasEvento(int idEvt) {
		EventoPlanificado event = this.consultarEventoPlanificadoPorId(idEvt);
		if (! event.getSugerencias().isEmpty()) {
			List<Sugerencia> suggestions = event.getSugerencias();
			System.out.println("Sugerencias para eliminar: " + suggestions.size() + ", del evento Id: " + event.getIdEvento());
			entityManager().getTransaction().begin();
			for (int i=0; i < suggestions.size(); i++){
				Sugerencia suge = suggestions.get(i);
				if (suge.getAtuendo() != null) {
					suge.setAtuendo(null);
				}
				if (suge.getCalificacion() != null) {
					suge.setCalificacion(null);
				}
				entityManager().remove(suge);
			}
			event.getSugerencias().clear();
			if (event.getSugerenciaElegida() != null) {
				event.setSugerenciaElegida(null);
			}
			entityManager().getTransaction().commit();
			event.setEstado(EstadoEvento.PENDIENTE);
			this.actualizarEventoPlanificado(event);
			
		}
	}

	private void cambiarEventosAProximo()  throws SinSenderActivoException {
		@SuppressWarnings("unchecked")
		List<EventoPlanificado> planificados = entityManager().createQuery("from Evento where Tipo_Evento = 'EventoPlanificado'").getResultList();
		List<EventoPlanificado> conReminder = planificados.stream().filter(evt->evt.getRecordatorio() != null).collect(Collectors.toList());
		List<EventoPlanificado> proximos = conReminder.stream().filter(evt->evt.estaProximo()).collect(Collectors.toList());
		if (proximos.size() != 0) {
			proximos.stream().forEach(evt -> {
				try {
					this.notifUsuarioProxEvento(evt);
				} catch (SinSenderActivoException e) {
					e.printStackTrace();
				}
			});
			proximos.stream().forEach(evt->evt.setEstado(EstadoEvento.PROXIMO));
			this.actualizarEventosPlanificados(proximos);
		}
	}
	
	private void procesarEventosVencidos() throws SinSenderActivoException {
		@SuppressWarnings("unchecked")
		List<EventoPlanificado> planificados = entityManager().createQuery("from Evento where Tipo_Evento = 'EventoPlanificado'").getResultList();
		List<EventoPlanificado> vencidos = planificados.stream().filter(evt->evt.estaVencido()).collect(Collectors.toList());
		if (vencidos.size() != 0) {
			System.out.println("Hay " + vencidos.size() + " nuevos Eventos Vencidos");
			vencidos.stream().forEach(evt -> {
			try {
				this.notifUsuarioCalifSugerencia(evt);
			} catch (SinSenderActivoException e) {
				e.printStackTrace();
			}
		});
		vencidos.stream().forEach(evt->this.deProximoAVencido(evt));
		vencidos.stream().forEach(evt->this.eliminarEventoPlanificado(evt));
		}
	}
	
	private void deProximoAVencido(EventoPlanificado proximo) {
		RepoEventosHistoricos.getInstance().dePlanificadoAHistorico(proximo);
	}
	
	private void notifUsuarioProxEvento(EventoPlanificado event) throws SinSenderActivoException {
		if (event.estado == EstadoEvento.PENDIENTE) {
			String notificacion = this.notificacionEventoProximo(event);
			ServicioNotificador.getInstance().enviarNotificacion(event, notificacion);
		}
	}
	
	private void notifUsuarioCalifSugerencia(EventoPlanificado event) throws SinSenderActivoException {
		String notificacion = this.notificacionCalificarSugerencia(event);
		ServicioNotificador.getInstance().enviarNotificacion(event, notificacion);
	}
	
	private String notificacionCalificarSugerencia(EventoPlanificado event) throws SinSenderActivoException {
		String nombreUsuario = event.getUsuario().getUserName();
		int idEvt = event.getIdEvento();
		return "Estimado/a " + nombreUsuario + " debe calificar la sugerencia elegida para el evento nro. " + idEvt + ".";
	}

	private String notificacionEventoProximo(EventoPlanificado event) {
		String nombreUsuario = event.getUsuario().getUserName();
		String datosEvento = "Fecha y Hora: " + event.getFechaHoraEvento().toString() + ", Lugar: " + event.getUbicacion() + ", Actividad: " + event.getActividad();
		return  "Estimado/a " + nombreUsuario + ", Ud. tiene el siguiente evento planificado: " + datosEvento 
				+ ". Puede consultar las sugerencias de vestimenta programadas por el sistema Que_Me_Pongo?" ;
	}
		
	private int cantEventosRepetidos(Planificacion plan) {
		long timeInicial = plan.getFechaHoraEvento().toEpochSecond(ZoneOffset.UTC);
		long timeFinal = plan.getFechaHoraFin().toEpochSecond(ZoneOffset.UTC);
		long diferencia = timeFinal - (timeInicial + 60);
		return (int) diferencia / (plan.getUnidadPeriodo().multiplicadorSegundos() * plan.getCantidadPeriodo());
	}

}

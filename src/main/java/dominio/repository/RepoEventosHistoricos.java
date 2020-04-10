package dominio.repository;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import javax.persistence.PersistenceException;

import java.util.List;
import java.util.stream.Collectors;

import dominio.eventos.*;
import dominio.placard.CalificacionSugerencia;
import dominio.usuario.Usuario;

public class RepoEventosHistoricos implements WithGlobalEntityManager {
	
	private static RepoEventosHistoricos instance = null;
	
	private RepoEventosHistoricos(){
	}

	public static RepoEventosHistoricos getInstance() {
	  if (instance == null) {
			instance = new RepoEventosHistoricos();
	  } 
	   return instance;
	 }
	
	//private List<EventoHistorico> eventos = new LinkedList<>();



	@SuppressWarnings("unchecked")
	public List<EventoHistorico> getEventosHistoricos() {
		return entityManager().createQuery("from EventoHistorico").getResultList();
	}
	
	public void agregarEventoHistorico(EventoHistorico event) {
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

	public void agregarEventoHistoricos(List<EventoHistorico> eventos) {
		try {
			entityManager().getTransaction().begin();
			for(int i = 0; i < eventos.size(); i = i + 1) {
				EventoHistorico event = eventos.get(i);
				entityManager().persist(event);
			}
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
	
	public void actualizarEventoHistorico(EventoHistorico event) {
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
	
	public void eliminarEventoHistorico(EventoHistorico event) {
		int idEvt = event.getIdEvento();
		EventoHistorico evt = this.consultarEventoHistoricoPorId(idEvt); 
		try {
			entityManager().getTransaction().begin();
			this.previoDeleteEvento(evt);
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
	
	public void eliminarEventosHistoricosUsuario(Usuario user) {
    	List<EventoHistorico> eventosBorrar = this.consultarEventosHistoricosUsuario(user);
    	try {
    		entityManager().getTransaction().begin();
			for(int i = 0; i < eventosBorrar.size(); i = i + 1) {
	    		EventoHistorico evt = eventosBorrar.get(i);
				this.previoDeleteEvento(evt);
				entityManager().remove(evt);
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
	
	public EventoHistorico consultarEventoHistoricoPorId(int idEvt) {
		return this.getEventosHistoricos().stream().filter(ev -> ev.getIdEvento() == idEvt).collect(Collectors.toList()).get(0);
	}
	
	public List<EventoHistorico> consultarEventosHistoricosUsuario(Usuario user) {
		return this.getEventosHistoricos().stream().filter(ev -> ev.getUsuario().getLogin().equals(user.getLogin())).collect(Collectors.toList());
	}
	
	public void dePlanificadoAHistorico(EventoPlanificado event) {
		EventoHistorico historico = new EventoHistorico();
		historico.setIdPlanificado(event.getIdEvento());
		historico.setFechaHoraEvento(event.getFechaHoraEvento());
		historico.setUbicacion(event.getUbicacion());
		historico.setCaracterVestimenta(event.getCaracterVestimenta());
		historico.setActividad(event.getActividad());
		historico.setUsuario(event.getUsuario());
		historico.setSugerenciaElegida(event.getSugerenciaElegida());
		this.agregarEventoHistorico(historico);
	}
	
	public CalificacionSugerencia getFeedbackSugerenciaEvento(int idEvt) {
		EventoHistorico event = this.consultarEventoHistoricoPorId(idEvt);
		return event.getFeedbackSugerencia();
	}
	
	public void setFeedbackSugerenciaEvento(int idEvt, CalificacionSugerencia calif) {
		EventoHistorico event = this.consultarEventoHistoricoPorId(idEvt);
		event.setFeedbackSugerencia(calif);
		this.actualizarEventoHistorico(event);
	}
	
	public CalificacionSugerencia ultimoFeedbackUsuario(Usuario user) {
		List<EventoHistorico> events = this.consultarEventosHistoricosUsuario(user);
		int idMax = events.stream().map(ev -> ev.getIdEvento()).max(Integer::compare).get();
		return this.consultarEventoHistoricoPorId(idMax).getFeedbackSugerencia();
	}
	
	private void previoDeleteEvento(EventoHistorico evt) {
		if (evt.getSugerenciaElegida() != null) {
			evt.setSugerenciaElegida(null);
		}
		if (evt.getFeedbackSugerencia() != null) {
			evt.setFeedbackSugerencia(null);
		}
		if (evt.getUsuario() != null) {
			evt.setUsuario(null);
		}
	}
	

}
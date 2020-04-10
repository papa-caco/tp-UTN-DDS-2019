package dominio.repository;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.eventos.EscalaTiempo;
import dominio.eventos.Recordatorio;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;


public class RepoReminders implements WithGlobalEntityManager {
	
	private static RepoReminders INSTANCE = null;
	
	private RepoReminders() {
		super();
	}
	
	public static RepoReminders getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new RepoReminders();
		}
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public List<Recordatorio> getRecordatorios() {
		return entityManager().createQuery("from Recordatorio").getResultList();
	}
	
	public Recordatorio consultarRecordatorioPorId(int idReminder) {
		if (this.getRecordatorios().isEmpty()) {
			return null;
		}
		return this.getRecordatorios().stream().filter(reco -> reco.getIdRecordatorio() == idReminder).collect(Collectors.toList()).get(0);
	}
	
	public void agregarRecordatorio(Recordatorio reminder) {
	    try {
	      entityManager().getTransaction().begin();
	      Integer id = entityManager().merge(reminder).getIdRecordatorio();
	      reminder.setIdRecordatorio(id);
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
	
	public void eliminarRecordatorio(Recordatorio reminder) {
	    Integer idReminder = reminder.getIdRecordatorio();
	    Recordatorio reco = this.consultarRecordatorioPorId(idReminder);
	    if (reco != null) {
	    	try {
	    		entityManager().getTransaction().begin();
	    		entityManager().remove(reco);
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
	}
	
	public void actualizarRecordatorio(Recordatorio reminder) {
	    try {
	      entityManager().getTransaction().begin();
	      Integer id = entityManager().merge(reminder).getIdRecordatorio();
	      reminder.setIdRecordatorio(id);
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
	
	public Recordatorio obtenerRecordatorio(int cant, EscalaTiempo escala) {
		List<Recordatorio> reminders = this.getRecordatorios().stream().filter(reco -> reco.getCantidadTiempo() == cant && reco.getUnidad()== escala).collect(Collectors.toList());
		if (! reminders.isEmpty()) {
			return reminders.get(0);
		}
		Recordatorio reminder = new Recordatorio(cant, escala);
		this.agregarRecordatorio(reminder);
		return reminder;
	}
	
	
	
	

}

package dominio.repository;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;
import dominio.placard.CalificacionSugerencia;
import dominio.prenda.ValorTermico;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

public class RepoCalificaciones implements WithGlobalEntityManager {
	
private static RepoCalificaciones INSTANCE = null;
	
	private RepoCalificaciones() {
		super();
	}
	
	public static RepoCalificaciones getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new RepoCalificaciones();
		}
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public List<CalificacionSugerencia> getCalificaciones() {
		return entityManager().createQuery("from CalificacionSugerencia").getResultList();
	}
	
	public CalificacionSugerencia consultarCalificacionPorId(int idCalif) {
		if (this.getCalificaciones().isEmpty()) {
			return null;
		}
		return this.getCalificaciones().stream().filter(calif -> calif.getIdCalificacion() == idCalif).collect(Collectors.toList()).get(0);
	}
	
	public void agregarCalificacion(CalificacionSugerencia calif) {
	    try {
	      entityManager().getTransaction().begin();
	      Integer id = entityManager().merge(calif).getIdCalificacion();
	      calif.setIdCalificacion(id);
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
	
	public void actualizarCalificacion(CalificacionSugerencia calif) {
	    try {
	      entityManager().getTransaction().begin();
	      Integer id = entityManager().merge(calif).getIdCalificacion();
	      calif.setIdCalificacion(id);
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
	
	public void eliminarCalificacion(CalificacionSugerencia calif) {
		Integer idCalif = calif.getIdCalificacion();
		CalificacionSugerencia calific = this.consultarCalificacionPorId(idCalif);
		if (calific.getUsuario() != null) {
			calific.setUsuario(null);
		}
		try {
			entityManager().getTransaction().begin();
			entityManager().remove(calific);
			entityManager().getTransaction().commit();
		} catch (PersistenceException e) {
		    e.printStackTrace();
		    entityManager().getTransaction().rollback();
		    throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		} finally {
		    entityManager().close();
		}
	}
	
	public  CalificacionSugerencia obtengoCalificacion(Usuario user, ValorTermico global, ValorTermico cabeza, ValorTermico cuello, ValorTermico manos) {
		List<CalificacionSugerencia> calificaciones = this.getCalificaciones().stream().filter( calif ->
		calif.getUsuario().getIdUsuario() == user.getIdUsuario() &&
		calif.getSensacionGlobal().getIdValor() == global.getIdValor() &&
		calif.getSensacionCabeza().getIdValor() == cabeza.getIdValor() &&
		calif.getSensacionCuello().getIdValor() == cuello.getIdValor() &&
		calif.getSensacionManos().getIdValor() == manos.getIdValor()).collect(Collectors.toList());
		if (! calificaciones.isEmpty()) {
			return calificaciones.get(0);
		}
		CalificacionSugerencia calific = new CalificacionSugerencia(user, global, cabeza, cuello, manos);
		this.agregarCalificacion(calific);
		return calific;
	}
	
	

}

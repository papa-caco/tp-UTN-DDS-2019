package dominio.repository;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.eventos.Ubicacion;


public class RepoUbicaciones implements WithGlobalEntityManager {
	
	private static RepoUbicaciones INSTANCE = null;
	
	private RepoUbicaciones() {
		super();
	}
	
	public static RepoUbicaciones getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new RepoUbicaciones();
		}
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public List<Ubicacion> getUbicaciones() {
		return entityManager().createQuery("from Ubicacion").getResultList();
	}
	
	public Ubicacion consultarUbicacionPorCiudad(String ciudad) {
		if (this.getUbicaciones().isEmpty()) {
			return null;
		}
		return this.getUbicaciones().stream().filter(ubic -> ubic.getCiudad() == ciudad).collect(Collectors.toList()).get(0);
	}
	
	public Ubicacion consultarUbicacionPorId(int idUbicacion) {
		if (this.getUbicaciones().isEmpty()) {
			return null;
		}
		return this.getUbicaciones().stream().filter(ubic -> ubic.getIdUbicacion() == idUbicacion).collect(Collectors.toList()).get(0);
	}
	
	public void agregarUbicacion(Ubicacion ubicacion) {
	    try {
	      entityManager().getTransaction().begin();
	      Integer id = entityManager().merge(ubicacion).getIdUbicacion();
	      ubicacion.setIdUbicacion(id);
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
	
	public void eliminarUbicacion(Ubicacion ubicacion) {
	    Integer idUbicacion = ubicacion.getIdUbicacion();
	    Ubicacion ubic = this.consultarUbicacionPorId(idUbicacion);
	    if (ubic != null) {
	    	try {
	    		entityManager().getTransaction().begin();
	    		entityManager().remove(ubic);
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
	
	public void actualizarUbicacion(Ubicacion ubicacion) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(ubicacion).getIdUbicacion();
		      ubicacion.setIdUbicacion(id);
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
	
	public Ubicacion obtenerUbicacion(String ciudad, int idAccu) {
		List<Ubicacion> ubicaciones = this.getUbicaciones().stream().filter(ubic -> ubic.getCiudad() == ciudad && ubic.getIdAccu() == idAccu).collect(Collectors.toList());
		if (! ubicaciones.isEmpty()) {
			return ubicaciones.get(0);
		}
		Ubicacion ubicacion = new Ubicacion(ciudad, idAccu);
		this.agregarUbicacion(ubicacion);
		return ubicacion;
	}

}

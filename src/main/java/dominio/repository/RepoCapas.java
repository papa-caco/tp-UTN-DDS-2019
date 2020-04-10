package dominio.repository;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.prenda.*;

public class RepoCapas implements WithGlobalEntityManager {
	
	private static RepoCapas INSTANCE = null;
	
	private RepoCapas() {
		super();
	}
	
	public static RepoCapas getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new RepoCapas();
		}
		return INSTANCE;
	}
	
	@SuppressWarnings("unchecked")
	public List<Interior> getInteriores() {
		return entityManager().createQuery("from Capa where tipo_capa = 'Interior'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Intermedia> getIntermedias() {
		return entityManager().createQuery("from Capa where tipo_capa = 'Intermedia'").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Superior> getSuperiores() {
		return entityManager().createQuery("from Capa where tipo_capa = 'Superior'").getResultList();
	}
	
	public Capa getCapaPorId(int idCapa) {
		@SuppressWarnings("unchecked")
		List<Capa> capas = entityManager().createQuery("from Capa").getResultList();
		List<Capa> seleccion = capas.stream().filter(capa -> capa.getIdCapa() == idCapa).collect(Collectors.toList());
		if (! seleccion.isEmpty()) {
			return seleccion.get(0);
		}
		else {
			return null;
		}
	}
	
	public void agregarCapa(Capa capa) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(capa).getIdCapa();
		      capa.setIdCapa(id);
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
	
	public void actualizarCapa(Capa capa) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(capa).getIdCapa();
		      capa.setIdCapa(id);
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
	
	public void eliminarCapa(Capa capa) {
	    int idCapa = capa.getIdCapa(); 
	    Capa _capa = this.getCapaPorId(idCapa);
	    if (_capa != null) {
	    	try {
	    		entityManager().getTransaction().begin();
	    		entityManager().remove(_capa);
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
	
	public Superior obtengoSuperior(double valor) {
		List<Superior> seleccion = this.getSuperiores().stream().filter(capa -> capa.getUnidades() == valor).collect(Collectors.toList());
		if (! seleccion.isEmpty()) {
			return seleccion.get(0);
		}
		else {
			Superior capa = Superior.of(valor);
			this.agregarCapa(capa);
			return capa;
		}
	}
	
	public Intermedia obtengoIntermedia(double valor) {
		List<Intermedia> seleccion = this.getIntermedias().stream().filter(capa -> capa.getUnidades() == valor).collect(Collectors.toList());
		if (! seleccion.isEmpty()) {
			return seleccion.get(0);
		}
		else {
			Intermedia capa = Intermedia.of(valor);
			this.agregarCapa(capa);
			return capa;
		}
	}
	
	public Interior obtengoInterior(double valor) {
		List<Interior> seleccion = this.getInteriores().stream().filter(capa -> capa.getUnidades() == valor).collect(Collectors.toList());
		if (! seleccion.isEmpty()) {
			return seleccion.get(0);
		}
		else {
			Interior capa = Interior.of(valor);
			this.agregarCapa(capa);
			return capa;
		}
	}
}
package dominio.repository;

import javax.persistence.PersistenceException;

import java.util.List;
import java.util.stream.Collectors;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.prenda.Capa;
import dominio.prenda.Categoria;
import dominio.prenda.Tipo;

public class RepoTipos implements WithGlobalEntityManager {

	private static RepoTipos instance = null;
	
	private RepoTipos(){
	}

	public static RepoTipos getInstance() {
	  if (instance == null) {
			instance = new RepoTipos();
	  } 
	   return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tipo> getTipos() {
		return entityManager().createQuery("from Tipo").getResultList();
	}
	
	public Tipo getTipoPorId(int idTipo) {
		List<Tipo> filtered = this.getTipos().stream().filter(tp -> tp.getIdTipo() == idTipo).collect(Collectors.toList());
		if (! filtered.isEmpty()) {
			return filtered.get(0);
		}
		else {
			return null;
		}
	}
	
	public void agregarTipo(Tipo tipo) {
	    try {
	      entityManager().getTransaction().begin();
	      Integer id = entityManager().merge(tipo).getIdTipo();
	      tipo.setIdTipo(id);
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
	
	public void eliminarTipo(Tipo tipo) {
	    Integer idTipo = tipo.getIdTipo();
	    Tipo type = this.getTipoPorId(idTipo);
	    if (type != null) {
	    	try {
	    		entityManager().getTransaction().begin();
	    		entityManager().remove(type);
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
	
	public void actualizarTipo(Tipo tipo) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(tipo).getIdTipo();
		      tipo.setIdTipo(id);
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
	
	public Tipo getTipoNombre(String nombre) {
		List<Tipo> filtered = this.getTipos().stream().filter(tp -> tp.getTipo().equals(nombre)).collect(Collectors.toList());
		if (! filtered.isEmpty()) {
			return filtered.get(0);
		}
		else {
			return null;
		}
	}
	
	public Tipo obtengoTipoPorNombreCapaCategoria(String nombre, Capa capa, Categoria categoria) {
		List<Tipo> filtered = this.getTipos().stream().filter(tp -> tp.getTipo().equals(nombre) 
				&& tp.getCapa().getIdCapa() == capa.getIdCapa() 
				&& tp.getParaUsarEn().getIdCategoria() == categoria.getIdCategoria()).collect(Collectors.toList());
		if (! filtered.isEmpty()) {
			return filtered.get(0);
		}
		else {
			Tipo tipo = new Tipo(nombre, categoria, capa);
			this.agregarTipo(tipo);
			return tipo;
		}
	}
	
	
}

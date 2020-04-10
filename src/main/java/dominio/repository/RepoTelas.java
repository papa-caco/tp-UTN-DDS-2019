package dominio.repository;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.prenda.Tela;

public class RepoTelas implements WithGlobalEntityManager {
	
	private static RepoTelas instance = null;
	
	private RepoTelas(){
	}

	public static RepoTelas getInstance() {
	  if (instance == null) {
			instance = new RepoTelas();
	  } 
	   return instance;
	 }
	
	@SuppressWarnings("unchecked")
	public List<Tela> getTelas() {
		return entityManager().createQuery("from Tela").getResultList();
	}
	
	public Tela getTelaMaterial(String material) {
		List<Tela> filtered = this.getTelas().stream().filter(tl -> tl.getMaterial().equals(material)).collect(Collectors.toList());
		return filtered.get(0);
	}
	
	public Tela getTelaPorId(int idTela) {
		@SuppressWarnings("unchecked")
		List<Tela> telas = entityManager().createQuery("from Tela").getResultList();
		List<Tela> seleccion = telas.stream().filter(tela -> tela.getIdTela() == idTela).collect(Collectors.toList());
		if (! seleccion.isEmpty()) {
			return seleccion.get(0);
		}
		else {
			return null;
		}
	}
	
	public void agregarTela(Tela tela) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(tela).getIdTela();
		      tela.setIdTela(id);
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
	
	public void actualizarTela(Tela tela) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(tela).getIdTela();
		      tela.setIdTela(id);
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
	
	public void eliminarTela(Tela tela) {
	    int idTela = tela.getIdTela(); 
	    Tela _tela = this.getTelaPorId(idTela);
	    if (_tela != null) {
	    	try {
	    		entityManager().getTransaction().begin();
	    		entityManager().remove(_tela);
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
	
	
	
	
}

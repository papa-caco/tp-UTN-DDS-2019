package dominio.repository;

import java.util.List;
import java.util.Optional;

//import com.google.common.collect.Lists;
import javax.persistence.PersistenceException;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.prenda.Prenda;


public class RepoPrendas implements WithGlobalEntityManager {
	
	private static RepoPrendas instance = null;

	private RepoPrendas(){
	}
	
	public static RepoPrendas getInstance() {
		  if (instance == null) {
				instance = new RepoPrendas();
		  } 
		   return instance;
		 }
	
	@SuppressWarnings("unchecked")
	public List<Prenda> getPrendas() {
		return entityManager().createQuery("from Prenda").getResultList();
	}
	
	public void agregarPrenda(Prenda prenda) {
		try {
				entityManager().getTransaction().begin();
				entityManager().persist(prenda);
				entityManager().getTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		} finally {
			entityManager().close();
		}
	}
	
	public void quitarPrenda(Prenda prenda) {
		try {
			entityManager().remove(prenda);
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
	}
	
	public void actualizarPrenda(Prenda prenda) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(prenda).getIdPrenda();
			prenda.setIdPrenda(id);
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
	
	public Prenda buscoPrendaNombre(String nombre) {
		Optional<Prenda> prendaABuscar = this.getPrendas().stream().filter(pr -> pr.getNombre().equals(nombre)).findFirst();
		if (prendaABuscar.isPresent()) {
            return prendaABuscar.get();
        }
        return null;
    }
	
}

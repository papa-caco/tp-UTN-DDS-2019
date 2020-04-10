package dominio.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import javax.persistence.PersistenceException;

import dominio.placard.Placard;

public class RepoPlacards implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	
	private static RepoPlacards instance = null;
	
	private RepoPlacards(){
	}

	public static RepoPlacards getInstance() {
	  if (instance == null) {
			instance = new RepoPlacards();
	  } 
	   return instance;
	 }
	
	@SuppressWarnings("unchecked")
	public List<Placard> getPlacards() {
		return entityManager().createQuery("from Placard").getResultList();
	}
	
	public void agregarPlacard(Placard placard) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(placard).getIdPlacard();
			placard.setIdPlacard(id);
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
	
	public void actualizarPlacard(Placard placard) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(placard).getIdPlacard();
			placard.setIdPlacard(id);
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
	
	public void quitarPlacard(Placard placard) {
		try {
			entityManager().remove(placard);
		} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
	}
	
	public Placard buscoPlacardNombre(String name) {
		return this.getPlacards().stream().filter(plac ->plac.getNombre().equals(name))
					.collect(Collectors.toList()).get(0);
	}
	
	public Placard buscoPlacardPorId(int idPlcard) {
		List<Placard> seleccion = this.getPlacards().stream().filter(plac ->plac.getIdPlacard() == idPlcard).collect(Collectors.toList());
		if (! seleccion.isEmpty()) {
			return seleccion.get(0);
		}
		else {
			return null;
		}
	}
	
}

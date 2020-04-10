package dominio.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.placard.EstadoSugerencia;
import dominio.placard.Placard;
import dominio.placard.Sugerencia;
import dominio.prenda.Atuendo;

public class RepoSugerencias implements WithGlobalEntityManager {
	
	private static RepoSugerencias instance = null;
	
	private RepoSugerencias(){
	}

	public static RepoSugerencias getInstance() {
	  if (instance == null) {
			instance = new RepoSugerencias();
	  } 
	   return instance;
	 }
	
	@SuppressWarnings("unchecked")
	public List<Sugerencia> getSugerencias() {
		return entityManager().createQuery("from Sugerencia").getResultList();
	}
	
	public Sugerencia buscoSugerenciaPorId(int id) {
		return this.getSugerencias().stream().filter(suge ->suge.getIdSugerencia() == id).collect(Collectors.toList()).get(0);
	}
	
	public List<Sugerencia> buscoSugerenciaPorIdAtuendo(Integer idAtuendo) {
		Atuendo atu = RepoAtuendos.getInstance().buscoAtuendoPorId(idAtuendo);
		return this.getSugerencias().stream().filter(suge ->suge.getAtuendo() == atu).collect(Collectors.toList());
	}
		
	public List<Sugerencia> sugerenciasConEstado(EstadoSugerencia estado) {
		return this.getSugerencias().stream().filter(suge ->suge.getEstado() == estado).collect(Collectors.toList());
	}
	
	public void agregarSugerencia(Sugerencia suge) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(suge).getIdSugerencia();
			suge.setIdSugerencia(id);
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
		
	public void agregarSugerencias(List<Sugerencia> sugerencias) {
		if (sugerencias.size() != 0) {
			try {
				entityManager().getTransaction().begin();
				for(int i = 0; i < sugerencias.size(); i = i + 1) {
					Sugerencia suge = sugerencias.get(i);
					Integer id = entityManager().merge(suge).getIdSugerencia();
					suge.setIdSugerencia(id);
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
	}
	
	public void generarSugerenciasConAtuendos(List<Atuendo> atuendos) {
		try {
			entityManager().getTransaction().begin();
			for(int i = 0; i < atuendos.size(); i = i + 1) {
				Atuendo atu = atuendos.get(i);
				Sugerencia suge = new Sugerencia(atu);
				entityManager().persist(suge);
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
	
	public void actualizarSugerencia(Sugerencia sugerencia) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(sugerencia).getIdSugerencia();
			sugerencia.setIdSugerencia(id);
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
	
	public void eliminarSugerencia(Sugerencia sugerencia) {
		int idSuge = sugerencia.getIdSugerencia();
		Sugerencia suge = this.buscoSugerenciaPorId(idSuge); 
		try {
			entityManager().getTransaction().begin();
			if (suge.getAtuendo() != null) {
				suge.setAtuendo(null);
			}
			if (suge.getCalificacion() != null) {
				suge.setCalificacion(null);
			}
			entityManager().remove(suge);
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
	
	public void eliminarSugerencias(List<Sugerencia> sugerencias) {
    	try {
    		entityManager().getTransaction().begin();
			for(int i = 0; i < sugerencias.size(); i = i + 1) {
				Sugerencia suge = sugerencias.get(i);
				if (suge.getAtuendo() != null) {
					suge.setAtuendo(null);
				}
				if (suge.getCalificacion() != null) {
					suge.setCalificacion(null);
				}
				entityManager().remove(suge);
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
	
	public boolean contieneSugerenciaConPrendaDePlacard(int idPlacard) {
		Placard placard = RepoPlacards.getInstance().buscoPlacardPorId(idPlacard);
		return ! this.getSugerencias().stream().filter(suge -> suge.contienePrendasDePlacard(placard)).collect(Collectors.toList()).isEmpty();
	}

}

package dominio.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import javax.persistence.PersistenceException;

import dominio.excepciones.SinPrendasException;
import dominio.placard.Placard;
import dominio.placard.Sugerencia;
import dominio.prenda.Atuendo;
import dominio.prenda.Prenda;

public class RepoAtuendos  implements WithGlobalEntityManager {
	
	private static RepoAtuendos instance = null;
	
	private RepoAtuendos(){
	}

	public static RepoAtuendos getInstance() {
	  if (instance == null) {
			instance = new RepoAtuendos();
	  } 
	   return instance;
	 }
	
	@SuppressWarnings("unchecked")
	public List<Atuendo> getAtuendos() {
		return entityManager().createQuery("from Atuendo").getResultList();
	}
	
	public void agregarAtuendo(Atuendo atuendo) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(atuendo).getIdAtuendo();
			atuendo.setIdAtuendo(id);
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
	
	public void agregarAtuendos(List<Atuendo> atuendos) {
		try {
			entityManager().getTransaction().begin();
			for(int i = 0; i < atuendos.size(); i = i + 1) {
				Atuendo atuendo = atuendos.get(i);
				Integer id = entityManager().merge(atuendo).getIdAtuendo();
				atuendo.setIdAtuendo(id);
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
	
	public void agregarAtuendosPlacard(Placard placard) throws SinPrendasException {
		List<Atuendo> atuendos = new ArrayList<>();
		try {
			atuendos = placard.generoAtuendosBasicos();
			entityManager().getTransaction().begin();
			for(int i = 0; i < atuendos.size(); i = i + 1) {
				Atuendo atuendo = atuendos.get(i);
				entityManager().persist(atuendo);
			}
			entityManager().getTransaction().commit();
			} catch (SinPrendasException e) {
				e.printStackTrace();
			} catch (PersistenceException e) {
			e.printStackTrace();
			entityManager().getTransaction().rollback();
			throw new RuntimeException("Ocurrio un error, la operacion no puede completarse", e);
		}
		finally {
			entityManager().close();
		}
	}
	
	public void actualizarAtuendo(Atuendo atuendo) {
		try {
			entityManager().getTransaction().begin();
			Integer id = entityManager().merge(atuendo).getIdAtuendo();
			atuendo.setIdAtuendo(id);
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
	
	public void eliminarAtuendo(Atuendo atuendo) {
		int idAtu = atuendo.getIdAtuendo();
		Atuendo atu = this.buscoAtuendoPorId(idAtu); 
		try {
			entityManager().getTransaction().begin();
			List<Prenda> prendas = atu.getPrendasAtuendo();
			if (prendas != null) {
				atu.getPrendasAtuendo().clear();
			}
			if (atu.getPlacard() != null) {
				atu.setPlacard(null);
			}
			List<Sugerencia> sugerencias = RepoSugerencias.getInstance().buscoSugerenciaPorIdAtuendo(atu.getIdAtuendo());
			for(int i = 0; i < sugerencias.size(); i = i + 1) {
				Sugerencia suge = sugerencias.get(i);
				suge.setAtuendo(null);
				if (suge.getCalificacion() != null) {
					suge.setCalificacion(null);
				}
				entityManager().remove(suge);
	    	}
			//System.out.println("Atuendo Id= " + atu.getIdAtuendo() + " del RepoAtuendos contiene " + atu.getPrendasAtuendo().size() + " prendas");
			entityManager().remove(atu);
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
	
	public void eliminarAtuendosPlacard(Placard placard) {
    	List<Atuendo> atuendosBorrar = RepoAtuendos.getInstance().buscoAtuendosPlacard(placard);
    	//System.out.println(placard.getNombre() + " " + atuendosBorrar.size() );
    	try {
    		entityManager().getTransaction().begin();
			for(int i = 0; i < atuendosBorrar.size(); i = i + 1) {
	    		Atuendo atu = atuendosBorrar.get(i);
	    		List<Prenda> prendas = atu.getPrendasAtuendo();
	    		if (prendas != null) {
	    			atu.getPrendasAtuendo().clear();
	    		}
	    		if (atu.getPlacard() != null) {
	    			atu.setPlacard(null);
	    		}
	    		List<Sugerencia> sugerencias = RepoSugerencias.getInstance().buscoSugerenciaPorIdAtuendo(atu.getIdAtuendo());
				for(int j = 0; j < sugerencias.size(); j = j + 1) {
					Sugerencia suge = sugerencias.get(j);
					suge.setAtuendo(null);
					if (suge.getCalificacion() != null) {
						suge.setCalificacion(null);
					}
					entityManager().remove(suge);
		    	}
	    		entityManager().remove(atu);
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

	public Atuendo buscoAtuendoPorId(Integer id) {
		return getAtuendos().stream().filter(atu ->atu.getIdAtuendo() == id).collect(Collectors.toList()).get(0);
	}
	
	public  List<Atuendo> buscoAtuendosPlacard(Placard placard) {
		return getAtuendos().stream().filter(atu ->atu.getPlacard().getIdPlacard() == placard.getIdPlacard()).collect(Collectors.toList());
	}
	
	public boolean contienePrendaId(int idPrenda) {
		List<Atuendo> seleccion = this.getAtuendos().stream().filter(atu -> atu.contienePrendaId(idPrenda)).collect(Collectors.toList());
		return (! seleccion.isEmpty());
	}
	
}
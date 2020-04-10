package dominio.repository;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.prenda.Color;

public class RepoColores implements WithGlobalEntityManager {
	
private static RepoColores instance = null;
	
	private RepoColores(){
	}

	public static RepoColores getInstance() {
	  if (instance == null) {
			instance = new RepoColores();
	  } 
	   return instance;
	 }
	
	@SuppressWarnings("unchecked")
	public List<Color> getColores() {
		return entityManager().createQuery("from Color").getResultList();
	}
	
	public Color getColorPorId(int idColor) {
		@SuppressWarnings("unchecked")
		List<Color> colores = entityManager().createQuery("from Color").getResultList();
		List<Color> seleccion = colores.stream().filter(color -> color.getIdColor() == idColor).collect(Collectors.toList());
		if (! seleccion.isEmpty()) {
			return seleccion.get(0);
		}
		else {
			return null;
		}
	}
	
	public Color getColor(String deColor) {
		List<Color> filtered = this.getColores().stream().filter(cl -> cl.getDeColor().equals(deColor)).collect(Collectors.toList());
		return filtered.get(0);
	}
	
	
	public void agregarColor(Color color) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(color).getIdColor();
		      color.setIdColor(id);
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
	
	public void actualizarColor(Color color) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(color).getIdColor();
		      color.setIdColor(id);
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
	
	public void eliminarColor(Color color) {
	    int idColor = color.getIdColor(); 
	    Color _color = this.getColorPorId(idColor);
	    if (_color != null) {
	    	try {
	    		entityManager().getTransaction().begin();
	    		entityManager().remove(_color);
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
	
	public Color obtengoColor(String deColor) {
		List<Color> seleccion = this.getColores().stream().filter(col -> col.getDeColor().equals(deColor)).collect(Collectors.toList());
		if (! seleccion.isEmpty()) {
			return seleccion.get(0);
		}
		else {
			Color color = Color.of(deColor);
			this.agregarColor(color);
			return color;
		}
	}

}



package dominio.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import dominio.usuario.*;

public class RepoUsuarios implements WithGlobalEntityManager {
	
	private static RepoUsuarios instance = null;
				
	private RepoUsuarios() {
			}

	public static RepoUsuarios getInstance() {
	  if (instance == null) {
			instance = new RepoUsuarios();
	  } 
	   return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuarios() {
			return entityManager().createQuery("from Usuario").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosConCondicion(String userBuscado) {
		
		Query query = entityManager()
				.createQuery("from Usuario where login LIKE "
					+ "'"+userBuscado+"'", Usuario.class);
		List<Usuario> users = query.getResultList();
		return users;
	}

	public void agregarUsuario(Usuario usuario) {
		String loginUsuario = usuario.getLogin();
	/*	Usuario usuarioAux = this.buscarPorLogin(loginUsuario);
		if (usuarioAux != null) {
			throw new RuntimeException("Usuario ya existente");
		} else {
			entityManager().persist(usuario);
		}
		*/
	}
	
	public void actualizarUsuario(Usuario user) {
	    try {
		      entityManager().getTransaction().begin();
		      Integer id = entityManager().merge(user).getIdUsuario();
		      user.setIdUsuario(id);
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
	
	public void quitarUsuario(Usuario user) {
	    int idUser = user.getIdUsuario(); 
	    Usuario usuario = this.buscarPorId(idUser);
	    if (usuario != null) {
	    	try {
	    		entityManager().getTransaction().begin();
	    		entityManager().remove(usuario);
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
	
	public Usuario buscarPorLogin(String login) {
		List<Usuario> users = this.getUsuarios().stream().filter(user -> user.getLogin().equals(login)).collect(Collectors.toList());
		if (users.isEmpty()) { 
			return null;
		}
			else return users.get(0);
	}
	
	public Usuario buscarPorId(int idUser) {
		List<Usuario> users = this.getUsuarios().stream().filter(user -> user.getIdUsuario() == idUser).collect(Collectors.toList());
		if (users.isEmpty()) { 
			return null;
		}
			else return users.get(0);
	}
	
	public Usuario obtengoUsuarioPorUserName(String userName) {
		List<Usuario> users = this.getUsuarios().stream().filter(user -> user.getUserName().equals(userName)).collect(Collectors.toList());
		if (users.isEmpty()) { 
			return null;
		}
			else return users.get(0);
	}
}
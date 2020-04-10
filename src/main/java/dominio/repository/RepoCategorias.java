package dominio.repository;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

import dominio.prenda.Categoria;

public class RepoCategorias {

	
	private static RepoCategorias instance = null;
	
	private RepoCategorias() {
		categorias.addAll(Lists.newArrayList(Categoria.ACCESORIO,Categoria.CABEZA,Categoria.CUELLO,Categoria.GLOBAL,Categoria.MANO,Categoria.PIE,Categoria.PIERNAS,Categoria.TORSO));
	}

	public static RepoCategorias getInstance() {
	  if (instance == null) {
			instance = new RepoCategorias();
	  } 
	   return instance;
	}

	private List<Categoria> categorias = new LinkedList<>();

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
}

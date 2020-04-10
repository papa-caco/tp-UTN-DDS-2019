package dominio.repository;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

import dominio.prenda.CaracterPrenda;

public class RepoUsoPrendas {
	
private static RepoUsoPrendas instance = null;
	
	private RepoUsoPrendas() {
		usos.addAll(Lists.newArrayList(CaracterPrenda.DEPORTIVO,CaracterPrenda.CASUAL,CaracterPrenda.FORMAL));
	}

	public static RepoUsoPrendas getInstance() {
	  if (instance == null) {
			instance = new RepoUsoPrendas();
	  } 
	   return instance;
	}

	private List<CaracterPrenda> usos = new LinkedList<>();
	
	public List<CaracterPrenda> getUsos() {
		return usos;
	}
	
	public void setUsos(List<CaracterPrenda> usoPrendas) {
		this.usos = usoPrendas;
	}



}

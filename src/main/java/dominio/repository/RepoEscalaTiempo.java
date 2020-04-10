package dominio.repository;

import java.util.LinkedList;
import java.util.List;
import com.google.common.collect.Lists;

import dominio.eventos.EscalaTiempo;


public class RepoEscalaTiempo {
	
	private static RepoEscalaTiempo instance = null;
	
	private RepoEscalaTiempo() {
		unidadesTiempo.addAll(Lists.newArrayList(EscalaTiempo.MINUTOS,EscalaTiempo.HORAS,EscalaTiempo.DIAS,EscalaTiempo.SEMANAS,EscalaTiempo.MESES,EscalaTiempo.ANIOS));
	}

	public static RepoEscalaTiempo getInstance() {
	  if (instance == null) {
			instance = new RepoEscalaTiempo();
	  } 
	   return instance;
	}

	private List<EscalaTiempo> unidadesTiempo = new LinkedList<>();
	
	public List<EscalaTiempo> getUnidadesTiempo() {
		return this.unidadesTiempo;
	}
	
	public void addUnidadTiempo(EscalaTiempo unidad) {
		this.unidadesTiempo.add(unidad);
	}
	
	

}

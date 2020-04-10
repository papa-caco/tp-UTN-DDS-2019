package dominio.repository;

import java.util.LinkedList;
import java.util.List;
import com.google.common.collect.Lists;

import dominio.prenda.ValorTermico;

public class RepoValoresTermicos {
	
	private static RepoValoresTermicos instance = null;
	
	private RepoValoresTermicos() {
		valoresTermicos.addAll(Lists.newArrayList(ValorTermico.SIN_CALIFICAR,ValorTermico.FRIA,ValorTermico.APROPIADA,ValorTermico.CALUROSA));
	}

	public static RepoValoresTermicos getInstance() {
	  if (instance == null) {
			instance = new RepoValoresTermicos();
	  } 
	   return instance;
	}

	private List<ValorTermico> valoresTermicos = new LinkedList<>();

	public List<ValorTermico> getValoresTermicos() {
		return valoresTermicos;
	}

	public void setValoresTermicos(List<ValorTermico> valores) {
		this.valoresTermicos = valores;
	}


}

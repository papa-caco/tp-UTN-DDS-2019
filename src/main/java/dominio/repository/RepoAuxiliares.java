package dominio.repository;

import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import dominio.webapp.request.Auxiliar;

@Getter
@Setter(AccessLevel.PUBLIC)
public class RepoAuxiliares {
	
	private static RepoAuxiliares INSTANCE = null;
	
	private RepoAuxiliares() {
		super();
	}
	
	public static RepoAuxiliares getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new RepoAuxiliares();
		}
		return INSTANCE;
	}
	
	private List<Auxiliar> valores = new LinkedList<Auxiliar>();
	
}

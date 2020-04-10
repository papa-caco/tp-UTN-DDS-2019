package dominio.webapp.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Auxiliar {
	
	private String estaOk = null;

	private Integer valorNumerico = null;
	
	public Auxiliar(String valor) {
		this.estaOk = valor;
	}
	
	public Auxiliar(int valor) {
		this.valorNumerico = valor;
	}

}

package dominio.webapp.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import dominio.placard.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class RequestSugerencia {
	
	private Integer idSugerencia;
	private List<String> prendas = new LinkedList<String>();
	private List<String> tipos = new LinkedList<String>();
	private String estado;
	private String idCalificacion = "Sin Calificar";
	
	public RequestSugerencia(Sugerencia sugerencia) {
		this.idSugerencia = sugerencia.getIdSugerencia();
		this.prendas = sugerencia.getAtuendo().getNombrePrendas();
		this.tipos = sugerencia.getAtuendo().getTiposAtuendo();
		this.estado = sugerencia.getEstado().name();
		if (sugerencia.getCalificacion() != null) {
			this.idCalificacion = sugerencia.getCalificacion().getIdCalificacion().toString();
		}
	}

	
}

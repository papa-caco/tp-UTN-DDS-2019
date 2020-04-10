package dominio.webapp.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import dominio.placard.CalificacionSugerencia;
import dominio.placard.Sugerencia;
import dominio.prenda.ValorTermico;
import dominio.repository.RepoCalificaciones;
import dominio.repository.RepoUsuarios;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class RequestCalificacion {
	private String usuario;
	private int idCalificacion;
	private String idSugerencia;
	private String sensacionGlobal;
	private String sensacionCabeza;
	private String sensacionCuello;
	private String sensacionManos;
	
	public CalificacionSugerencia getCalificacionFromThis() {
		CalificacionSugerencia calificacion = RepoCalificaciones.getInstance().obtengoCalificacion(RepoUsuarios.getInstance().obtengoUsuarioPorUserName(this.usuario),
			ValorTermico.valueOf(sensacionGlobal),
			ValorTermico.valueOf(sensacionCabeza),
			ValorTermico.valueOf(sensacionCuello),
			ValorTermico.valueOf(sensacionManos));
		return calificacion;
	}
	
	public RequestCalificacion(CalificacionSugerencia calificacion) {
		this.idCalificacion = calificacion.getIdCalificacion(); 
		this.usuario = calificacion.getUsuario().getUserName();
		this.idSugerencia = "";
		this.sensacionGlobal = calificacion.getSensacionGlobal().getValor();
		this.sensacionCabeza = calificacion.getSensacionCabeza().getValor();
		this.sensacionCuello =calificacion.getSensacionCuello().getValor();
		this.sensacionManos = calificacion.getSensacionManos().getValor();
	}
	
	public RequestCalificacion(Sugerencia sugerencia) {
		this.idCalificacion = sugerencia.getCalificacion().getIdCalificacion(); 
		this.usuario = sugerencia.getCalificacion().getUsuario().getUserName();
		this.idSugerencia = sugerencia.getIdSugerencia().toString();
		this.sensacionGlobal = sugerencia.getCalificacion().getSensacionGlobal().getValor();
		this.sensacionCabeza = sugerencia.getCalificacion().getSensacionCabeza().getValor();
		this.sensacionCuello =sugerencia.getCalificacion().getSensacionCuello().getValor();
		this.sensacionManos = sugerencia.getCalificacion().getSensacionManos().getValor();
	}
}

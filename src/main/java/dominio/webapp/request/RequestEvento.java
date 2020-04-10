package dominio.webapp.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.format.DateTimeFormatter;

import dominio.eventos.*;
import dominio.placard.Sugerencia;
import dominio.prenda.CaracterPrenda;
import dominio.repository.RepoReminders;
import dominio.repository.RepoSugerencias;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class RequestEvento{
	
	private int idEvento;
	private String fechaHoraEvento;
	private String ubicacion;
	private String caracter;
	private String actividad;
	private String userName;
	private Integer cantidadTiempo = 0;
	private String unidad;
	private String repetitivo;
	private int cantidadPeriodo = 0;
	private String unidadPeriodo;
	private String fechaHoraFin;
	private String estado;
	private String idSugerenciaElegida = "Sin Seleccionar";
	private String calificacionSugerencia = "Sin Calificar";
	
	public void completoIdSugerenciaElegida(Integer idSugerencia) {
		String valor = idSugerencia.toString();
		this.idSugerenciaElegida = valor;
	}
	
	public void completoCalificacionSugerencia(int idSugerencia) {
		Sugerencia sugerencia = RepoSugerencias.getInstance().buscoSugerenciaPorId(idSugerencia);
		if (sugerencia != null && sugerencia.getCalificacion() != null ) {
			this.calificacionSugerencia = sugerencia.getCalificacion().getIdCalificacion().toString();
		}
	}
	
	public RequestEvento(Evento evento){
		this.setIdEvento(evento.getIdEvento());
		this.setFechaHoraEvento(evento.getFechaHoraEvento().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm")));
		this.setUbicacion(evento.getUbicacion());
		this.setCaracter(evento.getCaracterVestimenta().name());
		this.setActividad(evento.getActividad());
		this.setUserName(evento.getUsuario().getUserName());
		this.setEstado(evento.getEstado().name());
		if ( evento.getSugerenciaElegida() != null) { 
			this.completoIdSugerenciaElegida(evento.getSugerenciaElegida().getIdSugerencia());
			this.completoCalificacionSugerencia(evento.getSugerenciaElegida().getIdSugerencia());
		}
	}
		
	public EventoPlanificado getEventoPlanificadofromThis() {
		Recordatorio reminder = RepoReminders.getInstance().obtenerRecordatorio(this.cantidadTiempo, EscalaTiempo.valueOf(this.unidad));
		EventoPlanificado planificado = Planificador.getInstance().obtengoEventoPlanificado(this.fechaHoraEvento, this.ubicacion, 
			CaracterPrenda.valueOf(this.caracter), this.actividad, this.userName, reminder);
		return planificado;
	}
	
	public Planificacion getPlanificacionfromThis() {
		Recordatorio reminder = RepoReminders.getInstance().obtenerRecordatorio(this.cantidadTiempo, EscalaTiempo.valueOf(this.unidad));
		Planificacion plan = Planificador.getInstance().obtengoPlanificacion(this.fechaHoraEvento, this.ubicacion, CaracterPrenda.valueOf(this.caracter), this.actividad, this.userName,
				this.fechaHoraFin, EscalaTiempo.valueOf(this.unidadPeriodo), this.cantidadPeriodo, reminder);
		return plan;
	}
}

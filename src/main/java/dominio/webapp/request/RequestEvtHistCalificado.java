package dominio.webapp.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import dominio.eventos.*;
import dominio.placard.*;
import dominio.prenda.Prenda;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class RequestEvtHistCalificado {
	
	private int idEvento;
	private String fechaHoraEvento;
	private String ubicacion;
	private String caracter;
	private String actividad;
	private String userName;
	private Integer cantidadTiempo;
	private String unidad;
	private String repetitivo;
	private int cantidadPeriodo;
	private String unidadPeriodo;
	private String fechaHoraFin;
	private String estado;
	private int idSugerenciaElegida;
	private int idCalificacionSugerencia;
	private List<String> prendasAtuendo = new LinkedList<>();
	private List<String> valoresCalificacion  = new LinkedList<>();
	
	public RequestEvtHistCalificado(EventoHistorico evento){
		this.setIdEvento(evento.getIdEvento());
		this.setFechaHoraEvento(evento.getFechaHoraEvento().format(DateTimeFormatter.ofPattern("dd-MM-YY HH:mm")));
		this.setUbicacion(evento.getUbicacion());
		this.setCaracter(evento.getCaracterVestimenta().name());
		this.setActividad(evento.getActividad());
		this.setUserName(evento.getUsuario().getUserName());
		this.setEstado(evento.getEstado().name());
		Sugerencia sugerencia = evento.getSugerenciaElegida();
		this.idSugerenciaElegida = sugerencia.getIdSugerencia();
		List<Prenda> prendas = sugerencia.getAtuendo().getPrendasAtuendo();
		this.prendasAtuendo = prendas.stream().map(pr -> pr.getTipo().getTipo()).collect(Collectors.toList());
		CalificacionSugerencia calificacion = sugerencia.getCalificacion();
		this.idCalificacionSugerencia = calificacion.getIdCalificacion();
		this.valoresCalificacion.addAll(Lists.newArrayList(calificacion.getSensacionGlobal().getValor(),
			calificacion.getSensacionCabeza().getValor(),calificacion.getSensacionCuello().getValor(),
			calificacion.getSensacionManos().getValor()));
	}
}

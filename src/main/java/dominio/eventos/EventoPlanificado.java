package dominio.eventos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.NonNull;
import lombok.Setter;

import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import dominio.usuario.*;
import dominio.placard.*;
import dominio.prenda.CaracterPrenda;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue(value = "EventoPlanificado")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class EventoPlanificado extends Evento {
	
	@JoinColumn(name = "IdRecordatorio")
    @ManyToOne(cascade = CascadeType.REMOVE)
	protected Recordatorio recordatorio = null;
	
	@ManyToMany(cascade = CascadeType.REMOVE, fetch=FetchType.LAZY)
	private List<Sugerencia> sugerencias = new LinkedList<Sugerencia>();
		
	public EventoPlanificado(String dateTime, String ubicacion,CaracterPrenda caracter, String actividad) {
		super(dateTime, ubicacion, caracter, actividad);
		this.estado = EstadoEvento.PENDIENTE;
		this.sugerenciaElegida = new Sugerencia();
	}

	public EventoPlanificado(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad, Recordatorio reminder) {
		super(dateTime, ubicacion, caracter, actividad);
		this.recordatorio = reminder;
		this.estado = EstadoEvento.PENDIENTE;
		this.sugerenciaElegida = new Sugerencia();
	}

	public EventoPlanificado(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad, String usuario) {
		super(dateTime, ubicacion, caracter, actividad, usuario);
	}
	
	public EventoPlanificado(LocalDateTime dateTime, String ubicacion, CaracterPrenda caracter,
											String actividad, Usuario usuario) {
		super(dateTime, ubicacion, caracter, actividad, usuario);
		this.estado = EstadoEvento.PENDIENTE;
		this.sugerenciaElegida = new Sugerencia();
	}
	
	public EventoPlanificado(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad, 
										String usuario, Recordatorio reminder) {
		super(dateTime, ubicacion, caracter, actividad, usuario);
		this.recordatorio = reminder;
	}
	
	public EventoPlanificado(LocalDateTime dateTime, String ubicacion, CaracterPrenda caracter, String actividad, Usuario usuario, Recordatorio reminder) {
		super(dateTime, ubicacion, caracter, actividad, usuario);
		this.recordatorio = reminder;
		this.estado = EstadoEvento.PENDIENTE;
		this.sugerenciaElegida = null;
	}
	
	@Override
	public boolean estaProximo() {
		long epochNow = (long) LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
		long epochDateTimeEvt = (long) this.fechaHoraEvento.toEpochSecond(ZoneOffset.UTC);
		long diferencia = epochDateTimeEvt - epochNow;
		if (this.recordatorio != null) {
			return this.recordatorio.segundosTotales() >= diferencia && diferencia >= 0;
		}
			return false;
	}
	
	@Override
	public boolean estaVencido() {
		long diferencia = fechaHoraEvento.compareTo(LocalDateTime.now());
		return diferencia < 0 ;
	}
	
	public boolean contieneSugerencia(int idSugerencia) {
		return ! this.sugerencias.stream().filter(suge -> suge.getIdSugerencia() == idSugerencia).collect(Collectors.toList()).isEmpty();
	}

}

package dominio.eventos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import dominio.prenda.CaracterPrenda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue(value = "Planificacion")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Planificacion extends EventoPlanificado {

	public Planificacion(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad,
			String fechaHoraFin, EscalaTiempo periodicidad, Recordatorio reminder) {
		super(dateTime, ubicacion, caracter, actividad,reminder);
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		this.fechaHoraFin = LocalDateTime.parse(fechaHoraFin, formatter);
		this.unidadPeriodo = periodicidad;
	}
	
	public Planificacion(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad, String usuario,
			String fechaHoraFin, EscalaTiempo unitPeriodo, int qtyPeriodo) {
		super(dateTime, ubicacion, caracter, actividad, usuario);
		this.fechaHoraFin = LocalDateTime.parse(fechaHoraFin, DateTimeFormatter.ISO_DATE_TIME);
		this.unidadPeriodo = unitPeriodo;
		this.cantidadPeriodo = qtyPeriodo;
	}

	public Planificacion(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad, String usuario,
			String fechaHoraFin, EscalaTiempo unitPeriodo, int qtyPeriodo, Recordatorio reminder) {
		super(dateTime, ubicacion, caracter, actividad, usuario, reminder);
		this.unidadPeriodo = unitPeriodo;
		this.cantidadPeriodo = qtyPeriodo;
		if (! fechaHoraFin.equals("")) {
			this.fechaHoraFin = LocalDateTime.parse(fechaHoraFin, DateTimeFormatter.ISO_DATE_TIME);
		}
	}

	@Column
	private LocalDateTime fechaHoraFin = LocalDateTime.now().plusYears(1);
		
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "IdTimeScale")
	private EscalaTiempo unidadPeriodo;
	
	@Column
	private int cantidadPeriodo;
		



}

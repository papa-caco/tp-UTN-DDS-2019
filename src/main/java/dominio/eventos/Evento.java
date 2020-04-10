package dominio.eventos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dominio.usuario.*;
import dominio.placard.*;
import dominio.prenda.CaracterPrenda;
import dominio.repository.RepoUsuarios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(name = "Evento")
@Table(name = "eventos")
@DiscriminatorColumn(name = "Tipo_Evento")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public abstract class Evento {
	
	@Id
	@GeneratedValue
	@Column(name = "IdEvento")
	protected int idEvento;
	
	@NonNull
	protected LocalDateTime fechaHoraEvento;
	
	@Column
	protected String ubicacion = null;
	
	@Column
	protected String actividad = null;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "IdCaracterPrendas")
	protected CaracterPrenda caracterVestimenta = null;
	
	@JoinColumn(name = "IdUsuario")
    @ManyToOne(cascade = CascadeType.REMOVE)
	protected Usuario usuario;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "IdStatusEvt")
	protected EstadoEvento estado = EstadoEvento.PENDIENTE;
	
    @JoinColumn(name = "IdSugerencia")
    @ManyToOne(cascade = CascadeType.REMOVE)
	protected Sugerencia sugerenciaElegida = null;
	

	public Evento(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad, String loginUser) {
		super();
		this.fechaHoraEvento = LocalDateTime.parse(dateTime,  DateTimeFormatter.ISO_DATE_TIME);
		this.ubicacion = ubicacion;
		this.actividad = actividad;
		this.caracterVestimenta = caracter;
		Usuario usuario = RepoUsuarios.getInstance().obtengoUsuarioPorUserName(loginUser);
		this.usuario = usuario;
		this.estado = EstadoEvento.PENDIENTE;
	}

	public Evento(String dateTime, String ubicacion, CaracterPrenda caracter, String actividad) {
		super();
		this.fechaHoraEvento = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
		this.ubicacion = ubicacion;
		this.caracterVestimenta = caracter;
		this.actividad = actividad;
	}
	
	public Evento(LocalDateTime dateTime, String ubicacion, CaracterPrenda caracter, String actividad, Usuario usuario) {
		super();
		this.fechaHoraEvento =  dateTime;
		this.ubicacion = ubicacion;
		this.caracterVestimenta = caracter;
		this.actividad = actividad;
		this.usuario = usuario;
	}
	
	public void setFechaHoraEvt(String fechaHora) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		this.fechaHoraEvento = LocalDateTime.parse(fechaHora, formatter);
	}
	
	public CalificacionSugerencia getFeedbackSugerencia() {
		if (this.getSugerenciaElegida() == null) {
			return null;
		}
		return this.sugerenciaElegida.getCalificacion();
	}

	public void setFeedbackSugerencia(CalificacionSugerencia calif) {
		this.sugerenciaElegida.setCalificacion(calif);
	}
	
	public abstract boolean estaProximo();
	public abstract boolean estaVencido();
	
}

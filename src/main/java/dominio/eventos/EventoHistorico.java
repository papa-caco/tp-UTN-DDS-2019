package dominio.eventos;

import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import dominio.placard.Sugerencia;
import dominio.prenda.CaracterPrenda;
import dominio.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;


@Entity
@DiscriminatorValue(value = "EventoHistorico")
public class EventoHistorico extends Evento {

	public EventoHistorico(LocalDateTime dateTime, String ubicacion, CaracterPrenda caracter,
						String actividad, Usuario usuario, Sugerencia sugerencia) {
		super(dateTime, ubicacion, caracter, actividad, usuario);
		this.estado = EstadoEvento.VENCIDO;
		this.sugerenciaElegida = sugerencia;
	}
	
	public EventoHistorico() {
		super();
		this.estado = EstadoEvento.VENCIDO;
	}
	
	@Override
	public boolean estaProximo() {
		return false;
	}

	@Override
	public boolean estaVencido() {
		return true;
	}
	
	@Setter
	@Getter
	@Column
	private int idPlanificado = 0;
	
	public boolean tieneCalificacion() {
		return this.sugerenciaElegida.getCalificacion()!= null;
	}
	
	public boolean sinCalificacion() {
		return this.sugerenciaElegida == null || this.sugerenciaElegida.getCalificacion() == null;
	}

	
}

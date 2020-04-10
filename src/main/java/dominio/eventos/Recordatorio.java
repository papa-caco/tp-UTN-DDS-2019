package dominio.eventos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Recordatorio")
@Table(name = "recordatorios")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Recordatorio {
	
	
	public Recordatorio(int cantidadTiempo, EscalaTiempo unidad) {
		super();
		this.cantidadTiempo = cantidadTiempo;
		this.unidad = unidad;
	}
	
	
	@Id
	@GeneratedValue
	@Column(name = "IdRecordatorio")
	private int idRecordatorio;
	
	@Column
	private long cantidadTiempo = 0;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "IdTimeScale")
	private EscalaTiempo unidad = EscalaTiempo.MINUTOS;
	
	
	public long segundosTotales() {
		return this.cantidadTiempo * this.unidad.multiplicadorSegundos();
	}
	

}

package dominio.placard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dominio.prenda.Atuendo;

@Entity (name="Sugerencia")
@Table(name = "Sugerencias")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Sugerencia {
	
	@Id
	@GeneratedValue
	@Column(name = "IdSugerencia")
	private Integer idSugerencia;
	
	@JoinColumn(name = "IdAtuendo")
    @ManyToOne(cascade = CascadeType.REMOVE)
	private Atuendo atuendo = null;
	
	@NonNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "IdEstado")
	private EstadoSugerencia estado = EstadoSugerencia.PENDIENTE;
	
	@JoinColumn(name = "IdCalificacion")
    @ManyToOne(cascade = CascadeType.REMOVE)
	private CalificacionSugerencia calificacion = null;
	
	public Sugerencia(Atuendo unAtuendo) {
		super();
		this.atuendo = unAtuendo;
	}
		
	public void aceptar() {
		this.estado = EstadoSugerencia.ACEPTADA;
	}
	public void rechazar() {
		this.estado = EstadoSugerencia.RECHAZADA;
	}
	
	public List<String> prendasAtuendo(){
		return this.atuendo.getNombrePrendas();
	}
	
	public boolean contienePrendasDePlacard(Placard placard) {
		return this.atuendo.getPlacard().getIdPlacard() == placard.getIdPlacard();
	}
	
}
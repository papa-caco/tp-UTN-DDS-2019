package dominio.placard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

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


import dominio.prenda.ValorTermico;
import dominio.usuario.Usuario;


@Entity (name="CalificacionSugerencia")
@Table(name = "Calificaciones")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class CalificacionSugerencia {
	
	public CalificacionSugerencia(Usuario user, ValorTermico global, ValorTermico cabeza, ValorTermico cuello, ValorTermico manos) {
		super();
		this.sensacionGlobal = global;
		this.sensacionCabeza = cabeza;
		this.sensacionCuello = cuello;
		this.sensacionManos = manos;
		this.usuario = user;
	}

	@Id
	@GeneratedValue
	@Column(name = "IdCalificacion")
	private Integer idCalificacion;
	
	@NonNull
    @JoinColumn(name = "IdUsuario")
    @ManyToOne(cascade = CascadeType.MERGE)
	private Usuario usuario;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "Global")
	@NonNull
	private ValorTermico sensacionGlobal = ValorTermico.SIN_CALIFICAR;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "Cabeza")
	@NonNull
	private ValorTermico sensacionCabeza = ValorTermico.SIN_CALIFICAR;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "Cuello")
	@NonNull
	private ValorTermico sensacionCuello = ValorTermico.SIN_CALIFICAR;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "Manos")
	@NonNull
	private ValorTermico sensacionManos = ValorTermico.SIN_CALIFICAR;
	
}

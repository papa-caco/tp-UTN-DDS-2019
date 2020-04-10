package dominio.prenda;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

@Entity
@Table(name = "tipos")
@Getter
@Setter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
@NoArgsConstructor
public class Tipo {
	
	public static Tipo of(String tipo,Categoria paraUsarEn, Capa capa) {
		final Tipo tipoNuevo = new Tipo(tipo, paraUsarEn, capa);
		return tipoNuevo;
	}
	
	public boolean sirvePara(Categoria otraCategoria) {
		return this.paraUsarEn.equals(otraCategoria);
	}
	
	public double nivelTermicoCapa() {
		return this.getCapa().getUnidades();
	}
	
	public boolean admiteSuperpuesta(Tipo unTipo) {
		return this.getCapa().admiteSuperpuesta(this, unTipo);
	}
	
	public boolean puedeSuperponer(Tipo unTipo) {
		return this.getCapa().puedeSuperponer(this, unTipo);
	}
	
	@Id
	@GeneratedValue
    @Column(name = "IdTipo", unique = true, nullable = false)
	private int idTipo;
	
	@NonNull
	private String tipo;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "IdCateg")
	@NonNull
	private Categoria paraUsarEn;
	
	@NonNull
    @JoinColumn(name = "IdCapa")
    @ManyToOne(cascade = CascadeType.ALL)
	private Capa capa;
	
}

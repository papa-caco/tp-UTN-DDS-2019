package dominio.prenda;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_capa")
@Getter
@Setter(AccessLevel.PUBLIC)
public abstract class Capa {
	
	public Capa() {
		super();
	}
	
	@Id
	@GeneratedValue
    @Column(name = "IdCapa", unique = true, nullable = false)
	private int idCapa;
	
	@Column(name = "Unidades")
	private double unidades;

	public Capa(double unidades) {
		super();
		this.unidades = unidades;
	}
	
	protected abstract boolean admiteSuperpuesta(Tipo unTipo, Tipo otroTipo);
	
	protected abstract boolean puedeSuperponer(Tipo unTipo, Tipo otroTipo);
	
	protected abstract boolean aportaValorFormal();
	
	public abstract boolean esInterior();
	
	public abstract boolean esSuperior();
		
}


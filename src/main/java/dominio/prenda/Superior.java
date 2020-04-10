package dominio.prenda;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue(value = "Superior")
public class Superior extends Capa {
	
	public Superior () {
		super();
	}
	
	public Superior(double unidades) {
		super(unidades);
	}
	
	public static Superior of(double valor) {
		Superior capa = new Superior(valor);
		return capa;
	}
		
	@java.lang.Override
	public boolean admiteSuperpuesta(Tipo unTipo, Tipo otroTipo) {
		return false;
	}
		
	@java.lang.Override
	public boolean puedeSuperponer(Tipo unTipo, Tipo otroTipo) {
		return !otroTipo.getCapa().esSuperior() && !otroTipo.getCapa().esInterior();
	}

	@Override
	public boolean aportaValorFormal() {
		return true;
	}

	@Override
	public boolean esInterior() {
		return false;
	}

	@Override
	public boolean esSuperior() {
		return true;
	}

}

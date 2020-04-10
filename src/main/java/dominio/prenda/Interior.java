package dominio.prenda;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue(value = "Interior")
public class Interior extends Capa{
	
	public Interior() {
		super();
	}

	public Interior(double unidades) {
		super(unidades);
	}
	
	public static Interior of(double valor) {
		Interior capa = new Interior(valor);
		return capa;
	}
		
	@java.lang.Override
	public boolean admiteSuperpuesta(Tipo unTipo, Tipo otroTipo) {
		return !otroTipo.getCapa().esInterior();
	}
	
	@java.lang.Override
	public boolean puedeSuperponer(Tipo unTipo, Tipo otroTipo) {
		return false;
	}
	
	@java.lang.Override
	public boolean aportaValorFormal() {
		return false;
	}

	@Override
	public boolean esInterior() {
		return true;
	}

	@Override
	public boolean esSuperior() {
		return false;
	}

}

package dominio.prenda;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue(value = "Intermedia")
public class Intermedia extends Capa{
	
	public Intermedia() {
		super();
	}

	public Intermedia(double unidades) {
		super(unidades);
	}
	
	public static Intermedia of(double valor) {
		Intermedia capa = new Intermedia(valor);
		return capa;
	}
	
	@java.lang.Override
	public boolean admiteSuperpuesta(Tipo unTipo, Tipo otroTipo) {
		return otroTipo.getCapa().esSuperior();
	}
	
	@java.lang.Override
	public boolean puedeSuperponer(Tipo unTipo, Tipo otroTipo) {
		return otroTipo.getCapa().esInterior();
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
		return false;
	}

}

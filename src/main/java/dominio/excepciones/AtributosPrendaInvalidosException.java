package dominio.excepciones;
@SuppressWarnings("serial")

public class AtributosPrendaInvalidosException extends Exception {

	public AtributosPrendaInvalidosException() {
		super("Los atributos ingresados no son apropiados para la prenda ");
	}
}

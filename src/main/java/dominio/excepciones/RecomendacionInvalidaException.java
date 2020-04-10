package dominio.excepciones;
@SuppressWarnings("serial")

public class RecomendacionInvalidaException extends Exception {

	public RecomendacionInvalidaException() {
		super("Las prendas seleccionadas para la recomendacion no corresponden. ");
	}
}

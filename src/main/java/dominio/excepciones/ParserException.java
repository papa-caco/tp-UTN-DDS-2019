package dominio.excepciones;
@SuppressWarnings("serial")

public final class ParserException extends RuntimeException {
	
	public ParserException(String mensaje) {
		super(mensaje);
	}

}

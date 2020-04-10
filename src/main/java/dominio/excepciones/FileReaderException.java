package dominio.excepciones;
@SuppressWarnings("serial")

public final class FileReaderException extends RuntimeException {
	public FileReaderException(String mensaje) {
		super(mensaje);
	}
}
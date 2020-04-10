package dominio.excepciones;
@SuppressWarnings("serial")

public class SinSenderActivoException extends Exception {
	
	public SinSenderActivoException() {
		super("No hay servicio de notificacion disponible");
	}

}

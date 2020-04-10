package dominio.excepciones;
@SuppressWarnings("serial")

public class SinPrendasException extends Exception {

	public SinPrendasException() {
		super("No se Encuentran las prendas solicitadas");
	}
	
	public SinPrendasException(String valor) {
		super("No se Encuentran las prenda tipo: " + valor + "solicitadas");
	}

}
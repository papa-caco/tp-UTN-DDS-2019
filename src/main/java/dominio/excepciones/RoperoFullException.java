package dominio.excepciones;
@SuppressWarnings("serial")

public class RoperoFullException extends Exception {

	public RoperoFullException() {
		super("Ropero Lleno ");
	}

}
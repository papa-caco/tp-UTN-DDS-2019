package dominio.excepciones;
@SuppressWarnings("serial")

public class SinDatosClimaException extends Exception {

	public SinDatosClimaException() {
		super("No existen los datos de pronostico solicitados ");
	}
}

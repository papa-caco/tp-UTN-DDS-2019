package dominio.excepciones;

public class PrendaNotFoundException extends Exception {
	
	@SuppressWarnings("unused")
	private String nombre;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrendaNotFoundException() {
		super();
		
	}
	public PrendaNotFoundException(String nombre) {
		super();
		this.nombre = nombre;
	}



	public PrendaNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}
}
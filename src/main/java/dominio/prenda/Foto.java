package dominio.prenda;

import java.io.File;


public class Foto  {
	
	private File archivo;
	public Foto(File archivo) {
		super();
		this.archivo = archivo;
	}

	public File getArchivo() {
		return archivo;
	}
	
	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}
	
	public long tamano() {
		return archivo.length();
	}
			
	public String nombreCompleto( ) {
		return archivo.getAbsolutePath();
	}
	
	public boolean existeArchivo( ) {
		return archivo.exists();
	}
	
	public boolean esArchivo( ) {
		return archivo.isFile();
	}
		
}


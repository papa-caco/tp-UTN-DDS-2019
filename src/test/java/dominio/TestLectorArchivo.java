package dominio;

import org.junit.Test;

import dominio.utilities.*;
import dominio.excepciones.*;

public class TestLectorArchivo {
	static String ruta_archivo1 = "./src/varios/alumnos.json";
	static String ruta_archivo2 = "./src/varios/docentes.json";
	static String ruta_archivo3 = "./src/varios/alumnosMal.json";
	LectorArchivo fReader = LectorArchivo.getInstance(); 
	
	@Test /* 001 -- Lectura correcta de archivo */
	public void lecturaCorrectaArchivo() {
		fReader.leerArchivo(ruta_archivo1);
	}

	/* 002 -- No ecuentro archivo en ruta */
	@Test (expected = FileReaderException.class)
	public void noSeEncuentraArhivo() {
		fReader.leerArchivo(ruta_archivo2);
	}
				
	/* TEST-003 -- El archivo no esta bien */
	@Test 
	public void archivoMalConfigurado() {
		fReader.leerArchivo(ruta_archivo3);
	}

}

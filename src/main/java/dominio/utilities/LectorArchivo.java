package dominio.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import dominio.excepciones.*;

public class LectorArchivo {
	
	private static LectorArchivo INSTANCE = null;
	
	private LectorArchivo() {
	}	
	
	public static LectorArchivo getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new LectorArchivo();
	}
	return INSTANCE;
	}
	
	public byte[] leerArchivo(String rutaArchivo) {
		byte[] formatoJson = null;
		if(rutaArchivo.isEmpty()) {
			throw new FileReaderException("Ruta del archivo incompleta");
		}else {
			try {
				formatoJson = Files.readAllBytes(Paths.get(rutaArchivo));
			} catch (IOException e) {
				throw new FileReaderException("Error de lectura archivo");
			}	
		return formatoJson;
		}
	}

}

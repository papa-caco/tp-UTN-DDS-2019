package dominio.servicios.pronostico.accuweather;

import java.io.IOException;

import dominio.utilities.*;

public class RecuperadorJsonAccuMock implements RecuperadorJsonAccu {
	private String jsonRepository;
	private byte[] contenido;
	private PronosticoExtAccu pronosticoExtendido;
	
	private RecuperadorJsonAccuMock(String jsonRepo) {
		super();
		this.jsonRepository = jsonRepo;
	}

	private static RecuperadorJsonAccuMock INSTANCE = null;
	
	public static RecuperadorJsonAccuMock getInstance(String jsonRepo) {
	if (INSTANCE == null) {
		INSTANCE = new RecuperadorJsonAccuMock(jsonRepo);
	}
		return INSTANCE;
	}
	
	public PronosticoExtAccu getPronosticoExtendido(int locationKey) throws IOException {
		this.completoPronosticoExtendido(locationKey);
		return this.pronosticoExtendido;
	}
	
	
	public boolean getEstadoActivo() throws IOException {
		return this.configuracionOk();
	}

	private void completoPronosticoExtendido(int locationKey) {
		this.completoContenido(locationKey);
		this.pronosticoExtendido = Parser.getInstance().fromJsonToObject(this.contenido, PronosticoExtAccu.class);
		this.pronosticoExtendido.setLocationKey(locationKey);
	}
	private String getJsonFilePath(int locationKey) { // el nombre del archivo .json debe coincidir con el location.key
		return this.jsonRepository 					  // ejemplo Avellaneda - locationKey  = 7033
				    + "/" + locationKey + ".json";		     //  Avellaneda - Archivo JSON = 7033.json (Pronostico Extendido)
	}														
	private void completoContenido(int locationKey) {
		this.contenido = LectorArchivo.getInstance().leerArchivo(this.getJsonFilePath(locationKey));
	}
	private boolean configuracionOk() {
		return this.jsonRepository != null;
	}
	
}



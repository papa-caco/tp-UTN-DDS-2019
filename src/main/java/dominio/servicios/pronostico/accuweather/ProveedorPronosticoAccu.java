package dominio.servicios.pronostico.accuweather;

import java.io.IOException;
import java.time.LocalDateTime;

import dominio.clima.*;
import dominio.excepciones.SinDatosClimaException;
import dominio.servicios.ProveedorPronostico;

public class ProveedorPronosticoAccu implements ProveedorPronostico {

	private RecuperadorJsonAccu jsonSearcher;
	private LocationKeysAccu locationKeyBase;
	private PronosticoExtAccu pronExtAccu = new PronosticoExtAccu();

	private static ProveedorPronosticoAccu INSTANCE = null;

	private ProveedorPronosticoAccu(RecuperadorJsonAccu jSearch, LocationKeysAccu locKB) {
		super();
		this.jsonSearcher = jSearch;
		this.locationKeyBase = locKB;
	}

	public static ProveedorPronosticoAccu getInstance(RecuperadorJsonAccu jsonSearcher, LocationKeysAccu locKB) {
		if (INSTANCE == null) {
			INSTANCE = new ProveedorPronosticoAccu(jsonSearcher, locKB);
		}
		return INSTANCE;
	}

	public RecuperadorJsonAccu getJsonSearcher() {
		return jsonSearcher;
	}

	public void setJsonSearcher(RecuperadorJsonAccu jsonSearcher) {
		this.jsonSearcher = jsonSearcher;
	}

	public PronosticoExtAccu getPronExtAccu() {
		return pronExtAccu;
	}

	public void setPronExtAccu(PronosticoExtAccu pronExtAccu) {
		this.pronExtAccu = pronExtAccu;
	}

	public LocationKeysAccu getLocationKeyBase() {
		return locationKeyBase;
	}

	public void setLocationKeyBase(LocationKeysAccu locationKeyBase) {
		this.locationKeyBase = locationKeyBase;
	}

	@Override
	public boolean respondeOK() throws IOException {
		return this.jsonSearcher.getEstadoActivo();
	}

	
	@Override
	public Pronostico getPronosticoCiudadFecha(String ciudad, LocalDateTime fechaHora) throws SinDatosClimaException, IOException {
		try {
			double tempMin = this.getTempMinCiudadFecha(ciudad, fechaHora);
			double tempMax = this.getTempMaxCiudadFecha(ciudad, fechaHora);
			boolean precipit = this.getPrecipitacionCiudadFecha(ciudad, fechaHora);
			Pronostico unPronostico = new Pronostico(fechaHora, ciudad, tempMin, tempMax, precipit);
			return unPronostico;
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}

	@Override
	public boolean getAlertaClimaCiudadFecha(String ciudad, LocalDateTime fechaHora)
			throws SinDatosClimaException, IOException {
		try { 
			return this.alertaCiudadFecha(ciudad, fechaHora);
		} catch (SinDatosClimaException exc) {
			throw exc;
		}
	}

	private double getTempMinCiudadFecha(String ciudad, LocalDateTime fechaHora) throws SinDatosClimaException, IOException {
		try {
			return this.getPronosticoAccu(ciudad, fechaHora).getTemperaturaMin();
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}
	
	private double getTempMaxCiudadFecha(String ciudad, LocalDateTime fechaHora)  throws SinDatosClimaException , IOException {
		try {
			return this.getPronosticoAccu(ciudad, fechaHora).getTemperaturaMax();
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}
	
	private boolean getPrecipitacionCiudadFecha(String ciudad, LocalDateTime fechaHora)  throws SinDatosClimaException, IOException {
		try {
			int horaDia = fechaHora.getHour();
			return this.getPronosticoAccu(ciudad, fechaHora).getLluviaProbable(horaDia);
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}

	private int getLocationKey(String ciudad) throws SinDatosClimaException {
		try {
			return this.locationKeyBase.getIdCiudad(ciudad);
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}
	
	private boolean alertaCiudadFecha(String ciudad, LocalDateTime fechaHora) throws SinDatosClimaException, IOException {
		this.getPronosticoExtAccu(ciudad);
		if (this.pronExtAccu.contieneFechaHoraEncabezado(fechaHora)) {
			return this.pronExtAccu.alertaClimatico();
		} else {
			throw new SinDatosClimaException();
		}
	}
	
	private void getPronosticoExtAccu(String ciudad) throws SinDatosClimaException, IOException {
		try {
			int key  = this.getLocationKey(ciudad);
			this.pronExtAccu = jsonSearcher.getPronosticoExtendido(key);
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		} catch (IOException exc) {
			throw exc;
		}
	}
	
	private PronosticoAccu getPronosticoAccu(String ciudad,LocalDateTime fechaHora) throws SinDatosClimaException, IOException {
		try {
			this.getPronosticoExtAccu(ciudad);
			return this.pronExtAccu.pronosticoUnaFecha(fechaHora);
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}
}
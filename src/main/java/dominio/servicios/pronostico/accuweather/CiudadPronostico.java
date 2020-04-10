package dominio.servicios.pronostico.accuweather;

import dominio.excepciones.SinDatosClimaException;

public class CiudadPronostico {
	private int id;
	private String ciudad;
	public CiudadPronostico(int id, String ciudad) {
		super();
		this.id = id;
		this.ciudad = ciudad;
	}
	public int getId() {
		return id;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	private boolean mismaCiudad(String city) throws SinDatosClimaException {
		if (!this.ciudad.equals(city)) {
			throw new SinDatosClimaException();
		}
		else {
			return true;
		}
	}
	
	public int getIdCiudad(String city) throws SinDatosClimaException {
		try {
			this.mismaCiudad(city);
			return this.getId();
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}
	
}
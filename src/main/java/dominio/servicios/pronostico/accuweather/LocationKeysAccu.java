package dominio.servicios.pronostico.accuweather;

import dominio.excepciones.SinDatosClimaException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationKeysAccu {
	
	private List<CiudadPronostico> paresCodigoCiudad;
	
	private LocationKeysAccu() {
		this.paresCodigoCiudad = new ArrayList<CiudadPronostico>();
	}

	private static LocationKeysAccu INSTANCE = null;
	
	public static LocationKeysAccu getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new LocationKeysAccu();
	}
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(7033,"Avellaneda"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(7894,"Buenos Aires"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(7261,"Olivos"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(7245,"Moron"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(8869,"Cordoba"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(11222,"Rosario"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(5168,"Posadas"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(5126,"Mendoza"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(6462,"Ushuaia"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(4338,"La Quiaca"));
	INSTANCE.paresCodigoCiudad.add(new CiudadPronostico(7893,"Mar Del Plata"));
	return INSTANCE;
	}
	
	public List<String> getCiudades() {
		return this.paresCodigoCiudad.stream().map(par -> par.getCiudad()).collect(Collectors.toList());
	}
	
	public List<CiudadPronostico> getParesCodigoCiudad() {
		return paresCodigoCiudad;
	}
	
	public int getIdCiudad(String city) throws SinDatosClimaException {
		try {
			return this.codigoCity(city);
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}

	private boolean contieneCiudad(String city) {
		return this.paresCodigoCiudad.stream().anyMatch(ciudad -> ciudad.getCiudad().equals(city));
	}
	private int codigoCity(String city) throws SinDatosClimaException {
		if (this.contieneCiudad(city)) {
			List<CiudadPronostico> unaCiudad = this.paresCodigoCiudad.stream().filter(ciudad -> ciudad.getCiudad().equals(city)).collect(Collectors.toList());
			return unaCiudad.get(0).getId();
		}
		else {
			throw new SinDatosClimaException();
		}
	}

}


	

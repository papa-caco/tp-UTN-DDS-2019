package dominio.servicios.pronostico.accuweather;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;

import dominio.excepciones.SinDatosClimaException;

public class PronosticoExtAccu {
	
	@JsonProperty("Headline")
	private EncabezadoAccu headline;
	@JsonProperty("DailyForecasts")
	private List<PronosticoAccu> dailyForecasts = new LinkedList<>();
	
	private int LocationKey;
	
	public PronosticoExtAccu() {
		
	}

	public EncabezadoAccu getHeadline() {
		return headline;
	}
	public List<PronosticoAccu> getDailyForecasts() {
		return dailyForecasts;
	}
	public int getLocationKey() {
		return LocationKey;
	}
	
	public boolean alertaClimatico() {
		return this.getSeverity() > 0 && this.getSeverity() <= 3;
	}
	
	public void setLocationKey(int locationKey) {
		this.LocationKey = locationKey;
	}

	public double getTempMinimaFecha(LocalDateTime fechaHora) throws SinDatosClimaException, IOException {
		try {
			return this.pronosticoUnaFecha(fechaHora).getTemperaturaMin();
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}
	public double getTempMaximaFecha(LocalDateTime fechaHora) throws SinDatosClimaException, IOException {
		try {
			return this.pronosticoUnaFecha(fechaHora).getTemperaturaMax();
		}
		catch (SinDatosClimaException ex) {
			throw ex;
		}
	}
	public PronosticoAccu pronosticoUnaFecha(LocalDateTime fechaHora)  throws SinDatosClimaException, IOException{
		if (!this.getContieneFecha(fechaHora)) {
			throw new SinDatosClimaException();
		} else {
			List<PronosticoAccu> pronosticoUnico = this.dailyForecasts.stream().filter(prono -> prono.esMismaFecha(fechaHora)).collect(Collectors.toList());
			return pronosticoUnico.get(0);
		}
	}

	public boolean contieneFechaHoraEncabezado(LocalDateTime fechaHora) {
		return this.headline.contieneFechaHora(fechaHora);
	}

	private int getSeverity() {
		return this.headline.getSeverity();
	}

	private boolean getContieneFecha(LocalDateTime fechaHora) {
		return this.dailyForecasts.stream().anyMatch(prono -> prono.esMismaFecha(fechaHora));
	}
	
}

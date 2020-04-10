package dominio.servicios.pronostico.accuweather;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PronosticoAccu {
	
	private String date;
	private int epochDate;
	@JsonProperty("Temperature")
	private TemperatureAccu temperature;
	@JsonProperty("Day")
	private MomentoDelDiaAccu day;
	@JsonProperty("Night")
	private MomentoDelDiaAccu night;
		
	public PronosticoAccu() {
	}
	
	@JsonCreator
	public PronosticoAccu(@JsonProperty("Date") String d, @JsonProperty("EpochDate") int eD) {
		this.date =d;
		this.epochDate = eD;
	}

	public String getDate() {
		return date;
	}
	public MomentoDelDiaAccu getDay() {
		return day;
	}
	public MomentoDelDiaAccu getNight() {
		return night;
	}
	public int getEpochDate() {
		return epochDate;
	}
	public TemperatureAccu getTemperature() {
		return temperature;
	}

	public double getTemperaturaMin() {
		return this.temperature.getMinimum().getValue();
	}
	public double getTemperaturaMax() {
		return this.temperature.getMaximum().getValue();
	}
	public boolean getLluviaProbable(int horaDia) {
		if (horaDia >= 6 && horaDia <= 18) {
			return this.getDiaLluvioso();
		} else {
				return this.getNocheLluviosa();
		}
	}

	public boolean esMismaFecha(LocalDateTime fechaHora) {
		int soloFecha = fechaHora.getDayOfMonth();
		return this.getFechaPronostico().getDayOfMonth() == soloFecha;
	}

	private LocalDateTime getFechaPronostico() {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		LocalDateTime fechaHora = LocalDateTime.parse(this.date , formatter);
		return fechaHora;
	}

	private boolean getDiaLluvioso() {
		return this.day.getHasPrecipitation();
	}
	private boolean getNocheLluviosa() {
		return this.night.getHasPrecipitation();
	}

}

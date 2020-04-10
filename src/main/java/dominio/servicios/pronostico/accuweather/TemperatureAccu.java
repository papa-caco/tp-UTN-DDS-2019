package dominio.servicios.pronostico.accuweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TemperatureAccu {
	
	@JsonProperty("Minimum")
	private ExtremoTempAccu minimum;
	@JsonProperty("Maximum")
	private ExtremoTempAccu maximum;
	
	public TemperatureAccu() {
	}
	
	public ExtremoTempAccu getMinimum() {
		return minimum;
	}
	public ExtremoTempAccu getMaximum() {
		return maximum;
	}
	
	public void setMinimum(ExtremoTempAccu minimum) {
		this.minimum = minimum;
	}
	public void setMaximum(ExtremoTempAccu maximum) {
		this.maximum = maximum;
	}

}
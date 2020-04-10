package dominio.servicios.pronostico.accuweather;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtremoTempAccu {
	
	private Double value;
	private String unit;
		
	public ExtremoTempAccu() {
	
	}

	@JsonCreator
	public ExtremoTempAccu(@JsonProperty("Value") Double v,
					   @JsonProperty("Unit") String u) {
		this.value = v;
		this.unit = u;
	}

	public Double getValue() {
		return value;
	}
	public String getUnit() {
		return unit;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}


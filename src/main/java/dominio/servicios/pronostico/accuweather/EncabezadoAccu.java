package dominio.servicios.pronostico.accuweather;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncabezadoAccu {
	
	private String eftDate;
	private String endDate;
	private int eftEpochDate;
	private int endEpochDate;
	private int severity;
	
	public EncabezadoAccu() {
		
	}
	
	@JsonCreator
	public EncabezadoAccu(@JsonProperty("EffectiveDate") String eD,
			   		  @JsonProperty("EndDate") String enD,
			   		  @JsonProperty("Severity") int sev,
			   		  @JsonProperty("EffectiveEpochDate") int eED,
   				      @JsonProperty("EndEpochDate") int enED) {
		this.eftDate = eD;
		this.endDate = enD;
		this.eftEpochDate = eED;
		this.endEpochDate = enED;
		this.severity = sev;
	}
	
	public String getEftDate() {
		return eftDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public int getEftEpochDate() {
		return eftEpochDate;
	}
	public int getEndEpochDate() {
		return endEpochDate;
	}
	public int getSeverity() {
		return severity;
	}

	public boolean contieneFechaHora(LocalDateTime fechaHora){
		long epochDateTime = (long) fechaHora.toEpochSecond(ZoneOffset.UTC) + 10800;
		return epochDateTime >= this.eftEpochDate && epochDateTime <= this.endEpochDate;
	}

}

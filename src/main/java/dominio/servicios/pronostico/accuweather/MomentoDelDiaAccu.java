package dominio.servicios.pronostico.accuweather;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MomentoDelDiaAccu {
	
	private String iconPhrase;
	private Boolean hasPrecipitation;
	
	public MomentoDelDiaAccu() {
	}
	
	@JsonCreator
	public MomentoDelDiaAccu(@JsonProperty("IconPhrase") String iP, @JsonProperty("HasPrecipitation") Boolean hP) {
		this.iconPhrase = iP;
		this.hasPrecipitation = hP;
	}

	public String getIconPhrase() {
		return iconPhrase;
	}
	public Boolean getHasPrecipitation() {
		return hasPrecipitation;
	}

	public void setIconPhrase(String iconPhrase) {
		this.iconPhrase = iconPhrase;
	}
	public void setHasPrecipitation(Boolean hasPrecipitation) {
		this.hasPrecipitation = hasPrecipitation;
	}
	
}

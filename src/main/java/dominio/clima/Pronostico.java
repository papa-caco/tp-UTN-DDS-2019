package dominio.clima;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Pronostico {
	private LocalDateTime fechaHora;
	private String ciudad;
	private double temperaturaMin;
	private double temperaturaMax;
	private boolean precipitaciones;
	
	public Pronostico(LocalDateTime time, String city, double tempMin, double tempMax, boolean precipit) {
		super();
		this.fechaHora = time;
		this.ciudad = city;
		this.temperaturaMin = tempMin;
		this.temperaturaMax = tempMax;
		this.precipitaciones = precipit;
	}
	
	public LocalDateTime getFechaHora() {
		return this.fechaHora;
	}
	public String getCiudad() {
		return this.ciudad;
	}
	public double getTempMin() {
		return this.temperaturaMin;
	}
	public double getTempMax() {
		return this.temperaturaMax;
	}
	public boolean getPrecipitacion() {
		return this.precipitaciones;
	}
	
	
	public void setFechaHora(String dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.fechaHora = LocalDateTime.parse(dateTime, formatter);
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public void setPrecipitacion(boolean precipit) {
		this.precipitaciones = precipit;
	}	
	public void setTempMin(double tempMin) {	
		this.temperaturaMin = tempMin;
	}
	public void setTempMax(double tempMax) {	
		this.temperaturaMax = tempMax;
	}
	
	public List<String> datosPronostico(){
		return Arrays.asList(this.fechaHora.toString(),this.ciudad,String.valueOf(this.temperaturaMin),String.valueOf(this.temperaturaMax),String.valueOf(this.precipitaciones));
	}	
	
}

package dominio.servicios;

import java.io.IOException;
import java.time.LocalDateTime;

import dominio.clima.Pronostico;
import dominio.excepciones.SinDatosClimaException;

public interface ProveedorPronostico {
	
	public abstract boolean respondeOK() throws IOException;
	public abstract boolean getAlertaClimaCiudadFecha(String ciudad, LocalDateTime fechaHora) throws SinDatosClimaException, IOException;
	public abstract Pronostico getPronosticoCiudadFecha(String ciudad, LocalDateTime fechaHora) throws SinDatosClimaException, IOException;
}

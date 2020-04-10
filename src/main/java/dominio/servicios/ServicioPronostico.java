 package dominio.servicios;

import dominio.clima.Pronostico;
import dominio.excepciones.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioPronostico { // <<SINGLETON>>
	
	private List<ProveedorPronostico> proveedoresPronotico = new ArrayList<>() ;

	private static ServicioPronostico INSTANCE = null;
	
	private ServicioPronostico() {
		super();
	}
	
	public static ServicioPronostico getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new ServicioPronostico();
	}
	return INSTANCE;
	}
	
	public void setProveedores(List<ProveedorPronostico> proveedores) {
		this.proveedoresPronotico = proveedores;
	}
	
	public Pronostico getpronosticoCiudadFecha(String ciudad, LocalDateTime fechaHora) throws SinDatosClimaException, IOException {
		return this.proveedorUtilizado().getPronosticoCiudadFecha(ciudad,fechaHora);
	}
	
	public boolean getAlertaClimaticoCiudadFecha(String ciudad, LocalDateTime fechaHora) throws SinDatosClimaException, IOException {
		return this.proveedorUtilizado().getAlertaClimaCiudadFecha(ciudad, fechaHora);
	}
	
	private List<ProveedorPronostico> proveedoresOk() throws IOException {
		return this.proveedoresPronotico.stream().filter(proveedor -> {
			try {
				return proveedor.respondeOK();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}).collect(Collectors.toList());
	}
	private int proveedoresActivos() throws IOException {
		return this.proveedoresOk().size();
	}
	private ProveedorPronostico proveedorUtilizado() throws SinDatosClimaException, IOException {
		if (this.proveedoresActivos() == 0) {
				throw new SinDatosClimaException();
		}
		else {
			return this.proveedoresOk().get(0);
		}
	}

}

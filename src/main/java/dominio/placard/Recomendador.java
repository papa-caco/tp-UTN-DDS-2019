package dominio.placard;

import dominio.usuario.*;
import dominio.clima.*;
import dominio.eventos.EventoPlanificado;
import dominio.excepciones.*;
import dominio.prenda.*;
import dominio.repository.RepoAtuendos;
import dominio.servicios.ServicioPronostico;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Recomendador {
	
	private static Recomendador INSTANCE = null;
	
	private Recomendador() {
		super();
	}
	
	public static Recomendador getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new Recomendador();
		}
		return INSTANCE;
	}
	
	public List<Sugerencia> entregarSugerenciasEvento(EventoPlanificado event) throws SinDatosClimaException, SinPrendasException, IOException {
		LocalDateTime fechaHora = event.getFechaHoraEvento();
		String ciudad = event.getUbicacion();
		Usuario user = event.getUsuario();
		CaracterPrenda caracter = event.getCaracterVestimenta();
		System.out.println(fechaHora +" "+ciudad+" "+user.getUserName());
		Pronostico pronoEvento = ServicioPronostico.getInstance().getpronosticoCiudadFecha(ciudad, fechaHora);
		return this.entregaSugerenciasCriterio(user, pronoEvento, caracter);
	}

	private List<Sugerencia> entregaSugerenciasCriterio(Usuario user, Pronostico prono, CaracterPrenda caracter) throws SinPrendasException { //TODO - revisar
		CriterioSeleccionAtuendos unCriterio = user.getCriterioVestimenta();
		List<Atuendo> porCaracterVestimenta = this.buscoAtuendosUsuario(user).stream().filter(atu -> atu.esAtuendoDeCaracter(caracter)).collect(Collectors.toList());
		List<Atuendo> atuendosSelectos = porCaracterVestimenta.stream().filter(at1->unCriterio.cumpleCondicion(prono,user,at1)).collect(Collectors.toList());
		List<Sugerencia> sugerencias = atuendosSelectos.stream()
				.map(at1 -> new Sugerencia(at1)).collect(Collectors.toList());
		return sugerencias;
	}
    
	public List<Atuendo> buscoAtuendosUsuario(Usuario unUsuario) throws SinPrendasException {
		List<Atuendo> atuendosUser= new LinkedList<>();
		List<Placard> placardsUser = unUsuario.getPlacards();
		for (int i = 0; i < placardsUser.size(); i = i + 1) {
			Placard placard = placardsUser.get(i);
			atuendosUser.addAll(RepoAtuendos.getInstance().buscoAtuendosPlacard(placard));
		}
		return atuendosUser;
	}
	
	public double getTempMinimaPronosticada(EventoPlanificado event) throws SinDatosClimaException, IOException {
		return ServicioPronostico.getInstance().getpronosticoCiudadFecha(event.getUbicacion(), event.getFechaHoraEvento()).getTempMin();
	}
	
	public double getTempMaximaPronosticada(EventoPlanificado event) throws SinDatosClimaException, IOException {
		return ServicioPronostico.getInstance().getpronosticoCiudadFecha(event.getUbicacion(), event.getFechaHoraEvento()).getTempMax();
		
	}
	
	public String getProbabilidadPrecipitacion(EventoPlanificado event) throws SinDatosClimaException, IOException {
		if (ServicioPronostico.getInstance().getpronosticoCiudadFecha(event.getUbicacion(), event.getFechaHoraEvento()).getPrecipitacion()) {
			return "SI";
		} else {
			return "NO";
		}
	}
	
}

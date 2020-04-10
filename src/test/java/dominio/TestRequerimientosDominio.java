package dominio;

import dominio.eventos.*;
import dominio.excepciones.*;
import dominio.placard.*;
import dominio.usuario.*;
import dominio.utilities.*;
import dominio.webapp.request.RequestPrenda;
import dominio.prenda.*;
import dominio.repository.RepoPrendas;
import dominio.servicios.*;
import dominio.servicios.pronostico.*;
import dominio.servicios.pronostico.accuweather.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
//import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/*
public class TestRequerimientosDominio {
	private Prenda camisetaBlanca;
	private Prenda musculosaAmarilla;
	private Prenda sweaterAzul;
	private Prenda camperaVerde;
	private Prenda camisaBlanca;
	private Prenda chombaAzul;
	private Prenda calzoncilloRojo;
	private Prenda calzoncilloNegro;
	private Prenda shorsVerde;
	private Prenda bermudaCeleste;
	private Prenda joggingGris;
	private Prenda pantalonAzul;
	private Prenda zapatillasBlanca;
	private Prenda zapatosNegros;
	private Prenda mediasNegras;
	private Prenda mediasBlancas;
	private Prenda mochilaNegra;
	private Prenda bolsoAzul;
	private Prenda panueloVerde;
	private Prenda bufandaGris;
	private Tela algodon;
	private Tela lona;
	private Tela cuero;
	private Tela gabardina;
	private Tela seda;
	private Tela dryfit;
	private Tela lana;
	private Placard placard1;
	private Placard placard2;
	private Recomendador recomendador;
	private Membresia Premium;
	private Membresia Gratis;
	private Usuario jose;
	private Usuario juan;
	private LectorArchivo fileReader;
	private Parser jsonParser;
	private LocationKeysAccu idLocations;
	private RecuperadorJsonAccu recuperadorJson;
	private ProveedorPronosticoAccu proveePronost;
	private ServicioPronostico serviceProno;
	private EventoPlanificado evento1;
	private EventoPlanificado evento2;
	private EventoPlanificado evento3;
	private EventoPlanificado evento4;
	private EventoPlanificado evento5;
	private EventoPlanificado evento6;
	//private Planificacion evtPeriod01;
	private Planificador planificador;
	//private CriterioSeleccionAtuendos criterioFormal;
	//private CriterioSeleccionAtuendos criterioAbrigado;
	private CriterioSeleccionAtuendos criterioVentilado;
	private CriterioSeleccionAtuendos criterioCasual;
	private HistorialEventos acumulador;
	private ServicioNotificador  serviceCorreo;
	private NotificationSender sender;
				
	@Before
	public void init() throws AtributosPrendaInvalidosException, RecomendacionInvalidaException, RoperoFullException, RoperoIncorrectoException, SinDatosClimaException, IOException {
		this.fileReader = LectorArchivo.getInstance(); //<<SINGLETON>>
		this.jsonParser = Parser.getInstance(); //<<SINGLETON>>
		this.idLocations = LocationKeysAccu.getInstance(); //<<SINGLETON>>
		this.recuperadorJson = RecuperadorJsonAccuMock.getInstance("./src/varios/jsonAccu/",fileReader,jsonParser); //<<SINGLETON>>
		this.proveePronost = ProveedorPronosticoAccu.getInstance(recuperadorJson, idLocations); //<<SINGLETON>>
		List<ProveedorPronostico> providers = new LinkedList<ProveedorPronostico>(); 
		providers.add(proveePronost); 
		this.serviceProno = ServicioPronostico.getInstance(providers);
		//this.criterioFormal = new CriterioMasFormal();
		//this.criterioAbrigado = new CriterioAbrigar();
		this.criterioVentilado = new CriterioRefrescar();
		this.criterioCasual = new CriterioMasCasual();
		Tipo camiseta = new Tipo("camiseta",Categoria.TORSO,new Interior(5.0));
		Tipo musculosa = new Tipo("musculosa",Categoria.TORSO,new Interior(5.0));
		Tipo sweater = new Tipo("sweater",Categoria.TORSO,new Superior(25.0));
		Tipo campera = new Tipo("campera",Categoria.TORSO,new Superior(25.0));
		Tipo camisa = new Tipo("camisa",Categoria.TORSO,new Intermedia(10.0));
		Tipo chomba = new Tipo("chomba",Categoria.TORSO,new Intermedia(10.0));
		Tipo calzoncillo = new Tipo("calzoncillo",Categoria.PIERNAS,new Intermedia(10.0));
		Tipo shors = new Tipo("shors",Categoria.PIERNAS,new Intermedia(10.0));
		Tipo bermuda = new Tipo("bermuda",Categoria.PIERNAS,new Intermedia(10.0));
		Tipo jogging = new Tipo("jogging",Categoria.PIERNAS,new Superior(25.0));
		Tipo pantalon = new Tipo("pantalon",Categoria.PIERNAS,new Superior(25.0));
		Tipo zapatillas = new Tipo("zapatillas",Categoria.PIE,new Intermedia(30.0));
		Tipo zapatos = new Tipo("zapatos",Categoria.PIE,new Intermedia(30.0));
		Tipo medias = new Tipo("medias",Categoria.PIE,new Interior(7.0));
		Tipo mochila = new Tipo("mochila",Categoria.ACCESORIO,new Intermedia(30.0));
		Tipo bolso = new Tipo("bolso",Categoria.ACCESORIO,new Intermedia(30.0));
		Tipo panuelo = new Tipo("panuelo",Categoria.ACCESORIO,new Interior(30.0));
		Tipo bufanda  = new Tipo("bufanda",Categoria.ACCESORIO,new Interior(30.0));
		this.lona = new Tela("lona",Arrays.asList(zapatillas,mochila));
		this.algodon = new Tela("algodon",Arrays.asList(chomba,calzoncillo,camiseta,jogging,medias));
		this.seda = new Tela("seda",Arrays.asList(camisa,panuelo));
		this.dryfit = new Tela("dryfit",Arrays.asList(musculosa,shors));
		this.gabardina = new Tela("gabardina",Arrays.asList(pantalon,bermuda,campera));
		this.lana = new Tela("lana",Arrays.asList(bufanda,sweater));
		this.cuero = new Tela("cuero",Arrays.asList(bolso,zapatos));
		this.chombaAzul = new Prenda("chomba azul",Categoria.TORSO,chomba,algodon,new Foto(new File("./src/varios/fotosPrendas/chombaAzul.jpg")), 8, new Color("azul"));
		this.camisaBlanca = new Prenda("camisa Reblanca",Categoria.TORSO,camisa,seda,new Foto(new File("./src/varios/fotosPrendas/camisaBlanca.jpg")), 1, new Color("blanco"));
		this.musculosaAmarilla = new Prenda("musculosa amarilla",Categoria.TORSO,musculosa,dryfit,new Foto(new File("./src/varios/fotosPrendas/musculosaAmarilla.jpg")),10 ,new Color("amarilla"));
		this.shorsVerde = new Prenda("shorts verde",Categoria.PIERNAS,shors,dryfit,new Foto(new File("./src/varios/fotosPrendas/shortVerde.jpg")), 0, new Color("verde"));
		this.zapatillasBlanca = new Prenda("zapatillas blancas",Categoria.PIE,zapatillas,lona,new Foto(new File("./src/varios/fotosPrendas/zapasBlancas.jpg")), 2, new Color("blanco"));
		this.mochilaNegra = new Prenda("mochila negra",Categoria.ACCESORIO,mochila,lona,new Foto(new File("./src/varios/fotosPrendas/mochilaNegra.jpg")), 3, new Color("negro"));
		this.pantalonAzul = new Prenda("pantalon azul",Categoria.PIERNAS,pantalon,gabardina,new Foto(new File("./src/varios/fotosPrendas/pantalonAzul.jpg")), 9, new Color("azul"));
		this.calzoncilloNegro = new Prenda("calzoncillo negro",Categoria.PIERNAS,calzoncillo,algodon,new Foto(new File("./src/varios/fotosPrendas/calzoncilloNegro.jpg")),5 ,new Color("negro"));
		this.camisetaBlanca = new Prenda("camiseta blanca",Categoria.TORSO,camiseta,algodon,new Foto(new File("./src/varios/fotosPrendas/camisetaBlanca.jpg")),5 ,new Color("blanco"));
		this.sweaterAzul = new Prenda("sweater azul",Categoria.TORSO,sweater,lana,new Foto(new File("./src/varios/fotosPrendas/sweaterAzul.jpg")),9 ,new Color("azul"));
		this.camperaVerde = new Prenda("campera verde",Categoria.TORSO,campera,gabardina,new Foto(new File("./src/varios/fotosPrendas/camperaVerde.jpg")),8 ,new Color("verde"));
		this.calzoncilloRojo = new Prenda("calzoncillo rojo",Categoria.PIERNAS,calzoncillo,algodon,new Foto(new File("./src/varios/fotosPrendas/calzoncilloRojo.jpg")),5 ,new Color("rojo"));
		this.bermudaCeleste  = new Prenda("bermuda celeste",Categoria.PIERNAS,bermuda,gabardina,new Foto(new File("./src/varios/fotosPrendas/bermudaCeleste.jpg")),7 ,new Color("celeste"));
		this.joggingGris  = new Prenda("jogging gris",Categoria.PIERNAS,jogging,algodon,new Foto(new File("./src/varios/fotosPrendas/joggingGris.jpg")),2 ,new Color("gris"));
		this.zapatosNegros  = new Prenda("zapatos negros",Categoria.PIE,zapatos,cuero,new Foto(new File("./src/varios/fotosPrendas/zapatosNegros.jpg")),10, new Color("negro"));
		this.mediasNegras = new Prenda("medias negras",Categoria.PIE,medias,algodon,new Foto(new File("./src/varios/fotosPrendas/mediasNegras.jpg")),5,new Color("negro"));
		this.mediasBlancas = new Prenda("medias blancas",Categoria.PIE,medias,algodon,new Foto(new File("./src/varios/fotosPrendas/mediasBlancas.jpg")),5 ,new Color("blanco"));
		this.bolsoAzul = new Prenda("bolso azul",Categoria.ACCESORIO,bolso,cuero,new Foto(new File("./src/varios/fotosPrendas/bolsoAzul.jpg")),3 ,new Color("azul"));
		this.panueloVerde = new Prenda("panuelo verde",Categoria.ACCESORIO,panuelo,seda,new Foto(new File("./src/varios/fotosPrendas/panueloVerde.jpg")),5 ,new Color("verde"));
		this.bufandaGris = new Prenda("bufanda gris",Categoria.ACCESORIO,bufanda,lana,new Foto(new File("./src/varios/fotosPrendas/bufandaGris.jpg")),8 ,new Color("gris"));
		this.Premium = new Membresia(50);
		this.Gratis = new Membresia(20);
		this.placard1 = new Placard("Placard Prendas de Verano");
		this.placard2 = new Placard("Placard Prendas de Invierno");
		this.jose = new Usuario("Jose Lopez","jose",Premium,"jlopez@dds-utn.com.ar",Arrays.asList(placard1),criterioVentilado);
		this.juan = new Usuario("Juan Perez","juan",Gratis,"jperez@dds-utn.com.ar",Arrays.asList(placard2),criterioCasual);
		this.placard1.setPrendas(Arrays.asList(musculosaAmarilla,chombaAzul,camperaVerde,calzoncilloRojo,shorsVerde,joggingGris,mediasBlancas,zapatillasBlanca,mochilaNegra,panueloVerde),jose);
		this.placard2.setPrendas(Arrays.asList(camisetaBlanca,camisaBlanca,sweaterAzul,calzoncilloNegro,bermudaCeleste,pantalonAzul,mediasNegras,zapatosNegros,bufandaGris,bolsoAzul),juan);
		this.recomendador = Recomendador.getInstance(this.serviceProno);
		this.evento1 = new EventoPlanificado("2019-10-10T01:50:00","Buenos Aires","Asistir a Conferencia",jose,new Recordatorio(2,EscalaTiempo.HORAS));
		this.evento2 = new EventoPlanificado("2019-10-11T07:00:00","Buenos Aires","Salir con Amigos",jose);
		this.evento3 = new EventoPlanificado("2019-10-12T07:00:00","Buenos Aires","Trabajar",jose);
		this.evento4 = new EventoPlanificado("2019-09-25T01:01:00","Buenos Aires","Estudiar",juan,new Recordatorio(2,EscalaTiempo.HORAS));
		this.evento5 = new EventoPlanificado("2019-10-13T07:00:00","Rosario","Pescar",juan); 
		this.evento6 = new EventoPlanificado("2019-10-14T07:00:00","Cordoba","Visitar Familiares",juan);
		this.acumulador = new HistorialEventos();
		this.sender = new NotificationSender();
		serviceCorreo = ServicioNotificador.getInstance(Arrays.asList(sender));
		this.planificador = new Planificador(recomendador,acumulador,serviceCorreo);
		this.planificador.agregarEvento(evento1);
		this.planificador.agregarEvento(evento2);
		this.planificador.agregarEvento(evento3);
		}
	
	@Test // -- T E S T #01 --//
	public void nro01_prendasSuperpuestas() throws SinDatosClimaException , IOException, SinPrendasException, SinSenderActivoException {
		this.jose.consultarEventosProximos(planificador);
		Assert.assertFalse(jose.pedirSugerenciaEvento(planificador, 2).getAtuendo().getPrendasTorso().size()>1);
		Assert.assertTrue(jose.pedirSugerenciaEvento(planificador, 3).getAtuendo().getPrendasPiernas().size()>=1);
		Assert.assertEquals(1,jose.pedirSugerenciaEvento(planificador, 3).getAtuendo().getPrendasPies().size());
	}	//	Queremos verificar que el Atuendo Sugerido contiene prendas superpuestas
		// 	El evento n� 3 es en Buenos Aires el 26-06 y el pron�stico indica -5�C.
	
	
	@Test // -- T E S T #02 --//
	public void nro02_guardarropasLimitados() {
		Assert.assertEquals(10,juan.getPlacards().get(0).capacidadDisponible(juan));
		System.out.println("Test 02 - " + jose.getPlacards().get(0).getPrendas().get(2).getCategoria().name());
		Assert.assertEquals(40,jose.getPlacards().get(0).capacidadDisponible(jose));
	}
	
	
	@Test // -- T E S T #03 --//
	public void nro03_cargaEventos() throws SinDatosClimaException , IOException, SinPrendasException, SinSenderActivoException {
		Assert.assertFalse(evento1.estaProximo());
		Assert.assertTrue(evento4.estaVencido());
		this.planificador.agregarEvento(evento4);
		this.planificador.agregarEvento(evento5); 
		this.planificador.agregarEvento(evento6);
		Assert.assertEquals(6,planificador.getEventos().size());
		Assert.assertEquals(0,acumulador.getEventos().size());
		this.juan.consultarEventosProximos(planificador);
		Assert.assertEquals(5,planificador.getEventos().size());
		Assert.assertEquals(1,acumulador.getEventos().size());
		Assert.assertTrue(planificador.getEventos().get(1).getSugerencias().size() >= 0);
		Assert.assertEquals(0,planificador.eventosProximos(juan).size());
		System.out.println("Test 09 - " + 
			this.planificador.getEventos().stream().map(ev->ev.getActividad()).collect(Collectors.toList()));
	} 	// Cargamos 3 eventos en la agenda para el usuario juan
		// Dos de los eventos est�n en estado Proximo, (se considera un evento pr�ximo
		// un evento que ocurre el mismo d�a o el d�a siguiente).
	
	@Test // -- T E S T #04 --//
	public void nro04_aceptacionSugerenciasEventos() throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		this.planificador.agregarEvento(evento4);
		this.planificador.agregarEvento(evento5); 
		this.planificador.agregarEvento(evento6);
		this.juan.cargarEventoPlanificado(planificador, new EventoPlanificado("2019-10-12T01:01:00","Moron","Estudiar",new Recordatorio(16,EscalaTiempo.DIAS)));
		this.juan.consultarEventosProximos(planificador);
		this.juan.pedirSugerenciaEvento(planificador, 5);
		Assert.assertEquals(0,juan.revisarEvento(planificador, 5).getSugerenciaElegida().prendasAtuendo().size()); // (1) 
		this.juan.aceptarSugerenciaEvento(planificador,5);
		Assert.assertEquals(4,juan.revisarEvento(planificador, 5).getSugerenciaElegida().prendasAtuendo().size());
		Assert.assertEquals(7.0,juan.revisarEvento(planificador, 5).getSugerenciaElegida().getAtuendo().caloriasBasicas(),2);
		System.out.println("Test 04 - " + planificador.consultarEventoPlanificado(5).getSugerenciaElegida().prendasAtuendo());
	}	
		
	// (1) Todav�a o acept� la sugerencia, por ende el evento no tiene Atuendo sugerido (no hay prendas).
		// (2) Despu�s de Aceptar la sugerencia, en el Evento queda cargado un Atuendo.
		// El m�todo calor�as acumuladas devuelve la sumatoria de las calor�as de cada prenda que compone el atuendo.
	
	@Test // -- T E S T #05 --//
	public void nro05_rechazarSugerenciasEventos() throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		this.juan.cargarEventoPlanificado(planificador, new EventoPlanificado("2019-10-14T15:30:00", "Rosario", "Comprar alfajores",new Recordatorio(1,EscalaTiempo.HORAS)));
		this.juan.consultarEventosProximos(planificador);
		Sugerencia sugest1 = this.juan.pedirSugerenciaEvento(planificador, 4);
		this.juan.rechazarSugerenciaEvento(planificador, 4);
		Sugerencia sugest2 = this.juan.pedirSugerenciaEvento(planificador, 4);
		Assert.assertEquals(0,juan.revisarEvento(planificador,4).getSugerenciaElegida().prendasAtuendo().size()); 
		Assert.assertFalse(sugest1.prendasAtuendo().containsAll(sugest2.prendasAtuendo()));
	}	// Al pedir sugerencia para un evento, rechazar esa sugerencia y luego volver a pedir una sugerencia
		// la agenda ofrecer� una sugerencia distinta de la lista de atuendos.
		// Con el Assert Test anterior comprobamos que atuendo1 y atuendo2 son dos sugerencias diferentes que entreg� la agenda.
	
	@Test // -- T E S T #06 --//
	public void nro06_DeshacerUltimaOperacion() throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		this.jose.cargarEventoPlanificado(planificador,new EventoPlanificado("2019-10-11T01:00:00", "Buenos Aires", "Visita al Doctor"));
		this.planificador.consultarEventoPlanificado(4).setRecordatorio(new Recordatorio(1,EscalaTiempo.DIAS));
		this.jose.consultarEventosProximos(planificador);
		this.jose.pedirSugerenciaEvento(planificador, 4);
		this.jose.rechazarSugerenciaEvento(planificador, 4);
		this.jose.pedirSugerenciaEvento(planificador, 4);
		this.jose.rechazarSugerenciaEvento(planificador, 4);
		this.jose.pedirSugerenciaEvento(planificador, 4);
		this.jose.aceptarSugerenciaEvento(planificador, 4);
		Assert.assertTrue(jose.revisarEvento(planificador, 4).getSugerenciaElegida().prendasAtuendo().size()>=4);
		Assert.assertEquals(3,planificador.getLogIds().size());
		this.jose.deshacerUltimaOperacion(planificador);
		Assert.assertEquals(0,jose.revisarEvento(planificador, 4).getSugerenciaElegida().prendasAtuendo().size());
		Assert.assertEquals(2,planificador.getLogIds().size());
	}
	
	@Test // -- T E S T #07 --//
	public void nro07_consultasApiClima() throws SinDatosClimaException, IOException, SinPrendasException {
		//Assert.assertEquals(18.5,recomendador.getservicioClima().getpronosticoCiudadFecha("Buenos Aires", LocalDateTime.now()).getTempMax(),5);
		Assert.assertEquals(14.5,this.serviceProno.getpronosticoCiudadFecha("Cordoba", LocalDateTime.parse("2019-08-14T22:00:00")).getTempMax(),3);
		try {
			Assert.assertEquals(15.9,this.serviceProno.getpronosticoCiudadFecha("Posadas", LocalDateTime.parse("2019-08-13T20:00:21")).getTempMax(),4);
		}
		catch (SinDatosClimaException exception) {
		}
		Assert.assertEquals(0.2,this.serviceProno.getpronosticoCiudadFecha("Rosario", LocalDateTime.parse("2019-08-12T22:00:50")).getTempMin(),4);
	}
	
	@Test // -- T E S T #08 --//
	public void nro08_sugerenciasFlexibles() throws SinDatosClimaException, IOException, SinPrendasException {
		//Assert.assertTrue(jose.pideSugerenciasCiudadFechaRecomendador(recomendador,"Buenos Aires", LocalDateTime.now()).size() > 23);
		Assert.assertTrue(jose.pideSugerenciasCiudadFechaRecomendador(recomendador,"Buenos Aires", LocalDateTime.parse("2019-10-12T22:00:50")).size() < 500);
		Assert.assertTrue(jose.pideSugerenciasCiudadFechaRecomendador(recomendador,"Buenos Aires", LocalDateTime.parse("2019-10-11T22:00:50")).size() > 40);
		Assert.assertTrue(jose.pideSugerenciasCiudadFechaRecomendador(recomendador,"Buenos Aires", LocalDateTime.parse("2019-10-10T22:00:50")).size() > 30);
		Assert.assertTrue(jose.pideSugerenciasCiudadFechaRecomendador(recomendador,"Buenos Aires", LocalDateTime.parse("2019-10-14T22:00:50")).size() > 50);
		//System.out.println("Test 08 - " + jose.pideSugerenciasCiudadFechaRecomendador(recomendador,"Buenos Aires", LocalDateTime.parse("2019-08-26T22:00:50")).stream().map(sg->sg.getAtuendo().caloriasMinimas()).collect(Collectors.toList()));
		
	}	// A medida que disminuye la temperatura es menor la cantidad de Atuendos v�lidos que se puede
		// lograr. Dado que para satisfacer las calor�as necesarias tenemos que superponer las prendas.
		
		@Test // -- T E S T #09 --//
	public void nro09_imagenesPrendas() {
		Assert.assertFalse(pantalonAzul.tieneFoto());
		Assert.assertTrue(mochilaNegra.getFoto().esArchivo());
		Assert.assertTrue(zapatillasBlanca.tieneFoto());
		Assert.assertEquals(27929,camperaVerde.getFoto().tamano());
	}
	
	@Test // -- T E S T #10 --//
	public void nro010_superposicionPrendas() {
		Assert.assertTrue(pantalonAzul.puedeSuperponerA(calzoncilloNegro));
		Assert.assertFalse(camperaVerde.admiteSuperposicionDe(sweaterAzul));
		Assert.assertTrue(mediasNegras.admiteSuperposicionDe(zapatosNegros));
		Assert.assertTrue(sweaterAzul.puedeSuperponerA(camisaBlanca));
		Assert.assertEquals(25.5,joggingGris.nivelTermico(),3);
		Assert.assertEquals(5.4,musculosaAmarilla.nivelTermico(),3);
	}
	
	
	@Test // -- T E S T #10 --//
	public void nro011_TestPlanificacion() throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		this.juan.cargarPlanificacion(planificador,new Planificacion("2019-10-13T07:00:00","Rosario","Asistir a clase DDS","2019-10-13T11:00:00", EscalaTiempo.HORAS, new Recordatorio(10,EscalaTiempo.MINUTOS)));
		Assert.assertEquals(8,planificador.getEventos().size());
		juan.consultarEventosProximos(planificador);
		juan.revisarEvento(planificador, 4);
		juan.aceptarSugerenciaEvento(planificador, 6);
		Assert.assertEquals(7.0,juan.revisarEvento(planificador, 6).getSugerenciaElegida().getAtuendo().caloriasBasicas(),2);
	}
	
	@Test // -- T E S T #10 --//
	public void nro012_TestUsuarioCalificaSugerencia() throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
		this.juan.cargarEventoPlanificado(planificador,new EventoPlanificado("2019-10-13T07:00:00","Avellaneda","Hinchar por La Academia",new Recordatorio(20,EscalaTiempo.DIAS)));
		Assert.assertEquals(4,planificador.getEventos().size());
		Assert.assertTrue(juan.revisarEvento(planificador, 4).getEstado().equals(EstadoEvento.PENDIENTE));
		juan.consultarEventosProximos(planificador);
		Assert.assertTrue(juan.revisarEvento(planificador, 4).getEstado().equals(EstadoEvento.PROXIMO));
		juan.pedirSugerenciaEvento(planificador,4);
		System.out.println("Test 12 - " + juan.pedirSugerenciaEvento(planificador,4).prendasAtuendo());
		juan.rechazarSugerenciaEvento(planificador,4);
		juan.pedirSugerenciaEvento(planificador, 4);
		juan.aceptarSugerenciaEvento(planificador,4);
		System.out.println("Test 12 - " + juan.revisarEvento(planificador, 4).getSugerenciaElegida().prendasAtuendo());
		juan.revisarEvento(planificador,4).setFechaHora("2019-09-13T07:00:00");
		Assert.assertTrue(juan.revisarEvento(planificador, 4).getEstado().equals(EstadoEvento.PROXIMO));
		Assert.assertTrue(juan.revisarEvento(planificador, 4).estaVencido());
		juan.consultarEventosProximos(planificador);
		Assert.assertTrue(acumulador.consultarEventoEjecutado(4).getEstado().equals(EstadoEvento.VENCIDO));
		juan.calificarSugerenciaEvento(acumulador,4,new CalificacionSugerencia(ValorTermico.APROPIADA,ValorTermico.APROPIADA,ValorTermico.APROPIADA,ValorTermico.FRIA));
		Assert.assertTrue(acumulador.consultarEventoEjecutado(4).getFeedbackSugerencia().getSensacionCabeza().equals(ValorTermico.FRIA));
	}

    @Test // -- T E S T #13 --//
    public void nro013_TestMailPorEvento() throws SinDatosClimaException, IOException, SinPrendasException, SinSenderActivoException {
        NotificationSender not = new NotificationSender();
        not.enviarNotificacion(evento1,"Alerta meteorológica para su evento");
	}
    
    @Test // -- T E S T #14 --//
    public void nro014_TestCreacionPrenda() throws AtributosPrendaInvalidosException {
        RequestPrenda reqPrenda = new RequestPrenda();
        reqPrenda.setNombre("prueba");
        reqPrenda.setTipo("camiseta");
        reqPrenda.setCategoria("TORSO");
        reqPrenda.setTela("algodon");
        reqPrenda.setColorBase("rojo");
        reqPrenda.setColorSecundario("negro");
        Prenda prendita = reqPrenda.getPrendaFromThis();
        RepoPrendas.getInstance();
        System.out.println("Test 12 - " + prendita.deTipo() + " " + prendita.nivelTermico() + " " + RepoPrendas.getPrendas().size());
        RepoPrendas.getPrendas().add(prendita);
        Assert.assertTrue(prendita.nivelTermico() >= 5);
        System.out.println("Test 12 - " + prendita.deTipo() + " " + prendita.nivelTermico() + " " + RepoPrendas.getPrendas().size());
     }
}*/
package dominio;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;

import dominio.excepciones.SinDatosClimaException;
import dominio.servicios.ProveedorPronostico;
import dominio.servicios.ServicioPronostico;
import dominio.servicios.pronostico.accuweather.*;

public class TestGetMockAccuWeather {
	
	private LocationKeysAccu idLocations;
	private RecuperadorJsonAccu recuperadorJson;
	private ProveedorPronosticoAccu proveePronost;
	private PronosticoExtAccu pronosticoExtendido;
	private PronosticoExtAccu pronosticoExt2;
	private ServicioPronostico serviceProno;
	
	@Before
	public void init() throws IOException {
		this.recuperadorJson = RecuperadorJsonAccuMock.getInstance("./src/varios/jsonAccu/");
		this.proveePronost = ProveedorPronosticoAccu.getInstance(recuperadorJson, idLocations);
		List<ProveedorPronostico> providers = new LinkedList<ProveedorPronostico>();
		providers.add(proveePronost);
		this.pronosticoExtendido = this.recuperadorJson.getPronosticoExtendido(11222);
		this.pronosticoExt2 = this.recuperadorJson.getPronosticoExtendido(6462);
		this.serviceProno = ServicioPronostico.getInstance();
		this.serviceProno.setProveedores(providers);
	}

	@Test
	public void test01_obtengoFechaEfectivaEpochPronosticoExtendido() {
		Assert.assertEquals(1570870400,this.pronosticoExtendido.getHeadline().getEftEpochDate());
	}
	
	@Test
	public void test02_obtengoFechaValidezPronosticoExtendido() {
		Assert.assertEquals("2019-10-12T19:00:00-03:00",this.pronosticoExtendido.getHeadline().getEndDate());
	}
	
	@Test
	public void test03_cantidadDiasPronosticoExtendido() {
		Assert.assertEquals(5,this.pronosticoExtendido.getDailyForecasts().size());
	}
	
	@Test
	public void test04_obtengoFechasDia3() {
		Assert.assertEquals("2019-10-13T07:00:00-03:00",this.pronosticoExtendido.getDailyForecasts().get(2).getDate());
		Assert.assertEquals(1565690400,this.pronosticoExtendido.getDailyForecasts().get(2).getEpochDate());
	}
	
	@Test
	public void test05_obtengoTemperaturasDia4() throws SinDatosClimaException, IOException {
		Assert.assertEquals(12.8,this.proveePronost.getPronosticoCiudadFecha("Rosario",LocalDateTime.parse("2019-10-14T07:00:00")).getTempMax(),0.5);
		Assert.assertEquals(5.1,this.serviceProno.getpronosticoCiudadFecha("Rosario",LocalDateTime.parse("2019-10-11T07:00:00")).getTempMin(),0.005);
	}
	
	@Test
	public void test06_obtengoCondicionesDia5() {
		Assert.assertFalse(this.pronosticoExtendido.getDailyForecasts().get(4).getDay().getHasPrecipitation());
		Assert.assertEquals("Nubes intermitentes",this.pronosticoExtendido.getDailyForecasts().get(4).getDay().getIconPhrase());
		Assert.assertFalse(this.pronosticoExtendido.getDailyForecasts().get(4).getNight().getHasPrecipitation());
		Assert.assertEquals("Parcialmente nublado",this.pronosticoExtendido.getDailyForecasts().get(4).getNight().getIconPhrase());
	}
	
	@Test
	public void test07_obtengoCondicionesDia1() throws SinDatosClimaException, IOException {
		Assert.assertFalse(this.pronosticoExtendido.getDailyForecasts().get(0).getNight().getHasPrecipitation());
		Assert.assertEquals("Mayormente despejado",this.pronosticoExtendido.getDailyForecasts().get(0).getNight().getIconPhrase());
		System.out.println(this.proveePronost.getPronosticoCiudadFecha("Ushuaia",LocalDateTime.parse("2019-10-12T08:06:00")).datosPronostico());
		System.out.println(this.serviceProno.getpronosticoCiudadFecha("Cordoba",LocalDateTime.parse("2019-10-11T22:06:00")).getTempMax());
		System.out.println(this.proveePronost.getPronosticoCiudadFecha("La Quiaca",LocalDateTime.parse("2019-10-13T08:06:00")).datosPronostico());
		System.out.println(this.proveePronost.getPronosticoCiudadFecha("Posadas",LocalDateTime.parse("2019-10-12T19:06:00")).datosPronostico());
		System.out.println(this.serviceProno.getpronosticoCiudadFecha("Avellaneda", LocalDateTime.parse("2019-10-11T08:06:00")).datosPronostico());
	}
	
	@Test
	public void test08_obtengoAlertaMeteorologico() throws SinDatosClimaException, IOException {
		Assert.assertFalse(this.pronosticoExtendido.alertaClimatico());
		Assert.assertTrue(this.pronosticoExt2.alertaClimatico());
		System.out.println(LocalDateTime.parse("2019-10-12T19:00:00").toEpochSecond(ZoneOffset.UTC));
		Assert.assertTrue(this.proveePronost.getAlertaClimaCiudadFecha("Ushuaia", LocalDateTime.parse("2019-10-12T10:00:00")));
		Assert.assertFalse(this.proveePronost.getAlertaClimaCiudadFecha("Moron", LocalDateTime.parse("2019-10-12T18:00:00")));
		
	}
	
}


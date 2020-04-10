package dominio;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.Assert;

import dominio.excepciones.SinDatosClimaException;
import dominio.servicios.pronostico.accuweather.*;
import dominio.utilities.*;

public class TestGetHttpAccuWeather {
	private Parser jsonParser;
	private RecuperadorJsonAccu recuperadorJson;
	private PronosticoExtAccu pronosticoExtendido;
	private PronosticoExtAccu pronosticoExtMoron;
	private PronosticoAccu pronMoron;
	private double tempMinMoron;
	
	@Before
	public void init() throws SinDatosClimaException, IOException {
		this.jsonParser = Parser.getInstance();//<<SINGLETON>>
		this.recuperadorJson = RecuperadorHttpAccu.getInstance(jsonParser);//<<SINGLETON>>
		this.pronosticoExtendido = this.recuperadorJson.getPronosticoExtendido(11222); // Para la ciudad de Rosario.
		this.pronosticoExtMoron = this.recuperadorJson.getPronosticoExtendido(7245);
		this.pronMoron = this.pronosticoExtMoron.pronosticoUnaFecha(LocalDateTime.parse("2019-09-29T07:00:00"));
		this.tempMinMoron = this.pronMoron.getTemperaturaMin(); 
		}

	@Test
	public void test001_AssertsVariosPronostico() throws IOException,SinDatosClimaException {
		Assert.assertEquals(5,this.pronosticoExtendido.getDailyForecasts().size());
		Assert.assertEquals(19.6,tempMinMoron,2);
		Assert.assertEquals(23.2,this.pronosticoExtMoron.getTempMaximaFecha(LocalDateTime.parse("2019-09-28T07:00:00")),2);
		Assert.assertEquals(32.3,this.pronosticoExtendido.getDailyForecasts().get(3).getTemperature().getMaximum().getValue(),0.005);
		Assert.assertFalse(this.pronosticoExtendido.getDailyForecasts().get(1).getNight().getHasPrecipitation());
		Assert.assertEquals("Chaparrones",this.pronosticoExtendido.getDailyForecasts().get(4).getDay().getIconPhrase());
		Assert.assertTrue(this.pronosticoExtendido.getDailyForecasts().get(4).getNight().getHasPrecipitation());
		Assert.assertEquals("Parcialmente nublado",this.pronosticoExtendido.getDailyForecasts().get(1).getNight().getIconPhrase());
	}
	
}


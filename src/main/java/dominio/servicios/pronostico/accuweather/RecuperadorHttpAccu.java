package dominio.servicios.pronostico.accuweather;

import dominio.utilities.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecuperadorHttpAccu implements RecuperadorJsonAccu {
	
	private PronosticoExtAccu pronosticoExtendido;
	private Parser jsonParser;
	
	private static String readLine = null;
    private static String urlRest = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    private static String apiKey = "QSmmh7w3cUfjhuRfR0GJwMs6FPJcnJHc";
    private static String language = "es-ar";
    private static String metric = "true";
    
    public static RecuperadorHttpAccu getInstance(Parser p) {
	if (INSTANCE == null) {
		INSTANCE = new RecuperadorHttpAccu(p);
	}
		return INSTANCE;
	}

	private RecuperadorHttpAccu(Parser p) {
		super();
		this.jsonParser = p;
	}

	private static RecuperadorHttpAccu INSTANCE = null;


	@Override
	public boolean getEstadoActivo() throws IOException {
		return RecuperadorHttpAccu.respondeOk(7849);
	}

	@Override
	public PronosticoExtAccu getPronosticoExtendido(int locationKey) throws IOException {
		this.completoPronosticoExtendido(locationKey);
		return this.pronosticoExtendido;
	}

	private void completoPronosticoExtendido(int locationKey) throws IOException {
		this.pronosticoExtendido = this.jsonParser.fromJsonToObject(RecuperadorHttpAccu.getRequest(locationKey), PronosticoExtAccu.class);
		this.pronosticoExtendido.setLocationKey(locationKey);
	}
	private static boolean respondeOk(int locationId) throws IOException {
	    int responseCode = RecuperadorHttpAccu.codigoRta(locationId);
	    return responseCode == HttpURLConnection.HTTP_OK;
	}
	private static byte[] getRequest(int valor) throws IOException {
	    HttpURLConnection conection = RecuperadorHttpAccu.conn(valor);
		if (RecuperadorHttpAccu.codigoRta(valor) == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
	        	return String.valueOf(response).getBytes();
	        } else {
	        	return String.valueOf("").getBytes();
	    }
	}
	private static int codigoRta(int valor) throws IOException {
	    HttpURLConnection conection = RecuperadorHttpAccu.conn(valor);
	    return conection.getResponseCode();
	}
	private static String urlCompleta(int locationId) {
		return urlRest + locationId + "?apikey=" + apiKey + "&language=" + language + "&metric=" + metric;
	}
	private static URL urlForGetReq(int valor) throws IOException {
		return new URL(RecuperadorHttpAccu.urlCompleta(valor));
	}
	private static HttpURLConnection conn(int valor) throws IOException {
		return (HttpURLConnection) RecuperadorHttpAccu.urlForGetReq(valor).openConnection();
	}

}

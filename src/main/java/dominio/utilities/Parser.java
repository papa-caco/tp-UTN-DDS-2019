package dominio.utilities;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import dominio.excepciones.*;

public class Parser {
	
	private ObjectMapper objectMapper;
	
	private static Parser INSTANCE = null;
	
	private Parser() {
		this.objectMapper = new ObjectMapper();
	}
	
	public static Parser getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new Parser();
	}
		INSTANCE.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return INSTANCE;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	
/*toJson(Object object) para posibles salidas de archivos Json*/
	public String toJson(Object object) {
		try {
			String jsonString = this.objectMapper.writeValueAsString(object);
			return jsonString;
		} catch (JsonProcessingException e) {
			throw new ParserException("Error  de formato Json");
		}
	}

/* fromJson(byte[] json, Class<T> typeReference)devuevle un objecto Java desde un archivo json */
	public <T> T fromJsonToObject(byte[] json, Class<T> typeReference) {
		try { 
			return this.objectMapper.readValue(json, typeReference);
		} catch (IOException e) {
			throw new ParserException("Error  de formato Json");
		}
	}
	
/* fromJsonList(byte[] json, Class<T> typeReference)devuelve una lista de objetos desde un archivo Json  */
	public  <typeReference, T> List<typeReference> fromJsonToList(byte[] json, Class<T> typeReference) {
		try{
			List<typeReference>  list= this.objectMapper.readValue(json,TypeFactory.defaultInstance().constructCollectionType(List.class,typeReference));
			return  list ;
		} catch (IOException e) {
			throw new ParserException("Error  de formato Json");
		}
	 }
}

package dominio.servicios.pronostico.accuweather;

import java.io.IOException;

public interface RecuperadorJsonAccu {
	
	public abstract PronosticoExtAccu getPronosticoExtendido(int locationKey)throws IOException;
	public abstract boolean getEstadoActivo()throws IOException;

}

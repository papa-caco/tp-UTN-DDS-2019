package dominio.utilities.ordenadores;

import java.util.Comparator;

import dominio.eventos.Evento;

public class OrdenadorEventosPorAntiguo implements Comparator<Evento> {
		
	    @Override
	    public int compare(Evento e1, Evento e2) {
	        if(e1.getFechaHoraEvento().isAfter(e2.getFechaHoraEvento())){
	           return 1;
	        } else {
	            return -1;
	        }
	    }
}
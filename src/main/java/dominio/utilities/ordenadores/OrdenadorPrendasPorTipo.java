package dominio.utilities.ordenadores;

import java.util.Comparator;

import dominio.prenda.Prenda;

public class OrdenadorPrendasPorTipo implements Comparator<Prenda> {
	
    @Override
    public int compare(Prenda p1, Prenda p2) {
       if ( p1.getTipo().getIdTipo() < p2.getTipo().getIdTipo()) {
    	   return 1;
       }
       else	{
    	   return -1;
       }
     }
    
}

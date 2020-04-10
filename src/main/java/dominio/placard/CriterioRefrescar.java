package dominio.placard;

import dominio.clima.Pronostico;
import dominio.prenda.Atuendo;
import dominio.usuario.Usuario;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue(value = "CriterioRefrescar")
public class CriterioRefrescar extends CriterioSeleccionAtuendos {
	
	public CriterioRefrescar() {
		super();
	}
	
	@Override
	public boolean cumpleCondicion(Pronostico prono,Usuario user, Atuendo unAtuendo) {
		return unAtuendo.caloriasBasicas() <= (float)prono.getTempMax() * 0.3 ;
	}

}
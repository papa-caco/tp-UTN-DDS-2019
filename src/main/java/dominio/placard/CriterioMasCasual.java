package dominio.placard;

import dominio.clima.Pronostico;
import dominio.prenda.Atuendo;
import dominio.prenda.CaracterPrenda;
import dominio.usuario.Usuario;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue(value = "CriterioMasCasual")
public class CriterioMasCasual extends CriterioSeleccionAtuendos {
	
	public CriterioMasCasual( ) {
		super();
	}

	@Override
	public boolean cumpleCondicion(Pronostico prono, Usuario user, Atuendo unAtuendo) {
		return unAtuendo.esAtuendoDeCaracter(CaracterPrenda.CASUAL);
	}

}

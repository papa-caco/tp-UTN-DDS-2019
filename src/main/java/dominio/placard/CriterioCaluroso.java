package dominio.placard;

import dominio.clima.Pronostico;
import dominio.prenda.Atuendo;
import dominio.usuario.Usuario;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue(value = "CriterioCaluroso")
public class CriterioCaluroso extends CriterioSeleccionAtuendos {

    public CriterioCaluroso() {
        super();
    }

    @Override
    public boolean cumpleCondicion(Pronostico prono, Usuario user, Atuendo unAtuendo) {
        return unAtuendo.caloriasBasicas() - 150 <= prono.getTempMin();
    }

}

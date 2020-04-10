package dominio.placard;

import dominio.clima.Pronostico;
import dominio.prenda.Atuendo;
import dominio.usuario.Usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;

@Entity
@Table(name = "CriteriosUsuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo_Criterio")
public abstract class CriterioSeleccionAtuendos {
	
	public CriterioSeleccionAtuendos() {
		super();
	}
	
	public abstract boolean cumpleCondicion(Pronostico prono,Usuario user, Atuendo unAtuendo);
	
	@Id
	@GeneratedValue
    @Column(name = "IdCriterio", unique = true, nullable = false)
	private int idCriterio;

}

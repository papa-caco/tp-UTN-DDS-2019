package dominio.eventos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Ubicacion")
@Table(name = "ubicaciones")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Ubicacion {
	
	@Id
	@GeneratedValue
	@Column(name = "IdUbicacion")
	private int idUbicacion;
	
	@Column
	private int idAccu;
	
	@Column
	private String ciudad;
	
	public Ubicacion(String ciudad, int idAccu) {
		this.ciudad = ciudad;
		this.idAccu = idAccu;
	}

}

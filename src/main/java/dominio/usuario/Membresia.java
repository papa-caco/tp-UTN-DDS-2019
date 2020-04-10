package dominio.usuario;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "membresias")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Membresia {
	
	@Id
	@GeneratedValue
	@Column(name = "IdMembresia")
	private int idMembresia;
	
	@Column
	@NonNull
	private Integer cantPrendasPlacard;


}

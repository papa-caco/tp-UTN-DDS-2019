package dominio.prenda;

import java.util.List;
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
import javax.persistence.Transient;

@Entity
@Table(name = "telas")
@Getter
@Setter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
@NoArgsConstructor
	
public class Tela {
	
	public boolean tipoValido(Tipo unTipo) {
		return this.paraTipos.stream().anyMatch(tipo -> tipo.equals(unTipo));
	}
	
	public static Tela of(String material, List<Tipo> paraTipos) {
		Tela tela = new Tela(material, paraTipos);
		return tela;
	}
	
	@Id
	@GeneratedValue
    @Column(name = "IdTela", unique = true, nullable = false)
	private int idTela;

	@NonNull
	private String material;	// Algodon, Lana, Seda, Cuero, Lona
	
	@Transient
	@NonNull
	private List<Tipo> paraTipos;

}
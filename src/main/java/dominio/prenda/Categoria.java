package dominio.prenda;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Categoria{

	PIE,PIERNAS,TORSO,ACCESORIO,CABEZA,CUELLO,MANO,GLOBAL;

	private int idCategoria = this.ordinal();
	
	private String categoria = this.name();

}
	

package dominio.prenda;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum CaracterPrenda {
	
	DEPORTIVO,CASUAL,FORMAL;

	private int idUso = this.ordinal();
	
	private String usoPrenda = this.name();
	
	private String caracter = this.name();

}

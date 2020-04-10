package dominio.prenda;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ValorTermico {
	SIN_CALIFICAR,FRIA,APROPIADA,CALUROSA;
	
	private String valor = this.name();
	private int idValor = this.ordinal();
		
}

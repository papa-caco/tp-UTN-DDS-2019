package dominio.eventos;

import lombok.Getter;

@Getter
public enum EscalaTiempo {
	MINUTOS,HORAS,DIAS,SEMANAS,MESES,ANIOS;
	EscalaTiempo(){
	}

	public int multiplicadorSegundos() {
		if (this.ordinal() <= 1) {
			return (int) Math.pow(60,this.ordinal()+1);
		}
		if (this.ordinal() == 2) {
			return 3600 * 24;
		}
		if (this.ordinal() == 3) {
			return 86400 * 7;
		}
		if (this.ordinal() == 4) {
			return 86400 * 30;
		}
		else {
			return 86400 * 365;
		}
	}
	
	private int idEscalaTiempo = this.ordinal();
	
	private String unidadTiempo = this.name();
}


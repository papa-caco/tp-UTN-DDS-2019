package dominio.servicios;

import dominio.eventos.Evento;

public interface Notificador {
	
	public void enviarNotificacion(Evento evento, String body);
	public boolean respondeOk();
	public void sendMail(String dest, String subj, String body);

}

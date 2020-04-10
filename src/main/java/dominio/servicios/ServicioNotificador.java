package dominio.servicios;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import dominio.eventos.Evento;
import dominio.excepciones.SinSenderActivoException;

public class ServicioNotificador {
	
	private List<Notificador> notificadores = new LinkedList<>();

	private static ServicioNotificador INSTANCE = null;

	private ServicioNotificador() {
		super();
	}

	public static ServicioNotificador getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new ServicioNotificador();
	}
	return INSTANCE;
	}
	
	public List<Notificador> getSenders() {
		return notificadores;
	}

	public void addSender(Notificador notif) {
		this.notificadores.add(notif);
	}
	
	public void setSenders(List<Notificador> notif) {
		this.notificadores = notif;
	}
	
	public void enviarNotificacion(Evento evento, String body) throws SinSenderActivoException {
		try {
			this.senderActivo().enviarNotificacion(evento, body);
		} catch (SinSenderActivoException exc) {
			throw exc;
		}
	}
		
	private List<Notificador> sendersOk(){
		return this.notificadores.stream().filter(snd -> snd.respondeOk()).collect(Collectors.toList());
	}

	private Notificador senderActivo() throws SinSenderActivoException {
		if (this.sendersOk().isEmpty()) {
				throw new SinSenderActivoException();
		}
		else {
			return this.sendersOk().get(0);
		}
	}
	
}
	
	
	



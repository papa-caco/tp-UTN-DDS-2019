package dominio.servicios.notificacion;

import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import dominio.eventos.Evento;
import dominio.servicios.Notificador;


public class NotificationSender2 implements Notificador {
	
	private static NotificationSender2 INSTANCE = null;
	
	private NotificationSender2() {
		super();
	}
	
	//ServicioPronostico

	public static NotificationSender2 getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new NotificationSender2();
		}
		return INSTANCE;
	}
	
	
	private static final String SMTP_SERVER_HOST = "smtp.gmail.com";
	private static final String SMTP_SERVER_PORT = "587";
	private static final String USERNARME = "disenioutn2019@gmail.com";
	private static final String PASSWORD = "disenio2019";
	private static final String EMAIL_FROM = "disenioutn2019@gmail.com";
	
	private Address fromAddr;	
	
	private String EMAIL_TO;

	private String SUBJECT;

	private String BODY;

	public void enviarNotificacion(Evento evento, String body) {
		String destination = evento.getUsuario().getEMail();
		String subject =  "Sistema Que me pongo? - Notificacion Evento nro: " + evento.getIdEvento() + "- Usuario: " + evento.getUsuario().getUserName();
				;
		this.sendMail(destination, subject, body);
	}

	public boolean respondeOk() {
		return true;
	}
	
	public void sendMail(String dest, String subj, String body) {
		try {
			Properties prop = System.getProperties();
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.host", SMTP_SERVER_HOST);
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.port", SMTP_SERVER_PORT);
			prop.put("mail.smtp.user", USERNARME);
			prop.put("mail.smtp.password", PASSWORD);
			prop.put("mail.smtp.ssl.trust", "*");
			
			this.EMAIL_TO = dest;
			this.SUBJECT = subj;
			this.BODY = body;

			Session session = Session.getInstance(prop, null);
			session.getProperties().put("mail.smtp.starttls.enable", "true");
			Message msg = new MimeMessage(session);

			try {
				
				this.fromAddr = new InternetAddress(EMAIL_FROM, "Admin - DDS - K3151_Grupo-7");
				// from
				msg.setFrom(this.fromAddr);
				// to
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO, false));
				// subject
				msg.setSubject(SUBJECT);
				// content
				msg.setText(BODY);
				msg.setSentDate(new Date());
				// Get SMTPTransport
				SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
				// connect
				transport.connect(SMTP_SERVER_HOST, USERNARME, PASSWORD);
				// send
				transport.sendMessage(msg, msg.getAllRecipients());
				System.out.println("Response: " + transport.getLastServerResponse());
				transport.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		NotificationSender2.getInstance().sendMail("ccomesa@gmail.com","Prueba Metodos Sender n° 2","This is a test from NotificationSender 2 !");
	}
}
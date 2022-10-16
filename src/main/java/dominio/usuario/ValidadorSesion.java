package dominio.usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class ValidadorSesion {
	
	public static boolean validarSesionUsuario(Usuario user,String login, String pass) throws NoSuchAlgorithmException {	
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(pass.getBytes());
		byte[] digest = md.digest();
	    String myHash = DatatypeConverter
	    	      .printHexBinary(digest).toUpperCase();
		
		return (user.getLogin().equals(login) && myHash.equals(user.getPassword()));
	}

}
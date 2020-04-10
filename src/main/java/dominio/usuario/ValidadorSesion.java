package dominio.usuario;

public class ValidadorSesion {
	
	public static boolean validarSesionUsuario(Usuario user,String login, String pass) {	
		
		return (user.getLogin().equals(login) && pass.equals(user.getPassword()));
	}

}
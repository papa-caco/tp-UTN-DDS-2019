package dominio.webapp;

import spark.debug.DebugScreen;
import static spark.Spark.*;

import java.io.IOException;

import dominio.excepciones.*;
import dominio.repository.InstanciadorClases;

public class ServerWeb {

    public static void main(String[] args) throws AtributosPrendaInvalidosException, RecomendacionInvalidaException, RoperoFullException, SinPrendasException, RoperoIncorrectoException, SinDatosClimaException, IOException {
    	DebugScreen.enableDebugScreen();
        port(8400);
        InstanciadorClases.getInstance().init();
        Router.configure();
    }
 }
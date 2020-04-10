package dominio.webapp;

import spark.Spark;
import static spark.Spark.staticFiles;
import spark.template.handlebars.HandlebarsTemplateEngine;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import dominio.webapp.controller.*;
import dominio.webapp.handlebars.BooleanHelper;
import dominio.webapp.handlebars.HandlebarsTemplateEngineBuilder;


public class Router implements WithGlobalEntityManager {

	public static void configure() {
		
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers().withHelper("isTrue", BooleanHelper.isTrue).build();
        
		boolean localhost = true;
        
		if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static/";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/public");
        }
		
		Spark.get("/", HomeController::home, engine);
        Spark.get("/login", LoginController::show, engine);
		Spark.post("/login", LoginController::login, engine);
		Spark.get("/salir", LoginController::salir, engine);
        Spark.get("/usuario", HomeUsuarioController.getInstance()::show, engine);
		Spark.get("/placards", HomeUsuarioController.getInstance()::listPlacards, engine);
		Spark.get("/altaprenda", HomeUsuarioController.getInstance()::mostrarAltaPrenda, engine);
		Spark.post("/altaprenda", HomeUsuarioController.getInstance()::altaPrenda, engine);
		Spark.get("/prendas", PrendasController.getInstance()::listPrendasTipo, engine);
		Spark.get("/prendasPlacard", PrendasController.getInstance()::listPrendasPlacard, engine);
		Spark.get("/gestionEventos", EventosController.getInstance()::showGestionEventos, engine);
		Spark.get("/gestionEventos/altaEvento", EventosController.getInstance()::mostrarAltaEvento, engine);
		Spark.post("/gestionEventos/altaEvento", EventosController.getInstance()::altaEvento, engine);
		Spark.get("/gestionEventos/getProximos", EventosController.getInstance()::mostrarProximosEventos, engine);
		Spark.get("/gestionEventos/getHistoricos", EventosController.getInstance()::mostrarEventosHistoricos, engine);
		Spark.get("/gestionEventos/getHistoricosCalificados", EventosController.getInstance()::mostrarHistoricosCalificados, engine);
		Spark.get("/gestionEventos/actualizarCalificacion/:idEvento", EventosController.getInstance()::mostrarActualizarCalificacion, engine);
		Spark.post("/gestionEventos/actualizarCalificacion/:idEvento", EventosController.getInstance()::actualizarCalificacion, engine);
		Spark.get("/gestionEventos/consultarSugerenciasEvento/:idEvento", EventosController.getInstance()::consultarSugerenciasEvento, engine);
		Spark.post("/gestionEventos/deshacerSeleccionSugerencia/:idEvento", EventosController.getInstance()::anularSeleccionSugerencia, engine);
		Spark.get("/gestionEventos/calificarSugerencia/:idEvento", EventosController.getInstance()::mostrarCalificarSugerencia, engine);
		Spark.get("/sugerencias/mostrarAccionesSugerencia/:idSugerencia", SugerenciasController.getInstance()::mostrarAccionesSugerencia, engine);
		Spark.post("/sugerencias/aceptarSugerencia/:idSugerencia", SugerenciasController.getInstance()::aceptarSugerencia, engine);
		Spark.post("/sugerencias/rechazarSugerencia/:idSugerencia", SugerenciasController.getInstance()::rechazarSugerencia, engine);
		Spark.post("/sugerencias/calificarSugerencia/:idSugerencia", SugerenciasController.getInstance()::calificarSugerencia, engine);
		
		
		Spark.after((req, res) -> {
			PerThreadEntityManagers.getEntityManager().clear();
		});
	}
         	
}

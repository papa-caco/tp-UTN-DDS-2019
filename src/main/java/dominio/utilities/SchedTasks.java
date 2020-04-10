package dominio.utilities;

import java.io.IOException;
import java.time.LocalDateTime;
//import java.io.IOException;
//import java.util.List;
import java.util.TimerTask;

import dominio.eventos.*;
import dominio.excepciones.*;
//import dominio.placard.Sugerencia;
import dominio.repository.*;
import dominio.usuario.Usuario;

public class SchedTasks extends TimerTask {
	private static SchedTasks INSTANCE = null;
	
	private SchedTasks() {
		super();
	}
	
	public static SchedTasks getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new SchedTasks();
		}
		return INSTANCE;
	}
	
	public void run() {
		
		Usuario jose = RepoUsuarios.getInstance().buscarPorLogin("jose");
		Usuario juan = RepoUsuarios.getInstance().buscarPorLogin("juan");
		
		System.out.println("Juan tiene " + RepoEventosHistoricos.getInstance().consultarEventosHistoricosUsuario(juan).size() + " eventos historicos cargados en el sistema");
		System.out.println("Jose tiene " + RepoEventosHistoricos.getInstance().consultarEventosHistoricosUsuario(jose).size() + " eventos historicos cargados en el sistema");
		System.out.println("Jose tiene " + Planificador.getInstance().consultarEventosPlanificadosUsuario(jose).size() + " eventos planificados cargados en el sistema");
		System.out.println("Juan tiene " + Planificador.getInstance().consultarEventosPlanificadosUsuario(juan).size() + " eventos planificados cargados en el sistema");
		
		
		System.out.println("Sistema ''Que me pongo?'' - Fecha y hora actual: " + LocalDateTime.now().toString());
		System.out.println("Hay " + RepoTelas.getInstance().getTelas().size() + " telas cargadas en el sistema.");
        System.out.println("Hay " + RepoColores.getInstance().getColores().size() + " colores cargados en el sistema.");
        System.out.println("Hay " + RepoTipos.getInstance().getTipos().size() + " tipos de prenda cargados en el sistema.");
        System.out.println("Hay " + RepoPrendas.getInstance().getPrendas().size() + " prendas cargadas en el sistema.");
        System.out.println("Hay " + RepoPlacards.getInstance().getPlacards().size() + " placards cargados en el sistema.");
        System.out.println("Hay " + RepoAtuendos.getInstance().getAtuendos().size() + " atuendos generados en el sistema.");
        System.out.println("Hay " + RepoSugerencias.getInstance().getSugerencias().size() + " sugerencias generadas en el sistema.");
        System.out.println("Hay " + RepoUsuarios.getInstance().getUsuarios().size() + " usuarios cargados en el sistema.");
        Planificador.getInstance().mostrarCantidadEventosEnBaseDeDatos();
        System.out.println("Hay " + RepoEventosHistoricos.getInstance().getEventosHistoricos().size() + " eventos historicos cargados en el sistema.");
        
        /*Recordatorio reminder1 = RepoReminders.getInstance().obtenerRecordatorio(1, EscalaTiempo.DIAS);
        Recordatorio reminder2 = RepoReminders.getInstance().obtenerRecordatorio(3, EscalaTiempo.HORAS);
        Recordatorio reminder3 = RepoReminders.getInstance().obtenerRecordatorio(45, EscalaTiempo.MINUTOS);
        
        Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-05T07:14:00","Rosario","Pescar","jose",reminder1));
        Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2019-09-05T13:01:00","Cordoba","Estudiar","jose"));
        Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-05T01:01:00","Mar Del Plata","Playa y Mate","juan",reminder3));
        Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2019-12-25T01:01:00","Cordoba","Navidad","juan"));
        Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-05T02:50:00","Mendoza","Visitar Bodega","juan",reminder2));
        Planificador.getInstance().agregarEventoPlanificado(new EventoPlanificado("2020-02-05T08:50:00","Posadas","Congreso","jose",reminder2));
        
        Planificador.getInstance().completarSugerenciaElegidaEventoPlanificado(3999,4300);
        Planificador.getInstance().completarSugerenciaElegidaEventoPlanificado(4001,4434);
        Planificador.getInstance().completarSugerenciaElegidaEventoPlanificado(4003,4450);
        Planificador.getInstance().completarSugerenciaElegidaEventoPlanificado(4004,4900);*/
         
        Planificador.getInstance().cargarSubeventosPlanificaciones();
        try {
        Planificador.getInstance().actualizarEstadoEventos();
    	} catch (SinSenderActivoException exc) {
    		exc.printStackTrace();
    	} catch (SinDatosClimaException exc) {
    		exc.printStackTrace();
   		} catch (IOException exc) {
    		exc.printStackTrace();
   		} catch (SinPrendasException exc) {
    		exc.printStackTrace();
        }

	}
	
}
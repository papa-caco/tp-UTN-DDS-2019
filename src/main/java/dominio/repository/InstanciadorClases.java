package dominio.repository;

//import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import com.google.common.collect.Lists;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.excepciones.*;
import dominio.servicios.ProveedorPronostico;
import dominio.servicios.ServicioNotificador;
import dominio.servicios.ServicioPronostico;
import dominio.servicios.notificacion.NotificationSender1;
import dominio.servicios.notificacion.NotificationSender2;
import dominio.servicios.pronostico.accuweather.*;
import dominio.utilities.SchedTasks;
import dominio.webapp.request.Auxiliar;

public class InstanciadorClases implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	
	private static InstanciadorClases INSTANCE = null;
	
	private InstanciadorClases() {
		super();
	}
		
	public static InstanciadorClases getInstance() {
	if (INSTANCE == null) {
		INSTANCE = new InstanciadorClases();
		}
		return INSTANCE;
	}
	
	public  void  init() throws AtributosPrendaInvalidosException, RecomendacionInvalidaException, RoperoFullException, RoperoFullException, SinPrendasException, RoperoIncorrectoException, SinDatosClimaException, IOException {
		withTransaction(() -> {
			ProveedorPronosticoAccu proveePronost = ProveedorPronosticoAccu.getInstance(RecuperadorJsonAccuMock.getInstance("./src/varios/jsonAccu/"), LocationKeysAccu.getInstance()); //<<SINGLETON>>
	        List<ProveedorPronostico> providers = new LinkedList<>(); 
	        providers.addAll(Lists.newArrayList(proveePronost));
	        //CriterioSeleccionAtuendos criterioVentilado = new CriterioRefrescar();
	        //CriterioSeleccionAtuendos criterioCasual = new CriterioMasCasual();
	        ServicioNotificador.getInstance().setSenders(Lists.newArrayList(NotificationSender1.getInstance(),NotificationSender2.getInstance()));
	        ServicioPronostico.getInstance().setProveedores(Lists.newArrayList(proveePronost));
	        RepoAuxiliares.getInstance().setValores(Lists.newArrayList(new Auxiliar("NO"),new Auxiliar("SI")));
	        Timer t1 = new Timer();
	        t1.schedule(SchedTasks.getInstance(), 0, 30000);
		});
	
	}

}

package dominio.usuario;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.persistence.Transient;

import dominio.placard.*;
import dominio.repository.RepoEventosHistoricos;
import dominio.eventos.*;
import dominio.excepciones.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter(AccessLevel.PUBLIC)
public class Usuario {
	
	@Id
	@GeneratedValue
	@Column(name = "IdUsuario")
	private int idUsuario;
	
	@Column
	@NonNull
    private String userName;
	
	@Column
	@NonNull
    private String login;
	
	@Column
	@NonNull
    private String password;
	
	@Column
	@NonNull
    private String eMail;
	
	@NonNull
	@JoinColumn(name = "IdMembresia")
    @ManyToOne(cascade = CascadeType.ALL)
    private Membresia membresia;
	
	@NonNull
	@Column
	private Integer esAdmin;
	
	@ManyToMany(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<Placard> placards = new ArrayList<>();
	
    private Integer puntosFormal = 0;
    
    private Integer puntosCasual = 0;
    
	@NonNull
	@JoinColumn(name = "IdCriterio")
    @ManyToOne(cascade = CascadeType.ALL)
    private CriterioSeleccionAtuendos criterioVestimenta; 

    public Usuario (String unNombre,String login, Membresia membre, List<Placard> misPlacards, CriterioSeleccionAtuendos unCriterio) {
    	this.userName = unNombre;
       	this.membresia = membre;
       	this.placards = misPlacards;
       	this.criterioVestimenta = unCriterio;
       	this.eMail = "user@dds-utn.com.ar";
       	this.login = login;
       	this.password = "1234";
       	this.esAdmin = 0;
    }
    
    public Usuario (String unNombre,String login , Membresia membre, String correo, List<Placard> misPlacards, CriterioSeleccionAtuendos unCriterio) {
    	this.userName = unNombre;
       	this.membresia = membre;
       	this.placards = misPlacards;
       	this.criterioVestimenta = unCriterio;
       	this.eMail = correo;
       	this.login = login;
       	this.password = "1234";
       	this.esAdmin = 0;
    }
    
    public Usuario (String unNombre, String login, Membresia membre, List<Placard> misPlacards, CriterioSeleccionAtuendos unCriterio, int valorFormal, int valorCasual) {
        this.userName = unNombre;
       	this.membresia = membre;
       	this.placards = misPlacards;
       	this.criterioVestimenta = unCriterio;
       	this.eMail = "user@dds-utn.com.ar";
       	this.puntosFormal = valorFormal;
       	this.puntosCasual = valorCasual;
       	this.login = login;
       	this.password = "1234";
       	this.esAdmin = 0;
    }
    
    public Usuario (String unNombre,String login, Membresia membre, String correo, String clave, List<Placard> misPlacards, CriterioSeleccionAtuendos unCriterio, int valorFormal, int valorCasual) {
        this.userName = unNombre;
       	this.membresia = membre;
       	this.placards = misPlacards;
       	this.criterioVestimenta = unCriterio;
       	this.eMail = correo;
       	this.puntosFormal = valorFormal;
       	this.puntosCasual = valorCasual;
       	this.login = login;
       	this.password = clave;
       	this.esAdmin = 0;
    }
    public Usuario (String unNombre, Membresia membre, String correo, String login, List<Placard> misPlacards, CriterioSeleccionAtuendos unCriterio, int valorFormal, int valorCasual) {
        this.userName = unNombre;
       	this.membresia = membre;
       	this.placards = misPlacards;
       	this.criterioVestimenta = unCriterio;
       	this.eMail = correo;
       	this.puntosFormal = valorFormal;
       	this.puntosCasual = valorCasual;
       	this.login = login;
       	this.password = "1234";
       	this.esAdmin = 0;
    }
    
    public Usuario () {
    }
    
    public Usuario (String unNombre, String correo,Membresia membre, String login,CriterioSeleccionAtuendos criterio, int valorFormal, int valorCasual) {
        this.userName = unNombre;
        this.eMail = correo;
       	this.login = login;
       	this.password = "1234";
       	this.membresia = membre;
       	this.criterioVestimenta = criterio;
       	this.puntosFormal = valorFormal;
       	this.puntosCasual = valorCasual;
       	this.esAdmin = 0;
    }
    
    public void cargarEventoPlanificado(EventoPlanificado event) {
    	event.setUsuario(this);
    	Planificador.getInstance().agregarEventoPlanificado(event);
	}
    
    public void cargarPlanificacion(Planificacion plan) {
		plan.setUsuario(this);
		Planificador.getInstance().cargarPlanificacion(plan);
	}
    
    public List<EventoPlanificado> consultarEventosProximos() throws SinDatosClimaException, IOException , SinPrendasException, SinSenderActivoException {
		return Planificador.getInstance().eventosProximos(this);
	}
    
    public List<Evento> consultarEventos() throws SinDatosClimaException, IOException , SinPrendasException, SinSenderActivoException {
		return Planificador.getInstance().getEventosUsuario(this.idUsuario);
	}

	public EventoPlanificado revisarEvento(int idEvt) {
		return Planificador.getInstance().consultarEventoPlanificadoPorId(idEvt);
	}
    
    public void rechazarSugerenciaEvento(int idEvento, int idSugerencia) {
    	Planificador.getInstance().rechazaSugerenciaEventoPlanificado(idEvento, idSugerencia);
	}

	public void aceptarSugerenciaEvento(int idEvento, int idSugerencia) {
		Planificador.getInstance().aceptarSugerenciaEventoPlanificado(idEvento, idSugerencia);
    }
	
	public void calificarSugerenciaEvento(int idEvt,CalificacionSugerencia calif) {
		EventoHistorico event = RepoEventosHistoricos.getInstance().consultarEventoHistoricoPorId(idEvt);
		calif.setUsuario(this);
		event.setFeedbackSugerencia(calif);
	}
    
    public void deshacerUltimaOperacion(int idEvento) {
    	Planificador.getInstance().deshacerEleccionSugerencia(idEvento);
    }
    
    public int capacidadPlacards() {
    	return membresia.getCantPrendasPlacard();
    }
  
}


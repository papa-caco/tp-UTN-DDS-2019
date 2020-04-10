package dominio.placard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import dominio.excepciones.*;
import dominio.prenda.*;
import dominio.usuario.*;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Streams;

@Entity(name = "Placard")
@Table(name = "placards")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Placard {
	
    public void addPrenda(Prenda prenda, Usuario user) throws RoperoFullException {
		try {
			this.sumaPrenda(prenda, user);
		}
		catch (RoperoFullException exception) {
			throw exception;
		}
	}

	public void setPrendas(List<Prenda> items, Usuario user) throws RoperoFullException {
		try {
			this.seteoPrendas(items, user);
		}
		catch (RoperoFullException exception) {
			throw exception;
		}
	}

	public int capacidadDisponible(Usuario user) {
		return user.capacidadPlacards() - this.prendas.size();
	}

	public List<Atuendo> generoAtuendosBasicos() throws SinPrendasException {
		return this.combinoTipos().stream().
				map(unSet -> this.generaAtuendoSuper(unSet)).collect(Collectors.toList());
	}
	
	public void colocoPrendaComplementaria(Atuendo atuendo, Categoria categoria) throws SinPrendasException {
		Prenda prenda = this.buscoPrendaCategoria(categoria);
		atuendo.setPrendaComplementaria(prenda);
	}
		
	public Prenda buscoPrendaCategoria(Categoria categoria) throws SinPrendasException {
		try {
			Prenda prenda = this.prendasPara(categoria).get(0);
			return prenda;
		} catch (SinPrendasException exc) {
			throw exc;
		}
	}
	
	public boolean contienePrendaId(int idPrenda) {
		return ! this.prendas.stream().filter(pr -> pr.getIdPrenda() == idPrenda).collect(Collectors.toList()).isEmpty();
	}
	
	private Atuendo generaAtuendoSuper(List<List<Prenda>> combinaciones){
		List<Prenda> listaPrendas = combinaciones.stream().flatMap(List::stream).collect(Collectors.toList());
		return new Atuendo(listaPrendas, this);
	}
	
	private List<List<List<Prenda>>> combinoTipos() throws SinPrendasException {
	   	return Lists.cartesianProduct(ImmutableList.of
	    	(this.combinacionValidaAccesorios(),this.combinacionValidaPies()
	    	,this.combinacionValidaPiernas(), this.combinacionValidaTorso()));
	}
	
	private List<List<Prenda>> combinacionValidaAccesorios() throws SinPrendasException{
		return this.combinacionesValidas(Categoria.ACCESORIO);
	}
	
	private List<List<Prenda>> combinacionValidaPies() throws SinPrendasException{
		return this.combinacionesValidas(Categoria.PIE);
	}
	
	private List<List<Prenda>> combinacionValidaPiernas() throws SinPrendasException{
		return this.combinacionesValidas(Categoria.PIERNAS);
	}
	
	private List<List<Prenda>> combinacionValidaTorso() throws SinPrendasException{
		return this.combinacionesValidas(Categoria.TORSO);
	}
	
    private List<List<Prenda>> combinacionesValidas(Categoria categoria) throws SinPrendasException {
    	if (categoria == Categoria.PIE || categoria == Categoria.PIERNAS) {
    		return this.combinacionPrendasCategoria(categoria).stream().filter(prendas -> esValidoConInterior(prendas)).collect(Collectors.toList());
    	}
    	if (categoria == Categoria.TORSO) {
    		return this.combinacionPrendasCategoria(categoria).stream().filter(prendas -> esValidoSinSuperiorSola(prendas)).collect(Collectors.toList());
    	}
		return this.combinacionPrendasCategoria(categoria).stream().filter(prendas -> esConjuntoValido(prendas)).collect(Collectors.toList());
	}
    
	private boolean esConjuntoValido(List<Prenda> prendasVarias) {
		return esSuperposicionValida(prendasVarias) && sonPrendasMismoUso(prendasVarias);
	}
    
	private boolean esSuperposicionValida(List<Prenda> prendasVarias) {
		return StreamEx.of(prendasVarias).pairMap((p1,p2)->p1.admiteSuperposicionDe(p2)).allMatch(b1->b1.equals(true));
	}
	
	private boolean contienePrendaInterior(List<Prenda> prendasVarias) {
		boolean tieneInterior = prendasVarias.stream().filter(pr->pr.getTipo().getCapa().esInterior()).count() > 0;
		return tieneInterior && prendasVarias.size() >= 2;
	}
	
	private boolean contieneSuperiorSola(List<Prenda> prendasVarias) {
		boolean tieneSuperior = prendasVarias.stream().filter(pr->pr.getTipo().getCapa().esSuperior()).count() > 0;
		return tieneSuperior && prendasVarias.size() == 1;
	}
	
	private boolean esValidoConInterior(List<Prenda> prendasVarias) {
		return esConjuntoValido(prendasVarias) && contienePrendaInterior(prendasVarias);
	}
	
	private boolean esValidoSinSuperiorSola(List<Prenda> prendasVarias) {
		return esConjuntoValido(prendasVarias) && !contieneSuperiorSola(prendasVarias);
	}
	
	private boolean sonPrendasMismoUso(List<Prenda> prendasVarias) {
		CaracterPrenda mismoUso = prendasVarias.get(0).getUsoPrenda();
		List<Prenda> prendasCondicion = prendasVarias.stream().filter(pr -> pr.getUsoPrenda().equals(mismoUso)).collect(Collectors.toList());
		return prendasVarias.size() == prendasCondicion.size();
	}
	
	
	private void seteoPrendas(List<Prenda> items, Usuario user)  throws RoperoFullException {
		if (this.capacidadDisponible(user) < items.size()) {
			throw new RoperoFullException();
		} else {
			this.prendas.addAll(items);
		}
	}
		
	private void sumaPrenda(Prenda prenda, Usuario user) throws RoperoFullException {
		if (this.sinCapacidad(user)) {
			throw new RoperoFullException();
		} else {
			this.getPrendas().add(prenda);
		}
	}
	
	private boolean sinCapacidad(Usuario user) {
		return this.prendas.size() >= user.capacidadPlacards();
	}
	
	private boolean chequeoPrendasParaUsuario(List<Prenda> items, Usuario user) throws RoperoFullException {
		if (user.capacidadPlacards() < items.size()) {
		 	throw new RoperoFullException();
		}
		else {
			return user.capacidadPlacards() > items.size();
		}
	}
	
	private boolean validoPrendasParaUsuario(List<Prenda> items, Usuario user) throws RoperoFullException {
		try {
			return this.chequeoPrendasParaUsuario(items,user);
		}
		catch (RoperoFullException exception) {
			throw exception;
		}
	}
	
	private List<Prenda> prendasPara(Categoria unaCategoria) throws SinPrendasException {
	    List<Prenda> _prendas =  this.getPrendas().stream().filter(prenda -> prenda.getCategoria().equals(unaCategoria)).collect(Collectors.toList());
	    if (_prendas.size() == 0) {
			throw new SinPrendasException();
		} else {
			return _prendas;
		}
	}
	
	private Set<Prenda> prendasMismaCategoria(Categoria unaCategoria) throws SinPrendasException {
		try {
			return this.prendasPara(unaCategoria).stream().collect(Collectors.toSet());
		} catch (SinPrendasException exception) {
			throw exception;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<List<Prenda>> combinacionPrendasCategoria(Categoria categoria) throws SinPrendasException {
		Set<Set<Prenda>> prendasSuperponibles = Sets.powerSet(this.prendasMismaCategoria(categoria));
		Set<Set<Prenda>> prendas = this.prendasMismaCategoria(categoria).stream().map((prenda) -> Sets.newHashSet(prenda)).collect(Collectors.toSet());
		Set<List<Set<Prenda>>> cartesiano = Sets.cartesianProduct(prendas, prendasSuperponibles);
		return cartesiano.stream().map((lista) -> Lists.newArrayList(Streams.concat(lista.get(0).stream(), lista.get(1).stream()).iterator())).collect(Collectors.toList()).stream()
				.sorted(ordenoPorTamano).collect(Collectors.toList());
	}
	
	public Placard(List<Prenda> items, Usuario user, String name) throws RoperoFullException {
		super();
		this.validoPrendasParaUsuario(items,user);
		this.prendas = items;
		this.nombre = name;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "IdPlacard")
	private int idPlacard;

	@NonNull
	private String nombre;

	@NonNull
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Prenda> prendas = new ArrayList<>();
	
 	@Transient
	private Comparator<List<Prenda>> ordenoPorTamano = Comparator.comparing(List<Prenda>::size);
}
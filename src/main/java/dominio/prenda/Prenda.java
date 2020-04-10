package dominio.prenda;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import dominio.excepciones.*;

@Entity (name="Prenda")
@Table(name = "prendas")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Prenda {
	
    public Prenda(String nombre, Categoria categoria, Tipo tipo, Tela tela,Foto foto, CaracterPrenda uso, Color unColor) throws AtributosPrendaInvalidosException {
    		this.nombre = nombre;
    		this.categoria = categoria;
    		this.colorBase = unColor;
    		this.foto = foto;
    		this.validoAtributos3(categoria, tipo, tela);
    		this.tipo = tipo;
    		this.tela = tela;
    		this.usoPrenda = uso;
    	}
    
    public Prenda(String nombre, Categoria categoria, Tipo tipo, Tela tela, Color unColor, Color otroColor, CaracterPrenda uso) {
		super();
		this.nombre = nombre;
		this.categoria = categoria;
		this.foto = null;
		this.colorBase = unColor;
		this.tipo = tipo;
		this.tela = tela;
		this.colorSecundario = otroColor;
		this.usoPrenda = uso;
	}
    
    public static Prenda of(String nombre, Categoria categoria, Tipo tipo, Tela tela,Foto foto, Color unColor, Color otroColor, CaracterPrenda uso) {
    	Prenda prenda = new Prenda(nombre, categoria, tipo, tela, foto, unColor, otroColor, uso);
    	return prenda;
    }

	public void setTipo(Tipo unTipo) throws AtributosPrendaInvalidosException {
		try {
			this.checkTipo(unTipo);
		}
		catch (AtributosPrendaInvalidosException exception) {
			throw exception;
		}
	}

	public void setTela(Tela tela) throws AtributosPrendaInvalidosException {
		try {
			this.checkTela(tela);
		}
		catch (AtributosPrendaInvalidosException exception) {
			throw exception;
		}
	}

	public void setColorSecundario(Color unColor) throws AtributosPrendaInvalidosException  {
		try {
			this.checkColorSecundario(unColor);
		}
		catch (AtributosPrendaInvalidosException exception) {
			throw exception;
		}
	}

	public boolean admiteSuperposicionDe(Prenda unaPrenda) {
		return this.getTipo().admiteSuperpuesta(unaPrenda.getTipo());
	}
	
	public boolean puedeSuperponerA(Prenda unaPrenda) {
		return this.getTipo().puedeSuperponer(unaPrenda.getTipo());
	}
	
	public double nivelTermico() {
		return this.getTipo().nivelTermicoCapa();
	}
	
	public boolean esPrendaComplementaria() {
		return this.categoria.getIdCategoria() == Categoria.CABEZA.getIdCategoria()
				|| this.categoria.getIdCategoria() == Categoria.CUELLO.getIdCategoria()
				|| this.categoria.getIdCategoria() == Categoria.MANO.getIdCategoria();
	}
	
	public boolean tieneFoto( ) {
		return foto.existeArchivo();
	}
	
	public String deTipo() {
		return this.tipo.getTipo();
	}
	
	private boolean validoAtributos3(Categoria unaCategoria, Tipo unTipo,Tela unaTela)  throws AtributosPrendaInvalidosException {
		try {
			return this.atributosValidos3(unaCategoria, unTipo, unaTela);
		}
		catch (AtributosPrendaInvalidosException exception) {
			throw exception;
		}
	}

	private boolean coloresValidos(Color unColor, Color otroColor) {
		return otroColor.equals(null) || (unColor.getDeColor() != otroColor.getDeColor());
	}

	private boolean chequeoColores(Color unColor, Color otroColor) throws AtributosPrendaInvalidosException {
		if (unColor.getDeColor().equals(otroColor.getDeColor())) {
		 	throw new AtributosPrendaInvalidosException();
		}
		else {
			return this.coloresValidos(unColor, otroColor);
		}
	}

	private boolean chequeoTelaConTipo(Tela unaTela, Tipo unTipo) throws AtributosPrendaInvalidosException {
		if (!unaTela.tipoValido(unTipo)) {
		 	throw new AtributosPrendaInvalidosException();
		}
		else {
			return unaTela.tipoValido(unTipo);
		}
	}

	private boolean chequeoTipoConCategoria(Tipo unTipo, Categoria unaCategoria) throws AtributosPrendaInvalidosException {
		if (!unTipo.sirvePara(unaCategoria)) {
		 	throw new AtributosPrendaInvalidosException();
		}
		else {
			return unTipo.sirvePara(unaCategoria);
		}
	}

	private boolean atributosValidos3(Categoria unaCategoria, Tipo unTipo,Tela unaTela) throws AtributosPrendaInvalidosException {
		 return this.chequeoTipoConCategoria(unTipo, unaCategoria) 
				&& this.chequeoTelaConTipo(unaTela, unTipo);
	}

	private void checkTela(Tela unaTela) throws AtributosPrendaInvalidosException {
		
		if (unaTela.tipoValido(this.tipo)) {
			this.tela = unaTela;
		}
		else {
			throw new AtributosPrendaInvalidosException();
		}
	}

	private void checkColorSecundario(Color unColor) throws AtributosPrendaInvalidosException  {
		if (this.chequeoColores(this.colorBase, unColor)) {
			this.colorSecundario = unColor;
			}
		else {
			throw new AtributosPrendaInvalidosException();
		}
	}

	private void checkTipo(Tipo unTipo) throws AtributosPrendaInvalidosException {
		
		if (unTipo.sirvePara(this.categoria)) {
			this.tipo = unTipo;
		}
		else {
			throw new AtributosPrendaInvalidosException();
		}
	}
	
	@Id
	@GeneratedValue
	@Column(name = "IdPrenda")
	private int idPrenda;

	@NonNull
	private String nombre;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "IdCateg")
	@NonNull
	private Categoria categoria;
	
	@NonNull
    @JoinColumn(name = "IdTipo")
    @OneToOne(cascade = CascadeType.MERGE)
	private Tipo tipo;

	@NonNull
    @JoinColumn(name = "IdTela")
    @OneToOne(cascade = CascadeType.MERGE)
	private Tela tela;
	
	@Transient
	@NonNull
	private Foto foto;

	@NonNull
    @JoinColumn(name = "IdColor")
    @ManyToOne(cascade = CascadeType.MERGE)
	private Color colorBase;

	@NonNull
    @JoinColumn(name = "deColor")
    @ManyToOne(cascade = CascadeType.MERGE)
	private Color colorSecundario;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "IdUso")
	@NonNull
	private CaracterPrenda usoPrenda;
	
}
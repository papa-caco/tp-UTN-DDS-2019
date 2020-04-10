package dominio.webapp.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


import dominio.prenda.*;
import dominio.repository.RepoColores;
import dominio.repository.RepoTelas;
import dominio.repository.RepoTipos;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class RequestPrenda {
	
	private String nombre;
    private String tipo;
	private String categoria;
    private String tela;
    private String colorBase;
    private String colorSecundario;
    private double nivelTermico;
    private String usoPrenda;
    
    public RequestPrenda(Prenda prenda) {
    	this.setNombre(prenda.getNombre());
    	this.setTipo(prenda.getTipo().getTipo());
    	this.setCategoria(prenda.getCategoria().name());
    	this.setNivelTermico(prenda.nivelTermico());
    	this.setUsoPrenda(prenda.getUsoPrenda().name());
    	this.setTela(prenda.getTela().getMaterial());
    	this.setColorBase(prenda.getColorBase().getDeColor());
    	this.setColorSecundario(prenda.getColorSecundario().getDeColor());
    }
  
    public Prenda getPrendaFromThis() {
		String name = this.getNombre();
		Categoria categ = Categoria.valueOf(categoria);
		Tipo type = RepoTipos.getInstance().getTipoNombre(tipo);
		Tela telita = RepoTelas.getInstance().getTelaMaterial(tela);
		Color color1 = RepoColores.getInstance().getColor(colorBase);
		Color color2 = RepoColores.getInstance().getColor(colorSecundario);
		CaracterPrenda uso = CaracterPrenda.valueOf(usoPrenda);
		Prenda prendita = new Prenda(name,categ,type,telita,color1,color2,uso);
		return prendita;
	}
}

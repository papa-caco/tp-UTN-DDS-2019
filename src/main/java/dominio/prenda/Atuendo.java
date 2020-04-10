package dominio.prenda;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

import com.google.common.collect.Lists;

import dominio.placard.Placard;


@Entity (name="Atuendo")
@Table(name = "Atuendos")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PUBLIC)
public class Atuendo {
		
		@Id
		@GeneratedValue
		@Column(name = "IdAtuendo")
		private int idAtuendo;
		
		
	    @JoinColumn(name = "IdPlacard")
	    @ManyToOne(cascade = CascadeType.REMOVE)
		private Placard placard;
		
		@ManyToMany(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
		private List<Prenda> prendasAtuendo = new ArrayList<>();
		
		public Atuendo(List<Prenda> prendas, Placard unPlacard) {
			super();
			this.prendasAtuendo = prendas;
			this.placard = unPlacard;
		}
					
		public void setPrendaComplementaria(Prenda prenda) {
			this.prendasAtuendo.add(prenda);
		}
			
		public double caloriasBasicas() {
			List<Double> listaCalorias = Lists.newArrayList(this.caloriasConjunto(this.prendasPiernas()),this.caloriasConjunto(this.prendasTorso()));
		    return Collections.min(listaCalorias);
		}
		
		public double caloriasComplementarias() {
			List<Double> listaCalorias = Arrays.asList(this.caloriasConjunto(prendaCabeza()), 
					this.caloriasConjunto(this.prendaCuello()),this.caloriasConjunto(this.prendaManos()));
			if (listaCalorias.isEmpty()) { 
				return 0;
			} else {
				return Collections.min(listaCalorias);
			}
		}
		
		public boolean contienePrendaId(int idPrenda) {
			List<Prenda> seleccion = this.prendasAtuendo.stream().filter(pr -> pr.getIdPrenda() == idPrenda).collect(Collectors.toList());
			return (! seleccion.isEmpty());
		}

		public List<Prenda> accesorios() {
			return this.prendasCategoria(Categoria.ACCESORIO);
		}
				
		public List<Prenda> prendasPiernas() {
			return this.prendasCategoria(Categoria.PIERNAS);
		}
		
		public List<Prenda> prendasPies() {
			return this.prendasCategoria(Categoria.PIE);
		}
		
		public List<Prenda> prendasTorso() {
			return this.prendasCategoria(Categoria.TORSO);
		}
		
		public List<Prenda> prendaCabeza() {
			return this.prendasCategoria(Categoria.CABEZA);
		}
		
		public List<Prenda> prendaCuello() {
			return this.prendasCategoria(Categoria.CUELLO);
		}
			
		public List<Prenda> prendaManos() {
			return this.prendasCategoria(Categoria.MANO);
		}

		public List<String> getNombrePrendas(){
			return this.prendasAtuendo.stream().map(unaPrenda -> unaPrenda.getNombre()).collect(Collectors.toList());
		}
		
		public List<String> getTiposAtuendo(){
			return this.prendasAtuendo.stream().map(unaPrenda -> unaPrenda.getTipo().getTipo()).collect(Collectors.toList());
		}
		
		private double caloriasConjunto(List<Prenda> prendas) {
			return prendas.stream().mapToDouble(prenda -> prenda.nivelTermico()).sum();
		}

		private List<Prenda> prendasCategoria(Categoria categoria){
			return this.prendasAtuendo.stream().filter(pr -> pr.getCategoria().equals(categoria)).collect(Collectors.toList());
		}

		private boolean  sonPrendasDeUso(List<Prenda> prendas, CaracterPrenda uso) {
			return prendas.stream().allMatch(pr -> pr.getUsoPrenda().equals(uso));
		}
		
		public boolean esAtuendoDeCaracter(CaracterPrenda caracter) {
			return this.sonPrendasDeUso(this.prendasAtuendo, caracter);
		}
				
}

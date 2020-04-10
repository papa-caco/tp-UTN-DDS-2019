package dominio.repository;

import java.util.List;
//import java.util.stream.Collectors;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.google.common.collect.Lists;

import dominio.placard.CriterioSeleccionAtuendos;
import dominio.eventos.Ubicacion;
import dominio.excepciones.SinPrendasException;
import dominio.placard.*;
import dominio.prenda.*;
import dominio.servicios.pronostico.accuweather.CiudadPronostico;
import dominio.servicios.pronostico.accuweather.LocationKeysAccu;
import dominio.usuario.Membresia;
import dominio.usuario.Usuario;

public class PersistenciaInicial {
	public static void main(String[] args) throws SinPrendasException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		TypedQuery<Color> queryColor = entityManager.createQuery("SELECT c FROM Color c", Color.class);
		TypedQuery<Tipo> queryTipo = entityManager.createQuery("SELECT t FROM Tipo t", Tipo.class);
		TypedQuery<Tela> queryTela = entityManager.createQuery("SELECT l FROM Tela l", Tela.class);
		TypedQuery<Prenda> queryPrenda = entityManager.createQuery("SELECT p FROM Prenda p", Prenda.class);
		TypedQuery<Capa> queryCapa = entityManager.createQuery("SELECT a FROM Capa a", Capa.class);
		TypedQuery<Placard> queryPlacard = entityManager.createQuery("SELECT r FROM Placard r", Placard.class);
		TypedQuery<Membresia> queryMembre = entityManager.createQuery("SELECT m FROM Membresia m", Membresia.class);
		TypedQuery<Usuario> queryUser = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class);
		TypedQuery<CriterioSeleccionAtuendos> queryCriterios = entityManager.createQuery("SELECT crit FROM CriterioSeleccionAtuendos crit", CriterioSeleccionAtuendos.class);
		TypedQuery<Atuendo> queryAtuendo = entityManager.createQuery("SELECT atu FROM Atuendo atu", Atuendo.class);
		TypedQuery<CalificacionSugerencia> queryCalificacion = entityManager.createQuery("SELECT cali FROM CalificacionSugerencia cali", CalificacionSugerencia.class);
		TypedQuery<Sugerencia> querySugerencia = entityManager.createQuery("SELECT suge FROM Sugerencia suge", Sugerencia.class);
		
		List<Color> resultsColor = queryColor.getResultList();
		List<Tipo> resultsTipo = queryTipo.getResultList();
		List<Tela> resultsTela = queryTela.getResultList();
		List<Prenda> resultsPrenda = queryPrenda.getResultList();
		List<Capa> resultsCapa = queryCapa.getResultList();
		List<Placard> resultsPlacard = queryPlacard.getResultList();
		List<Membresia> resultsMembre = queryMembre.getResultList();
		List<Usuario> resultsUser = queryUser.getResultList();
		List<CriterioSeleccionAtuendos> resultsCriterios = queryCriterios.getResultList();
		List<Atuendo> resultsAtuendo = queryAtuendo.getResultList();
		List<CalificacionSugerencia> resultsCalifica = queryCalificacion.getResultList();
		List<Sugerencia> resultsSugerencia = querySugerencia.getResultList();
		
			if ((resultsTipo.size() == 0) || (resultsTela.size() == 0) || 
			(resultsPrenda.size() == 0) || (resultsColor.size() == 0) || 
			(resultsCapa.size() == 0)  || (resultsPlacard.size() == 0) ||
			(resultsMembre.size() == 0) || (resultsUser.size() == 0) ||
			(resultsCriterios.size() == 0) || (resultsAtuendo.size() == 0) ||
			(resultsCalifica.size() == 0) || (resultsSugerencia.size() == 0)) {
				/* Instanciamos Capas y Colores */
				Superior superior20 = Superior.of(20.0);
				Superior superior30 = Superior.of(30.0);
				Interior interior10 = Interior.of(10.0);
				Interior interior05 = Interior.of(5.0);
				Intermedia intermedia12 = Intermedia.of(12.0);
				Intermedia intermedia15 = Intermedia.of(15.0);
				Intermedia intermedia18 = Intermedia.of(18.0);
				Color azul = Color.of("azul");
				Color rojo = Color.of("rojo");
				Color verde = Color.of("verde");
				Color amarillo = Color.of("amarillo");
				Color marron = Color.of("marron");
				Color naranja = Color.of("naranja");
				Color blanco = Color.of("blanco");
				Color negro = Color.of("negro");
				Color gris = Color.of("gris");
				Color celeste = Color.of("celeste");
				/* Instanciamos Tipos */
		        Tipo camisa = Tipo.of("camisa",Categoria.TORSO,intermedia15);
				Tipo chomba = Tipo.of("chomba",Categoria.TORSO,intermedia12);
				Tipo camiseta = Tipo.of("camiseta",Categoria.TORSO,interior05);
				Tipo musculosa = Tipo.of("musculosa",Categoria.TORSO,intermedia12);
				Tipo sweater = Tipo.of("sweater",Categoria.TORSO,superior20);
				Tipo campera = Tipo.of("campera",Categoria.TORSO,superior20);
				Tipo calzoncillo = Tipo.of("calzoncillo",Categoria.PIERNAS,interior05);
				Tipo shors = Tipo.of("shors",Categoria.PIERNAS,intermedia15);
				Tipo bermuda = Tipo.of("bermuda",Categoria.PIERNAS,intermedia18);
				Tipo jogging = Tipo.of("jogging",Categoria.PIERNAS,intermedia18);
				Tipo pantalon = Tipo.of("pantalon",Categoria.PIERNAS,intermedia18);
				Tipo zapatillas = Tipo.of("zapatillas",Categoria.PIE,intermedia18);
				Tipo zapatos = Tipo.of("zapatos",Categoria.PIE,intermedia18);
				Tipo medias = Tipo.of("medias",Categoria.PIE,interior10);
				Tipo mochila = Tipo.of("mochila",Categoria.ACCESORIO,intermedia18);
				Tipo bolso = Tipo.of("bolso",Categoria.ACCESORIO,intermedia18);
				Tipo panuelo = Tipo.of("panuelo",Categoria.ACCESORIO,intermedia18);
				Tipo bufanda  = Tipo.of("bufanda",Categoria.ACCESORIO,intermedia18);
				/* Instanciamos Telas */
				Tela algodon = Tela.of("algodon",Arrays.asList(chomba,calzoncillo,camiseta,jogging,medias));
				Tela seda = Tela.of("seda",Arrays.asList(camisa,panuelo));
				Tela lona = Tela.of("lona",Arrays.asList(zapatillas,mochila));
				Tela dryfit = Tela.of("dryfit",Arrays.asList(musculosa,shors));
				Tela gabardina = Tela.of("gabardina",Arrays.asList(pantalon,bermuda,campera));
				Tela lana = Tela.of("lana",Arrays.asList(bufanda,sweater));
				Tela cuero = Tela.of("cuero",Arrays.asList(bolso,zapatos));
				/* Instanciamos Prendas */
				Prenda prenda01 = Prenda.of("calzoncillo azul",Categoria.PIERNAS,calzoncillo,algodon,new Foto(new File("./src/varios/fotosPrendas/calzoncilloNegro.jpg")),azul,negro,CaracterPrenda.DEPORTIVO);
				Prenda prenda02 = Prenda.of("calzoncillo blanco",Categoria.PIERNAS,calzoncillo,algodon,new Foto(new File("./src/varios/fotosPrendas/calzoncilloRojo.jpg")),blanco,rojo,CaracterPrenda.DEPORTIVO);
				Prenda prenda03 = Prenda.of("shorts verde",Categoria.PIERNAS,shors,dryfit,new Foto(new File("./src/varios/fotosPrendas/shortVerde.jpg")),verde,verde,CaracterPrenda.DEPORTIVO);
				Prenda prenda04 = Prenda.of("jogging gris",Categoria.PIERNAS,jogging,algodon,new Foto(new File("./src/varios/fotosPrendas/joggingGris.jpg")),gris,gris,CaracterPrenda.DEPORTIVO);
				Prenda prenda05 = Prenda.of("medias blancas",Categoria.PIE,medias,algodon,new Foto(new File("./src/varios/fotosPrendas/mediasBlancas.jpg")),blanco,blanco,CaracterPrenda.DEPORTIVO);
				Prenda prenda06 = Prenda.of("medias verdes",Categoria.PIE,medias,algodon,new Foto(new File("./src/varios/fotosPrendas/mediasBlancas.jpg")),verde,verde,CaracterPrenda.DEPORTIVO);
				Prenda prenda07 = Prenda.of("zapatillas blancas",Categoria.PIE,zapatillas,lona,new Foto(new File("./src/varios/fotosPrendas/zapasBlancas.jpg")),blanco,rojo,CaracterPrenda.DEPORTIVO);
				Prenda prenda08 = Prenda.of("zapatillas azules",Categoria.PIE,zapatillas,lona,new Foto(new File("./src/varios/fotosPrendas/zapasBlancas.jpg")),azul,azul,CaracterPrenda.DEPORTIVO);
				Prenda prenda09 = Prenda.of("musculosa amarilla",Categoria.TORSO,musculosa,dryfit,new Foto(new File("./src/varios/fotosPrendas/musculosaAmarilla.jpg")),amarillo,amarillo,CaracterPrenda.DEPORTIVO);
				Prenda prenda10 = Prenda.of("camiseta blanca",Categoria.TORSO,camiseta,algodon,new Foto(new File("./src/varios/fotosPrendas/camisetaBlanca.jpg")),blanco,rojo,CaracterPrenda.DEPORTIVO);
				Prenda prenda11 = Prenda.of("campera verde",Categoria.TORSO,campera,gabardina,new Foto(new File("./src/varios/fotosPrendas/camperaVerde.jpg")),verde,verde,CaracterPrenda.DEPORTIVO);
				Prenda prenda12 = Prenda.of("bolso azul",Categoria.ACCESORIO,bolso,lona,new Foto(new File("./src/varios/fotosPrendas/bolsoAzul.jpg")),azul,negro,CaracterPrenda.DEPORTIVO);
				Prenda prenda13 = Prenda.of("mochila azul",Categoria.ACCESORIO,mochila,lona,new Foto(new File("./src/varios/fotosPrendas/mochilaNegra.jpg")),azul,azul,CaracterPrenda.DEPORTIVO);
				
				Prenda prenda14 = Prenda.of("calzoncillo negro",Categoria.PIERNAS,calzoncillo,algodon,new Foto(new File("./src/varios/fotosPrendas/calzoncilloNegro.jpg")),negro,negro,CaracterPrenda.CASUAL);
				Prenda prenda15 = Prenda.of("calzoncillo rojo",Categoria.PIERNAS,calzoncillo,algodon,new Foto(new File("./src/varios/fotosPrendas/calzoncilloRojo.jpg")),rojo,rojo,CaracterPrenda.CASUAL);
				Prenda prenda16 = Prenda.of("bermuda celeste",Categoria.PIERNAS,bermuda,gabardina,new Foto(new File("./src/varios/fotosPrendas/bermudaCeleste.jpg")),celeste,blanco,CaracterPrenda.CASUAL);
				Prenda prenda17 = Prenda.of("jean azul",Categoria.PIERNAS,pantalon,gabardina,new Foto(new File("./src/varios/fotosPrendas/pantalonAzul.jpg")),azul,azul,CaracterPrenda.CASUAL);
				Prenda prenda18 = Prenda.of("medias blancas",Categoria.PIE,medias,algodon,new Foto(new File("./src/varios/fotosPrendas/mediasBlancas.jpg")),blanco,blanco,CaracterPrenda.CASUAL);
				Prenda prenda19 = Prenda.of("zapatos azules",Categoria.PIE,zapatos,cuero,new Foto(new File("./src/varios/fotosPrendas/zapatosNegros.jpg")),azul,azul,CaracterPrenda.CASUAL);
				Prenda prenda20 = Prenda.of("chomba azul",Categoria.TORSO,chomba,algodon,new Foto(new File("./src/varios/fotosPrendas/chombaAzul.jpg")),azul,rojo,CaracterPrenda.CASUAL);
				Prenda prenda21 = Prenda.of("chomba roja",Categoria.TORSO,chomba,algodon,new Foto(new File("./src/varios/fotosPrendas/chombaAzul.jpg")),rojo,rojo,CaracterPrenda.CASUAL);
				Prenda prenda22 = Prenda.of("campera azul",Categoria.TORSO,campera,gabardina,new Foto(new File("./src/varios/fotosPrendas/camperaVerde.jpg")),azul,azul,CaracterPrenda.CASUAL);
				Prenda prenda23 = Prenda.of("panuelo verde",Categoria.ACCESORIO,panuelo,seda,new Foto(new File("./src/varios/fotosPrendas/panueloVerde.jpg")),verde,amarillo,CaracterPrenda.CASUAL);
				Prenda prenda24 = Prenda.of("mochila negra",Categoria.ACCESORIO,mochila,lona,new Foto(new File("./src/varios/fotosPrendas/mochilaNegra.jpg")),negro,negro,CaracterPrenda.CASUAL);
				
				Prenda prenda25 = Prenda.of("calzoncillo azul",Categoria.PIERNAS,calzoncillo,algodon,new Foto(new File("./src/varios/fotosPrendas/calzoncilloNegro.jpg")),azul,azul,CaracterPrenda.FORMAL);
				Prenda prenda26 = Prenda.of("pantalon azul",Categoria.PIERNAS,pantalon,gabardina,new Foto(new File("./src/varios/fotosPrendas/pantalonAzul.jpg")),azul,azul,CaracterPrenda.FORMAL);
				Prenda prenda27 = Prenda.of("pantalon negro",Categoria.PIERNAS,pantalon,gabardina,new Foto(new File("./src/varios/fotosPrendas/pantalonAzul.jpg")),negro,negro,CaracterPrenda.FORMAL);
				Prenda prenda28 = Prenda.of("zapatos negros",Categoria.PIE,zapatos,cuero,new Foto(new File("./src/varios/fotosPrendas/zapatosNegros.jpg")),negro,negro,CaracterPrenda.FORMAL);
				Prenda prenda29 = Prenda.of("zapatos marrones",Categoria.PIE,zapatos,cuero,new Foto(new File("./src/varios/fotosPrendas/zapatosNegros.jpg")),marron,marron,CaracterPrenda.FORMAL);
				Prenda prenda30 = Prenda.of("medias negras",Categoria.PIE,medias,algodon,new Foto(new File("./src/varios/fotosPrendas/mediasNegras.jpg")),negro,negro,CaracterPrenda.FORMAL);
				Prenda prenda31 = Prenda.of("sweater azul",Categoria.TORSO,sweater,lana,new Foto(new File("./src/varios/fotosPrendas/sweaterAzul.jpg")),azul,azul,CaracterPrenda.FORMAL);
				Prenda prenda32 = Prenda.of("campera negra",Categoria.TORSO,campera,gabardina,new Foto(new File("./src/varios/fotosPrendas/camperaVerde.jpg")),negro,negro,CaracterPrenda.FORMAL);
				Prenda prenda33 = Prenda.of("camisa_blanca_seda",Categoria.TORSO,camisa,seda,new Foto(new File("./src/varios/fotosPrendas/camisaBlanca.jpg")),blanco,blanco,CaracterPrenda.FORMAL);
				Prenda prenda34 = Prenda.of("camisa celeste",Categoria.TORSO,camisa,algodon,new Foto(new File("./src/varios/fotosPrendas/camisaBlanca.jpg")),celeste,celeste,CaracterPrenda.FORMAL);
				Prenda prenda35 = Prenda.of("camiseta algodon",Categoria.TORSO,camiseta,algodon,new Foto(new File("./src/varios/fotosPrendas/camisetaBlanca.jpg")),blanco,rojo,CaracterPrenda.FORMAL);
				Prenda prenda36 = Prenda.of("bolso marron",Categoria.ACCESORIO,bolso,cuero,new Foto(new File("./src/varios/fotosPrendas/bolsoAzul.jpg")),marron,marron,CaracterPrenda.FORMAL);
				Prenda prenda37 = Prenda.of("bufanda gris",Categoria.ACCESORIO,bufanda,lana,new Foto(new File("./src/varios/fotosPrendas/bufandaGris.jpg")),gris,marron,CaracterPrenda.FORMAL);
				
		        Placard placard1 = new Placard("Placard Prendas Deportivas");
		        Placard placard2 = new Placard("Placard Prendas Casuales");
				Placard placard3 = new Placard("Placard Prendas de Vestir");
				Membresia premium = new Membresia(100);
		        Membresia gratis = new Membresia(40);
		        CriterioAbrigar abrigar = new CriterioAbrigar();
		        CriterioRefrescar refrescar = new CriterioRefrescar();
				Usuario jose = new Usuario("Jose Lopez","ccomesa@gmail.com",premium,"jose",refrescar,8,3);
				Usuario juan = new Usuario("Juan Perez","ccomesa@gmail.com",gratis,"juan",abrigar,4,6);

				/* Iniciamos Transaccion */
		        entityManager.getTransaction().begin();
		        entityManager.persist(superior30);
		        entityManager.persist(superior20);
		        entityManager.persist(interior05);
		        entityManager.persist(interior10);
		        entityManager.persist(intermedia12);
		        entityManager.persist(intermedia15);
		        entityManager.persist(intermedia18);
		        entityManager.persist(azul);
				entityManager.persist(rojo);
				entityManager.persist(verde);
				entityManager.persist(amarillo);
				entityManager.persist(marron);
				entityManager.persist(naranja);
				entityManager.persist(blanco);
				entityManager.persist(negro);
				entityManager.persist(gris);
				entityManager.persist(celeste);
				entityManager.persist(camisa);
				entityManager.persist(chomba);
				entityManager.persist(camiseta);
				entityManager.persist(musculosa);
				entityManager.persist(sweater);
				entityManager.persist(campera);
				entityManager.persist(calzoncillo);
				entityManager.persist(shors);
				entityManager.persist(bermuda);
				entityManager.persist(jogging);
				entityManager.persist(pantalon);
				entityManager.persist(zapatillas);
				entityManager.persist(zapatos);
				entityManager.persist(medias);
				entityManager.persist(mochila);
				entityManager.persist(bolso);
				entityManager.persist(panuelo);
				entityManager.persist(bufanda);
				entityManager.persist(algodon); 
				entityManager.persist(seda);
				entityManager.persist(lona);
				entityManager.persist(dryfit);
				entityManager.persist(gabardina);
				entityManager.persist(lana);
				entityManager.persist(cuero);
		        entityManager.persist(placard1);
		        entityManager.persist(placard2);
		        entityManager.persist(placard3);
		        entityManager.persist(prenda01);
		        entityManager.persist(prenda02);
		        entityManager.persist(prenda03);
		        entityManager.persist(prenda04);
		        entityManager.persist(prenda05);
		        entityManager.persist(prenda06);
		        entityManager.persist(prenda07);
		        entityManager.persist(prenda08);
		        entityManager.persist(prenda09);
		        entityManager.persist(prenda10);
		        entityManager.persist(prenda11);
		        entityManager.persist(prenda12);
		        entityManager.persist(prenda13);
		        entityManager.persist(prenda14);
		        entityManager.persist(prenda15);
		        entityManager.persist(prenda16);
		        entityManager.persist(prenda17);
		        entityManager.persist(prenda18);
		        entityManager.persist(prenda19);
		        entityManager.persist(prenda20);
		        entityManager.persist(prenda21);
		        entityManager.persist(prenda22);
		        entityManager.persist(prenda23);
		        entityManager.persist(prenda24);
		        entityManager.persist(prenda25);
		        entityManager.persist(prenda26);
		        entityManager.persist(prenda27);
		        entityManager.persist(prenda28);
		        entityManager.persist(prenda29);
		        entityManager.persist(prenda30);
		        entityManager.persist(prenda31);
		        entityManager.persist(prenda32);
		        entityManager.persist(prenda33);
		        entityManager.persist(prenda34);
		        entityManager.persist(prenda35);
		        entityManager.persist(prenda36);
		        entityManager.persist(prenda37);
		        entityManager.getTransaction().commit();
				placard1.getPrendas().addAll(Lists.newArrayList(prenda01, prenda02, prenda03, prenda04, prenda05, prenda06, prenda07, prenda08, prenda09, prenda10, prenda11, prenda12, prenda13));
				placard2.getPrendas().addAll(Lists.newArrayList(prenda14, prenda15, prenda16, prenda17, prenda18, prenda19, prenda20, prenda21, prenda22, prenda23, prenda24));
				placard3.getPrendas().addAll(Lists.newArrayList(prenda25, prenda26, prenda27, prenda28, prenda29, prenda30, prenda31, prenda32, prenda33, prenda34, prenda35, prenda36, prenda37));
		        juan.setPlacards(Lists.newArrayList(placard1, placard3));
		        jose.setPlacards(Lists.newArrayList(placard1,placard2, placard3));
		        entityManager.getTransaction().begin();
		        entityManager.persist(placard1);
		        entityManager.persist(placard2);
		        entityManager.persist(placard3);
		        entityManager.persist(jose);
		        entityManager.persist(juan);
		        entityManager.getTransaction().commit();
     
		        System.out.println("Hay " + RepoPrendas.getInstance().getPrendas().size() + " Prendas cargadas en el sistema.");
		        System.out.println("Hay " + RepoPlacards.getInstance().getPlacards().size() + " Placards en el sistema.");
		        
		        for(int j = 0; j < RepoPlacards.getInstance().getPlacards().size() ; j = j + 1) {
			        entityManager.getTransaction().begin();
		          	Placard placard = RepoPlacards.getInstance().getPlacards().get(j);

		        	List<Atuendo> atuendosPlacard = new ArrayList<>();
		        	try {
						atuendosPlacard = placard.generoAtuendosBasicos();
					} catch (SinPrendasException e) {
						e.printStackTrace();
					}
		        	for(int i = 0; i < atuendosPlacard.size(); i = i + 1) {
		        		Atuendo atuendo = atuendosPlacard.get(i);
		        		entityManager.persist(atuendo);
		        	}
		        	entityManager.getTransaction().commit();
		        }
		        
		        Usuario user1 = RepoUsuarios.getInstance().getUsuarios().get(1);
		        CalificacionSugerencia calificacion1 = new CalificacionSugerencia(user1);
		        entityManager.getTransaction().begin();
		        Atuendo atuendo1 = RepoAtuendos.getInstance().getAtuendos().get(0);
		        Sugerencia sugerencia1 = new Sugerencia(atuendo1);
		        sugerencia1.setCalificacion(calificacion1);
		        entityManager.persist(calificacion1);
		        entityManager.persist(sugerencia1);
		        entityManager.getTransaction().commit();
		        
				List<CiudadPronostico> pares = LocationKeysAccu.getInstance().getParesCodigoCiudad();
				pares.stream().forEach(par -> {
					RepoUbicaciones.getInstance().agregarUbicacion(new Ubicacion(par.getCiudad(), par.getId()));
				});

		        System.out.println("Hay " + RepoPrendas.getInstance().getPrendas().size() + " prendas cargadas en el sistema.");
		        System.out.println("Hay " + RepoAtuendos.getInstance().getAtuendos().size() + " atuendos generados en el sistema.");
		        System.out.println("Hay " + RepoUsuarios.getInstance().getUsuarios().size() + " usuarios cargados en el sistema.");
		        		    } else {
				System.out.println("Ya existen los datos iniciales en la base GRUPO07");
			}
	}
}
			
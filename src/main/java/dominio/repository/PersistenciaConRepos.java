package dominio.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import dominio.eventos.Ubicacion;
import dominio.excepciones.SinPrendasException;
import dominio.placard.*;
import dominio.prenda.*;
import dominio.servicios.pronostico.accuweather.*;
import dominio.usuario.*;


public class PersistenciaConRepos {
	
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
		RepoCapas.getInstance().agregarCapa(Superior.of(20.0));
		RepoCapas.getInstance().agregarCapa(Superior.of(30.0));
		RepoCapas.getInstance().agregarCapa(Interior.of(10.0));
		RepoCapas.getInstance().agregarCapa(Interior.of(5.0));
		RepoCapas.getInstance().agregarCapa(Intermedia.of(12.0));
		RepoCapas.getInstance().agregarCapa(Intermedia.of(15.0));
		RepoCapas.getInstance().agregarCapa(Intermedia.of(18.0));
		
		RepoColores.getInstance().obtengoColor("amarillo");
		RepoColores.getInstance().obtengoColor("azul");
		RepoColores.getInstance().obtengoColor("blanco");
		RepoColores.getInstance().obtengoColor("celeste");
		RepoColores.getInstance().obtengoColor("gris");
		RepoColores.getInstance().obtengoColor("marron");
		RepoColores.getInstance().obtengoColor("naranja");
		RepoColores.getInstance().obtengoColor("negro");
		RepoColores.getInstance().obtengoColor("rojo");
		RepoColores.getInstance().obtengoColor("verde");
		RepoColores.getInstance().obtengoColor("violeta");
	
		//Instanciamos Tipos
		
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("bermuda",RepoCapas.getInstance().obtengoIntermedia(15.0),Categoria.PIERNAS);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("bolso",RepoCapas.getInstance().obtengoIntermedia(18.0),Categoria.ACCESORIO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("bufanda",RepoCapas.getInstance().obtengoIntermedia(25.0),Categoria.CUELLO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("calzoncillo",RepoCapas.getInstance().obtengoInterior(5.0),Categoria.PIERNAS);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("camisa",RepoCapas.getInstance().obtengoIntermedia(15.0),Categoria.TORSO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("camiseta",RepoCapas.getInstance().obtengoInterior(5.0),Categoria.TORSO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("campera",RepoCapas.getInstance().obtengoSuperior(25.0),Categoria.TORSO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("chomba",RepoCapas.getInstance().obtengoIntermedia(12.0),Categoria.TORSO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("guantes",RepoCapas.getInstance().obtengoIntermedia(18.0),Categoria.MANO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("jogging",RepoCapas.getInstance().obtengoIntermedia(20.0),Categoria.PIERNAS);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("medias",RepoCapas.getInstance().obtengoInterior(10.0),Categoria.PIE);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("mochila",RepoCapas.getInstance().obtengoIntermedia(20.0),Categoria.ACCESORIO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("musculosa",RepoCapas.getInstance().obtengoIntermedia(12.0),Categoria.TORSO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("pantalon",RepoCapas.getInstance().obtengoIntermedia(18.0),Categoria.PIERNAS);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("panuelo",RepoCapas.getInstance().obtengoIntermedia(18.0),Categoria.CUELLO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("paraguas",RepoCapas.getInstance().obtengoIntermedia(18.0),Categoria.ACCESORIO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("shors",RepoCapas.getInstance().obtengoIntermedia(12.0),Categoria.PIERNAS);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("sweater",RepoCapas.getInstance().obtengoSuperior(20.0),Categoria.TORSO);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("zapatillas",RepoCapas.getInstance().obtengoIntermedia(20.0),Categoria.PIE);
		RepoTipos.getInstance().obtengoTipoPorNombreCapaCategoria("zapatos",RepoCapas.getInstance().obtengoIntermedia(20.0),Categoria.PIE);
		
		RepoTelas.getInstance().agregarTela(Tela.of("algodon",Arrays.asList(RepoTipos.getInstance().getTipoNombre("chomba"),RepoTipos.getInstance().getTipoNombre("calzoncillo"),RepoTipos.getInstance().getTipoNombre("camiseta"),RepoTipos.getInstance().getTipoNombre("jogging"),RepoTipos.getInstance().getTipoNombre("medias"))));
		RepoTelas.getInstance().agregarTela(Tela.of("cuero",Arrays.asList(RepoTipos.getInstance().getTipoNombre("bolso"),RepoTipos.getInstance().getTipoNombre("zapatillas"),RepoTipos.getInstance().getTipoNombre("zapatos"))));
		RepoTelas.getInstance().agregarTela(Tela.of("dryfit",Arrays.asList(RepoTipos.getInstance().getTipoNombre("musculosa"),RepoTipos.getInstance().getTipoNombre("shors"))));
		RepoTelas.getInstance().agregarTela( Tela.of("gabardina",Arrays.asList(RepoTipos.getInstance().getTipoNombre("pantalon"),RepoTipos.getInstance().getTipoNombre("bermuda"),RepoTipos.getInstance().getTipoNombre("campera"))));
		RepoTelas.getInstance().agregarTela(Tela.of("lana",Arrays.asList(RepoTipos.getInstance().getTipoNombre("bufanda"),RepoTipos.getInstance().getTipoNombre("sweater"),RepoTipos.getInstance().getTipoNombre("guantes"))));
		RepoTelas.getInstance().agregarTela(Tela.of("lona",Arrays.asList(RepoTipos.getInstance().getTipoNombre("zapatillas"),RepoTipos.getInstance().getTipoNombre("mochila"))));
		RepoTelas.getInstance().agregarTela(Tela.of("seda",Arrays.asList(RepoTipos.getInstance().getTipoNombre("camisa"),RepoTipos.getInstance().getTipoNombre("panuelo"),RepoTipos.getInstance().getTipoNombre("paraguas"))));
		
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("bolso azul",Categoria.ACCESORIO,RepoTipos.getInstance().getTipoNombre("bolso"),RepoTelas.getInstance().getTelaMaterial("lona"),new Foto(new File("./src/varios/fotosPrendas/bolsoAzul.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("negro"),CaracterPrenda.DEPORTIVO));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("calzoncillo blanco",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("calzoncillo"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/calzoncilloRojo.jpg")),RepoColores.getInstance().getColor("blanco"),RepoColores.getInstance().getColor("rojo"),CaracterPrenda.DEPORTIVO));
		//RepoPrendas.getInstance().agregarPrenda(Prenda.of("calzoncillo azul",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("calzoncillo"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/calzoncilloNegro.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("negro"),CaracterPrenda.DEPORTIVO));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("camiseta blanca",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("camiseta"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/camisetaBlanca.jpg")),RepoColores.getInstance().getColor("blanco"),RepoColores.getInstance().getColor("rojo"),CaracterPrenda.DEPORTIVO));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("campera verde",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("campera"),RepoTelas.getInstance().getTelaMaterial("gabardina"),new Foto(new File("./src/varios/fotosPrendas/camperaVerde.jpg")),RepoColores.getInstance().getColor("verde"),RepoColores.getInstance().getColor("verde"),CaracterPrenda.DEPORTIVO));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("jogging gris",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("jogging"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/joggingGris.jpg")),RepoColores.getInstance().getColor("gris"),RepoColores.getInstance().getColor("gris"),CaracterPrenda.DEPORTIVO));
		//RepoPrendas.getInstance().agregarPrenda(Prenda.of("medias blancas",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("medias"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/mediasBlancas.jpg")),RepoColores.getInstance().getColor("blanco"),RepoColores.getInstance().getColor("blanco"),CaracterPrenda.DEPORTIVO));*/
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("medias azules",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("medias"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/mediasBlancas.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.DEPORTIVO));
		//RepoPrendas.getInstance().agregarPrenda(Prenda.of("mochila azul",Categoria.ACCESORIO,RepoTipos.getInstance().getTipoNombre("mochila"),RepoTelas.getInstance().getTelaMaterial("lona"),new Foto(new File("./src/varios/fotosPrendas/mochilaNegra.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.DEPORTIVO));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("musculosa amarilla",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("musculosa"),RepoTelas.getInstance().getTelaMaterial("dryfit"),new Foto(new File("./src/varios/fotosPrendas/musculosaAmarilla.jpg")),RepoColores.getInstance().getColor("amarillo"),RepoColores.getInstance().getColor("amarillo"),CaracterPrenda.DEPORTIVO));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("paraguas azul",Categoria.ACCESORIO,RepoTipos.getInstance().getTipoNombre("paraguas"),RepoTelas.getInstance().getTelaMaterial("seda"),new Foto(new File("./src/varios/fotosPrendas/mochilaNegra.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.DEPORTIVO));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("short negro",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("shors"),RepoTelas.getInstance().getTelaMaterial("dryfit"),new Foto(new File("./src/varios/fotosPrendas/shortVerde.jpg")),RepoColores.getInstance().getColor("negro"),RepoColores.getInstance().getColor("negro"),CaracterPrenda.DEPORTIVO));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("zapatillas blancas",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("zapatillas"),RepoTelas.getInstance().getTelaMaterial("lona"),new Foto(new File("./src/varios/fotosPrendas/zapasBlancas.jpg")),RepoColores.getInstance().getColor("blanco"),RepoColores.getInstance().getColor("rojo"),CaracterPrenda.DEPORTIVO));
		//RepoPrendas.getInstance().agregarPrenda(Prenda.of("zapatillas azules",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("zapatillas"),RepoTelas.getInstance().getTelaMaterial("lona"),new Foto(new File("./src/varios/fotosPrendas/zapasBlancas.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.DEPORTIVO));
		
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("bermuda celeste",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("bermuda"),RepoTelas.getInstance().getTelaMaterial("gabardina"),new Foto(new File("./src/varios/fotosPrendas/bermudaCeleste.jpg")),RepoColores.getInstance().getColor("celeste"),RepoColores.getInstance().getColor("blanco"),CaracterPrenda.CASUAL));
		//RepoPrendas.getInstance().agregarPrenda(Prenda.of("calzoncillo negro",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("calzoncillo"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/calzoncilloNegro.jpg")),RepoColores.getInstance().getColor("negro"),RepoColores.getInstance().getColor("negro"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("calzoncillo rojo",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("calzoncillo"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/calzoncilloRojo.jpg")),RepoColores.getInstance().getColor("rojo"),RepoColores.getInstance().getColor("rojo"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("campera azul",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("campera"),RepoTelas.getInstance().getTelaMaterial("gabardina"),new Foto(new File("./src/varios/fotosPrendas/camperaVerde.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("chomba azul",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("chomba"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/chombaAzul.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("rojo"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("chomba roja",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("chomba"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/chombaAzul.jpg")),RepoColores.getInstance().getColor("rojo"),RepoColores.getInstance().getColor("rojo"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("jean azul",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("pantalon"),RepoTelas.getInstance().getTelaMaterial("gabardina"),new Foto(new File("./src/varios/fotosPrendas/pantalonAzul.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("medias blancas",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("medias"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/mediasBlancas.jpg")),RepoColores.getInstance().getColor("blanco"),RepoColores.getInstance().getColor("blanco"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("mochila negra",Categoria.ACCESORIO,RepoTipos.getInstance().getTipoNombre("mochila"),RepoTelas.getInstance().getTelaMaterial("lona"),new Foto(new File("./src/varios/fotosPrendas/mochilaNegra.jpg")),RepoColores.getInstance().getColor("negro"),RepoColores.getInstance().getColor("negro"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("panuelo verde",Categoria.ACCESORIO,RepoTipos.getInstance().getTipoNombre("panuelo"),RepoTelas.getInstance().getTelaMaterial("seda"),new Foto(new File("./src/varios/fotosPrendas/panueloVerde.jpg")),RepoColores.getInstance().getColor("verde"),RepoColores.getInstance().getColor("amarillo"),CaracterPrenda.CASUAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("zapatos azules",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("zapatos"),RepoTelas.getInstance().getTelaMaterial("cuero"),new Foto(new File("./src/varios/fotosPrendas/zapatosNegros.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.CASUAL));
		
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("bolso marron",Categoria.ACCESORIO,RepoTipos.getInstance().getTipoNombre("bolso"),RepoTelas.getInstance().getTelaMaterial("cuero"),new Foto(new File("./src/varios/fotosPrendas/bolsoAzul.jpg")),RepoColores.getInstance().getColor("marron"),RepoColores.getInstance().getColor("marron"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("bufanda gris",Categoria.CUELLO,RepoTipos.getInstance().getTipoNombre("bufanda"),RepoTelas.getInstance().getTelaMaterial("lana"),new Foto(new File("./src/varios/fotosPrendas/bufandaGris.jpg")),RepoColores.getInstance().getColor("gris"),RepoColores.getInstance().getColor("marron"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("calzoncillo azul",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("calzoncillo"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/calzoncilloNegro.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("camisa_blanca",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("camisa"),RepoTelas.getInstance().getTelaMaterial("seda"),new Foto(new File("./src/varios/fotosPrendas/camisaBlanca.jpg")),RepoColores.getInstance().getColor("blanco"),RepoColores.getInstance().getColor("blanco"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("camisa celeste",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("camisa"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/camisaBlanca.jpg")),RepoColores.getInstance().getColor("celeste"),RepoColores.getInstance().getColor("celeste"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("camiseta algodon",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("camiseta"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/camisetaBlanca.jpg")),RepoColores.getInstance().getColor("blanco"),RepoColores.getInstance().getColor("rojo"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("campera blanca",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("campera"),RepoTelas.getInstance().getTelaMaterial("gabardina"),new Foto(new File("./src/varios/fotosPrendas/camperaVerde.jpg")),RepoColores.getInstance().getColor("blanco"),RepoColores.getInstance().getColor("blanco"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("medias negras",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("medias"),RepoTelas.getInstance().getTelaMaterial("algodon"),new Foto(new File("./src/varios/fotosPrendas/mediasNegras.jpg")),RepoColores.getInstance().getColor("negro"),RepoColores.getInstance().getColor("negro"),CaracterPrenda.FORMAL));
		//RepoPrendas.getInstance().agregarPrenda(Prenda.of("pantalon azul",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("pantalon"),RepoTelas.getInstance().getTelaMaterial("gabardina"),new Foto(new File("./src/varios/fotosPrendas/pantalonAzul.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("pantalon negro",Categoria.PIERNAS,RepoTipos.getInstance().getTipoNombre("pantalon"),RepoTelas.getInstance().getTelaMaterial("gabardina"),new Foto(new File("./src/varios/fotosPrendas/pantalonAzul.jpg")),RepoColores.getInstance().getColor("negro"),RepoColores.getInstance().getColor("negro"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("sweater azul",Categoria.TORSO,RepoTipos.getInstance().getTipoNombre("sweater"),RepoTelas.getInstance().getTelaMaterial("lana"),new Foto(new File("./src/varios/fotosPrendas/sweaterAzul.jpg")),RepoColores.getInstance().getColor("azul"),RepoColores.getInstance().getColor("azul"),CaracterPrenda.FORMAL));
		RepoPrendas.getInstance().agregarPrenda(Prenda.of("zapatos negros",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("zapatos"),RepoTelas.getInstance().getTelaMaterial("cuero"),new Foto(new File("./src/varios/fotosPrendas/zapatosNegros.jpg")),RepoColores.getInstance().getColor("negro"),RepoColores.getInstance().getColor("negro"),CaracterPrenda.FORMAL));
		//RepoPrendas.getInstance().agregarPrenda(Prenda.of("zapatos marrones",Categoria.PIE,RepoTipos.getInstance().getTipoNombre("zapatos"),RepoTelas.getInstance().getTelaMaterial("cuero"),new Foto(new File("./src/varios/fotosPrendas/zapatosNegros.jpg")),RepoColores.getInstance().getColor("marron"),RepoColores.getInstance().getColor("marron"),CaracterPrenda.FORMAL));
		
		Placard deportivo = new Placard("Placard Prendas Deportivas");
        Placard casual = new Placard("Placard Prendas Casuales");
        Placard formal = new Placard("Placard Prendas de Vestir");
		Membresia premium = new Membresia(100);
        Membresia gratis = new Membresia(40);
        CriterioAbrigar abrigar = new CriterioAbrigar();
        CriterioRefrescar refrescar = new CriterioRefrescar();
		Usuario jose = new Usuario("Jose Lopez","ccomesa@gmail.com",premium,"jose",refrescar,8,3);
		Usuario juan = new Usuario("Juan Perez","ccomesa@gmail.com",gratis,"juan",abrigar,4,6);
		
        deportivo.getPrendas().addAll(Lists.newArrayList(
        		RepoPrendas.getInstance().buscoPrendaNombre("bolso azul"),
        		RepoPrendas.getInstance().buscoPrendaNombre("calzoncillo blanco"),
        		RepoPrendas.getInstance().buscoPrendaNombre("camiseta blanca"),
        		RepoPrendas.getInstance().buscoPrendaNombre("campera verde"),
        		RepoPrendas.getInstance().buscoPrendaNombre("jogging gris"),
        		RepoPrendas.getInstance().buscoPrendaNombre("medias azules"),
        		RepoPrendas.getInstance().buscoPrendaNombre("musculosa amarilla"),
        		RepoPrendas.getInstance().buscoPrendaNombre("paraguas azul"),
        		RepoPrendas.getInstance().buscoPrendaNombre("short negro"),
        		RepoPrendas.getInstance().buscoPrendaNombre("zapatillas blancas")));
        		
        casual.getPrendas().addAll(Lists.newArrayList(
        		RepoPrendas.getInstance().buscoPrendaNombre("bermuda celeste"),
        		RepoPrendas.getInstance().buscoPrendaNombre("calzoncillo rojo"),
        		RepoPrendas.getInstance().buscoPrendaNombre("campera azul"),
        		RepoPrendas.getInstance().buscoPrendaNombre("chomba azul"),
        		RepoPrendas.getInstance().buscoPrendaNombre("chomba roja"),
        		RepoPrendas.getInstance().buscoPrendaNombre("jean azul"),
        		RepoPrendas.getInstance().buscoPrendaNombre("medias blancas"),
        		RepoPrendas.getInstance().buscoPrendaNombre("mochila negra"),
        		RepoPrendas.getInstance().buscoPrendaNombre("panuelo verde"),
        		RepoPrendas.getInstance().buscoPrendaNombre("zapatos azules")));
        
		formal.getPrendas().addAll(Lists.newArrayList(
				RepoPrendas.getInstance().buscoPrendaNombre("bolso marron"),
				RepoPrendas.getInstance().buscoPrendaNombre("bufanda gris"),
				RepoPrendas.getInstance().buscoPrendaNombre("calzoncillo azul"),
				RepoPrendas.getInstance().buscoPrendaNombre("camisa_blanca"),
				RepoPrendas.getInstance().buscoPrendaNombre("camisa celeste"),
				RepoPrendas.getInstance().buscoPrendaNombre("camiseta algodon"),
				RepoPrendas.getInstance().buscoPrendaNombre("campera blanca"),
				RepoPrendas.getInstance().buscoPrendaNombre("medias negras"),
				RepoPrendas.getInstance().buscoPrendaNombre("pantalon negro"),
				RepoPrendas.getInstance().buscoPrendaNombre("sweater azul"),
				RepoPrendas.getInstance().buscoPrendaNombre("zapatos negros")));
				
		juan.setPlacards(Lists.newArrayList(deportivo,formal));
		jose.setPlacards(Lists.newArrayList(deportivo, casual, formal));
		RepoPlacards.getInstance().agregarPlacard(deportivo);
		RepoPlacards.getInstance().agregarPlacard(casual);
		RepoPlacards.getInstance().agregarPlacard(formal);
		RepoUsuarios.getInstance().agregarUsuario(juan);
		RepoUsuarios.getInstance().agregarUsuario(jose);
		
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
        
	List<CiudadPronostico> pares = LocationKeysAccu.getInstance().getParesCodigoCiudad();
		pares.stream().forEach(par -> {
		RepoUbicaciones.getInstance().agregarUbicacion(new Ubicacion(par.getCiudad(), par.getId()));
	});	        
		
		
	System.out.println("Hay " + RepoPrendas.getInstance().getPrendas().size() + " prendas cargadas en el sistema.");
    System.out.println("Hay " + RepoAtuendos.getInstance().getAtuendos().size() + " atuendos generados en el sistema.");
    System.out.println("Hay " + RepoUsuarios.getInstance().getUsuarios().size() + " usuarios cargados en el sistema.");

	} else {
		System.out.println("Ya existen los datos iniciales en la base GRUPO7");
	}

}		

}
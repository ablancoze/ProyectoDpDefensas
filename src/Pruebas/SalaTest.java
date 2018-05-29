package Pruebas;
import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import Mapa_SuperHeroes.Arma;
import Mapa_SuperHeroes.Sala;
import Personajes.Personaje;
import Personajes.ShPhysical;
import Personajes.Villano;

public class SalaTest {
	static ShPhysical spiderCerdo;
	static Villano howardElPato;
	static ShPhysical mario;
	static Villano bowser ;
	static Sala sala;
	static Arma arma;
	static Personaje s;
	static Personaje v;
	
	@BeforeClass
	public static void inicio() throws IOException{
		sala=new Sala(0);
		
		arma=new Arma("guante",35);
		
		spiderCerdo=new ShPhysical("spiderCerdo",'S');
		
		howardElPato=new Villano("howardElPato",'H');
		
		mario=new ShPhysical("mario",'M');
		
		bowser=new Villano("bowser",'B');
		
		sala.insertarPersonaje(spiderCerdo);
		
		sala.insertarPersonaje(howardElPato);
		
		sala.insertarPersonaje(mario);
		
		sala.insertarPersonaje(bowser);
		
		sala.insertarArma(arma);
		
		
	}

	@Test
	public void getPrimerSuperHeroeTest() {
		assertTrue(sala.primerSuperHereo().getNombre()=="spiderCerdo");
		assertFalse(sala.primerSuperHereo().getNombre()=="mario");
		
	}
	
	@Test
	public void getPrimerVillanoTest() {
		assertTrue(sala.primerVillano().getNombre()=="howardElPato");
		assertFalse(sala.primerVillano().getNombre()=="bowser");
	}
}

package Pruebas;
import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import Personajes.*;
import Mapa_SuperHeroes.Arma;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Mapa.Dir;

public class VillanoTest {
	
	static Mapa m;
	static ShPhysical spiderCerdo;
	static Villano howardElPato;
	
	@BeforeClass
	public static void inicio() throws IOException{
		m =Mapa.getMapa(15, 4, 4, 3);
		
		spiderCerdo=new ShPhysical("spiderCerdo",'S',1);
		spiderCerdo.pintarRuta();
		howardElPato=new Villano("howardElPato",'H',1);
		howardElPato.pintarRuta();
		
		m.insertarPersonaje(spiderCerdo);
		m.insertarPersonaje(howardElPato);
		
		while(!howardElPato.ruta.isEmpty()){
			howardElPato.ruta.removeFirst();
		}
		
		howardElPato.ruta.add(Dir.S);
		Arma a=new Arma ("Baston",22);
		m.setModificarArmaParaPruebaVillano(a);
		m.simulacion(9);
		
	}

	@Test
	public void testInteractuar() {
		assertTrue(spiderCerdo.villanosCapturados.getFirst().getIdentificador()=='H');
		assertFalse(spiderCerdo.villanosCapturados.getFirst()==null);
	}
}

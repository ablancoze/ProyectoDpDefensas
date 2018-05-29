package Pruebas;
import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import Mapa_SuperHeroes.Arma;
import Puerta.HombrePuerta;

public class HombrePuertaTest {
	
	static HombrePuerta hp;

	@BeforeClass
	public static void inicio() throws IOException{
		Arma [] armasSalas={new Arma("Baston",28),new Arma("Anillo",1),new Arma("Escudo",5),new Arma("CampoMagnetico",4)};
		hp=new HombrePuerta();
		hp.configurar(armasSalas);
		
	}
	
	//Tras la configuracion las armas deberian quedar asi
	
//	  		B
//		A		  	E
//	null   null   C	  null

//Siendo B=Baston el arma mas poderosa

	@Test
	public void getMayorArmaTest() {
		
		assertTrue(hp.getArmaMayor().getNombre()=="Baston");
		assertTrue(hp.getArmaMayor().getPoder()==28);
		assertFalse(hp.getArmaMayor().getPoder()<28);

	}
}

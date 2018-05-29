package Pruebas;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.LinkedList;
import org.junit.BeforeClass;
import org.junit.Test;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Mapa.Dir;
import Rutas.*;
import Rutas.MoveBehavior;
import Rutas.MovimientoMasCorto;

public class RutasTest {
	
	static Mapa m;
	static MoveBehavior moveBehaviorDerecha;
	static MoveBehavior moveBehaviorIzquierda;
	static MoveBehavior moveBehaviorMasCorto;
	static MoveBehavior moveBehaviorProfundidad;
	static LinkedList<Dir>rutaManoDerecha=new LinkedList<Dir>();
	static LinkedList<Dir>rutaManoIzquierda=new LinkedList<Dir>();
	static LinkedList<Dir>rutaMasCorta=new LinkedList<Dir>();
	static LinkedList<Dir>rutaProfundidad=new LinkedList<Dir>();
	
	
	@BeforeClass
	public static void inicio() throws IOException{
		m =Mapa.getMapa(15, 4, 4, 3);
		
		
		moveBehaviorDerecha=new ManoDerecha();
		rutaManoDerecha=moveBehaviorDerecha.move(0, 15);
		
		
		moveBehaviorIzquierda=new ManoIzquierda();
		rutaManoIzquierda=moveBehaviorIzquierda.move(0, 15);
		
		
		moveBehaviorMasCorto=new MovimientoMasCorto();
		rutaMasCorta=moveBehaviorMasCorto.move(0, 15);
		
		
		moveBehaviorProfundidad=new MovimientoProfundidad();
		rutaProfundidad=moveBehaviorProfundidad.move(0, 15);
	}

	@Test
	public void rutaManoDerechaTest() {
		//Sengun las caracterisiticas del programa las rutas resultantes de los algoritmos deben ser iguales a las siguientes rutas.ç
		
		LinkedList<Dir>rutaTestMD=new LinkedList<Dir>();
		rutaTestMD.add(Dir.E);
		rutaTestMD.add(Dir.E);
		rutaTestMD.add(Dir.S);
		rutaTestMD.add(Dir.O);
		rutaTestMD.add(Dir.E);
		rutaTestMD.add(Dir.S);
		rutaTestMD.add(Dir.S);
		rutaTestMD.add(Dir.O);
		rutaTestMD.add(Dir.N);
		rutaTestMD.add(Dir.O);
		rutaTestMD.add(Dir.N);
		rutaTestMD.add(Dir.S);
		rutaTestMD.add(Dir.E);
		rutaTestMD.add(Dir.S);
		rutaTestMD.add(Dir.O);
		rutaTestMD.add(Dir.E);
		rutaTestMD.add(Dir.E);
		rutaTestMD.add(Dir.N);
		rutaTestMD.add(Dir.N);
		rutaTestMD.add(Dir.E);
		rutaTestMD.add(Dir.S);
		rutaTestMD.add(Dir.S);
		
		assertTrue(rutaManoDerecha.equals(rutaTestMD));
	}
	
	@Test
	public void RutasManoIzquierdaTest() {
		LinkedList<Dir>rutaTestMI=new LinkedList<Dir>();
		rutaTestMI.add(Dir.E);
		rutaTestMI.add(Dir.E);
		rutaTestMI.add(Dir.S);
		rutaTestMI.add(Dir.E);
		rutaTestMI.add(Dir.N);
		rutaTestMI.add(Dir.S);
		rutaTestMI.add(Dir.S);
		rutaTestMI.add(Dir.S);
		
		assertTrue(rutaManoIzquierda.equals(rutaTestMI));
	}
	
	@Test
	public void rutaCaminoMasCortoTest() {
		LinkedList<Dir>rutaTestC=new LinkedList<Dir>();
		rutaTestC.add(Dir.E);
		rutaTestC.add(Dir.E);
		rutaTestC.add(Dir.S);
		rutaTestC.add(Dir.E);
		rutaTestC.add(Dir.S);
		rutaTestC.add(Dir.S);
		assertTrue(rutaMasCorta.equals(rutaTestC));
		
	}
	
	@Test
	public void rutaProfundidad() {
		LinkedList<Dir>rutaTestP=new LinkedList<Dir>();
		rutaTestP.add(Dir.E);
		rutaTestP.add(Dir.E);
		rutaTestP.add(Dir.S);
		rutaTestP.add(Dir.E);
		rutaTestP.add(Dir.S);
		rutaTestP.add(Dir.S);
		assertTrue(rutaProfundidad.equals(rutaTestP));
	}
}

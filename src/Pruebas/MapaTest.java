package Pruebas;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import Mapa_SuperHeroes.Mapa;


public class MapaTest {
	
	static Mapa m;

	
	@BeforeClass
	public static void inicio() throws IOException{
		m =Mapa.getMapa(3, 2, 2, 3);
	}
	
	//Las paredes generadas son las siguientes
	//	0-1 | 0-2 | 1-0 | 1-3 | 2-0 | 2-3 | 3-1 | 3-2
	//Los numeros aleatorios genereados para un rago de 8 son los siguientes: 4......4.....1....2....
	//Por lo tanto las paredes tiradas serian las siguientes 2-0 2-3 1-0
	//El mapa quedaria asi:
//	  _ _
//	 |  _|
//	 |_ _|

	//La salas con mayor frecuencia deberian ser la 0, 2 y 3

	@Test
	public void kruskalTest() {
		//Comprobamos los movimientos
		assertTrue(m.comprobarMovimiento(0, 1)==true);
		assertTrue(m.comprobarMovimiento(1, 0)==true);
		assertTrue(m.comprobarMovimiento(2, 0)==true);
		assertTrue(m.comprobarMovimiento(2, 3)==true);
		assertTrue(m.comprobarMovimiento(0, 2)==true);
		assertFalse(m.comprobarMovimiento(3, 1)==true);
		assertFalse(m.comprobarMovimiento(1, 3)==true);		
		
	}
	
	@Test
	public void getFrecuenciasTest() {
		//Compruebo la frecuencia de las salas:
		assertTrue(m.getSala(0).getFrecuencia()==1);
		assertTrue(m.getSala(2).getFrecuencia()==1);
		assertTrue(m.getSala(3).getFrecuencia()==1);
		assertFalse(m.getSala(1).getFrecuencia()==1);
	}
}

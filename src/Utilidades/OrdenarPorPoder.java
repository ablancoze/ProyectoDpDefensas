package Utilidades;

import java.util.Comparator;

import Mapa_SuperHeroes.Arma;

/**
 * PROYECTO DP 17/18
 * Clase OrdenarPorPoder
 * @author 
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class OrdenarPorPoder implements Comparator<Arma> {
	
	/**
	 * Permite a una estructura comparar el poder de las armas
	 */
	@Override
	public int compare(Arma o1, Arma o2) {
		if (o1.getPoder()>o2.getPoder()){
			return 1;
		}
		
		if(o1.getPoder()<o2.getPoder()){
			return -1;
		}
		
		return 0;
	}

}

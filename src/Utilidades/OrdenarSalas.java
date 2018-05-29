package Utilidades;

import java.util.Comparator;
import Mapa_SuperHeroes.Sala;

/**
 * PROYECTO DP 17/18
 * Clase OrdenarSalas
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class OrdenarSalas implements Comparator<Sala> {

	/**
	 * Permite ordenar una lista de salas por su frecuencia
	 */
	@Override
	public int compare(Sala o1, Sala o2){
		if (o1.getFrecuencia()>o2.getFrecuencia()){
			return 1;
		}
		
		if(o1.getFrecuencia()<o2.getFrecuencia()){
			return -1;
		}
		
		if(o1.getFrecuencia()==o2.getFrecuencia()){
			if (o1.getIdentificador()>o2.getIdentificador()){
				return -1;
			}
			return 1;
		}
		return 0;
	}
}
	


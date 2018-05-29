package Rutas;
import java.util.LinkedList;
import Mapa_SuperHeroes.Mapa.Dir;


/**
 * PROYECTO DP 17/18
 * Clase MoveBehavior
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public interface MoveBehavior {
	
	/**
	 * Metodo que se implementa en las clases inferiores de la interfaz MoveBehavior. Genera la ruta de un personaje
	 * @param origen sala en la que se inicia el movimiento
	 * @param destino sala a la que queremos llegar
	 * @return LinkedList de Dir que contiene la ruta que el personaje deber seguir
	 */
	public LinkedList<Dir> move(int origen,int destino);
}

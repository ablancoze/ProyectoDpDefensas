package Rutas;
import java.util.LinkedList;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Mapa.Dir;


/**
 * PROYECTO DP 17/18
 * Clase ManoDerecha
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class MovimientoMasCorto implements MoveBehavior {

	@Override
	public LinkedList<Dir> move(int origen,int destino) {
		LinkedList<Dir> ruta=new LinkedList<Dir>();
		ruta=move(origen,destino,ruta);
		return ruta;
	}
	
	
	/**
	 * Algoritmo que generar la ruta siguiendo el camino mas corto entre la sala en la que se empieza y la sala a la que se quiere llegar
	 * @param origen sala en la que comienza el movimiento
	 * @param destino sala a la que se quiere llegar
	 * @param ruta LinkedList que contiene la ruta que se devolvera
	 * @return LinkedList de Dir que contiene la ruta que el personaje debe seguir
	 */
	private LinkedList<Dir> move(int origen, int destino,LinkedList<Dir> ruta){
		int sig=Mapa.getMapa().siguienteNodo(origen, destino);

		if (origen!=destino){//Si me devuelve -1 significa que hemos llegado al origen

			move(sig,destino,ruta);

			if ((origen-Mapa.getMapa().getColumnas())==sig){//Significa que me muevo al norte
				ruta.addFirst(Dir.N);
			}else{
				if((origen-1)==sig){
					ruta.addFirst(Dir.O);
				}else{
					if((origen+Mapa.getMapa().getColumnas())==sig){
						ruta.addFirst(Dir.S);
					}else{
						if((origen+1)==sig){
							ruta.addFirst(Dir.E);
						}
					}
				}
			}
		}
		return ruta;
	}
}

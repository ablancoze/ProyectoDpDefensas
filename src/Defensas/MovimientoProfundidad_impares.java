package Defensas;
import java.util.LinkedList;
import java.util.TreeSet;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Sala;
import Mapa_SuperHeroes.Mapa.Dir;
import Rutas.MoveBehavior;


/**
 * PROYECTO DP 17/18
 * Clase ManoDerecha
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class MovimientoProfundidad_impares implements MoveBehavior {

	@Override
	public LinkedList<Dir> move(int origen, int destino) {
		LinkedList<Dir> ruta=new LinkedList<Dir>();
		ruta=move(origen,destino,ruta);
		return null;
	}
	
	
	
	/**
	 * Algoritmo que generar la ruta por el tablero siguiendo un algoritmo en profundidad del grafo
	 * @param origen sala desde la que se empieza a mover el personaje
	 * @param destino sala a la que se quiere llegar
	 * @param visitados LinkedList que guarda los nodos visitados
	 * @param ruta LinkedList que contendra la ruta a seguir
	 * @param cierto booleano que indica si el camino hallado es valido
	 * @return LinkedList de Dir que contiene la ruta que el personaje debe seguir
	 */
	private LinkedList<Dir> move(int origen,int destino,LinkedList<Dir> ruta){
		LinkedList<Integer>visitados=new LinkedList<Integer>();
		LinkedList<LinkedList<Integer>>todosLosCaminos=new LinkedList<LinkedList<Integer>>();
		todosLosCaminosInteger(origen, destino, visitados, todosLosCaminos);
		int contador=0;
		int maximo=0;
		for (int i = 0; i < todosLosCaminos.size(); i++) {
			contador=0;
			for (int j = 0; j < todosLosCaminos.get(i).size(); j++) {
				if( todosLosCaminos.get(i).get(j)%2!=0){
					contador=contador+1;
				}	
			}
			if (contador>maximo){
				maximo=contador;
				visitados=todosLosCaminos.get(i);
			}
		}
		
		for (int i = 1; i < visitados.size() ; i++) {
			
			if ((origen-Mapa.getMapa().getColumnas())==visitados.get(i)){//Significa que me muevo al norte
				ruta.addLast(Dir.N);
				origen=visitados.get(i);
			}else{
				if((origen-1)==visitados.get(i)){//Significa que me muevo al oeste
					ruta.addLast(Dir.N);
					origen=visitados.get(i);
				}else{
					if((origen+Mapa.getMapa().getColumnas())==visitados.get(i)){//Significa que me muevo al sur
						ruta.addLast(Dir.N);
						origen=visitados.get(i);
					}else{
						if((origen+1)==visitados.get(i)){//Significa que me muevo al este
							ruta.addLast(Dir.N);
							origen=visitados.get(i);
						}
					}
				}
			}
			
		}
		
		return ruta;
		
	}
	
	private void todosLosCaminosInteger(Integer origen, int destino, LinkedList<Integer>visitados,LinkedList<LinkedList<Integer>>todosLosCaminos){
		
	Integer vertice=0;//Nodo vertice al nodo en el que estamos
		
		TreeSet<Integer> ady= new TreeSet<Integer>();//creamos un treeSet para guardar los adyacentes del nodo con el que estamos trabajando
		
		if(origen!=destino){//Si el origen es igual al destino el camino que se ha llevado a cabo es valido, si no lo es seguimos explorando caminos

			visitados.add(origen);//A�adimos el nodo con el que estamos trabajando al visitados
			
			Mapa.getMapa().getAdyacentes(origen, ady);//Cogemos los adyacentes del nodo que estamos tratando

			while(!ady.isEmpty()){//Mientras haya nodos por explorar y no hayamos llegado al final

				vertice=ady.first();//Igualo el nodo axuliar al primer adyancente

				ady.remove(vertice);//Elimino el adyacente que vamos a tratar
				
				if(!visitados.contains(vertice)){
					todosLosCaminosInteger(vertice, destino,visitados,todosLosCaminos);
				}
			}
			visitados.remove(origen);
		}else{
			LinkedList<Integer>recorridoValido=new LinkedList<Integer>(visitados);
			visitados.add(origen);
			todosLosCaminos.add(recorridoValido);//Cuando llegue a la sala objetivo entonces el camino sera correcto, por lo tanto lo guardo.
			visitados.remove(origen);
		}
	}
	
}

package Defensas;
import java.util.Comparator;
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
public class MovimientoProfundidad_PesoArcos implements MoveBehavior {

	@Override
	public LinkedList<Dir> move(int origen, int destino) {
		LinkedList<Dir> ruta=new LinkedList<Dir>();
		LinkedList<Integer> visitados=new LinkedList<Integer>();
		boolean[] cierto;
		cierto=new boolean[1];
		cierto[0]=false;
		
		ruta=move(origen, destino, visitados, ruta, cierto);
		return ruta;
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
	private LinkedList<Dir> move(int origen,int destino,LinkedList<Integer> visitados , LinkedList<Dir> ruta,boolean[] cierto){
		int vertice=0;
		TreeSet<Integer> ady= new TreeSet<Integer>();//creamos un treeSet para guardar los adyacentes del nodo con el que estamos trabajando

		if(origen!=destino){//Si el origen es igual al destino el camino que se ha llevado a cabo es valido, si no lo es seguimos explorando caminos

			visitados.add(origen);//Añadimos el nodo con el que estamos trabajando al visitados

			Mapa.getMapa().getAdyacentes(origen,ady);//Cogemos los adyacentes del nodo que estamos tratando
			
			
			while(!ady.isEmpty()&&!cierto[0]){//Mientras haya nodos por explorar y no hayamos llegado al final

				vertice=adyacenteMayorPeso(origen,ady);//Igualo el nodo axuliar al nodo de menor mayor peso

				ady.remove(vertice);//Elimino el adyacente que vamos a tratar

				if(!visitados.contains(vertice)){//Si el adyacente esta dentro de los nodos vistados, significa que el camino no es valido

					ruta=move(vertice, destino, visitados, ruta, cierto);

					if (cierto[0]){
						if ((origen-Mapa.getMapa().getColumnas())==vertice){//Significa que me muevo al norte
							ruta.addFirst(Dir.N);
						}else{
							if((origen-1)==vertice){//Significa que me muevo al oeste
								ruta.addFirst(Dir.O);
							}else{
								if((origen+Mapa.getMapa().getColumnas())==vertice){//Significa que me muevo al sur
									ruta.addFirst(Dir.S);
								}else{
									if((origen+1)==vertice){//Significa que me muevo al este
										ruta.addFirst(Dir.E);
									}
								}
							}
						}
					}
				}
			}
		}else{
			cierto[0]=true;
			return ruta;
		}
		return ruta;
		
	}



	private Integer adyacenteMayorPeso(Integer origen, TreeSet<Integer> ady) {
		Integer [] adyA= new Integer[ady.size()];
		adyA=(Integer[]) ady.toArray();
		
		Integer mayorPeso=0;
		int costeCamino=0;
		Integer adyR=0;
		for (int i = 0; i < adyA.length; i++) {
			costeCamino=Mapa.getMapa().costeCamino(origen, adyA[i]);
			if (costeCamino>mayorPeso){
				mayorPeso=costeCamino;
				adyR= adyA[i];
			}	
		}
		
		return adyR;
	}
	
}

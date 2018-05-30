package Defensas;

import java.util.LinkedList;

import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Sala;
import Mapa_SuperHeroes.Mapa.Dir;
import Rutas.MoveBehavior;

public class MovimientoMayorPeso implements MoveBehavior {

	@Override
	public LinkedList<Dir> move(int origen, int destino) {
		LinkedList<Dir> ruta=new LinkedList<Dir>();
		ruta=move(origen,destino,ruta);
		return null;
	}

	private LinkedList<Dir> move(int origen, int destino, LinkedList<Dir> ruta) {
		LinkedList<Integer>visitados=new LinkedList<Integer>();
		LinkedList<Sala>recorrido=new LinkedList<Sala>();
		LinkedList<LinkedList<Sala>>todosLosCaminos=new LinkedList<LinkedList<Sala>>();
		Mapa.getMapa().obtenerTodosLosCaminos(origen, destino, visitados, recorrido, todosLosCaminos);
		int cantidadPoder=0;
		int mayorCamino=0;
		for (int i = 0; i < todosLosCaminos.size(); i++) {
			cantidadPoder=0;
			for (int j = 0; j < todosLosCaminos.get(i).size() ; j++) {
				
				cantidadPoder=todosLosCaminos.get(i).get(j).poderDeLaSala()+cantidadPoder;
			}
			
			if (cantidadPoder>mayorCamino){
				recorrido=todosLosCaminos.get(i);
			}
		}
		
		for (int i = 1; i < recorrido.size() ; i++) {
			
			if ((origen-Mapa.getMapa().getColumnas())==recorrido.get(i).getIdentificador()){//Significa que me muevo al norte
				ruta.addLast(Dir.N);
				origen=recorrido.get(i).getIdentificador();
			}else{
				if((origen-1)==recorrido.get(i).getIdentificador()){//Significa que me muevo al oeste
					ruta.addLast(Dir.N);
					origen=recorrido.get(i).getIdentificador();
				}else{
					if((origen+Mapa.getMapa().getColumnas())==recorrido.get(i).getIdentificador()){//Significa que me muevo al sur
						ruta.addLast(Dir.N);
						origen=recorrido.get(i).getIdentificador();
					}else{
						if((origen+1)==recorrido.get(i).getIdentificador()){//Significa que me muevo al este
							ruta.addLast(Dir.N);
							origen=recorrido.get(i).getIdentificador();
						}
					}
				}
			}
			
		}
		
		return null;
	}

}

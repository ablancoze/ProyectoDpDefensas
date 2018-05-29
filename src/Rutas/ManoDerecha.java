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
public class ManoDerecha implements MoveBehavior {
	
	/**
	 * Metodo publico que permite crear la ruta de la mano derecha
	 */
	@Override
	public LinkedList<Dir> move(int origen,int destino) {
		LinkedList<Dir> ruta=new LinkedList<Dir>();
		
		if(ruta.isEmpty()){
			
			if(Mapa.getMapa().comprobarMovimiento(origen, origen-1)){//Vemos si podemos girar a la derecha
				origen=origen-1;

				move(origen,destino,true,false,false,false,ruta);
				ruta.addFirst(Dir.O);
				
			}else{
				
				if(Mapa.getMapa().comprobarMovimiento(origen, origen+Mapa.getMapa().getColumnas())){//Vemos si podemos ir a hacia abajo
					
					origen=origen+Mapa.getMapa().getColumnas();

					move(origen,destino,false,true,false,false,ruta);
					ruta.addFirst(Dir.S);
					
				}else{
					
					if(Mapa.getMapa().comprobarMovimiento(origen, origen+1)){//Vemos si podemos ir a de frente

						origen=origen+1;
				
						move(origen,destino,false,false,true,false,ruta);
						ruta.addFirst(Dir.E);
						
					}else{
						
						if(Mapa.getMapa().comprobarMovimiento(origen, origen-Mapa.getMapa().getColumnas())){//Vemos si podemos ir hacia arriba
							
							origen=origen-Mapa.getMapa().getColumnas();
							
							move(origen,destino,false,false,false,true,ruta);
							ruta.addFirst(Dir.N);
							
						}
					}
				}
			}
		}
		return ruta;
	}
	
	
	/**
	 * Metodo privado que continua la generacion de la ruta de mano derecha
	 * @param origen entero que indica la sala en la que nos encontramos
	 * @param destino entero que indica la sala a la que queremos llegas
	 * @param o booleano que nos indica si venimos del oeste
	 * @param s booleano que nos indica si venimos del sur
	 * @param e booleano que nos indica si venimos del este
	 * @param n booleano que nos indica si venimos del norte
	 * @param ruta LinkedList que guardara la ruta generada por el algoritmo
	 * @return Linkedlist que contiene las direcciones que generar el algoritmo
	 */
	private LinkedList<Dir> move(int origen,int destino,boolean o,boolean s, boolean e, boolean n,LinkedList<Dir> ruta){
		
		if(origen!=destino){
			
			if(o){//Si vengo del oeste,signifca que tengo o tenia pared en el norte y que la mano derecha esta o estaba pegeda a ella
				o=false;
				if(Mapa.getMapa().comprobarMovimiento(origen, origen-Mapa.getMapa().getColumnas())){//Compruebo que pueda girar a la derecha
																								//es decir al norte
					origen=origen-Mapa.getMapa().getColumnas();//Actualizo el origen				
					n=true;
					move(origen,destino,o,s,e,n,ruta);
					ruta.addFirst(Dir.N);
					
				}else{
					
					if(Mapa.getMapa().comprobarMovimiento(origen, origen-1)){//No he podido girar a la derecha, compruebo que pueda seguir avanzando
						origen=origen-1;//Actualizo el origen
						o=true;
						move(origen,destino,o,s,e,n,ruta);
						ruta.addFirst(Dir.O);
						
					}else{
						
						if(Mapa.getMapa().comprobarMovimiento(origen, origen+Mapa.getMapa().getColumnas())){//No puedo girar ni avanzar por lo tanto
																										//la cara del personaje mira hacia abajo (SUR)
							origen=origen+Mapa.getMapa().getColumnas();//Actualizo el origen
							s=true;
							move(origen,destino,o,s,e,n,ruta);
							ruta.addFirst(Dir.S);
							
						}else{//Si no podemos hacer nada de lo anterior significa que tenemos que dar la vuelta
							
							origen=origen+1;//Actualizo el origen
							e=true;
							move(origen,destino,o,s,e,n,ruta);
							ruta.addFirst(Dir.E);
						}
					}
				}
				
			}else{
				
				if(s){//Si vengo del sur,signifca que tengo o tenia pared en el oeste y que la mano derecha esta o estaba pegeda a ella
					s=false;
					if(Mapa.getMapa().comprobarMovimiento(origen, origen-1)){//Compruebo que pueda girar a la derecha
						
						origen=origen-1;//Actualizo el origen
						o=true;
						move(origen,destino,o,s,e,n,ruta);
						ruta.addFirst(Dir.O);
						
					}else{
						
						if(Mapa.getMapa().comprobarMovimiento(origen, origen+Mapa.getMapa().getColumnas())){//No he podido girar a la derecha
																										//compruebo que pueda seguir avanzando
							origen=origen+Mapa.getMapa().getColumnas();//Actualizo el origen
							s=true;
							move(origen,destino,o,s,e,n,ruta);
							ruta.addFirst(Dir.S);

						}else{
							
							if(Mapa.getMapa().comprobarMovimiento(origen, origen+1)){//Compruebo que pueda avanzar hacia la izquierda (ESTE)
								
								origen=origen+1;//Actualizo el origen
								e=true;
								move(origen,destino,o,s,e,n,ruta);
								ruta.addFirst(Dir.E);
								
							}else{//Si no podemos hacer nada de lo anterior significa que tenemos que dar la vuelta
								origen=origen-Mapa.getMapa().getColumnas();//Actualizo el origen
								n=true;
								move(origen,destino,o,s,e,n,ruta);
								ruta.addFirst(Dir.N);
							}
						}
					}
					
				}else{
					
					if(e){//Si vengo del este, signifca que tengo o tenia pared en el sur y que la mano derecha esta o estaba pegeda a ella
						e=false;
						
						if(Mapa.getMapa().comprobarMovimiento(origen, origen+Mapa.getMapa().getColumnas())){//Compruebo que pueda girar a la derecha
							
							origen=origen+Mapa.getMapa().getColumnas();//Actualizo el origen
							s=true;
							move(origen,destino,o,s,e,n,ruta);
							ruta.addFirst(Dir.S);

						}else{
							
							if(Mapa.getMapa().comprobarMovimiento(origen, origen+1)){//No he podido girar a la derecha
																					 //compruebo que pueda seguir avanzando
								
								origen=origen+1;//Actualizo el origen
								e=true;
								move(origen,destino,o,s,e,n,ruta);
								ruta.addFirst(Dir.E);
								
							}else{
								
								if(Mapa.getMapa().comprobarMovimiento(origen, origen-Mapa.getMapa().getColumnas())){//Compruebo que pueda ir al norte
									
									origen=origen-Mapa.getMapa().getColumnas();//Actualizo el origen				
									n=true;
									move(origen,destino,o,s,e,n,ruta);
									ruta.addFirst(Dir.N);

								}else{//Si no podemos hacer nada de lo anterior significa que tenemos que dar la vuelta
									
									origen=origen-1;//Actualizo el origen
									o=true;
									move(origen,destino,o,s,e,n,ruta);
									ruta.addFirst(Dir.O);
								}
							}
						}
						
					}else{
						
						if(n){//Si vengo del norte, signifca que tengo o tenia pared en el este y que la mano derecha esta o estaba pegeda a ella
							
							n=false;
							
							if(Mapa.getMapa().comprobarMovimiento(origen, origen+1)){//Compruebo que pueda girar a la derecha
								
								origen=origen+1;//Actualizo el origen
								e=true;
								move(origen,destino,o,s,e,n,ruta);
								ruta.addFirst(Dir.E);
								
							}else{
								
								if(Mapa.getMapa().comprobarMovimiento(origen, origen-Mapa.getMapa().getColumnas())){//Compruebo que pueda seguir avanzando
									
									origen=origen-Mapa.getMapa().getColumnas();//Actualizo el origen				
									n=true;
									move(origen,destino,o,s,e,n,ruta);
									ruta.addFirst(Dir.N);

								}else{
									
									if(Mapa.getMapa().comprobarMovimiento(origen, origen-1)){//Compruebo que pueda girar a la izquierda(OESTE)
										
										origen=origen-1;//Actualizo el origen
										o=true;
										move(origen,destino,o,s,e,n,ruta);
										ruta.addFirst(Dir.O);
										
									}else{//Si no podemos hacer nada de lo anterior significa que tenemos que dar la vuelta
										
										origen=origen+Mapa.getMapa().getColumnas();//Actualizo el origen
										s=true;
										move(origen,destino,o,s,e,n,ruta);
										ruta.addFirst(Dir.S);
									}
								}
							}
						}
					}
				}
			}
		}
		return ruta;
	}
}

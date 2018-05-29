package Personajes;

import java.io.IOException;
import java.util.LinkedList;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Mapa.Dir;
import Rutas.MoveBehavior;


/**
 * PROYECTO DP 17/18 Super clase Personaje de la cual extenderan los distintos
 * tipos de personajes
 * 
 * @author <b> Blanco Mangut, Alvaro </b><br>
 *         Ablancoze@alumnos.unex.es <br>
 */
public abstract class Personaje {

/** Atributos de la clase personaje */
	
	/**
	 * Interfaz que contiene los metodos que generan las rutas
	 */
	MoveBehavior moveBehavior;

	/**
	 * Nombre del personaje
	 */
	protected String nombre;

	/**
	 * Inicial del personaje que lo identifica
	 */
	protected char identificador;

	/**
	 * Indica en que turno realizara las acciones el personaje
	 */
	protected int turno;
	
	/**
	 * Turno inicial de los personajes
	 */
	protected int turnoIni;

	/**
	 * Sala en la que se encuentra actualmente
	 */
	protected int salaActual;

	/**
	 * Indica la ruta que deben seguir los personajes
	 */
	public LinkedList<Dir> ruta;
	
	
	
	
/** Constructores de la clase Personaje */

	
	/**
	 * Constructor por defecto
	 */
	public Personaje() {
		this.nombre = " ";
		this.identificador = ' ';
		this.turno = 0;
		this.salaActual = 0;
		this.ruta = new LinkedList<Dir>();

	}
	
	/**
	 * Constructor por defecto
	 */
	public Personaje(String nombre, char identificador) {
		this.nombre = nombre;
		this.identificador =identificador;
		this.turno = 0;
		this.salaActual = 0;
		this.ruta = new LinkedList<Dir>();

	}
	
	/**
	 * Constructor por defecto
	 */
	public Personaje(String nombre, char identificador,int turno,int turnoIni,int salaActual) {
		this.nombre =nombre;
		this.identificador =identificador;
		this.turno = turno;
		this.turnoIni=turnoIni;
		this.salaActual = salaActual;

	}
	
	
	
	
/** Metodos abstractos de la clase personaje */
	
	/**
	 * Metodo que se implementa en las subclases Permite al personaje interacctuar con el hombre puerta
	 * @return boolean TRUE si ha abierto el portal, FALSE si no lo ha abierto
	 */
	public abstract boolean accionHombrePuerta();

	
	/**
	 * Metodo que se implementa en las subclases Permite al personaje realizar una accion
	 */
	public abstract void accionArma();

	
	/**
	 * Metodo que se implementa en las subclases. Permite a los personajes interacctuar
	 */
	public abstract void interactuar();
	
	
	/**
	 * Metodo que se implementa en las subclases. Permite mostrar la informacion relevante de un personaje
	 */
	public abstract String toString();
	
	
/**Metodos privados de la clase Personaje */
	
	
	/**
	 * Metodo que permite a un personaje realizar un movimiento en el mapa
	 */
	private boolean movimiento() {
		boolean movido = false;
		Mapa m = Mapa.getMapa();
		int dimY = m.getColumnas();
		int fila = m.identificarFila(this.salaActual, dimY);
		int columna = m.identificarColumna(this.salaActual, dimY); // Obtenemos las coordenas de la sala en la que se encuentra el personaje

		if (!ruta.isEmpty()) {
			
			if (ruta.getFirst() == Dir.N) {
				
				if (fila > 0) { // Comprobamos que no esta en el limite norte del mapa.
					
					if (m.comprobarMovimiento(this.salaActual,m.indentificaSala(fila-1, columna, dimY))) { // Comprobamos que no exista pared

				//		m.getSala(this.salaActual).salirSala(this); // Saco al personaje de su sala inicial

						this.salaActual = m.indentificaSala(fila - 1, columna, dimY); // Actualizo su sala acutal

						m.getSala(salaActual).entrarSala(this);// Lo inserto en la sala a la que se a movido
						movido = true;
					}
				}
				
			}else{
				
				if (ruta.getFirst() == Dir.O) {
					if (columna > 0) { //Comprobamos el limite del mapa
						if (m.comprobarMovimiento(this.salaActual, m.indentificaSala(fila, columna - 1, dimY))) { // Comprobamos que no exista pared
							
							m.getSala(this.salaActual).salirSala(this); // Saco al personaje de su sala incial


							this.salaActual = m.indentificaSala(fila, columna - 1, dimY); // Actualizo su sala actual

							m.getSala(salaActual).entrarSala(this);// Lo inserto en la sala a la que se ha movido
							movido = true;
						}
					}
					
				}else{
					if (ruta.getFirst() == Dir.S) {
						if (fila < m.getFila() - 1) {//Comprobamos el limite del mapa
							if (m.comprobarMovimiento(this.salaActual, m.indentificaSala(fila + 1, columna, dimY))) { // Comprobamos que no exista pared

								m.getSala(this.salaActual).salirSala(this); // Saco al personaje de su sala incial

								this.salaActual = m.indentificaSala(fila + 1, columna, dimY); // Actualizo su sala actual

								m.getSala(salaActual).entrarSala(this);// Lo inserto en la sala a la que se ha movido
								movido = true;
							}
						}
						
					}else{
						
						if (ruta.getFirst() == Dir.E) {
							if (columna < m.getColumnas() - 1) {//Comprobamos el limite del mapa
								if (m.comprobarMovimiento(this.salaActual, m.indentificaSala(fila, columna + 1, dimY))) { // Comprobamos que no exista pared

									m.getSala(this.salaActual).salirSala(this);// Saco al personaje de su sala incial

									this.salaActual = m.indentificaSala(fila, columna + 1, dimY); // Actualizo su sala actual
									
									m.getSala(salaActual).entrarSala(this);// Lo inserto en la sala a la que se ha movido
									movido = true;
								}
							}
						}
					}
				}
			}
			ruta.removeFirst();	//Se mueva o no siempre elimino ese movimiento
		}else{
			m.getSala(salaActual).entrarSala(this);
		}
		return movido;
	}
	
	
	
	
/**Metodos publicos de la clase Personaje */
	
	
	/**
	 * Permite a a los personajes realizar todas las acciones que conllevan un turno sobre un personaje
	 * @return boolean: TRUE si ha pasado a la sala ganadora, False si no gana o sigue moviendose
	 */
	public boolean accionPersonaje() {
		boolean ganador=false;
		
		if (salaActual == Mapa.getMapa().getSalaHombrePuerta()) { // si la sala en la que se encuentra esta el hombre puerta, realiza el enfrentamiento
			
			if(accionHombrePuerta()){//Si habre la puerta, entonces no coje armas y no interactua coje las armas he interactura
				
				this.salaActual=1111;//Actualizo su sala actual a la sala de los ganadores.
				
				ganador=true;
				
			}else{
				
				accionArma();
				
				interactuar();
				
				Mapa.getMapa().getSala(salaActual).entrarSala(this);//No actualizo su sala, puesto que esta en la ultia y lo inserto
			}
		}else{
			
			if(movimiento()){ //Si puede morvese, realizara acciones en la nueva sala en la que entre
				
				accionArma();
				
				interactuar();	
			}
		}
		
		this.turno=this.turno+1;
		return ganador;
	}
	
	
	/**
	 * Metodo que nos permite pintar la ruta de un personaje cuando esta se crea
	 */
	public void pintarRuta() {
		
		if (!ruta.isEmpty()){
			
			String str;
			System.out.print("(path:"+identificador+":");
			str="(path:"+identificador+":";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < ruta.size(); i++) {
				if(ruta.get(i)==Dir.O){
					System.out.print(" "+"W");
					
					str=" "+"W";
					try {
						Mapa.getMapa().getFlujo().write(str);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}else{
					System.out.print(" "+ruta.get(i));
					
					str=" "+ruta.get(i);
					try {
						Mapa.getMapa().getFlujo().write(str);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}			
			}
			System.out.println(")");
			
			str=")"+"\n";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
/** GET'S y SET'S de la clase Personaje */

	
	/**
	 * Metodo que nos da el nombre del personaje
	 * @return String nombre del personaje
	 */
	public String getNombre() {
		return nombre;
	}

	
	/**
	 * Metodo que nos da el identificador del personaje
	 * @return char Caracter del personaje
	 */
	public char getIdentificador() {
		return identificador;
	}

	
	/**
	 * Metodo que nos da el turno del personaje
	 * @return int Turno en el que se movera el personaje
	 */
	public int getTurno() {
		return turno;
	}

	
	/**
	 * Metodo que nos da la sala en la que se encuentra el personaje
	 * @return int Identificador de la sala 
	 */
	public int getSala() {
		return salaActual;
	}

	
	/**
	 * Metodo que nos permite modificar el nombre de un personaje
	 * @param nombre Nombre que queremos ponerle al personaje
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	/**
	 * Metodo que nos permite modificar el identificador de un personaje
	 * @param identificador caracter que queremos ponerle al personaje
	 */
	public void setIdentificador(char identificador) {
		this.identificador = identificador;
	}

	
	/**
	 * Metod que nos permite modificar el turno en el que se mueve el personaje
	 * @param turno Numero que indica el turno en el que se movera el personaje
	 */
	public void setTurno(int turno) {
		this.turno = turno;
	}
	
	
	/**
	 * Metodo que nos permite insertar una direccion a la ruta del personaje
	 * @param e Tipo enumerado Dir que indica la direccion que se insertara en la ruta
	 */
	public void setDir(Dir e) {
		ruta.addLast(e);
	}
}

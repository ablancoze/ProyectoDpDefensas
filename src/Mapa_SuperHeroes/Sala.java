package Mapa_SuperHeroes;
import java.util.Collections;
import java.util.LinkedList;
import Personajes.*;
import Puerta.HombrePuerta;
import Utilidades.OrdenarPorPoder;


/**
 * PROYECTO DP 17/18
 * Clase Sala
 * @author alvaro
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class Sala {

/**Atributos de la clase sala*/	
	
	/**
	 * Indentificador de la sala
	 */
	private int identificador;
	
	/**
	 * Marca de la sala
	 */
	private int marca;
	
	/**
	 * Estructura que contiene las armas de la sala
	 */
	private LinkedList<Arma> armaSala;
	
	/**
	 * Cola que almacena los personajes que se encuentran en la sala
	 */
	private LinkedList <Personaje> personajesSala;
	
	/**
	 * Puentero "puertaSala" que apuntara a una instancia de la HombrePuerta. En cualquier caso este puntero apuntara
	 * a null, excepto que se construlla la sala del trono en cuyo caso tendra una instancia puerta
	 */
	private HombrePuerta hombrePuertaSala;	
	
	/**
	 * Frecuencia con la que se visita esa sala.
	 */
	private int frecuencia;
	
	
	
	
/**Constructores de la clase sala */
	
	
	/**
	 * Constructor por defecto de Sala
	 */
	public Sala(){
		this.identificador=0;
		this.marca=0;
		this.armaSala=new LinkedList<Arma>();
		this.personajesSala=null;
		this.hombrePuertaSala=null;
		this.frecuencia=0;

	}
	
	
	/**
	 * Constructir parametrizado de la clase Sala
	 * @param identificador: int que indica el identificador de la sala.
	 */
	public Sala(int identificador){
		this.identificador=identificador;
		this.marca=identificador;
		this.armaSala=new LinkedList<Arma>();
		this.personajesSala=new LinkedList<Personaje>();	
		this.hombrePuertaSala=null;
		this.frecuencia=0;

	}
	
	
	
	
/**Metodos privados de la clase Sala */
	
	
	/**
	 * 
	 * @param p
	 * @param turno
	 * @return
	 */
	private boolean procesarPersonaje(Personaje p,int turno) {
		
		if (p.getTurno()==turno){//Comprobamos que sea su turno
			
			if(p.accionPersonaje()){//Realizamos las acciones pertinentes; TRUE SI HABRE LA PUERTA, FALSE SI NO LO HACE.
				
				Mapa.getMapa().insertarPersonajeGanador(p);//Pasamos al personaje a la sala de los ganadores
				
				return true;
			}
			
		}else{
			
			entrarSala(p);
			
		}
		
		return false;
	}
	
	
	
/**Metodos publicos de la clase Sala */
	
	
	/**
	 * Metodo que permite sacar a un personaje de la sala
	 * @param p Personaje que saldra de la sala
	 */
	public void  salirSala(Personaje p){
		personajesSala.remove(p);
	}
	
	
	/**
	 * Metodo que permite inserta a un personaje en una sala
	 * @param p Persoanje que entra en la sala
	 */
	public void entrarSala(Personaje p){
		personajesSala.addLast(p);
	}
	
	
	/**
	 * Metodo que simula las acciones de los personajes en la sala
	 * @param turno Numero del turno en el que nos encontramos
	 * @return boolean TRUE si un personaje a ganado la partida, FALSE si no se ha ganado la partida aun
	 */
	public boolean simulacion(int turno) {
		Personaje p=null;
		int i=0;
		boolean fin=false;
		int nPersonajes=personajesSala.size();
		while(i<nPersonajes){
			p=personajesSala.getFirst();//Cogemos al primer personaje de la sal.
			
			this.salirSala(p);//Lo sacamos de la sala.
			
			if(procesarPersonaje(p,turno)){//Procesamos al personaje segun su turno; TRUE si el personaje pasa la puerta, false si no lo hace
				
				fin=true;
				
			}
			i++;
		}
		return fin;
	}

	/**
	 * Metodo que permita pintar una sala
	 * @param paredE TRUE si existe pared ESTE en la sala, FALSE si no existe pared ESTE
	 * @param paredS TRUE si existe pared SUR en la sala, FALSE si no existe pared SUR
	 */
	public void pintarSala(boolean paredE, boolean paredS) {
		String str;
		int p=personajesSala.size(); //Obtenemos el numero de persoanjes en la sala
		
		if (p==1) {//Como hay personajes, no mostramos la pared del SUR
			
			System.out.print(personajesSala.getFirst().getIdentificador()); //Pintamos su identificador
			
			try {
				Mapa.getMapa().getFlujo().write(personajesSala.getFirst().getIdentificador());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (paredE==true) { // Si tiene pared en el ESTE, la pintamos
				
				System.out.print("|");
				str="|";
				try {
					Mapa.getMapa().getFlujo().write(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				
				System.out.print(" ");
				
				str=" ";
				try {
					Mapa.getMapa().getFlujo().write(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else{
			
			if (p>1) {//Como hay personajes, no mostramos la pared del SUR
				
				System.out.print(personajesSala.size()); //Si hay mas de un personaje, mostramos el numero de personajes que hay.
				str=""+personajesSala.size();
				try {
					Mapa.getMapa().getFlujo().write(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (paredE==true){
					
					System.out.print("|");
					
					str="|";
					try {
						Mapa.getMapa().getFlujo().write(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else{
					
					System.out.print(" ");
					
					str=" ";
					try {
						Mapa.getMapa().getFlujo().write(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}else{ //Como no hay personajes, pintamos las paredes que haya.
				
				if (paredS==true) { 
					
					System.out.print("_");
					
					str="_";
					try {
						Mapa.getMapa().getFlujo().write(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else{
					
					System.out.print(" ");
					
					str=" ";
					try {
						Mapa.getMapa().getFlujo().write(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				if(paredE==true){
					
					System.out.print("|");
					
					str="|";
					try {
						Mapa.getMapa().getFlujo().write(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else{
					
					System.out.print(" ");
					
					str=" ";
					try {
						Mapa.getMapa().getFlujo().write(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Metodo que nos permite aumentar la frecuencia de una sala en 1
	 */
	public void frecuenciaMas(){
		frecuencia=frecuencia+1;
	}
	
	
	/*Metodos con Armas de la sala*/
	
	
		/**
		 * Metodo que nos permite conoce el tama�o actual de la lista de armas de la sala
		 * @return int: Tama�o de la lista de armas de la sala
		 */
		public int tamanoArmas() {
			return armaSala.size();
		}
		
		
		/**
		 * Metodo que nos permite insertar un arma en la lista de armas. Esta lista simpre queda ordenada de forma decreciente (MAYOR/MENOR)
		 * @param arma Arma que se quiere insertar
		 */
		public void insertarArma(Arma arma) {
			armaSala.add(arma);
			Collections.sort(armaSala,Collections.reverseOrder(new OrdenarPorPoder()));
		}
	
	
		/**
		 * Metodo que nos permite saber el estado de la lista de armas
		 * @return boolean: TRUE si esta vacia, FALSE si no lo esta.
		 */
		public boolean armasVacia() {
			return armaSala.isEmpty();
		}
	
	
		/**
		 * Metodo que nos da la primera arma de la lista de armas de la sala
		 * @return Arma: Primera arma de la sala y por lo tanto la de mayor poder.
		 */
		public Arma primeraArma() {
			return armaSala.getFirst();
		}
		
		
		/**
		 * Metodo que permite coger un arma de la sala
		 * @return Arma Arma que se ha recogido
		 */
		public Arma cogerArma(){
			Arma a=armaSala.getFirst();
			armaSala.removeFirst();
			return a;
		}
		
		
		/**
		 * Metodo sobre cargado
		 * Metodo que permite coger un arma de la sala
		 * @param a Arma que se va ha dejar en la sala
		 * @return Arma Arma que se va a coger de la sala
		 */
		public Arma cogerArma(Arma a){
			Arma aux=armaSala.getFirst();
			armaSala.removeFirst();
			if (a!=null){
				armaSala.add(a);
				Collections.sort(armaSala,Collections.reverseOrder(new OrdenarPorPoder()));
			}
	
			return aux;
		}
		
		public int poderDeLaSala(){
			int acumulador=0;
			for (int i = 0; i < armaSala.size() ; i++) {
				acumulador=armaSala.get(i).getPoder()+acumulador;
			}
			return frecuencia;
			
		}
		
		
	/*Metodos con Personajes de la sala*/
		
		
		/**
		 * Metodo que permite insertar a un personaje en la sala
		 * @param p personajes que queremos añadir
		 */
		public void insertarPersonaje(Personaje p) {
			this.personajesSala.addLast(p);
		}
		
		
		/**
		 * Metodo que nos da el primero personaje en la cola de la sala
		 * @return devuelve el primer persoanje en la cola de la sala
		 */
		public Personaje primerPersonaje(){
			return(personajesSala.getFirst());
		}
		
		
		/**
		 * 
		 * @return boolean: TRUE si no hay personajes en la sala, FALSE si los hay.
		 */
		public boolean personajesVacia() {
			return personajesSala.isEmpty();
		}
	
	
		/**
		 * 
		 * @param turno
		 */
		public void mostrarPersonajesSala() {
			personajesSala.toString();
			
		}
	
	
		/**
		 * 
		 */
		public void mostrarPersonajesGanadores() {
			for (int i = 1; i < personajesSala.size(); i++) {
				personajesSala.get(i).toString();
			}
		}
		
		
		/**
		 * 
		 * @return
		 */
		public Personaje primerSuperHereo() {
			for (int i = 0; i < personajesSala.size(); i++) {
				if (personajesSala.get(i) instanceof SuperHeroe){
					return personajesSala.get(i);
				}
			}
			return null;
		}

		
		/**
		 * 
		 * @return
		 */
		public Personaje primerVillano() {
			for (int i = 0; i < personajesSala.size(); i++) {
				if (personajesSala.get(i) instanceof Villano){
					return personajesSala.get(i);
				}
			}
			return null;
		}
	
	
	
/**GET'S y SET'S de la clase sala */
	
	
	/**
	 * Metodo que nos da el identificador de la sala
	 * @return int: Identificador de la sala
	 */
	public int getIdentificador(){
		return identificador;
	}
	
	/**
	 * Metodo que nos da la marca de la sala
	 * @return int: Marca de la sala
	 */
	public int getMarca(){
		return marca;
	}
	
	
	/**
	 * Metodo que nos da al hombre puerta
	 * @return instacia del hombre puerta o null si en la sala no se encuentra el hombre puerta
	 */
	public HombrePuerta getHombrePuerta() {
		return hombrePuertaSala ;
	}
	
	
	/**
	 * Metodo que nos da la frecuencia de paso de la sala
	 * @return int Frecuencia de paso de la sala
	 */
	public int getFrecuencia(){
		return frecuencia;
	}
	
	
	/**
	 * Metodo que nos permite modificar el identificador de la sala
	 * @param identificador: int que indica el indentificador que queremos ponerle a la sala
	 */
	public void setIdentificador(int identificador){
		this.identificador=identificador;
	}
	
	/**
	 * Metodo que nos permite modificar la marca  de la sala
	 * @param marca: int que indica la marca que queremos ponerle a la sala
	 */
	public void setMarca(int marca){
		this.marca=marca;
	}
	
	
	/**
	 * Metodo que permite insertar al hombre puerta en la sala
	 * @param hp: Puntero que apunta al hombre puerta que se ha generado
	 */
	public void setHombrePuerta(HombrePuerta hp){
		this.hombrePuertaSala=hp;
	}
	
	
	/**
	 * Metodo que permite modificar la frecuencia de paso de la sala
	 * @param frecuencia Numero que indica la frecuencia de paso
	 */
	public void setFrecuencia(int frecuencia){
		this.frecuencia=frecuencia;
	}
	

/**compareTo, equals, toString */
	
	
	/**
	 * Muestra por pantalla la informacion relevante de la sala.
	 */
	public String toString(){
		String str;
		System.out.print("(square:"+this.identificador+":");
		
		str="(square:"+this.identificador+":";
		try {
			Mapa.getMapa().getFlujo().write(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < armaSala.size(); i++) {
			System.out.print(armaSala.get(i).toString());
			
			str=armaSala.get(i).toString();
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
		System.out.println(")");
		str=")"+"\n";
		try {
			Mapa.getMapa().getFlujo().write(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ("");
	}


	public void setModificarArmaParaPruebaVillano(Arma a) {
		armaSala.set(0, a);
	}
}

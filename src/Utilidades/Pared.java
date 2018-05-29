package Utilidades;
/**
 * PROYECTO DP 17/18
 * Clase Pared
 * @author 
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class Pared {
	
/**Atributos de la clase Pared */
	
	
	/**
	 * Entero que almecena el primer identificador de la Pared
	 */
	private Integer primero;
	
	/**
	 * Entero que almecena el segundo identificador de la Pared
	 */
	private Integer segundo;
	
	
	
	
/**Constructores de la clase Pair */
	
	
	/**
	 * Constructor por defecto de la clase Pared
	 */
	public Pared() {
		this.primero=0;
		this.segundo=0;
	}
	
	
	/**
	 * Constructor parametrizado de la clase Pared
	 * @param primero primer numero del par de enteros
	 * @param segundo segundo numero del par de enteros
	 */
	public Pared(Integer primero,Integer segundo) {
		this.primero=primero;
		this.segundo=segundo;
	}
	
	
	
	
/**GET'S y SET'S de la clase Pared */
	
	
	/**
	 * Metodo que devuelve el identificador de la primera  sala a la que pertenece la pared
	 * @return Integer Identificador de la sala
	 */
	public Integer getPrimero() {
		return primero;
	}
	
	
	/**
	 * Metodo que devuelve el identificador de la segunda  sala a la que pertenece la pared
	 * @return Integer Identificador de la sala
	 */
	public Integer getSegundo() {
		return segundo;
	}
	
	
	/**
	 * Metodo que permite insertar el identificador de la primera sala que forma la pared
	 * @param primero Identificador de la sala que forma la pared 
	 */
	public void setPrimero(Integer primero) {
		this.primero=primero;
	}
	
	
	/**
	 * Metodo que permite insertar el identificador de la segunda sala que forma la pared
	 * @param segundo Identificador de la sala que forma la pared 
	 */
	public void setSegundo(Integer segundo) {
		this.segundo=segundo;
	}
	
	
	/**
	 * Metodo que permite modificar una pared por medio de los identificadores de las salas que lo forman
	 * @param primero Identificador de la sala que forma la pared 
	 * @param segundo Identificador de la sala que forma la pared 
	 */
	public void setPar(Integer primero, Integer segundo) {
		this.primero=primero;
		this.segundo=segundo;
	}
	
	
	
/**Metodos publicos de la clase Pared */
	
	
	/**
	 * Metodo que permite comprobar si dos paredes son iguales 
	 * @param obj objeto que queremos comprobar
	 * @return boolean TRUE si las paredes son iguales, FALSE si no lo son
	 */
	public boolean equals(Object obj) {
		
    	if (this == obj){
		return true;
    	}
    	
    	if (!(obj instanceof Pared)){
		return false;
    	}
    	
    	Pared aAux = (Pared) obj;
 
    	if ((this.primero.equals(aAux.segundo)&&this.segundo.equals(aAux.primero)) || (this.primero.equals(aAux.primero)&&this.segundo.equals(aAux.segundo))){
    		return true;
    	}
    	
    	return false;
    	
	}
	
	/**
	 * Metodo que nos permite mostrar los identificadores que forman la pared
	 */
	public String toString() {
		System.out.print(" "+primero+"-"+segundo);
		return "";
	}
}

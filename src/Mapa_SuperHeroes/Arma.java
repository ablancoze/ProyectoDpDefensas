package Mapa_SuperHeroes;


/**
 * PROYECTO DP 17/18
 * Clase Arma
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class Arma implements Comparable<Arma> {
	
	/**
	 * Indica el nombre del arma
	 */
	private String nombre;
	
	
	/**
	 * Indica el poder del arma
	 */
	private int poder;
	
	
	
	
/**Constructores de la clase arma */
	
	
	/**
	 * Construcctor por defecto
	 */
	public Arma(){
		this.nombre=null;
		this.poder=0;
	}
	
	
	/**
	 * Constructor parametrizado
	 * @param nom indica el nombre del arma
	 * @param pod indica el poder del arma
	 */
	public Arma(String nom, int pod){
		this.nombre=nom;
		this.poder=pod;
	}
	
	
	
	
/**GET'S y SET'S de la clase Arma */

	
	/**
	 * Metodo permite obtener el nombre de la instancia
	 * @return devuelve nombre de la instancia mediante un String
	 */
	public String getNombre(){
		
		return nombre;
	}
	
	
	/**
	 * Metodo permite obtener el poder de la instancia
	 * @return devuelve el poder de la instancia mediante un int
	 */
	public int getPoder(){
		
		return poder;
	}
	
	/**
	 * Metodo que permite modificar el atributo nombre de la clase arma
	 * @param n: String de entrada que modifica el nombre
	 */
	public void setNombre(String n){
		this.nombre=n;
	}
	
	
	/**
	 * Metodo que permite modificar el atributo poder de la clase arma
	 * @param p: int de entrada que modifica el poder
	 */
	public void setPoder(int p){
		
		this.poder=p;
	}
	
	
	/**
	 * Metodo que permite comparar las armas por su nombre
	 */
	@Override
	public int compareTo(Arma a) {
		if (this==a){
			return 0;
		}
		return (this.nombre.compareTo(a.nombre));
	}
	
	
	/**
	 * Metodo que permite saber si dos instancias de la misma clase son iguales.
	 */
	public boolean equals(Object obj){
    	if (this == obj){
    		return true;
    	}
    	
    	if (!(obj instanceof Arma)){
    		return false;
    	}
    	
    	Arma aAux = (Arma) obj;
 
    	if (this.nombre.equals(aAux.getNombre())){
    		return true;
    	}
    	
    	return false;
    	
	}
	
	
	/**
	 * Muestra el arma por pantalla
	 * @return muestra el identificador y el nombre del arma
	 */
	public String toString(){	
		return "("+this.nombre +","+ this.poder+")";
	}
	
}

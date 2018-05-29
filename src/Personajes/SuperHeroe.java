package Personajes;
import java.util.LinkedList;
import Estructuras.Arbol;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Sala;
import Mapa_SuperHeroes.Arma;
import Puerta.HombrePuerta;


/**
 * PROYECTO DP 17/18
 * Subclase SuperHeroe que hereda de Personaje
 * @author 
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public abstract class SuperHeroe  extends Personaje {

/**Atributos de la clase SuperHeroe */
	
	
	/**
	 * Estructura que guarda las armas del persoje
	 */
	protected Arbol<Arma> armasPersonaje;
	
	/**
	 * Estructura que guarda los villanos capturados
	 */
	public LinkedList<Personaje> villanosCapturados;



/**Constructores de la clase SuperHeroe */	
	
	
	/**
	 * Constructor por defecto de la clase SuperHeroe
	 * Este constructor llama al constructor por defecto de la clase personaje
	 */
	public SuperHeroe(){
		super();
	}
	
	/**
	 * Constructor parametrizado utilizado durante la realizacion de las pruebas
	 * @param nombre String que indica el nombre del personaje
	 * @param identificador char que contiene el primer caracter del nombre del personaje
	 */
	public SuperHeroe(String nombre, char identificador){
		super(nombre,identificador);
	}
	
	
	/**
	 * Constructor parametrizado de la clase SuperHeroe
	 * @param nombre Nombre del personaje
	 * @param identificador Identificador del personaje
	 * @param turno Turno en el que el SuperHeroe comienza a moverse
	 * @param salaActual Sala en la que se encuentra el personaje
	 */
	public SuperHeroe(String nombre, char identificador, int turno,int turnoIni, int salaActual){
		super(nombre,identificador,turno,turnoIni,salaActual);
		this.armasPersonaje=new Arbol<Arma>();
		this.villanosCapturados=new LinkedList<Personaje>();
	}
	
	
	
	
/**Metodos privados de la clase SuperHeroe */	
	
	
	/**
	 * Metodo que nos permite obtener el mayor arma del arbol de armas de los superheroes
	 * @return Arma: Arma mayor del arbol de armas
	 */
	protected Arma getMayorArma(){
		return getMayorArmaRec(this.armasPersonaje);
	}
	
	
	/**
	 * Metodo recursivo que nos da el arma de mayor poder. Si hay dos armas de igual poder se quedara con la de mayor nombre. Basado en el algoritmo esHoja del arbol
	 * @param aux: Arbol que contendra las armas del superHeroe
	 * @return Arma: Arma de mayor poder que se encuentra en el arbol
	 */
	private Arma getMayorArmaRec(Arbol<Arma> aux){
		Arma aAux = null;	//Arma auxiliar

		Arma acumulada=null;//Mayor arma hasta ahora

		if(aux!=null){//Mientras que el personaje tenga arma

			if (aux.esHoja()){//Vemos si el nodo es hoja

				return aux.getRaiz();//si lo es devuelvo el arma

			}

			if(aux.getHijoIzq()!=null){

				aAux=getMayorArmaRec(aux.getHijoIzq());

				try {

					if (aAux.getPoder()>acumulada.getPoder())
						acumulada=aAux;

					if(aAux.getPoder()==acumulada.getPoder())
						if(aAux.compareTo(acumulada)<0)
							acumulada=aAux;

				}catch (NullPointerException e){

					if(aAux.getPoder()>aux.getRaiz().getPoder())
						acumulada=aAux;

					if(aAux.getPoder()<aux.getRaiz().getPoder())
						acumulada=aux.getRaiz();

					if(aAux.getPoder()==aux.getRaiz().getPoder())
						if(aAux.compareTo(aux.getRaiz())<0)
							acumulada=aAux;
						else
							acumulada=aux.getRaiz();
				}
			}

			if(aux.getHijoDer()!=null){

				aAux=getMayorArmaRec(aux.getHijoDer());

				try {

					if (aAux.getPoder()>acumulada.getPoder())
						acumulada=aAux;

					if(aAux.getPoder()==acumulada.getPoder())
						if(aAux.compareTo(acumulada)<0)
							acumulada=aAux;

				}catch (NullPointerException e){

					if(aAux.getPoder()>aux.getRaiz().getPoder())
						acumulada=aAux;

					if(aAux.getPoder()<aux.getRaiz().getPoder())
						acumulada=aux.getRaiz();

					if(aAux.getPoder()==aux.getRaiz().getPoder())
						if(aAux.compareTo(aux.getRaiz())<0)
							acumulada=aAux;
						else
							acumulada=aux.getRaiz();
				}
			}
		}
		return acumulada;
	}
	
/**Metodos publicos de la clase superHeroe */
	
	
	/**
	 * Metodo que se implementa en las subclases Permite al personaje interacctuar con el hombre puerta
	 * @return boolean TRUE si ha abierto el portal, FALSE si no lo ha abierto
	 */
	public abstract boolean accionHombrePuerta();
	
	
	/**
	 * Metodo que permite al SuperHeroe interactuar con la sala, recogiendo armas
	 */
	@Override
	public void accionArma(){
		Mapa m= Mapa.getMapa();
		Sala s=m.getSala(this.salaActual);
		if(!s.armasVacia()){ //Comprobamos si la sala tienes armas
			
			Arma aux=s.cogerArma(); //Cogemos la primera arma de la sala que sera la de mayor poder
			
			Arma aux2=armasPersonaje.getDato(aux); //Buscamos el arma en el contenedor del SuperHeroe
			
			if (aux2!=null){//Si hay un arma con el mismo nombre modificamos su poder
				
				aux2.setPoder(aux.getPoder()+aux2.getPoder());
				
			}else{//Si no posee esa arma la insertamos en el contenedor del SuperHeroe
				
				armasPersonaje.insertar(aux);
				
			}
			aux=null;
			aux2=null;
		}
	}
	
	
	/**
	 * Metodo que permite al SuperHeroe interactuar con otros personajes del mapa
	 */
	@Override
	public void interactuar() {
		Mapa m= Mapa.getMapa();
		Sala sAux=m.getSala(this.salaActual);
		if(!sAux.personajesVacia()){
			Personaje p=sAux.primerVillano();
			if (p!=null){
				Villano v=(Villano) p;
				if (v.getArma()!=null){
					Arma aAux=this.armasPersonaje.getDato(v.getArma());
					if(aAux!=null){
						if(aAux.getPoder()>v.getArma().getPoder()){
							sAux.salirSala(v);
							this.villanosCapturados.addFirst(v);
						}
					}
				}else{
					this.villanosCapturados.addFirst(v);
					sAux.salirSala(v);
				}
			}
		}
	}
	
	
	/**
	 * Metodo que muestra la informacion de ShExtraSensorial
	 */
	@Override
	public String toString() {
		String str;
		String tipo;
		if(this instanceof ShExtraSensorial){
			tipo="shextrasensorial";
		}else{
			if(this instanceof ShFlight){
				tipo="shflight";
			}else{
				tipo="shphysical";
			}
		}
		if(Mapa.getMapa().getTurno()<this.turnoIni){
			System.out.print("("+tipo+":"+this.identificador+":"+this.salaActual+":"+this.turno+":");

			str="("+tipo+":"+this.identificador+":"+this.salaActual+":"+this.turno+":";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else{

			System.out.print("("+tipo+":"+this.identificador+":"+this.salaActual+":"+(this.turno-1)+":");

			str="("+tipo+":"+this.identificador+":"+this.salaActual+":"+(this.turno-1)+":";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		this.armasPersonaje.inOrden();
		if(!villanosCapturados.isEmpty()){
			System.out.print(":");
			
			str=":";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < villanosCapturados.size(); i++) {
				System.out.print("("+this.villanosCapturados.get(i).getNombre()+")");
				
				str="("+this.villanosCapturados.get(i).getNombre()+")";
				try {
					Mapa.getMapa().getFlujo().write(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		System.out.println(")");
		
		str=")"+"\n";
		try {
			Mapa.getMapa().getFlujo().write(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * Metodo que destruye el arma del super heroe
	 * @param a arma que se quiere eliminar
	 */
	public void soltarArma(Arma a){
		armasPersonaje.borrar(a);
	}
}

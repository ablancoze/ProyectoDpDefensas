package Personajes;
import Mapa_SuperHeroes.Arma;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Sala;
import Puerta.HombrePuerta;
import Rutas.ManoIzquierda;

/**
 * PROYECTO DP 17/18
 * Subclase Villano que hereda de Personaje
 * @author 
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class Villano extends Personaje {
	
/**Atributos de la clase SuperHeroe */	
	
	/**
	 * Elemento que almacena el arma del Villano
	 */
	private Arma armaVillano=null;
	
	
/**Constructores de la clase Villano */	
	
	
	/**
	 * Constructor por defecto de la clase Villano
	 * Este constructor llama al constructor por defecto de la clase personaje
	 */
	public Villano(){
		super();
	}
	
	/**
	 * Constructor parametrizado utilizado durante las pruebas
	 * @param nombre String que contiene el nombre del villano
	 * @param identificador cahr que indica el primer caracter del nombre del villano
	 */
	public Villano(String nombre, char identificador){
		super(nombre,identificador);
	}
	

	
	/**
	 * Constructor parametrizado de la clase Villano
	 * @param nombre Nombre del personaje
	 * @param identificador Identificador del personaje
	 * @param turno Turno en el que el Villano comienza a moverse
	 */
	public Villano(String nombre,char identificador, int turno){
		super(nombre, identificador, turno, turno, Mapa.getMapa().getCornerNorEste());
		moveBehavior=new ManoIzquierda();
		ruta=moveBehavior.move(Mapa.getMapa().getCornerNorEste(), Mapa.getMapa().getSalaHombrePuerta());
	}
	
	
	/**
	 * Metodo que nos da el arma que posee el villano
	 * @return Arma Arma que tiene el villano
	 */
	public Arma getArma(){
		return armaVillano;
	}
	
	
	/**
	 * Metodo que permite al Villano interactuar con el hombre puerta
	 */
	@Override
	public  boolean accionHombrePuerta(){
		
		Mapa m= Mapa.getMapa();
		Sala s=m.getSala(this.salaActual);
		HombrePuerta hp=s.getHombrePuerta();
		Arma armaHp=hp.getArmaMayor();//Obtenemos el arma de mayor porder del hombre puerta
		
			if(armaVillano.getPoder()>armaHp.getPoder()){//Si el arma del villano tiene mas poder que el arma mas poderosa del hombre puerta
				
				hp.borrarArma(armaHp);					//la eliminamos
			}
			if(hp.comprobarPortal()){//Comprobamos el estado del portal
				return true;
			}
		return false;
	}
	
	
	/**
	 * Metodo que permite al Villano interactuar con la sala, recogiendo armas
	 */
	@Override
	public void accionArma(){
		
		Mapa m= Mapa.getMapa();
		Sala s=m.getSala(this.salaActual);
		if(!s.armasVacia()){ //Comprobamos si la sala tienes armas
			
			if (armaVillano!=null){// Si el villano tiene algun arma
				
				if (armaVillano.getPoder()<s.primeraArma().getPoder()){//Si el arma del villano tiene menos poder
					
					armaVillano=s.cogerArma(armaVillano);			   //El villano deja su arma en la sala y coge la mas poderosa
				}
			}else{//Si no tiene arma la cogemos
				armaVillano=s.cogerArma(armaVillano);	
			}
		}
	}
	
	
	/**
	 * Metodo que permite al Villano interacctuar con otros personajes que se encuentre en la misma sala.
	 */
	@Override
	public void interactuar() {
		Mapa m= Mapa.getMapa();
		Sala sAux=m.getSala(this.salaActual);
		if(!sAux.personajesVacia()){

			Personaje p=sAux.primerSuperHereo(); //Cogemos al primer persoanje de la sala 
			if(p!=null){
				SuperHeroe s=(SuperHeroe) p;	//Realizamos un casting

				if (this.armaVillano!=null){	//Si el villano tiene arma

					Arma aAux=s.armasPersonaje.getDato(armaVillano);//Buscamos la misma arma en el super hereo

					if(aAux!=null){//Si tiene el arma

						if(armaVillano.getPoder()>aAux.getPoder()){//Comparamos los poderes de las mismas y si es mas poderosa la del villano

							s.soltarArma(aAux);					   //El supoer heroe pierde su arma
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Metodo que muestra por pantalla la informacion relevante del Villano
	 */
	@Override
	public String toString() {
		String str;
		if(Mapa.getMapa().getTurno()<this.turnoIni){
			System.out.print("(villain:"+this.identificador+":"+this.salaActual+":"+this.turno+":");

			str="(villain:"+this.identificador+":"+this.salaActual+":"+this.turno+":";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else{

			System.out.print("(villain:"+this.identificador+":"+this.salaActual+":"+(this.turno-1)+":");

			str="(villain:"+this.identificador+":"+this.salaActual+":"+(this.turno-1)+":";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		try{
			System.out.print(this.armaVillano.toString());
			
			try {
				Mapa.getMapa().getFlujo().write(this.armaVillano.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println(")");
			
			str=")"+"\n";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}catch (NullPointerException e) {
			System.out.println(")");
			
			str=")"+"\n";
			try {
				Mapa.getMapa().getFlujo().write(str);
			} catch (Exception b) {
				b.printStackTrace();
			}
			
		}
		return "";
	}
}

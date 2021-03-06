package Personajes;
import Mapa_SuperHeroes.Arma;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Sala;
import Puerta.HombrePuerta;
import Rutas.MovimientoProfundidad;

/**
 * PROYECTO DP 17/18
 * Subclase ShPhysical que ereda de SuperHeroe
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class ShPhysical extends SuperHeroe {
	
/**Constructores de la clase ShPhysical */	
	
	
	/**
	 * Constructor por defecto de la clase ShPhysical que hereda de SuperHeroe
	 * Este constructor utiliza el constructor de su clase padre que es SuperHeroe
	 */
	public ShPhysical() {
		super();
	}
	
	public ShPhysical(String nombre,char identificador) {
		super(nombre,identificador);
	}
	
	/**
	 * Constructor parametrizado de la clase ShPhysical
	 * @param nombre nombre del ShPhysical
	 * @param identificador caracter del ShPhysical
	 * @param turno turno en el que inicia su movimiento el ShPhysical
	 */
	public ShPhysical(String nombre, char identificador, int turno) {
		super(nombre,identificador,turno,turno,0);
		moveBehavior=new MovimientoProfundidad();
		ruta=moveBehavior.move(0, Mapa.getMapa().getSalaHombrePuerta());
	}
	
	/**
	 * Metodo que permite al SuperHeroe interactuar con el hombre puerta
	 */
	@Override
	public  boolean accionHombrePuerta(){
		boolean ganador=false;
		Mapa m= Mapa.getMapa();
		Sala s=m.getSala(this.salaActual);
			
			HombrePuerta hp=s.getHombrePuerta();
			Arma armaP=this.getMayorArma();
			Arma armaHp=hp.getContenedorArmas().getDato(armaP);
			
			try {
				if(armaP.getPoder()>armaHp.getPoder()){
					hp.getContenedorArmas().borrar(armaHp);
				}

			} catch (NullPointerException e) {

			}
			if(hp.comprobarPortal()){
				ganador=true;
			}
			
			armasPersonaje.borrar(armaP);
		return ganador;
	}
}

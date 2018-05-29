package Personajes;
import Mapa_SuperHeroes.Arma;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Sala;
import Puerta.HombrePuerta;
import Rutas.MovimientoMasCorto;

/**
 * PROYECTO DP 17/18
 * Subclase ShFlight que hereda de SuperHeroe
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class ShFlight extends SuperHeroe {
	
/**Constructores de la clase ShFlight */
	
	
	/**
	 * Constructor por defecto de la clase ShFlight que hereda de SuperHeroe
	 * Este constructor utiliza el constructor de su clase padre que es SuperHeroe
	 */
	public ShFlight() {
		super();
	}
	
	
	/**
	 * Constructor parametrizado de la clase ShFlight
	 * @param nombre nombre del ShFlight
	 * @param identificador caracter del ShFlight
	 * @param turno turno en el que inicia su movimiento el ShFlight
	 */
	public ShFlight(String nombre, char identificador, int turno) {
		super(nombre,identificador,turno,turno,Mapa.getMapa().getCornerSurOeste());
		moveBehavior=new MovimientoMasCorto();
		ruta=moveBehavior.move(Mapa.getMapa().getCornerSurOeste(), Mapa.getMapa().getSalaHombrePuerta());
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

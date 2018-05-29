package Personajes;

import Mapa_SuperHeroes.Arma;
import Mapa_SuperHeroes.Mapa;
import Mapa_SuperHeroes.Sala;
import Puerta.HombrePuerta;
import Rutas.MovimientoMasCorto;

public class ShflightBajo extends SuperHeroe {
	
	public ShflightBajo(String nombre, char identificador, int turno) {
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
				if(armaP.getPoder()<armaHp.getPoder()){
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

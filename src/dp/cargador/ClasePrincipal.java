package dp.cargador;
import java.io.FileNotFoundException;
import java.io.IOException;
import Mapa_SuperHeroes.Mapa;
import Utilidades.ControladorDeErrores;


/**
 * PROYECTO DP 17/18
 * Clase ClasePrincipal
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class ClasePrincipal {
	public static void main(String[] args)throws ControladorDeErrores {
		int maxTurnos=40;
		
		
		/**  
		instancia asociada al fichero de entrada inicio.txt
		*/
		Cargador cargador = new Cargador();
		
		try {
			
		    FicheroCarga.procesarFichero("init_10x10_2.txt", cargador); //leemos el fichero
		    Mapa.getMapa().simulacion(maxTurnos);	//iniciamos la simulacion
		}
		catch(ControladorDeErrores valor){
			
			System.err.println ("Error al procesar el fichero: "+valor.getMessage());
		}
		catch (FileNotFoundException valor)  {
			System.err.println ("Excepción capturada al procesar fichero: "+valor.getMessage());
		}
		
		catch (IOException valor)  {
			System.err.println ("Excepción capturada al procesar fichero: "+valor.getMessage());
		}
	}
}

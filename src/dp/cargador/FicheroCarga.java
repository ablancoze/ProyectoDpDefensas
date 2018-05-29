package dp.cargador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Utilidades.ControladorDeErrores;


/**
 * PROYECTO DP 17/18
 * Clase FicheroCarga
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class FicheroCarga {
	/**  
	 * atributo de la clase que indica el numero máximo de campos que se pueden leer
	 */
	public static final int MAXCAMPOS  = 10;

	/**  
	 * buffer para almacenar el flujo de entrada
	 */
	private static BufferedReader bufferIn;
	
	/**
	 * Metodo para procesar el fichero. Sin excepciones
	 * @param nombreFichero Nombre del fichero que vamos a leer
	 * @param cargador instancia de la clase Cargador
	 * @throws FileNotFoundException Lanza una excepcion si no se puede leer el fichero
	 * @throws IOException
	 * @throws ControladorDeErrores Lanza una excepcion si uno de los datos del fichero no es correcto
	 */
	public static void procesarFichero(String nombreFichero, Cargador cargador) throws  FileNotFoundException, IOException, ControladorDeErrores {
		List<String> vCampos = new ArrayList<String>();
		String S=new String();
		int numCampos=0;
		
		bufferIn = new BufferedReader(new FileReader(nombreFichero));//creación del filtro asociado al flujo de datos

		while((S=bufferIn.readLine())!= null) {
			if (!S.startsWith("--"))  {
				vCampos.clear();
				numCampos = trocearLinea(S, vCampos);
				cargador.crear(vCampos.get(0), numCampos, vCampos);
			}
		}
		bufferIn.close();	     //cerramos el filtro
	}

	/**
	 * Metodo para trocear cada línea y separarla por campos
	 * @param S cadena con la línea completa leída
	 * @param vCampos. Array de String que contiene en cada posición un campo distinto
	 * @return numCampos. Número campos encontrados
	 */
	private static int trocearLinea(String S,List<String> vCampos) {
		boolean eol = false;
		int pos=0,posini=0, numCampos=0;

		while (!eol) {
			pos = S.indexOf("#");
			if(pos!=-1) {
				vCampos.add(new String(S.substring(posini,pos)));
				S=S.substring(pos+1, S.length());
				numCampos++;
			}
			else eol = true;
		}
		return numCampos;
	}

}

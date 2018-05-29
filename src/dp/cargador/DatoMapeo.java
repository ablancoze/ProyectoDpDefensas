package dp.cargador;

/**
 * PROYECTO DP 17/18
 * Clase DatoMapeo
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class DatoMapeo {

/**Atributos de la clase Dato Mapeo */

	
	/**
	 * 
	 */
	private String nombre;


	/**
	 * 
	 */
	private int numCampos;




/**Constructores de la clase DatoMapeo */


	/**
	 * constructor por defecto
	 */
	DatoMapeo() {
		nombre = new String();
		nombre = "..";
		numCampos = 0;
	}
	
	
	/**
	 *  constructor parametrizado 
	 *  @param _nombre nombre del tipo de datos
	 *  @param _numCampos numero de campos-atributos que contendra¡
	 */
	DatoMapeo(String _nombre, int _numCampos) {
		nombre = new String(_nombre);
		numCampos = _numCampos;
	}

	
	/**
	 * devuelve el nombre del tipo
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	
	/**
	 * devuelve el numero de campos del tipo
	 * @return numCampos
	 */
	public int getCampos() {
		return numCampos;
	}


}

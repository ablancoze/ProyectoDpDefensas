package Utilidades;

/**
 * PROYECTO DP 17/18
 * Clase ControladorDeErrores
 * @author 
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class ControladorDeErrores extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param ms Mensaje que queremos mostrar si salta la excepcion
	 */
	public ControladorDeErrores(String ms){
		super(ms);
	}

}

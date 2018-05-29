package dp.cargador;
import java.io.IOException;
import java.util.List;
import Mapa_SuperHeroes.Mapa;
import Personajes.*;
import Utilidades.ControladorDeErrores;


/**
 * PROYECTO DP 17/18
 * Clase Cargador
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class Cargador {
	
/**Atributos de la clase Cargador */
	
	
	/**  
	* numero de elementos distintos que tendra� la simulacion - Mapa, ShExtraSensorial, ShVolador, ShFisico, Villano
	*/
	static final int NUMELTOSCONF = 6;
	
	
	/**  
	* atributo para almacenar el mapeo de los distintos elementos
	*/
	static private DatoMapeo [] mapeo;
	
	
	
	
/**Constructores de la clase Cargador */
	
	
	/**
	 *  constructor parametrizado 
	 *  @param e referencia a la instancia del patron Singleton
	 */
	Cargador()  {
		mapeo = new DatoMapeo[NUMELTOSCONF];
		mapeo[0]= new DatoMapeo("MAP", 5);
		mapeo[1]= new DatoMapeo("SHPHYSICAL", 4);
		mapeo[2]= new DatoMapeo("SHEXTRASENSORIAL", 4);
		mapeo[3]= new DatoMapeo("SHFLIGHT", 4);
		mapeo[4]= new DatoMapeo("VILLAIN", 4);	
		mapeo[5]= new DatoMapeo("SHPHYSICAL2", 4);
	}
	
	/**
	 *  busca en mapeo el elemento leido del fichero inicio.txt y devuelve la posicion en la que esta
	 *  @param elto elemento a buscar en el array
	 *  @return res posicion en mapeo de dicho elemento
	 */
	private int queElemento(String elto)  {
	    int res=-1;
	    boolean enc=false;

	    for (int i=0; (i < NUMELTOSCONF && !enc); i++)  {
	        if (mapeo[i].getNombre().equals(elto))      {
	            res=i;
	            enc=true;
	        }
	    }
	    return res;
	}
	
	/**
	 *  Metodo que crea las distintas instancias de la simulacion 
	 *  @param elto nombre de la instancia que se pretende crear
	 *  @param numCampos número de atributos que tendrá la instancia
	 *  @param vCampos array que contiene los valores de cada atributo de la instancia
	 * 	@throws ControladorDeErrores Lanza una excepcion si la configuracion inicial es incorrecta
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public void crear(String elto, int numCampos, List<String> vCampos) throws ControladorDeErrores, NumberFormatException, IOException	{
		
	    int numElto = queElemento(elto);//Si existe elemento y el numero de campos es correcto, procesarlo... si no, error

	    if ((numElto!=-1) && (mapeo[numElto].getCampos() == numCampos))   {//Comprobacion de datos basicos correctos
	        
	        switch(numElto){//procesar
	        case 0:	   
	            crearMap(numCampos,vCampos);
	            break;
	        case 1:
	            crearSHPhysical(numCampos,vCampos);
	            break;
	        case 2:
	            crearSHExtraSensorial(numCampos,vCampos);
	            break;
	        case 3:
	            crearSHFlight(numCampos,vCampos);
	            break;
	        case 4:
	            crearVillain(numCampos,vCampos);
	            break;
	        case 5:
	            crearSHPhysical2(numCampos, vCampos);
	            break;
	     	}
	    }
	    else
	        System.out.println("ERROR Cargador::crear: Datos de configuración incorrectos... " + elto + "," + numCampos+"\n");
	}

	
	/**
	 * Genera el mapa del juego
	 * @param numCampos Numero de cmapos que contiene la linea del fichero que estamos leyendo 
	 * @param vCampos Lista con los datos del mapa
	 * @throws ControladorDeErrores Lanza una excepcion si alguno de los datos leidos del fichero estan mal
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private void crearMap(int numCampos, List<String> vCampos) throws ControladorDeErrores, NumberFormatException, IOException{
		
			if (Integer.parseInt(vCampos.get(2))<0 || Integer.parseInt(vCampos.get(1))<0 ){
				
				throw new ControladorDeErrores("ERROR EN LA CONFIGURACION DEL MAPA");
			}
			
	    Mapa.getMapa(Integer.parseInt(vCampos.get(3)), Integer.parseInt(vCampos.get(2)), Integer.parseInt(vCampos.get(1)), 
	    										Integer.parseInt(vCampos.get(4)));
	}

	
	/**
	 * Crea al personaje del tipo SHPhysical
	 * @param numCampos Numero de cmapos que contiene la linea del fichero que estamos leyendo 
	 * @param vCampos Lista con los datos del mapa
	 */
	private void crearSHPhysical(int numCampos, List<String> vCampos){
	   ShPhysical ShP=new ShPhysical(vCampos.get(1),(vCampos.get(2)).charAt(0),Integer.parseInt(vCampos.get(3)));
	   Mapa.getMapa().insertarPersonaje(ShP);
	   ShP.pintarRuta();

	}

	/**
	 * Crea al personaje del tipo ShExtraSensorial
	 * @param numCampos Numero de cmapos que contiene la linea del fichero que estamos leyendo 
	 * @param vCampos Lista con los datos del mapa
	 */
	private void crearSHExtraSensorial(int numCampos, List<String> vCampos){
	    ShExtraSensorial ShE=new ShExtraSensorial(vCampos.get(1),(vCampos.get(2)).charAt(0),Integer.parseInt(vCampos.get(3)));
	    Mapa.getMapa().insertarPersonaje(ShE);
	    ShE.pintarRuta();
	}	

	
	/**
	 * Crea al personaje del tipo ShFlight
	 * @param numCampos Numero de cmapos que contiene la linea del fichero que estamos leyendo 
	 * @param vCampos Lista con los datos del mapa
	 */
	private void crearSHFlight(int numCampos, List<String> vCampos){
	    ShFlight ShF=new ShFlight(vCampos.get(1),(vCampos.get(2)).charAt(0),Integer.parseInt(vCampos.get(3)));
	    Mapa.getMapa().insertarPersonaje(ShF);
	    ShF.pintarRuta();
	}	

	
	/**
	 * Crea al personaje del tipo Villano
	 * @param numCampos Numero de cmapos que contiene la linea del fichero que estamos leyendo 
	 * @param vCampos Lista con los datos del mapa
	 */
	private void crearVillain(int numCampos, List<String> vCampos){
	    Villano V=new Villano(vCampos.get(1),(vCampos.get(2)).charAt(0), Integer.parseInt(vCampos.get(3)));
	    Mapa.getMapa().insertarPersonaje(V);
	    V.pintarRuta();
	}
	
	/**
	 * Crea al personaje del tipo SHPhysical2
	 * @param numCampos Numero de cmapos que contiene la linea del fichero que estamos leyendo 
	 * @param vCampos Lista con los datos del mapa
	 */
	private void crearSHPhysical2(int numCampos, List<String> vCampos){
	   ShPhysical2 ShP=new ShPhysical2(vCampos.get(1),(vCampos.get(2)).charAt(0),Integer.parseInt(vCampos.get(3)));
	   Mapa.getMapa().insertarPersonaje(ShP);
	   ShP.pintarRuta();

	}

}

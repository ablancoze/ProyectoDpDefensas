package Puerta;
import java.io.IOException;

import Estructuras.*;
import Mapa_SuperHeroes.*;


/**
 * PROYECTO DP 17/18
 * Clase HombrePuerta
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class HombrePuerta {

/**Atributos de la clase HombrePuerta */
	
	/**
	 * Indica el estado del portal que el hombre puerta es capaz de abrir. TRUE=Abierto/FALSE=Cerrado
	 */
	private boolean portal;
	
	/**
	 * Contenedor de armas del Hombre Puerta
	 */
	private Arbol<Arma> contenedorArmas;
	
	/**
	 * Altura de apertura del portal
	 */
	private int alturaApertura;
	
	
	
	
/**Constructores de la clase HombrePuerta */
	
	
	/**
	 * Constructor por defecto de la clase HombrePuerta
	 */
	public HombrePuerta(){
		this.portal=false;
		this.contenedorArmas=new Arbol<Arma>();
		this.alturaApertura=5;
	}
	
	
	/**
	 * Constructor parametrizado de la clase HombrePuerta
	 * @param alturaApertura Altura de apertura del portal
	 */
	public HombrePuerta(int alturaApertura){
		this.portal=false;
		this.contenedorArmas=new Arbol<Arma>();
		this.alturaApertura=alturaApertura;
	}
	
	
	
	
/*Metodos privados de la clase HombrePuerta */
	
	
	/**
	 * Metodo que nos permite obtener el arma de mayor poder del hombre puerta
	 * @param aux Arbol que contiene las armas del hombre puerta
	 * @return Arma Arma de mayor poder
	 */
	private Arma getMayorArmaRec(Arbol<Arma> aux){
		Arma aAux = null;	//Arma auxiliar
		
		Arma acumulada=null;//Mayor arma hasta ahora
		
		if(aux!=null){//Mientras que el personaje tenga arma
			
			if (aux.esHoja()){//Vemos si el nodo es hoja
				
				return aux.getRaiz();//si lo es devuelvo el arma
				
			}else{
				
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
		}
		return acumulada;
	}
	
	
	
	
/**Metodos publicos de la clase HombrePuerta */
	
	
	/**
	 * Constructor parametrizado de la clase HombrePuerta
	 * @param armasPuerta Array de armas que deseamos añadir al HombrePuerta
	 */
	public void configurar(Arma[] armasPuerta) {

		for (int i = 0; i < armasPuerta.length; i++) {
			
			contenedorArmas.insertar(armasPuerta[i]);
		}
	}
	
	
	/**
	 * Metodo que cierra el portal
	 */
	public void cerrar() {
		portal=false;
	}
	
	
	/**
	 * Metodo que permite comprobar si la altura del portal es la correcta para realizar su apertura
	 * @return TRUE si el portal esta listo para abrirse, FALSE si no lo esta
	 */
	public boolean comprobarPortal() {
			if(contenedorArmas.profundidadAbb()<alturaApertura){
				portal=true;
				return true;
			}
		return false;
	}
	
	
	/**
	 * Metodo que nos permite eliminar un arma del contenedor de armas
	 * @param armaHp Arma que queremos eliminar
	 */
	public void borrarArma(Arma armaHp) {
		contenedorArmas.borrar(armaHp);
	}
	
	
	/**
	 * Metodo publico que llama al metodo privado getMayorArmaRec
	 * @return Arma Arma de mayor poder del hombre puerta
	 */
	public Arma getArmaMayor() {
		return getMayorArmaRec(this.contenedorArmas);
	}
	
	
	
/**GET'S y SET'S de la clase HombrePuerta */
	
	
	/**
	 * Metodo que nos permite saber el estado del portal
	 * @return boolean TRUE si esta abierto, FALSE si no lo esta
	 */
	public boolean getPortal(){
		return portal;
	}
	
	
	/**
	 * Metodo que nos permite obtener el contenedor de armas del HombrePuerta
	 * @return Arbol que contienes las armas del hombre puerta
	 */
	public Arbol<Arma> getContenedorArmas(){
		return contenedorArmas;
	}
	
	
	/**
	 * Metodo que nos permite modificar el estado del portal
	 * @param portal booleano que modifica el portal. TRUE para abrirlo, FALSE para cerrarlo
	 */
	public void setPortal(boolean portal){
		this.portal=portal;
	}
	
	
	/**
	 * Metodo que nos permite insertar un contenedor de armas 
	 * @param ContenedorArmas contenedor de armas que queremos insertar
	 */
	public void setContenedorArmas(Arbol<Arma> ContenedorArmas){
		this.contenedorArmas=ContenedorArmas;
	}
	
	
/**compareTo, equals y toString */
	
	
	/**
	 * Metodo que nos muestra la informacion relevante del HombrePuerta
	 */
	public String toString(){
		String str;
		
		String portal="closed";
		if (this.portal){
			portal="open";
		}
		
		System.out.print("(doorman:"+portal+":"+alturaApertura+":");
		
		str="(doorman:"+portal+":"+alturaApertura+":";//Escribimos en el fichero de salida
		try {
			Mapa.getMapa().getFlujo().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		contenedorArmas.inOrden();
		
		System.out.print(")");
		
		str=")";//Escribimos en el fichero de salida
		try {
			Mapa.getMapa().getFlujo().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return"";
	}
}

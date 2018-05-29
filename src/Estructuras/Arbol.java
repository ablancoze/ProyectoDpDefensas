package Estructuras;

import java.io.IOException;

import Mapa_SuperHeroes.Mapa;

/**
 * PROYECTO DP 17/18
 * Estructura de tipo ABB (Arbol de busqueda binaria)
 * @author 
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class Arbol<T extends Comparable<T>> {

/**Atributos de la clase arbol */
	
	/** 
	 * Dato almacenado en cada nodo del arbol
	 */
	private T datoRaiz;
	
	/**
	 * Atributo que indica si el arbol esta vaccio
	 */
	boolean esVacio;
	
	/** 
	 * Hijo izquierdo del nodo actual
	 */
	private Arbol<T> hIzq;
	
	/**
	 * Hijo derecho del nodo actual
	 */
	private Arbol<T> hDer;
	
	
	
	
/**Constructores de la clase Arbol */
	
	
	/**
	 * Constructor por defecto de la clase. Inicializa un árbol vacío.
	 */
	public Arbol(){
	    this.esVacio=true;
	    this.hIzq = null;
	    this.hDer = null;
	}

	
	/**
	 * Constructor parametrizado
	 * Crea un nuevo árbol a partir de los datos pasados por parámetro.
	 *
	 * @param hIzq El hijo izquierdo del árbol que se está creando 
	 * @param datoRaiz Raíz del árbol que se está creando
	 * @param hDer El hijo derecho del árbol que se está creando
	 */
	public Arbol (Arbol<T> hIzq, T datoRaiz, Arbol<T> hDer){
	        this.esVacio=false;
		this.datoRaiz = datoRaiz;
		this.hIzq = hIzq;
		this.hDer=hDer;
	}
	
	
	
	
/**Metodos privados de la clase arbol */
	
	
	/**
	 * Borrar un dato. Este metodo es utilizado por el metodo borrar publico.
	 *
	 * @param dato El dato a borrar
	 * @return Devuelve el arbol resultante despues de haber realizado el borrado
	 */
	private Arbol<T> borrarOrden(T dato){
		
	    T datoaux;
	    Arbol<T> retorno=this;
	    Arbol<T> aborrar, candidato, antecesor;
	  	    
	    if (!vacio()) {
	    	
	        if (dato.compareTo(this.datoRaiz)<0){		// dato<datoRaiz
		    	       if (hIzq!=null)
	        	hIzq = hIzq.borrarOrden(dato);
	        }else
	        	
	            if (dato.compareTo(this.datoRaiz)>0){	// dato>datoRaiz
	    	        if (hDer!=null)  
	            	hDer = hDer.borrarOrden(dato);
	    	        
	            }else{
	            	
	            	if(dato.equals(this.datoRaiz)){
		                aborrar=this;
		                if ((hDer==null)&&(hIzq==null)) { /*si es hoja*/
		                    retorno=null;
		                }
		                else {
		                    if (hDer==null) { /*Solo hijo izquierdo*/
		                        aborrar=hIzq;
		                        datoaux=this.datoRaiz;
		                        datoRaiz=hIzq.getRaiz();
		                        hIzq.datoRaiz = datoaux;
		                        hIzq=hIzq.getHijoIzq();
		                        hDer=aborrar.getHijoDer();

		                        retorno=this;
		                    }
		                    else
		                        if (hIzq==null) { /*Solo hijo derecho*/
		                            aborrar=hDer;
		                            datoaux=datoRaiz;
		                            datoRaiz=hDer.getRaiz();
		                            hDer.datoRaiz = datoaux;
		                            hDer=hDer.getHijoDer();
		                            hIzq=aborrar.getHijoIzq();

		                            retorno=this;
		                        }
		                        else { /* Tiene dos hijos */
		                            candidato = this.getHijoIzq();
		                            antecesor = this;
		                            while (candidato.getHijoDer()!=null) {
		                                antecesor = candidato;
		                                candidato = candidato.getHijoDer();
		                            }

		                            /*Intercambio de datos de candidato*/
		                            datoaux = datoRaiz;
		                            datoRaiz = candidato.getRaiz();
		                            candidato.datoRaiz=datoaux;
		                            aborrar = candidato;
		                            if (antecesor==this)
		                                hIzq=candidato.getHijoIzq();
		                            else
		                                antecesor.hDer=candidato.getHijoIzq();
		                        } //Eliminar solo ese nodo, no todo el subarbol
		                    aborrar.hIzq=null;
		                    aborrar.hDer=null;
		                }
	            		
	            	}else{
		    	        if (hDer!=null)  
			            	hDer = hDer.borrarOrden(dato);
	            	}
	            	
	            	

	            }
	    }
	   
	    return retorno;
	}
	
	
	/**
	 * Metodo privado que calcula el numero de hojas del arbol
	 * @param aux: arbol de entrada.
	 * @return int que indica el numero de nodos hojas del arbol
	 */
	private int numHojas(Arbol<T> aux){
		int acumulador=0;
		if(aux!=null){
			if (aux.esHoja()){
				return 1;
			}else{
				if(aux.getHijoIzq()!=null){
					acumulador=acumulador+numHojas(aux.getHijoIzq());
				}
				if(aux.getHijoDer()!=null){
					acumulador=acumulador+numHojas(aux.getHijoDer());
				}
			}	
		}
		return acumulador;
	}
	
	
	/**
	 * Metodo que permite calcular la profundidad de un arbol
	 * @param aux: Arbol
	 * @param profundidad: int profundida que viene definido por su metodo publico
	 * @return int: altura del arbol
	 */
	private int profundidadAbb(Arbol<T> aux,int profundidad){
		int altura1=0;int altura2=0;
		if (aux!=null){
			if (aux.esHoja()){
				return profundidad;
			}
			if (aux.getHijoIzq()!=null){
				altura1=altura1+profundidadAbb(aux.getHijoIzq(),profundidad+1);
			}
			if (aux.getHijoDer()!=null){
				altura2=altura2+profundidadAbb(aux.getHijoDer(),profundidad+1);	
			}
		}
		if (altura1>altura2){
			return altura1;
		}
		return altura2;
	}
	
	
	
	
/**Metodos publicos de la clase arbol */
	
	
	/**
	 * Comprueba si el arbol esta vacio
	 * @return verdadero si el arbol esta vacio, falso en caso contrario
	 */
	public boolean vacio(){
		return esVacio;
	}
	
	
	/**
	 * Inserta un nuevo dato en el arbol
	 *
	 * @param dato El dato a insertar
	 * @return verdadero si el dato se ha insertado correctamente, falso en caso contrario
	 */
	public boolean insertar(T dato){
	    boolean resultado=true;
	    if (vacio()) {
	        datoRaiz = dato;
			esVacio = false;
		}
	    else {
	        if (!(this.datoRaiz.equals(dato))) {
	            Arbol<T> aux;
	            if (dato.compareTo(this.datoRaiz)<0) { //dato < datoRaiz
	                if ((aux=getHijoIzq())==null)
	                    hIzq = aux = new Arbol<T>();
	            }
	            else {									//dato > datoRaiz
	                if ((aux=getHijoDer())==null)
	                    hDer = aux = new Arbol<T>();
	            }
	            resultado = aux.insertar(dato);
	        }
	        else
	            resultado=false;
	    }
	    return resultado;
	}
	
	/**
	 * Comprueba si un dato se encuentra almacenado en el arbol
	 *
	 * @param dato El dato a buscar
	 * @return verdadero si el dato se encuentra en el arbol, falso en caso contrario
	 */
	public boolean pertenece(T dato){
	    Arbol<T> aux=null;
	    boolean encontrado=false;
	    if (!vacio()) {
	        if (this.datoRaiz.equals(dato))
	            encontrado = true;
	        else {
	            if (dato.compareTo(this.datoRaiz)<0)	//dato < datoRaiz
	                aux=getHijoIzq();
	            else									//dato > datoRaiz
	                aux = getHijoDer();
	            if (aux!=null)
	                encontrado = aux.pertenece(dato);
	        }
	    }
	    return encontrado;
	}
	
	
	/**
	 * Dado un dato del mismo tipo comprueba si esta almacenado en el arbol
	 * @param dato: Dato que deseamos buscar en el arbol
	 * @return devuelve el dato si este se encuentra en el arbol, si no devolvera NULL.
	 */
	public T getDato(T dato){
		
	    Arbol<T> aux=null;
	    T  encontrado=null;
	    if (!vacio()) {
	        if (this.datoRaiz.equals(dato))
	            encontrado = this.datoRaiz;
	        else {
	            if (dato.compareTo(this.datoRaiz)<0)	//dato < datoRaiz
	                aux=getHijoIzq();
	            else									//dato > datoRaiz
	                aux = getHijoDer();
	            if (aux!=null)
	                encontrado = aux.getDato(dato);
	        }
	    }
	    return encontrado;
	}
	
	
	/**
	 * Borrar un dato del arbol.
	 *
	 * @param dato El dato que se quiere borrar
	 */
	public void borrar(T dato){
	    if (!vacio()) {
	        if (dato.compareTo(this.datoRaiz)<0){			//dato<datoRaiz
	        		if (hIzq!=null)
					hIzq = hIzq.borrarOrden(dato);
			}	
	        else
	            if (dato.compareTo(this.datoRaiz)>0) {		//dato>datoRaiz 
	            		if (hDer!=null)
	            		hDer = hDer.borrarOrden(dato);
				}
	            else { //En este caso el dato es datoRaiz
	            
	                if (hIzq==null && hDer==null){
	                
	                    esVacio=true;
	                }
	                else
	                    borrarOrden(dato);
	            }
	    }
	}
	
	
	/**
	 * Recorrido inOrden del arbol
	 * @return String con los elementos del arbol
	 */
	public String inOrden(){
	    Arbol<T> aux=null;
	    if (!vacio()) {
	        if ((aux=getHijoIzq())!=null) {
	            aux.inOrden();
	        }    
	      
	        System.out.print(datoRaiz.toString());
	        
			try {
				Mapa.getMapa().getFlujo().write(datoRaiz.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        if ((aux=getHijoDer())!=null){
	            aux.inOrden();
	        }    
	    }
	    return "";
	}
	
	
	/**
	 * Recorrido preOrden del arbol
	 */
	public void preOrden(){
	    Arbol<T> aux=null;
	    if (!vacio()) {
	    	System.out.println(this.datoRaiz);
	    	
	        if ((aux=getHijoIzq())!=null) {
	            aux.preOrden();
	        }    
	        
	        if ((aux=getHijoDer())!=null){
	            aux.preOrden();
	        }    
	    }
	}
	
	
	/**
	 * Recorrido postOrden del arbol
	 */
	public void postOrden(){
	    Arbol<T> aux=null;
	    if (!vacio()) {
	    	
	        if ((aux=getHijoIzq())!=null) {
	            aux.postOrden();
	        }    
	        
	        if ((aux=getHijoDer())!=null){
	            aux.postOrden();
	        }    
	        
	        System.out.println(this.datoRaiz + " ");
	    }
	}
	
	
	/**
	 * Metodo que nos permite saber si un nodo es hoja o no
	 * @return boolean TRUE si es hoja, FALSE si no lo es
	 */
	public boolean esHoja(){
		if (this.hDer==null && this.hIzq==null){
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Metodo publico que llama a numHojas(this)
	 * @return Devuelve el numero de Hojas del Arbol
	 */
	public int numHojas(){
		return numHojas(this);
	}
	
	
	/**
	 * Metodo publico que llama a profundidadAbb(this,1)
	 * @return devuelve la profundidad o la altura del arbol.
	 */
	
	public int profundidadAbb(){
		if (!this.vacio()){
			return profundidadAbb(this,1);
		}
		return 0;
	}
	
	
	
	
/**GET'S y SET'S de la clase arbol */

	
	/**
	 * Devuelve el hijo izquierdo del árbol
	 *
	 * @return El hijo izquierdo
	 */
	public Arbol<T> getHijoIzq(){
		return hIzq;
	}
	
	
	/**
	 * Devuelve el hijo derecho del árbol
	 *
	 * @return Hijo derecho del árbol
	 */
	public Arbol<T> getHijoDer(){
		return hDer;
	}
	
	/**
	 * Devuelve la raíz del árbol
	 *
	 * @return La raíz del árbol
	 */
	public T getRaiz(){
		return datoRaiz;
	}
}

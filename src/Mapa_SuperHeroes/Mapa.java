package Mapa_SuperHeroes;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import Estructuras.Grafo;
import Personajes.*;
import Puerta.*;
import Utilidades.GenAleatorios;
import Utilidades.OrdenarSalas;
import Utilidades.Pared;

/**
 * PROYECTO DP 17/18
 * Clase Mapa
 * @author
 * <b> Blanco Mangut, Alvaro </b><br>
 * Ablancoze@alumnos.unex.es <br>
 */
public class Mapa {
	
	/**
	 * Puntero necesario para la instanciacion del singelton de la clase Mapa
	 */
	private static Mapa mapa;
	
	
/**Atributos de la clase mapa*/	
	
	/**
	 * Dimension X del tablero, que indica el numero de filas de la matriz
	 */
	private int dimX;
	
	/**
	 * Flujo de salida al fichero
	 */
	private static BufferedWriter bufferOff;
	
	/**
	 * Dimension Y del tablero, que indica el numero de columnas de la matriz
	 */
	private int dimY;
	
	/**
	 * Indica la altura de apertura del portal que genera el hombre puerta
	 */
	private int alturaApertura;
	
	/**
	 * Indica la sala en la que se encuentra el hombre puerta
	 */
	private int salaHombrePuerta;
	
	/**
	 * Matriz que contiene las salas
	 */
	private Sala[][] tablero;
	
	/**
	 * Sal que guarda a los personajes que pasa al HombrePuerta
	 */
	private Sala salaGanadores;
	
	/**
	 * Estructura lineal que guarda las paredes del mapa.
	 */
	private ArrayList<Pared> paredes;
	
	/**
	 * Estructura de tipo grafo que guarda las conexiones entre las sala.
	 */
	private Grafo grafoMapa;
	
	/**
	 * Atributo que gurda las direcciones que un personaje puede tomar
	 * @author Blanco Mangut, Alvaro
	 * ablancoze@alumnos.unex.es
	 */
	public enum Dir {S, E, N, O};
	
	/**
	 * 
	 */
	public int turno;
	
	
/**Singelton clase mapa */
	
	
	/**
	 * Constructor privado parametrizado de la clase Mapa
	 * @param salaHombrePuerta Indica la sala en la que se encontrara el HombrePuerta
	 * @param dimX Numero de filas del mapa
	 * @param dimY Numero de columnas del mapa
	 * @param alturaApertura Altura de apertura del portal de hombre puerta
	 * @throws IOException 
	 */
	private Mapa(int salaHombrePuerta,int dimX, int dimY, int alturaApertura) throws IOException{
		this.salaHombrePuerta=salaHombrePuerta;
		this.dimX=dimX;
		this.dimY=dimY;
		this.alturaApertura=alturaApertura;
		tablero=new Sala[dimX][dimY];
		this.paredes=new ArrayList<Pared>();
		grafoMapa=new Grafo();
		salaGanadores=new Sala(1111);
		bufferOff = new BufferedWriter(new FileWriter("FicheroSalida.log"));//creaci贸n del filtro asociado al flujo de datos
		crearTablero();
		crearParedes();
		crearGrafo();
		kruskal();
		pintarMapa();
		atajos();
		pintarMapa();
		configurarMapa();
		//pintarRutas();
	}
	
	
	/**
	 * Constructor privado por defecto necesario para el singelton
	 */
	private Mapa(){
		
	}
	

	/**
	 * Metodo que devuelve la instancia del mapa. Si esta instancia no se encuentra creada la crea
	 * Necesario para la implementacion del Singelton.
	 * @param salaHombrePuerta Indica la sala en la que se encontrara el HombrePuerta
	 * @param dimX Numero de filas del mapa
	 * @param dimY Numero de columnas del mapa
	 * @param alturaApertura Numero de columnas del mapa
	 * @return Devuelve la clase mapa.
	 * @throws IOException 
	 */
	public static Mapa getMapa(int salaHombrePuerta,int dimX, int dimY, int alturaApertura) throws IOException{
		if(mapa==null){
			mapa=new Mapa(salaHombrePuerta,dimX,dimY,alturaApertura);
		}
		return mapa;
	}
	
	
	/**
	 * Metodo que devuelve la instancia del mapa
	 * @return Devuelve la clase mapa
	 */
	public static Mapa getMapa(){
		if(mapa==null){
			mapa=new Mapa();
		}
		return mapa;
	}
	
	
	
	
/**Metodos privados de la clase mapa */
	
	
	/**
	 * Crea las salas del mapa
	 */
	private void crearTablero(){
		
		for (int i = 0; i < dimX; i++) {
			
			for (int j = 0; j < dimY; j++) {
	
				tablero[i][j]=new Sala(this.indentificaSala(i, j, dimY));
				
			}
		}
	}
	
	
	/**
	 * Genera el grafo a partir del numero de salas del mapa
	 */
	private void crearGrafo(){
		
		for (int i = 0; i < dimX*dimY; i++) {
			if(!grafoMapa.nuevoNodo(i)){
				System.out.println("Error al crear el nodo: "+i);
			}
		}
	}
	
	
	/**
	 * Metodo que nos permite configurar el mapa
	 */
	private void configurarMapa() {
		// Creaci贸n de las armas para el hombre puerta
		Arma [] armasPuerta = {new Arma("CampoEnergia", 5), new Arma("Armadura",13), new 
                Arma("Anillo",11), new Arma("Acido",1), new Arma("Antorcha",5), new 
                Arma("Bola",3), new Arma("Baston",22), new Arma("CadenaFuego",11), new 
                Arma("Espada",11), new Arma("Cetro",20), new Arma("Capa",10), new 
                Arma("CampoMagnetico",5), new Arma("Escudo",3), new Arma("Garra",22), new  
                Arma("Flecha",12), new Arma("Gema",4)};


		// Creaci贸n del hombre puerta y configuraci贸n
		HombrePuerta doorMan = new HombrePuerta(this.alturaApertura);
		// Configurar el hombre puerta introduciendo la combinaci贸n de armas
		doorMan.configurar(armasPuerta);

		// Cerrar el portal, por si inicialmente est谩 abierto
		doorMan.cerrar();
		// A帽adir el hombre puerta al mapa 
		insertarHombrePuerta(doorMan);  



		// Creaci贸n de las armas para repartir en salas
		Arma [] armasSalas = {new Arma("Mjolnir",29),
				new Arma("Anillo",1),
				new Arma("Garra",27), 
				new Arma("Armadura",3),
				new Arma("Red",25),
				
				new Arma("Escudo",5), 
				new Arma("Lucille",23),
				new Arma("Lawgiver",7),
				new Arma("GuanteInfinito",21), 
				new Arma("LazoVerdad",9),
				
				new Arma("CadenaFuego",19),
				new Arma("Capa",11), 
				new Arma("Flecha",17),
				new Arma("Tridente",13),
				new Arma("Antorcha",15), 
				
				new Arma("Baston",28),
				new Arma("Latigo",2),
				new Arma("MazaOro",26), 
				new Arma("CampoMagnetico",4),
				new Arma("Tentaculo",24), 
				
				new Arma ("CampoEnergia",6),
				new Arma("Cetro",22),
				new Arma("RayoEnergia",8), 
				new Arma("Laser",20),
				new Arma("Bola",10),
				
				new Arma("Espada",18), 
				new Arma("Sable",12), 
				new Arma("Acido",16),
				new Arma("Gema",14), 
				new Arma("Nullifier",23),
				
				new Arma("Mjolnir",1),
				new Arma("Anillo",29), 
				new Arma("Garra",3),
				new Arma("Armadura",27), 
				new Arma("Red",5), 
				
				new Arma("Lawgiver",23),
				new Arma("Lucille",7),
				new Arma("Escudo",25),
				new Arma("GuanteInfinito",9),
				new Arma("LazoVerdad",21), 
				
				new Arma("CadenaFuego",11),
				new Arma("Capa",19),
				new Arma("Flecha",13), 
				new Arma("Tridente",17),
				new Arma("Antorcha",28),
				
				new Arma("Baston",15), 
				new Arma("Latigo",26),
				new Arma("MazaOro",2),
				new Arma("CampoMagnetico",24), 
				new Arma("Tentaculo",4),
				
				new Arma("CampoEnergia",22),
				new Arma("Cetro",6), 
				new Arma("RayoEnergia",20),
				new Arma("Laser",8),
				new Arma("Bola",18), 
				
				new Arma("Espada",10),
				new Arma("Sable",16),
				new Arma("Acido",12), 
				new Arma("Gema",1),
				new Arma("Nullifier",3)};


		LinkedList<Integer>visitados=new LinkedList<Integer>();
		LinkedList<Sala>recorrido=new LinkedList<Sala>();
		LinkedList<LinkedList<Sala>>todosLosCaminos=new LinkedList<LinkedList<Sala>>();
		LinkedList<LinkedList<Integer>>todosLosCaminos2=new LinkedList<LinkedList<Integer>>();
		
		obtenerTodosLosCaminos(0, this.salaHombrePuerta, visitados, recorrido, todosLosCaminos);
		obtenerFrecuencia(0, this.salaHombrePuerta, visitados, recorrido,todosLosCaminos2);
	//	LinkedList<Sala>salasOrdenadas=new LinkedList<Sala>();
		
		//obtenerFrecuencia(salasOrdenadas,todosLosCaminos);//Nos devuelve el numero de caminos que hay entre la sala origen y la final
		//distribuirArmas(salasOrdenadas,armasSalas);
		distribuirArmas(recorrido,armasSalas);
		
	}
	
	/**
	 * Metodo que nos permite distribuir las armas por las salas de mayor frecuencia
	 * @param salas Lista con las salas ordenas, de mayor a menor, por su frecuencia
	 * @param armasSalas Array con las armas que se van a distribuir.
	 */
	private void distribuirArmas(LinkedList<Sala> salas,Arma [] armasSalas) {
		int aux=0;
		for (int i = 0; i<12 && !salas.isEmpty(); i++) {
			for (int j = aux; salas.getFirst().tamanoArmas() < 5; j++) {
				
				salas.getFirst().insertarArma(armasSalas[j]);
			}
			salas.removeFirst();
			aux=aux+5;	
		}
	}


	/**
	 * Metodo que genera las paredes del tablero.
	 * El orden para guardar las paredes sera el N, E, S, O 
	 */
	private void crearParedes() {
		Integer identificadorSalaActual;
		Integer identificadorSalaN;
		Integer identificadorSalaE;
		Integer identificadorSalaS;
		Integer identificadorSalaO;
		
		for (int fila = 0; fila < dimX; fila++) {		//Recorro fila
			for (int columna = 0; columna < dimY; columna++) {	//Recorro columnas
				
				identificadorSalaActual=indentificaSala(fila, columna, dimY);//Identificamos la sala en la que nos encontramos actualmente.
				
				if (fila-1>=0){//Comprobamos que exista una sala en el NORTE
					identificadorSalaN=indentificaSala(fila-1, columna, dimY);//Identificamos la sala que esta al NORTE
					Pared ActualNorte=new Pared(identificadorSalaActual,identificadorSalaN);//Generamos el PAR formado por los identificadores de las salas.
					paredes.add(ActualNorte);//Insertamos el en la estructura PAREDES.
				}
				
				if(columna+1<dimY){//Comprobamos que exista una sala en el ESTE
					identificadorSalaE=indentificaSala(fila, columna+1, dimY);//Identificamos la sala que esta al ESTE
					Pared ActualEste=new Pared(identificadorSalaActual,identificadorSalaE);//Generamos el PAR formado por los identificadores de las salas.
					paredes.add(ActualEste);//Insertamos el en la estructura PAREDES.
				}
				
				if(fila+1<dimX){//Comprobamos que exista una sala en el SUR
					identificadorSalaS=indentificaSala(fila+1, columna, dimY);//Identificamos la sala que esta al SUR
					Pared ActualSur=new Pared(identificadorSalaActual,identificadorSalaS);//Generamos el PAR formado por los identificadores de las salas.
					paredes.add(ActualSur);//Insertamos el en la estructura PAREDES.
				}
				
				if(columna-1>=0){//Comprobamos que exista una sala en el OESTE
					identificadorSalaO=indentificaSala(fila, columna-1, dimY);//Identificamos la sala que esta al OESTE
					Pared ActualOste=new Pared(identificadorSalaActual,identificadorSalaO);//Generamos el PAR formado por los identificadores de las salas.
					paredes.add(ActualOste);//Insertamos el en la estructura PAREDES.
				}
			}
		}
	}
	
	
	/**
	 * Distribuye las armas por las salas
	 * @param idSalasConArmas: Vector que contiene los identificadores de las salas en las que se distribuyen las armas
	 * @param armasSalas: Vector que contiene las armas que se distribuiran por las salas
	 */
	private int obtenerFrecuencia(Integer origen, int destino, LinkedList<Integer>visitados,LinkedList<Sala>recorrido,LinkedList<LinkedList<Integer>>todosLosCaminos) {
		Integer vertice=0;//Nodo vertice al nodo en el que estamos
		int bandera = 0;//Bandera booleana
		Integer caminosHastaAhora=0;//Entero que nos indica el numero de caminos validos hasta ahora

		TreeSet<Integer> ady= new TreeSet<Integer>();//creamos un treeSet para guardar los adyacentes del nodo con el que estamos trabajando
		Sala aux=getSala(origen);
		
		if(origen!=destino){//Si el origen es igual al destino el camino que se ha llevado a cabo es valido, si no lo es seguimos explorando caminos

			visitados.add(origen);//A馻dimos el nodo con el que estamos trabajando al visitados

			this.grafoMapa.adyacentes(origen, ady);//Cogemos los adyacentes del nodo que estamos tratando

			while(!ady.isEmpty()){//Mientras haya nodos por explorar y no hayamos llegado al final

				vertice=ady.first();//Igualo el nodo axuliar al primer adyancente

				ady.remove(vertice);//Elimino el adyacente que vamos a tratar
				
				if(!visitados.contains(vertice)){
					bandera=obtenerFrecuencia(vertice, destino,visitados,recorrido,todosLosCaminos);
					if(bandera>0){//Quiere decir que el camino es correcto
						
						aux.setFrecuencia(aux.getFrecuencia()+bandera);
						if(!recorrido.contains(aux)){
							recorrido.add(aux);
						}
						caminosHastaAhora=caminosHastaAhora+bandera;
					}
				}
			}
			visitados.remove(origen);
			return caminosHastaAhora;
		}
		aux.frecuenciaMas();
		
		visitados.add(origen);
		LinkedList<Integer> auxiliar=new LinkedList<Integer> (visitados);
		todosLosCaminos.add(auxiliar);
		visitados.remove(origen);
		
		if(!recorrido.contains(aux)){
			recorrido.add(aux);
		}
		
		bandera++;

		return bandera;
	}
	
	public void obtenerTodosLosCaminos(Integer origen, int destino, LinkedList<Integer>visitados,LinkedList<Sala>recorrido,LinkedList<LinkedList<Sala>>todosLosCaminos){
		
		Integer vertice=0;//Nodo vertice al nodo en el que estamos
		
		TreeSet<Integer> ady= new TreeSet<Integer>();//creamos un treeSet para guardar los adyacentes del nodo con el que estamos trabajando
		Sala aux=getSala(origen);
		
		recorrido.add(aux);
		
		if(origen!=destino){//Si el origen es igual al destino el camino que se ha llevado a cabo es valido, si no lo es seguimos explorando caminos

			visitados.add(origen);//A锟絘dimos el nodo con el que estamos trabajando al visitados

			this.grafoMapa.adyacentes(origen, ady);//Cogemos los adyacentes del nodo que estamos tratando

			while(!ady.isEmpty()){//Mientras haya nodos por explorar y no hayamos llegado al final

				vertice=ady.first();//Igualo el nodo axuliar al primer adyancente

				ady.remove(vertice);//Elimino el adyacente que vamos a tratar
				
				if(!visitados.contains(vertice)){
					obtenerTodosLosCaminos(vertice, destino,visitados,recorrido,todosLosCaminos);
				}
			}
			visitados.remove(origen);
			recorrido.remove(aux);//si me quedo sin adyacentes y no he llegado al nodo final es porque entonces el camino hasta ahora no es valido.
			
		}else{
			LinkedList<Sala>recorridoValido=new LinkedList<Sala>(recorrido);
			recorrido.remove(aux);
			todosLosCaminos.add(recorridoValido);//Cuando llegue a la sala objetivo entonces el camino sera correcto, por lo tanto lo guardo.
		}
	}
	
	private void obtenerFrecuencia(LinkedList<Sala> salas,LinkedList<LinkedList<Sala>>todosLosCaminos){
		
		for (int i = 0; i < todosLosCaminos.size();i++) {
			for (int j = 0; j < todosLosCaminos.get(i).size(); j++) {
				todosLosCaminos.get(i).get(j).frecuenciaMas();
			}
		}
		
		for (int i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				salas.add(tablero[i][j]);
			}
		}
		Collections.sort(salas, Collections.reverseOrder(new OrdenarSalas()));
	}
	
	
	/**
	 * Inserta al hombre puerta en una sala del mapa
	 * @param doorMan Instancia del hombre puerta.
	 */
	private void insertarHombrePuerta(HombrePuerta hp) {
	
		int fila=identificarFila(salaHombrePuerta, dimY);
		
		int columna=identificarColumna(salaHombrePuerta, dimY);
		
		tablero[fila][columna].setHombrePuerta(hp);
	}
	
	
	/**
	 * Muestra la informacion del mapa: Personajes, Armas, Hombre Puerta
	 * @param turno Nos indica el turno que vamos a pintar
	 */
	private void pintar(int turno) {
		int fila;
		int columna;
		String str;
		
		System.out.println("(turn:"+turno+")");
		
		str="(turn:"+turno+")"+"\n";//Escribimos en el fichero de salida
		try {
			bufferOff.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("(map:"+salaHombrePuerta+")");
		
		str="(map:"+salaHombrePuerta+")"+"\n";//Escribimos en el fichero de salida
		try {
			bufferOff.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tablero[dimX-1][dimY-1].getHombrePuerta().toString();
		pintarMapa();
		
		for (int i = 0; i < dimX*dimY; i++) {//Pintamos las salas que tienen armas
			fila=identificarFila(i, dimY);
			columna=identificarColumna(i, dimY);
			
			if(!tablero[fila][columna].armasVacia()){
				tablero[fila][columna].toString();
			}
			
		}
		
		for (int j = 0; j < dimX*dimY; j++) {//Pintamos la informacion de los personajes
			fila=identificarFila(j, dimY);
			columna=identificarColumna(j, dimY);
			
			if(!tablero[fila][columna].personajesVacia()){
				tablero[fila][columna].mostrarPersonajesSala();
			}
		}
		
		if(!salaGanadores.personajesVacia()){
			System.out.println("(teseractomembers)");
			
			str="(teseractomembers)"+"\n";//Escribimos en el fichero de salida
			try {
				bufferOff.write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.print("(owneroftheworld:");
			
			str="(owneroftheworld:";//Escribimos en el fichero de salida
			try {
				bufferOff.write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			salaGanadores.primerPersonaje().toString();
			salaGanadores.mostrarPersonajesGanadores();
		}
	}


	/**
	 * Pinta el estado actual del mapa. 
	 */
	private void pintarMapa() {
		boolean paredE;
		boolean paredS;
		int identificadorSalaActual;
		int identificadorSalaEste;
		int identificadorSalaSur;
		String str;
		
		System.out.println();//Saltamos de linea para que se muestre siempre correctamente.
		
		str="\n";//Escribimos en el fichero de salida
		try {
			bufferOff.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < dimY; i++) { //Pintamos las paredes superiores del tablero
			System.out.print(" _");
			
			str=" _";
			try {
				bufferOff.write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println();
		str="\n";
		try {
			bufferOff.write(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int fila = 0; fila < dimX; fila++) { //Pintamos el interior del tablero

			System.out.print("|");		//Pintamos las paredes del oeste del tablero
			
			str="|";
			try {
				bufferOff.write(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			for (int columna = 0; columna < dimY ; columna++) {

				identificadorSalaActual=indentificaSala(fila, columna, dimY); //Identificamos la sala en la que estamos.
				
				if(columna==dimY-1 && fila==dimX-1) {//Si nos encontramos en la ultima sala, siempre habra pared al ESTE y al SUR
					paredE=true;
					paredS=true;
					tablero[fila][columna].pintarSala(paredE,paredS);	//Pintamos la sala
					
				}else{
					
					if(columna==dimY-1 && fila<dimX-1) {//Si nos encontramos en la ultima columna, siempre habra pared al ESTE
						
						identificadorSalaSur=indentificaSala(fila+1, columna, dimY); //Identificamos la sala que esta al SUR.
						
						paredE=true;
						
						if(grafoMapa.adyacente(identificadorSalaActual, identificadorSalaSur))//Vemos si esa sala tiene pared en el SUR
							paredS=false;
						else
							paredS=true;
						tablero[fila][columna].pintarSala(paredE,paredS); //Pintamos la sala
						
					}else{
						
						if (fila==dimX-1 && columna<dimY-1) {//Si nos encontramos en la ultima fila, siempre habra pared al SUR
							
							identificadorSalaEste=indentificaSala(fila, columna+1, dimY); //Identificamos la sala que esta al ESTE.
							
							if(grafoMapa.adyacente(identificadorSalaActual, identificadorSalaEste))//Vemos si esa sala tiene pared en el ESTE
								paredE=false;
							else
								paredE=true;
							
							paredS=true;
							tablero[fila][columna].pintarSala(paredE,paredS);	//Pintamos la sala
							
						}else{
							
							identificadorSalaEste=indentificaSala(fila, columna+1, dimY); //Identificamos la sala que esta al ESTE.
							identificadorSalaSur=indentificaSala(fila+1, columna, dimY); //Identificamos la sala que esta al SUR.
							
							if(grafoMapa.adyacente(identificadorSalaActual, identificadorSalaEste))//Vemos si esa sala tiene pared en el ESTE
								paredE=false;
							else
								paredE=true;
							
							if(grafoMapa.adyacente(identificadorSalaActual, identificadorSalaSur))//Vemos si esa sala tiene pared en el SUR
								paredS=false;
							else
								paredS=true;
							tablero[fila][columna].pintarSala(paredE,paredS);	//Pintamos la sala
							
						}
						
					}
					
				}

			}
			System.out.println();
			
			str="\n";//Escribimos en el fichero de salida
			try {
				bufferOff.write(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Metodo que nos permite extender la marca de una sala a otra
	 * @param IdSala int que indica el indentificador de la sala que se va a extender.
	 * @param IdSala int que indica el indentificador de la sala que va a perder su id.
	 */
	private void extenderMarcaSala(int marcaSala1,int marcaSala2){
		
		Sala aux;
		for (int i = 0; i < dimX*dimY; i++) { //Recorro el tablero
			
			aux=tablero[identificarFila(i,dimY)][identificarColumna(i, dimY)];//Identifico la sala en la que nos encontramos
			
			if(aux.getMarca()==marcaSala2){//Compruebo si la marca de la sala es igual a la marca de la sala que quiero modificar.
				aux.setMarca(marcaSala1);	//Si se cumple, modifico la marca
			}
		}
	}
	
	
	/**
	 * Metodo que se utiliza al inicializar el mapa, para crear el tablero y derribar las paredes
	 */
	private void kruskal() {
		int random;
		Pared aux;
		Sala s1;
		Sala s2;
		while(!paredes.isEmpty()){//Mientras queden paredes por derribar
			random=GenAleatorios.generarNumero(paredes.size());//Genero una sala aleatoria entre las posibles que aun quedan por tirar
			aux=paredes.get(random);

			s1=tablero[identificarFila(aux.getPrimero(), dimY)][identificarColumna(aux.getPrimero(), dimY)];//Los punteros nos dan las salas que queremos derribar la pared
			s2=tablero[identificarFila(aux.getSegundo(), dimY)][identificarColumna(aux.getSegundo(), dimY)];
			
			if (s1.getMarca()!=s2.getMarca()){//si las marcas son distintas, significa que las podemos derribar.
				
				if(grafoMapa.nuevoArco(aux.getPrimero(),aux.getSegundo(), s1.getIdentificador()+s2.getIdentificador()) 
						&& 
				   grafoMapa.nuevoArco(aux.getSegundo(),aux.getPrimero(), s1.getIdentificador()+s2.getIdentificador())){//Creamos los nuevos arcos para el grafo
				}
				
				extenderMarcaSala(s1.getMarca(),s2.getMarca());//Extendemos la marca
			}
			
			paredes.remove(random);//Eliminamos la pared del conjunto
		}
		
		grafoMapa.warshall(); // actualizamos la matriz de warshall
		grafoMapa.floyd(); // actualizamos la matriz de floyd
	}
	
	/**
	 * Algoritmo que genera un numero N de atajos en el mapa. N=5% de salas del mapa
	 */
	private void atajos(){
		int n=(int) ((dimX*dimY)*0.05);
		int i=0;
		int random;
		int fila=0;
		int columna=0;
		int destino=0;
		boolean tirado;
		while(i<n){
			tirado=false;
			random=GenAleatorios.generarNumero((dimX*dimY));
			fila=identificarFila(random, dimY);
			columna=identificarColumna(random, dimY);

			if (fila>0 && i<n){//Compruebo los limites del mapa y que no se hayan tirado paredes por valor del 5% de la salas del mapa
				destino=indentificaSala(fila-1, columna, dimY);

				if (!grafoMapa.adyacente(random, destino)){//Si no son adyacentes

					if (grafoMapa.costeCamino(random, destino)>3){//Si coste del camino de una sala a otra es mayor de 3
						grafoMapa.nuevoArco(random, destino, tablero[random][destino].getIdentificador()+tablero[destino][random].getIdentificador());
						grafoMapa.nuevoArco(destino, random, tablero[random][destino].getIdentificador()+tablero[destino][random].getIdentificador());
						i++;
						tirado=true;
					}
				}
			}

			if (fila<dimX-1 && i<n && !tirado){//Compruebo los limites del mapa y que no se hayan tirado paredes por valor del 5% de la salas del mapa
				destino=indentificaSala(fila+1, columna, dimY);

				if (!grafoMapa.adyacente(random, destino)){//Si no son adyacentes

					if (grafoMapa.costeCamino(random, destino)>3){//Si coste del camino de una sala a otra es mayor de 3
						grafoMapa.nuevoArco(random, destino, 1);
						grafoMapa.nuevoArco(destino, random, 1);
						i++;
						tirado=true;
					}
				}
			}

			if (columna>0 && i<n && !tirado){//Compruebo los limites del mapa y que no se hayan tirado paredes por valor del 5% de la salas del mapa
				destino=indentificaSala(fila, columna-1, dimY);

				if (!grafoMapa.adyacente(random, destino)){//Si no son adyacentes

					if (grafoMapa.costeCamino(random, destino)>3){//Si coste del camino de una sala a otra es mayor de 3
						grafoMapa.nuevoArco(random, destino, 1);
						grafoMapa.nuevoArco(destino, random, 1);
						i++;
						tirado=true;
					}
				}
			}

			if (columna<dimY-1 && i<n && !tirado){
				destino=indentificaSala(fila, columna+1, dimY);

				if (!grafoMapa.adyacente(random, destino)){//Si no son adyacentes

					if (grafoMapa.costeCamino(random, destino)>3){//Si coste del camino de una sala a otra es mayor de 3
						grafoMapa.nuevoArco(random, destino, 1);
						grafoMapa.nuevoArco(destino, random, 1);
						i++;
						tirado=true;
					}
				}
			}


		}
		this.grafoMapa.floyd();
		this.grafoMapa.warshall();
	}
	

/**Metodos publicos de la clase mapa */
	
	
	/**
	 * Inserta a un personaje en una sala del mapa
	 * @param p: Personaje que vamos a insertar
	 */
	public void insertarPersonaje(Personaje p) {
	
		int fila=identificarFila(p.getSala(), dimY);
		int columna=identificarColumna(p.getSala(), dimY);
		
		tablero[fila][columna].insertarPersonaje(p);
	}
	
	
	/**
	 * Inserta a un personaje en una sala del mapa
	 * @param p: Personaje que vamos a insertar
	 */
	public void insertarPersonajeGanador(Personaje p) {
		salaGanadores.insertarPersonaje(p);
	}
	
	
	/**
	 * Metodo que nos permite iniciar la simulacion del juego
	 * @param maxTurnos Numero de turnos que tendra la simulacion
	 * @return boolean TRUE si hay un ganador, FALSE si no lo hay
	 */
	public boolean simulacion(int maxTurnos) {
		int turno=0;
		while(turno<=maxTurnos){
			setTurno(turno);
			if (turno>0){
				
				for (int i = 0; i < dimX; i++) {
					for (int j = 0; j < dimY; j++) {
						
						if (tablero[i][j].simulacion(turno)){//Si se gana en la simulacion, el juego termina
							pintar(turno);
							try {
								bufferOff.flush();
								bufferOff.close();
							} catch (Exception e) {
								System.out.println("Error al cerrar el flujo");
							}
							return true;
						}	
					}
				}
			}
			pintar(turno);
			turno++;
		}
		return false;
	}
	

	
	
	
	/**Metodos utiles de la clase mapa */
		
		
		/**
		 * Algoritmo que permite identificar una sala segun los parametros dados
		 * @param fila indica el numero de la fila
		 * @param columna indica el numero de la columna
		 * @param dimY indica el numero de columnas totales del tablero
		 * @return int Numero que indica el identificador de la sala
		 */
		public int indentificaSala(int fila, int columna, int dimY) {
			return (columna+(fila*dimY));
		}
		
		
		/**
		 * Permite obtener la fila en la que se encuentra la sala
		 * @param identificador int: Indica el identificador de la sala
		 * @param dimY int: Numero de columnas del tabler
		 * @return int: Fila en la que se encuentra la sala
		 */
		public int identificarFila(int identificador,int dimY) {
			return identificador/dimY;
		}
		
		
		/**
		 * Permite obtener la columna en la que se encuentra la sala
		 * @param identificador int: Indica el identificador de la sala
		 * @param dimY int: Numero de columnas del tabler
		 * @return int: Columna en la que se encuentra la sala
		 */
		public int identificarColumna(int identificador,int dimY) {
			return identificador%dimY;
		}
	
		
		/**
		 * Metodo que nos da una sala segun su identificador
		 * @param sala identificador de la sala que quemos obtener
		 * @return: Sala instancia de Sala
		 */
		public Sala getSala(int sala) {
			if (sala==0){
				return tablero[0][0];
			}else{
				return tablero[identificarFila(sala, dimY)][identificarColumna(sala, dimY)];
			}
		}
	
		
		/**
		 * Metodo que nos permite comprobar si un movimiento de un personaje es correcto
		 * @param salaInicio sala de la que quiere salir el personaje
		 * @param salaFin sala a la que quiere ir el personaje
		 * @return: boolean TRUE si el movimiento se puede realizar, FALSE si el movimiento no se puede realizar
		 */
		public boolean comprobarMovimiento(int salaInicio, int salaFin) {
			return this.grafoMapa.adyacente(salaInicio, salaFin);
			
		}
		
		
		/**
		 * Metodo que nos da el identificador de la sala que se encuentra en la esquina superior derecha
		 * @return int: identificador de la sala
		 */
		public int getCornerNorEste() {
			return dimY-1;
		}
	
		
		/**
		 * Metodo que nos da el identificador de la sala que se encuentra en la esquina inferior izquierda
		 * @return int: identificador de la sala
		 */
		public int getCornerSurOeste() {
			
			return indentificaSala(dimX-1, 0, dimY);
		}
		
		
		
		
/**GET'S y SET'S de la clase mapa */
	
	
	/**
	 * Metodo que nos da el numero de filas del tablero
	 * @return int numero de filas
	 */
	public int getFila(){
		return dimX;
	}
	
	
	/**
	 * Metodo que nos da el numero de columnas del tablero
	 * @return int numero de columnas
	 */
	public int getColumnas(){
		return dimY;
	}
	
	
	/**
	 * Metodo que nos da la altura de apertura del portal
	 * @return int altura a la que se abirara el portar
	 */
	public int getAlturaApertura(){
		return alturaApertura;
	}
	
	
	/**
	 * Metodo que nos da el identificador de la sala en la que se encuentra el hombre puerta
	 * @return int identificador de la sala en la que esta el hombre puerta
	 */
	public int getSalaHombrePuerta(){
		return salaHombrePuerta;
	}
	
	
	/**
	 * Metodo que devuelve el turno en el que nos encontramos
	 * @return
	 */
	public int getTurno(){
		return this.turno;
	}
	
	/**
	 * 
	 * @return
	 */
	public BufferedWriter getFlujo(){
		return bufferOff;
	}
	
	
	/**
	 * 
	 * @param turno
	 */
	public void setTurno(int turno){
		this.turno=turno;
	}
	
	/**
	 * 
	 * @param a
	 */
	public void setModificarArmaParaPruebaVillano(Arma a){
		tablero[1][3].setModificarArmaParaPruebaVillano(a);
	}

	/**
	 * 
	 * @param origen
	 * @param ady
	 */
	public void getAdyacentes(int origen, TreeSet<Integer> ady) {
		this.grafoMapa.adyacentes(origen, ady);
		
	}
	
	/**
	 * 
	 * @param origen
	 * @param destino
	 * @return
	 */
	public int costeCamino(Integer origen, Integer destino){
		return this.grafoMapa.costeCamino(origen, destino);
	}

	/**
	 * Metodo que devuelve el siguiente nodo entre el origen y el destino. 
	 * El nodo a devolver sera el mas cercano entre el orgien y el destino.
	 * @return
	 */
	public int siguienteNodo(int origen, int destino) {
		return this.grafoMapa.siguiente(origen, destino);
	}
}




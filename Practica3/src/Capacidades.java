import java.util.*;

public class Capacidades {
  private String[] arrayIndexString;
	/**
	Contructor vacio.
	*/
	public Capacidades(){}

  public Capacidades(String[] arrayIndexString){
  	this.arrayIndexString=arrayIndexString;
  }
		/**
		Metodo que crea una matriz triangular como representacion de una grafica
		con valores aleatorios y la regresa.
		@param n
		@return matriz.
		*/
	public int[][] creaGraficaMatriz(int n){
    int matriz[][] = new int[n][n];
    for (int x=0; x < matriz.length-1; x++) {
      for (int y=x; y < matriz[x].length; y++) {
        matriz[x][y] = (int) (Math.random()*99+1);
      }
    }
		imprimeMatriz(matriz);
    return matriz;
  }

  /**
  Se crea una arreglo de todos los nodos, recibiendo el numero de nodos
  @param n
  */
  public String[] creaArray(int n){
    Random rnd = new Random();
    String[] arrayIndexString = new String[n];
    for (int i = 1;i <= n-1;i++) {
      String prueba = Integer.toString(i);
      arrayIndexString[i] = prueba;
    }
    arrayIndexString[0] = "S";
    arrayIndexString[n-1] = "T";
    return arrayIndexString;
  }

	/**
	Metodo que recive una matriz y la imprime en consola
	@param Matriz
	*/
	public void imprimeMatriz(int[][] matriz){
		for (int x=0; x < matriz.length; x++) {
      System.out.print("|");
      for (int y=0; y < matriz[x].length; y++) {
        System.out.print (matriz[x][y]);
        if (y!=matriz[x].length-1) System.out.print("\t");
        }
        System.out.println("|");
      }
			System.out.println("\n");
	}

  /**
	Metodo de rutas escalables que regresa el maximo flujo de una red.
	@param matriz
	@param verticeS
	@param verticeT
	@param maxNodos
	*/
	public int capacidadesScalables(int matriz[][], int verticeS, int verticeT,int maxNodos){
		int maxCapa = 0;
		for(int i=0; i<matriz[verticeS].length; i++){
			maxCapa += matriz[verticeS][i];
		}
		int deltaLog = (int) (Math.log(maxCapa) / Math.log(2) );
		int deltaExp = (int) Math.pow(2, deltaLog);


		int maxFlujo = 0;
		int vecino[] = new int[maxNodos];
		int verticeU=0;
		int verticeV =0;

		int graficaResidual[][] = new int[maxNodos][maxNodos];
		for (verticeU = 0; verticeU < maxNodos; verticeU++){
			for (verticeV = 0; verticeV < maxNodos; verticeV++){
				graficaResidual[verticeU][verticeV] = matriz[verticeU][verticeV];
			}
		}

		while (bfs(graficaResidual, verticeS, verticeT, vecino,maxNodos)) {
			String camino = "";


			int maxFlujoRes = Integer.MAX_VALUE;
			for (verticeV=verticeT; verticeV != verticeS; verticeV=vecino[verticeV]) {
				verticeU = vecino[verticeV];
				maxFlujoRes = Math.min(maxFlujoRes, graficaResidual[verticeU][verticeV]);

				camino = " --> "+arrayIndexString[verticeV]+ camino;
			}
			camino= "S"+camino;


			if(maxFlujoRes < deltaExp){
				deltaExp /= 2;
				continue;
			}

			System.out.println("Ruta tomada\n"+camino);
      System.out.println("valor del flujo = "+maxFlujoRes+"\n");

			for (verticeV=verticeT; verticeV != verticeS; verticeV=vecino[verticeV]) {
				verticeU = vecino[verticeV];
				graficaResidual[verticeU][verticeV] -= maxFlujoRes;
				graficaResidual[verticeV][verticeU] += maxFlujoRes;
			}

      System.out.println("\n cambios de la matriz por iteracion");
  		imprimeMatriz(graficaResidual);

			maxFlujo += maxFlujoRes;
		}
    System.out.println("\n Matriz residual resultante");
		imprimeMatriz(graficaResidual);
		return maxFlujo;
	}

  /**
	Algoritmo que recorre la grafica en bfs.
	@param graficaResidual
	@param verticeS
	@param verticeT
	@param vecino
	@param maxNodos
	*/
  public boolean bfs(int graficaResidual[][], int verticeS, int verticeT, int vecino[],int maxNodos) {
		boolean verticeVis[] = new boolean[maxNodos];

		LinkedList<Integer> cola = new LinkedList<Integer>();
		cola.add(verticeS);
		verticeVis[verticeS] = true;
		vecino[verticeS]=-1;

		while (!cola.isEmpty()) {
			int verticeU = cola.remove();

			for (int verticeV=0; verticeV<maxNodos; verticeV++) {
				if (verticeVis[verticeV]==false && graficaResidual[verticeU][verticeV] > 0) {
					cola.add(verticeV);
					vecino[verticeV] = verticeU;
					verticeVis[verticeV] = true;
				}
			}
		}
		return verticeVis[verticeT];
	}

	/**
	Metodo main
	*/
	public static void main (String[] args) {
		System.out.println("Cuantos nodos deseas en la Grafica?");
		Scanner scan = new Scanner(System.in);
		int nodos = scan.nextInt();
    Capacidades indices = new Capacidades();
    String[] arrayIndexString = indices.creaArray(nodos);
		Capacidades m = new Capacidades(arrayIndexString);
		System.out.println("Matriz original");
		int graphMatrix[][] = m.creaGraficaMatriz(nodos);
		System.out.println("Nodos tomados por el algortimo");

		int verticeS = 0;
		int verticeT = nodos-1;
    System.out.println("\n Flujo Maximo: " + m.capacidadesScalables(graphMatrix, verticeS, verticeT,nodos));
	}

}

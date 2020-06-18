import java.util.*;

public class Etiquetamiento {
  private String[] arrayIndexString;
	/**
	Contructor vacio.
	*/
	public Etiquetamiento(){}

  public Etiquetamiento(String[] arrayIndexString){
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
  Se crea el algoritmo de etiquetacion
  @param
  @param graficaResidual
  @param verticeS
  @param verticeT
  @param vecino
  @param maxNodos
  */
  public boolean etiquetacion(int graficaResidual[][], int verticeS, int verticeT, int vecino[],int maxNodos) {
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
  Se manda a llamar de manera inversa el algoritmo
  ya que por parte de la implementacion representamos al if con un while.
  */
  public int aumenta(int matriz[][], int verticeS, int verticeT,int maxNodos) {
		int maxFlujo = 0;
		int vecino[] = new int[maxNodos];
		int verticeU=0;
		int verticeV =0;
    //se crea la matriz residual.
		int graficaResidual[][] = new int[maxNodos][maxNodos];
		for (verticeU = 0; verticeU < maxNodos; verticeU++){
			for (verticeV = 0; verticeV < maxNodos; verticeV++){
				graficaResidual[verticeU][verticeV] = matriz[verticeU][verticeV];
			}
		}
    //nuestra representacion del if en el algoritmo dado de aumenta.
		while (etiquetacion(graficaResidual, verticeS, verticeT, vecino,maxNodos)) {
      String camino = "";
			int maxFlujoRes = Integer.MAX_VALUE;
			for (verticeV=verticeT; verticeV != verticeS; verticeV=vecino[verticeV]) {
				verticeU = vecino[verticeV];
				maxFlujoRes = Math.min(maxFlujoRes, graficaResidual[verticeU][verticeV]);
        camino = " --> "+arrayIndexString[verticeV]+ camino;
			}
      camino= "S" + camino;
      System.out.println("\n"+camino);
			for (verticeV=verticeT; verticeV != verticeS; verticeV=vecino[verticeV]) {
				verticeU = vecino[verticeV];
				graficaResidual[verticeU][verticeV] -= maxFlujoRes;
				graficaResidual[verticeV][verticeU] += maxFlujoRes;
			}

			maxFlujo += maxFlujoRes;
		}
    System.out.println("\nMatriz residual");
		imprimeMatriz(graficaResidual);
		return maxFlujo;
	}



	/**
	Metodo main
	*/
	public static void main (String[] args) {
		System.out.println("Cuantos nodos deseas en la Grafica?");
		Scanner scan = new Scanner(System.in);
		int nodos = scan.nextInt();
    Etiquetamiento indices = new Etiquetamiento();
    String[] arrayIndexString = indices.creaArray(nodos);
		Etiquetamiento m = new Etiquetamiento(arrayIndexString);
		System.out.println("Matriz original");
		int graphMatrix[][] = m.creaGraficaMatriz(nodos);
		System.out.println("Nodos tomados por el algortimo");

		int verticeS = 0;
		int verticeT = nodos-1;
		System.out.println("\nFlujo Maximo: " + m.aumenta(graphMatrix, verticeS, verticeT,nodos));
	}

}

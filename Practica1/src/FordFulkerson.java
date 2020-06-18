import java.util.*;

public class FordFulkerson {
	/**
	Contructor vacio.
	*/
	public FordFulkerson(){}

		/**
		Metodo que crea una matriz triangular como representacion de una grafica
		con valores aleatorios y la regresa.
		@param n
		@return matriz.
		*/
	public int[][] creaGraficaMatriz(int n){
    int matriz[][] = new int[n][n];
    for (int x=0; x < matriz.length; x++) {
      for (int y=x; y < matriz[x].length; y++) {
        matriz[x][y] = (int) (Math.random()*99+1);
      }
    }
		imprimeMatriz(matriz);
    return matriz;
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
	Metodo que crea una grfica con listas de adyacencias.
	@param n
	*/
	public void creaGraficaLista(int n){
    ArrayList< ArrayList<Integer> > matriz = new ArrayList< ArrayList<Integer> >();
    for (int i = 0;i < n;i++) {
      matriz.add(new ArrayList<Integer>());
    }
      for (int i=0; i<n;i++) {
        for (int j=i+1;j<n;j++){
          int v = (int) (Math.random()*99+1);
          if(v > n){
            matriz.get(i).add(j);
            matriz.get(j).add(i);
          }
        }
      }
      imprimeMatrizLista(matriz);
  }

	/**
	Metodo que imprime una lista de adyacencias.
	@param matriz
	*/
	public void imprimeMatrizLista(ArrayList< ArrayList<Integer> > matriz){
    System.out.println("Nodos: Vecinos");
    for(int i=0; i<matriz.size(); i++) {
      System.out.print(i + ": ");
      for(int vertice: matriz.get(i))
        System.out.print(vertice + " ");
        System.out.println();
    }
  }

	/**
	Metodo de FordFulkerson que regresa el maximo flujo de una red.
	@param matriz
	@param verticeS
	@param verticeT
	@param maxNodos
	*/
	public int maxFlujo(int matriz[][], int verticeS, int verticeT,int maxNodos) {
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
			int maxFlujoRes = Integer.MAX_VALUE;
			for (verticeV=verticeT; verticeV != verticeS; verticeV=vecino[verticeV]) {
				verticeU = vecino[verticeV];
				maxFlujoRes = Math.min(maxFlujoRes, graficaResidual[verticeU][verticeV]);
			}

			for (verticeV=verticeT; verticeV != verticeS; verticeV=vecino[verticeV]) {
				verticeU = vecino[verticeV];
				graficaResidual[verticeU][verticeV] -= maxFlujoRes;
				graficaResidual[verticeV][verticeU] += maxFlujoRes;
			}

			maxFlujo += maxFlujoRes;
		}
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
	Metodo main donde sucede la magia xD.
	*/
	public static void main (String[] args) {
		System.out.println("Cuantos nodos deseas en la Grafica?");
		Scanner scan = new Scanner(System.in);
		int nodos = scan.nextInt();
		FordFulkerson m = new FordFulkerson();
		System.out.println("Matriz original");
		int graphMatrix[][] = m.creaGraficaMatriz(nodos);
		System.out.println("Matriz resultante");
		int verticeS = 0;
		int verticeT = nodos-1;
		System.out.println("\nFlujo Maximo: " + m.maxFlujo(graphMatrix, verticeS, verticeT,nodos));
		//System.out.println("Listas")
		//m.creaGraficaLista(nodos);
	}

}

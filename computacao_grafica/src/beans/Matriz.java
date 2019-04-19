package beans;

import negocio.exception.MatrizInvalidaException;
import negocio.exception.MultiplicacaoMatrizInvalidaException;
import negocio.exception.NegocioException;

public class Matriz {
	private double[][] matriz;
	
	public Matriz(int linha, int coluna) throws NegocioException{
		if(linha <= 0 || coluna <= 00)
			throw new MatrizInvalidaException("Entradas invalidas");
		this.matriz = new double[linha][coluna];
	}
	
	public Matriz(double matriz[][]) {
		if(matriz == null)
			throw new NullPointerException("Matriz nula");
		this.matriz = matriz;
	}
	
	/*
	 * Multiplica a this.matriz com uma matriz de entrada
	 * Retorna a matriz resultando desta operação
	 * Se a matriz de entrada for nula ou não puder ser multiplicada, o método leventa uma exception
	 */
	public Matriz multiplicar(Matriz matriz2) throws NegocioException{
		if(matriz2 == null) {
			throw new RuntimeException("Matriz2 é nula");
		}
		int linha1 = this.matriz.length;
		int coluna1 = this.matriz[0].length;
		int linha2 = matriz2.getMatriz().length; 
		int coluna2 = matriz2.getMatriz()[0].length;
		if(coluna1 != linha2) {
			throw new MultiplicacaoMatrizInvalidaException("matriz1.getNumLinhas eh diferente de matriz2.geNumtColunas");
		}
		Matriz matriz3 = new Matriz(new double[linha1][coluna2]);
		for (int i = 0; i < linha1; i++) {
			for (int j = 0; j < coluna2; j++) {
				matriz3.matriz[i][j] = 0;
				double somatorio = 0;
				for (int k = 0; k < linha2; k++) { // Somatório de cada entrada da matriz
					somatorio += matriz[i][k]*matriz2.matriz[k][j];
				}
				matriz3.matriz[i][j] = somatorio;
			}
		}
		return matriz3;
	}
	
	/*
	 * Retorna o numero de colunas de this.matriz
	 */
	public int getNumColunas() {
		return this.matriz[0].length;
	}
	
	/*
	 * Retorna o numero de linhas de this.matriz
	 */
	public int getNumLinhas() {
		return this.matriz.length;
	}
	
	/*
	 * Retorna a this.matriz
	 */
	public double[][] getMatriz() {
		return this.matriz;
	}
	
	/*
	 * Altera uma estrada específica da this.matriz
	 */
	public void setEntrada(int linha, int coluna, double valor) {
		this.matriz[linha][coluna] = valor;
	}
	
	/*
	 * Imprime no console a this.matriz
	 */
	public void print() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				System.out.print("["+matriz[i][j]+"]");
			}
			System.out.println();
		}
	}
	
	public boolean equals(Matriz m) {
		if(m == null) 
			throw new RuntimeException("Matriz invalida");
		if( (m.getNumLinhas() != this.getNumLinhas()) || (m.getNumColunas() != this.getNumColunas()) )
				return false;
		for (int i = 0; i < m.getNumLinhas(); i++) {
			for (int j = 0; j < m.getNumColunas(); j++) {
				if(m.matriz[i][j] != this.matriz[i][j])
					return false;
			}
		}
		return true;
	}
}

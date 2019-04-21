package negocio.beans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import negocio.exception.EntradaInvalidaException;
import negocio.exception.FormatoInvalidoException;
import negocio.exception.NegocioException;

public class CameraVirtual {
	private Ponto pontoC; // foco
	private Vetor vetorU, vetorV, vetorN; // Base do Espaço Vetorial
	private double d, hx, hy;
	private BufferedReader reader;
	private boolean ortonormal;
	
	public CameraVirtual(String fileName) throws IOException, NegocioException {
		String separator = System.getProperty("file.separator");
		String path = "CameraVirtual"+separator+fileName+".txt";
		reader = new BufferedReader(new FileReader(path));
		
		String line = reader.readLine();
		if(line.length() == 0)
			throw new FormatoInvalidoException("Arquivo .txt no formato invalido");
		String split[] = line.split(" ");
		this.pontoC = new Ponto(Double.parseDouble(split[0]), 
 								Double.parseDouble(split[1]), 
								Double.parseDouble(split[2]));

		line = reader.readLine();
		if(line.length() == 0)
			throw new FormatoInvalidoException("Arquivo .txt no formato invalido");
		split = line.split(" ");
		this.vetorN = new Vetor(Double.parseDouble(split[0]), 
								Double.parseDouble(split[1]), 
								Double.parseDouble(split[2]));
		
		line = reader.readLine();
		if(line.length() == 0)
			throw new FormatoInvalidoException("Arquivo .txt no formato invalido");
		split = line.split(" ");
		this.vetorV = new Vetor(Double.parseDouble(split[0]), 
								Double.parseDouble(split[1]), 
								Double.parseDouble(split[2]));
		
		line = reader.readLine();
		if(line.length() == 0)
			throw new FormatoInvalidoException("Arquivo .txt no formato invalido");
		if(Double.parseDouble(line) < 0)
			throw new EntradaInvalidaException("Valor de D negativo");
		this.d  = Double.parseDouble(line);
		
		line = reader.readLine();
		if(line.length() == 0)
			throw new FormatoInvalidoException("Arquivo .txt no formato invalido");
		if(Double.parseDouble(line) < 0)
			throw new EntradaInvalidaException("Valor de Hx negativo");
		this.hx = Double.parseDouble(line);
		
		line = reader.readLine();
		if(line.length() == 0)
			throw new FormatoInvalidoException("Arquivo .txt no formato invalido");
		if(Double.parseDouble(line) < 0)
			throw new EntradaInvalidaException("Valor de Hy negativo");
		this.hy = Double.parseDouble(line);
		
		ortonormal = false;
	}

	public Ponto getPontoC() {
		return this.pontoC;
	}

	public Vetor getVetorN() {
		return this.vetorN;
	}
	
	public Vetor getVetorV() {
		return this.vetorV;
	}

	public Vetor getVetorU() {
		return this.vetorU;
	}
	
	public double getD() {
		return this.d;
	}
		
	public double getHx() {
		return this.hx;
	}

	public double getHy() {
		return this.hy;
	}
	
	/**
	 * Processo de Gram-Schmidt e normalização da base
	 * Por decisão de projeto, só é possível ortonormalizar uma única vez
	 */
	public void ortonormalizar() {
		if(!this.ortonormal) {
			//Ortogonalização
			double prodEsc1 = this.vetorV.produtoEscalar3D(vetorN);
			double prodEsc2 = this.vetorN.produtoEscalar3D(vetorN);
			this.vetorV = vetorV.subtrair(vetorN.multplicarEscalar(prodEsc1/prodEsc2));
			this.vetorU = vetorN.produtoVetorial(vetorV);
			
			//Normalização
			double normaN = vetorN.norma(); 
			double normaV = vetorV.norma();
			double normaU = vetorU.norma();
			
			this.vetorN.setX(this.vetorN.getX()/normaN);
			this.vetorN.setY(this.vetorN.getY()/normaN);
			this.vetorN.setZ(this.vetorN.getZ()/normaN);
			
			this.vetorV.setX(this.vetorV.getX()/normaV);
			this.vetorV.setY(this.vetorV.getY()/normaV);
			this.vetorV.setZ(this.vetorV.getZ()/normaV);
			
			this.vetorU.setX(this.vetorU.getX()/normaU);
			this.vetorU.setY(this.vetorU.getY()/normaU);
			this.vetorU.setZ(this.vetorU.getZ()/normaU);
			this.ortonormal = true;
		}
	}
}

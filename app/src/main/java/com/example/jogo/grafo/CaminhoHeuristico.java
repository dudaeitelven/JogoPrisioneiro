package com.example.jogo.grafo;

public class CaminhoHeuristico {
	private int vertice; //vertice que corresponde a um caminho a ser seguido
	private int pai; //corresponde ao antecessor do vertice, quem abriu ele
	private float calculoHeuristico; //F(h)=h+g calculo da fun��o heuristica
	private float custo; //custo total para ir de um vertice inicial ate este vertice
	
	public CaminhoHeuristico() {
		vertice = 0;
		pai = 0;
		calculoHeuristico = 0;
		custo = 0;
	}
	
	public CaminhoHeuristico(int vertice, int pai, float calculoHeuristico, float custo) {
		this.vertice = vertice;
		this.pai = pai;
		this.calculoHeuristico = calculoHeuristico;
		this.custo = custo;
	}

	public int getVertice() {
		return vertice;
	}

	public void setVertice(int vertice) {
		this.vertice = vertice;
	}

	public int getPai() {
		return pai;
	}

	public void setPai(int pai) {
		this.pai = pai;
	}

	public float getCalculoHeuristico() {
		return calculoHeuristico;
	}

	public void setCalculoHeuristico(float calculoHeuristico) {
		this.calculoHeuristico = calculoHeuristico;
	}

	public float getCusto() {
		return custo;
	}

	public void setCusto(float custo) {
		this.custo = custo;
	}
	
}

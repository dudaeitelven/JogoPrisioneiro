package com.example.jogo.grafo;

public class Aresta {
	private int vertice; //vertice ligado
	private float custo; //custo de movimento
	
	public Aresta(int vertice, float custo) {
		this.vertice = vertice;
		this.custo = custo;
	}

	public int getVertice() {
		return vertice;
	}

	public void setVertice(int vertice) {
		this.vertice = vertice;
	}

	public float getCusto() {
		return custo;
	}

	public void setCusto(float custo) {
		this.custo = custo;
	}
	
}

package com.example.jogo.grafo;

import java.util.LinkedList;
import java.util.List;

public class Grafo {
	private int vertice; //identifica��o do vertice e localiza��o em um vetor
	
	private Object nome; //identifica��o subjetiva de um vertice, no jogo refere-se a cor dos quadrados no applet
	
	private int linha;
	private int coluna; //localiza��o de um vertice em uma matriz

    private int posX;
    private int posY;
	
	private int valorHeuristico; //cada vertice � resposavel por guardar seu proprio valor heuristico
	
	private List<Aresta> aresta; //lista de vertices ligados a este vertice
	
	public Grafo(int vertice, Object nome, int linha, int coluna, int posX, int posY, int valorHeuristico,  List<Aresta> aresta) {
		this.vertice = vertice;
		this.nome = nome;
		this.linha = linha;
		this.coluna = coluna;
		this.posX = posX;
		this.posY = posY;
		this.valorHeuristico = valorHeuristico;
		this.aresta = aresta;
	}

    public int getVertice() {
		return vertice;
	}

	public void setVertice(int vertice) {
		this.vertice = vertice;
	}

	public int getGrau() { //numero de vertices ligados a este vertice
		return aresta.size();
	}

	public List<Aresta> getAresta() {
		return aresta;
	}

	public void setAresta(List<Aresta> aresta) {
		this.aresta = aresta;
	}

	public Object getNome() {
		return nome;
	}

	public void setNome(Object nome) {
		this.nome = nome;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public int getPosX() { return posX; }

	public  void setPosX(int posX) { this.posX = posX; }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) { this.posY = posY; }

    public int getValorHeuristico() {
		return valorHeuristico;
	}

	public void setValorHeuristico(int valorHeuristico) {
		this.valorHeuristico = valorHeuristico;
	}
	
}

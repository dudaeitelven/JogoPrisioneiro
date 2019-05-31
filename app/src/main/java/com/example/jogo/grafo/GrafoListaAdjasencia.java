package com.example.jogo.grafo;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GrafoListaAdjasencia { //opera��es realizadas em um grafo de lista de adjacia

	private Grafo[] grafo = null;
	private int VERTICES = 0; //numero de vertices do grafo
	private int linhas = 0; //linhas da matriz do jogo
	private int colunas = 0; //colunas da matriz do jogo

	public GrafoListaAdjasencia(Grafo grafo[], int VERTICES, int linhas, int colunas) {
		this.grafo = grafo;
		this.VERTICES = VERTICES;
		this.linhas = linhas;
		this.colunas = colunas;
	}

	public void inicializaGrafo() { //fun��o que inicializa o grafo
		for (int i = 0, k = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++, k++) {
				//new Grafo(vertice, nome, linha, coluna, valorHeuristico, aresta)
				//inicializa o grafo com a cor padr�o white
				grafo[k] = new Grafo(k, Color.WHITE, i, j, 0, 0, calculaValorHeuristico(i, j), new LinkedList<Aresta>());
			}
		}

		//inicializa o objetivo com a cor green
		for (int i = 0; i < colunas; i++) {
			grafo[0 * linhas + i].setNome(Color.GREEN);
			grafo[i * linhas + 0].setNome(Color.GREEN);
			grafo[i * linhas + (colunas - 1)].setNome(Color.GREEN);
			grafo[(linhas - 1) * linhas + i].setNome(Color.GREEN);
		}

		//inicializa o prisioneiro com a cor red
		grafo[(linhas / 2) * linhas + (colunas / 2)].setNome(Color.RED);

	}

	//calcula o valor heuristico de cada vertice correspondende a localiza��o na matriz
	private int calculaValorHeuristico(int i, int j) {

		int m = linhas / 2; //localiza��o central na matriz do jogo
		int dLinha; //distancia horizontal de um vertice at� o objetivo
		int dColuna; //distancia vertical de um vertice at� o objetivo
		int distancia; //menor distancia real usada na heuristica em rela��o a dLinha e dColuna

		if (i == 0 || j == 0 || j == colunas - 1 || i == linhas - 1) { //corresponde a localiza��o do objetivo
			return 0;
		} else {
			if (i - m <= 0) { //coresponde a distancia horizontal
				dLinha = m + (i - m);
			} else {
				dLinha = m - (i - m);
			}

			distancia = dLinha;

			if (j - m <= 0) { //coresponde a distancia vertical
				dColuna = m + (j - m);
			} else {
				dColuna = m - (j - m);
			}

			if (dColuna < distancia)
				distancia = dColuna;
		}

		return distancia;
	}

	// insere aresta (vertice destino) de forma ordenada na lista de arestas de um vertice (origem), cuidando para n�o haver arestas repetidas
	private void insereAresta(List<Aresta> aresta, int destino, float custo, int grau) {
		int i;
		if (aresta.isEmpty()) {
			aresta.add(new Aresta(destino, custo));
		} else {
			if (destino <= aresta.get(0).getVertice()) {
				if (destino == aresta.get(0).getVertice())
					return;
				aresta.add(0, new Aresta(destino, custo));
			} else if (destino >= aresta.get(grau - 1).getVertice()) {
				if (destino == aresta.get(grau - 1).getVertice())
					return;
				aresta.add(new Aresta(destino, custo));
			} else {
				for (i = 0; i < grau && aresta.get(i).getVertice() < destino; i++)
					;
				if (destino == aresta.get(i).getVertice())
					return;
				aresta.add(i, new Aresta(destino, custo));
			}
		}
	}

	//metodo usado para gerenciar uma liga��o (aresta) de um grafo n�o direcionado
	public void insereGrafo(float custo, int origem, int destino) {

		insereAresta(grafo[origem].getAresta(), destino, custo, grafo[origem].getGrau());
		insereAresta(grafo[destino].getAresta(), origem, custo, grafo[destino].getGrau());

	}

	//inicializa todas as liga��es (arestas) possiveis para cada veritice
	public void inicializaArestas() {

		//faz as liga��es no vertice (0,0) da diagonal superior esquerda
		insereGrafo(1, 0 * linhas + 0, 0 * linhas + 1); //faz a liga��o para o vertice a direita
		insereGrafo(1, 0 * linhas + 0, 1 * linhas + 0); //faz a liga��o para o vertice a baixo
		insereGrafo(2, 0 * linhas + 0, 1 * linhas + 1); //faz a liga��o para o vertice na diagonal inferior direita

		//faz as liga��es no vertice (0,colunas - 1) da diagonal superior direita
		insereGrafo(1, 0 * linhas + (colunas - 1), 0 * linhas + (colunas - 2)); //faz a liga��o para o vertice a esquerda
		insereGrafo(1, 0 * linhas + (colunas - 1), 1 * linhas + (colunas - 1)); //faz a liga��o para o vertice a baixo
		insereGrafo(2, 0 * linhas + (colunas - 1), 1 * linhas + (colunas - 2)); //faz a liga��o para o vertice na diagonal inferior esquerda

		//faz as liga��es no vertice (linhas - 1,0) da diagonal inferior esquerda
		insereGrafo(1, (linhas - 1) * linhas + 0, (linhas - 1) * linhas + 1); //faz a liga��o para o vertice a direita
		insereGrafo(1, (linhas - 1) * linhas + 0, (linhas - 2) * linhas + 0); //faz a liga��o para o vertice a cima
		insereGrafo(2, (linhas - 1) * linhas + 0, (linhas - 2) * linhas + 1); //faz a liga��o para o vertice na diagonal superior direita

		//faz as liga��es no vertice (linhas - 1,colunas - 1) da diagonal inferior direita
		insereGrafo(1, (linhas - 1) * linhas + (colunas - 1), (linhas - 1) * linhas + (colunas - 2)); //faz a liga��o para o vertice a esquerda
		insereGrafo(1, (linhas - 1) * linhas + (colunas - 1), (linhas - 2) * linhas + (colunas - 1)); //faz a liga��o para o vertice a cima
		insereGrafo(2, (linhas - 1) * linhas + (colunas - 1), (linhas - 2) * linhas + (colunas - 2)); //faz a liga��o para o vertice na diagonal superior esquerda

		//faz as liga��es nos vestices das laterais
		for (int i = 1; i < linhas - 1; i++) {

			//faz as liga��es na lateral em cima
			insereGrafo(1, 0 * linhas + i, 0 * linhas + i + 1); //faz a liga��o para o vertice a direita
			insereGrafo(1, 0 * linhas + i, 1 * linhas + i); //faz a liga��o para o vertice a abaixo
			insereGrafo(2, 0 * linhas + i, 1 * linhas + i + 1); //faz a liga��o para o vertice na diagonal inferior direita
			insereGrafo(1, 0 * linhas + i, 0 * linhas + i - 1); //faz a liga��o para o vertice a esquerda
			insereGrafo(2, 0 * linhas + i, 1 * linhas + i - 1); //faz a liga��o para o vertice na diagonal inferior esquerda

			//faz as liga��es na lateral em baixo
			insereGrafo(1, (linhas - 1) * linhas + i, (linhas - 1) * linhas + i - 1);  //faz a liga��o para o vertice a esquerda
			insereGrafo(1, (linhas - 1) * linhas + i, (linhas - 2) * linhas + i); //faz a liga��o para o vertice a cima
			insereGrafo(2, (linhas - 1) * linhas + i, (linhas - 2) * linhas + i - 1); //faz a liga��o para o vertice na diagonal superior esquerda
			insereGrafo(1, (linhas - 1) * linhas + i, (linhas - 1) * linhas + i + 1); //faz a liga��o para o vertice a direita
			insereGrafo(2, (linhas - 1) * linhas + i, (linhas - 2) * linhas + i + 1); //faz a liga��o para o vertice na diagonal superior direita

			//faz as liga��es na lateral a esquerda
			insereGrafo(1, i * linhas + 0, (i - 1) * linhas + 0); //faz a liga��o para o vertice a cima
			insereGrafo(1, i * linhas + 0, i * linhas + 1); //faz a liga��o para o vertice a direita
			insereGrafo(2, i * linhas + 0, (i - 1) * linhas + 1); //faz a liga��o para o vertice na diagonal superior direita
			insereGrafo(1, i * linhas + 0, (i + 1) * linhas + 0); //faz a liga��o para o vertice a abaixo
			insereGrafo(2, i * linhas + 0, (i + 1) * linhas + 1); //faz a liga��o para o vertice na diagonal inferior direita

			//faz as liga��es na lateral a direita
			insereGrafo(1, i * linhas + colunas - 1, (i - 1) * linhas + colunas - 1); //faz a liga��o para o vertice a cima
			insereGrafo(1, i * linhas + colunas - 1, i * linhas + colunas - 2); //faz a liga��o para o vertice a esquerda
			insereGrafo(2, i * linhas + colunas - 1, (i - 1) * linhas + colunas - 2); //faz a liga��o para o vertice na diagonal superior esquerda
			insereGrafo(1, i * linhas + colunas - 1, (i + 1) * linhas + colunas - 1); //faz a liga��o para o vertice a abaixo
			insereGrafo(2, i * linhas + colunas - 1, (i + 1) * linhas + colunas - 2); //faz a liga��o para o vertice na diagonal inferior direita
		}

		//faz as liga��es no centro do mapa
		for (int i = 1; i < linhas - 1; i++) {
			for (int j = 1; j < colunas - 1; j++) {
				insereGrafo(2, i * linhas + j, (i - 1) * linhas + j - 1); //faz a liga��o para o vertice na diagonal superior esquerda
				insereGrafo(1, i * linhas + j, (i - 1) * linhas + j); //faz a liga��o para o vertice a cima
				insereGrafo(2, i * linhas + j, (i - 1) * linhas + j + 1); //faz a liga��o para o vertice na diagonal superior direita
				insereGrafo(1, i * linhas + j, i * linhas + j - 1); //faz a liga��o para o vertice a esquerda
				insereGrafo(2, i * linhas + j, (i + 1) * linhas + j - 1); //faz a liga��o para o vertice na diagonal inferior esquerda
				insereGrafo(1, i * linhas + j, (i + 1) * linhas + j); //faz a liga��o para o vertice a abaixo
				insereGrafo(2, i * linhas + j, (i + 1) * linhas + j + 1); //faz a liga��o para o vertice na diagonal inferior direita
				insereGrafo(1, i * linhas + j, i * linhas + j + 1); //faz a liga��o para o vertice a direita
			}
		}

	}

	//remove uma aresta de um vertice
	public void removeAresta(List<Aresta> aresta, int a, int grau) {
		int i;
		for (i = 0; i < grau && aresta.get(i).getVertice() != a; i++);
		aresta.remove(i);
	}

	//remove todas as arestas de um vertice deixando-o isolado
	public void removeVertice(int v) {
		for (Aresta a : grafo[v].getAresta()) {
			removeAresta(grafo[a.getVertice()].getAresta(), v, grafo[a.getVertice()].getGrau());
		}
		grafo[v].getAresta().removeAll(grafo[v].getAresta());
		grafo[v].setNome(Color.BLACK); //um vertice removido � um obstaculo intransponivel representado pela cor black
	}

	//inicializa o mapa com 15 obstaculos
	public void inicializaObstaculos(int obstaculos) {
		ArrayList<Integer> obstaculo = new ArrayList<>();
		int cont = 0;
		for (int i = 0; i < VERTICES; i++)
			obstaculo.add(i);

		for(int i = 0; i< obstaculos;) {
			int obs = new Random().nextInt(obstaculo.size());
			if(!grafo[obstaculo.get(obs)].getNome().equals(Color.RED) && !grafo[obstaculo.get(obs)].getNome().equals(Color.BLACK)) {
				cont = 0;
				for(int j = 0; j < grafo[obstaculo.get(obs)].getGrau(); j ++){
					if(grafo[obstaculo.get(obs)].getNome().equals(Color.WHITE) || grafo[obstaculo.get(obs)].getNome().equals(Color.GREEN)){
						cont++;
					}
				}

				if(cont > 2){
					i++;
					removeVertice(grafo[obstaculo.get(obs)].getVertice());
					obstaculo.remove(obs);
				}
			}
		}

	}

	//insere ordenadamente em rela��o a fun��o heuristica na lista aberta do a*
	private void insereAberto(Aresta a, float custo[], int pai, List<CaminhoHeuristico> aberto) {
		int i;
		float c = custo[pai] + a.getCusto(); //custo total para ir de um vertice inicial ate este
		float fh = c + grafo[a.getVertice()].getValorHeuristico(); //calculo da fun��o heuristica
		custo[a.getVertice()] += c;

		if (aberto.isEmpty()) {
			aberto.add(new CaminhoHeuristico(a.getVertice(), pai, fh, c));
		} else {
			if (fh < aberto.get(0).getCalculoHeuristico()) {
				aberto.add(0, new CaminhoHeuristico(a.getVertice(), pai, fh, c));
			} else if (fh > aberto.get(aberto.size() - 1).getCalculoHeuristico()) {
				aberto.add(new CaminhoHeuristico(a.getVertice(), pai, fh, c));
			} else {
				for (i = 0; i < aberto.size() && fh > aberto.get(i).getCalculoHeuristico(); i++);
				aberto.add(i, new CaminhoHeuristico(a.getVertice(), pai, fh, c));
			}
		}
	}

	//permuta dois nodos no inicio da lista de abertos do a* que possuem o calculo da fun��o heuristica iguais, proporcionando chances iguais pra tais nodos serem escolhidos como primeiro da fila
	private void segundaChance(List<CaminhoHeuristico> aberto) {
		CaminhoHeuristico aux = new CaminhoHeuristico();
		int i;
		int troca;
		for(i = 0; i<aberto.size() && aberto.get(0).getCalculoHeuristico() == aberto.get(i).getCalculoHeuristico();i++);

		troca = new Random().nextInt(i);
		aux = aberto.get(0);
		aberto.set(0, aberto.get(troca));
		aberto.set(troca, aux);
	}

	//metodo principal da busca a*
	public int buscaEstrela(int ini, CaminhoHeuristico caminho[]) {

		List<CaminhoHeuristico> aberto = new LinkedList<>();
		List<CaminhoHeuristico> fechado = new LinkedList<>();

		int pai = -1;
		float[] custo = new float[VERTICES];
		boolean[] visitado = new boolean[VERTICES];

		for (int i = 0; i < VERTICES; i++) {//inicializa variaveis chaves
			visitado[i] = false;
			custo[i] = 0;
			caminho[i] = new CaminhoHeuristico(i, -1, -1, -1);
		}

		aberto.add(new CaminhoHeuristico(ini, pai, grafo[ini].getValorHeuristico() + custo[ini], 0)); //inicializa lista de abertos de acordo com um vertice inicial ini

		while (!aberto.isEmpty()) { //repete enquanto existir um caminho

			segundaChance(aberto);

			if (!visitado[aberto.get(0).getVertice()]) { //isso torna o a* efetivo (e muito mais simples e direto) para todos os vertices n�o visitados
				visitado[aberto.get(0).getVertice()] = true; //o primeiro nodo da lista de abertos � analisado e fechado apartir deste ponto

				caminho[aberto.get(0).getVertice()] = aberto.get(0); //vetor que guarda fisicamente as coordenadas reais de um caminho
				fechado.add(aberto.get(0));

				if(grafo[aberto.get(0).getVertice()].getNome().equals(Color.GREEN)) //se o objetivo foi alcan�ado ent�o retorna o vertice do ultimo nodo da lista fechada para construir um caminho direto do inicio ao objetivo apartir da l�gica inver�a 
					return fechado.get(fechado.size()-1).getVertice();

				pai = aberto.get(0).getVertice(); //guarda o antecessor dos proximos vertives que ser�o abertos

				if (!grafo[aberto.get(0).getVertice()].getAresta().isEmpty()) { //se o vertice analizado tiver arestas ent�o esses vertices ligados s�o colocados na lista de abertos
					int vertice = aberto.get(0).getVertice();
					aberto.remove(0);

					for (Aresta a : grafo[vertice].getAresta()) {
						insereAberto(a, custo, pai, aberto);
					}
				}
			}else {
				aberto.remove(0);
			}
		}

		return fechado.get(fechado.size()-1).getVertice(); //se o objetivo n�o foi alcan�ado ent�o retorna o vertice do ultimo nodo da lista fechada para construir um caminho direto do inicio ao objetivo apartir da l�gica inver�a
	}

}

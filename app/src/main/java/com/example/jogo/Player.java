package com.example.jogo;

public final class Player {

    private static Player INSTANCE = null;
    private static String nome;
    private static float tempo;
    private static int dificuldade;
    private static int pontos;

    private Player(){
        nome = "";
        tempo = 0;
        dificuldade = 0;
        pontos = 0;
    }

    public static Player getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Player();
        }

        return INSTANCE;
    }

    public void setNome(String nome) {
        Player.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getDificuldade() { return dificuldade; }

    public void setDificuldade(int dificuldade) { Player.dificuldade = dificuldade; }

    public int getPontos() { return pontos; }

    public void setPontos(int pontos) { Player.pontos = pontos; }

    public float getTempo() { return tempo; }

    public void setTempo(float tempo) { Player.tempo = tempo; }
}

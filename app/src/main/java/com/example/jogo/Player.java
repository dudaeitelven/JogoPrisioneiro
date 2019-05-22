package com.example.jogo;

public final class Player {

    private static Player INSTANCE = null;
    private static String nome;

    private Player(){}

    public static Player getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Player();
        }

        return INSTANCE;
    }

    public static void setNome(String nome) {
        Player.nome = nome;
    }

    public static String getNome() {
        return nome;
    }

}

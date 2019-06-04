package com.example.jogo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.jogo.JogoDAO.DBAdapter;

import java.util.ArrayList;

public class Placar extends AppCompatActivity {
    private Button buttonVoltar;
    private Cursor cursor;

    DBAdapter db = new DBAdapter(this);

    private ListView ltListaClassificacao;
    ArrayList<String> ranking = new ArrayList<String>();
    ArrayAdapter<String> listaderanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placar);

        init();
    }

    public class voltarMenu implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        setResult(1, intent);
        super.finish();
    }

    public void mostraRanking() {
        String nome, descricaoDificuldade="";
        Integer tempo, pontos, dificuldade;

        db.open();
        cursor = db.getRanking();

        if (cursor.moveToFirst() == true) {
            do {
                nome = cursor.getString(0);
                tempo = cursor.getInt(1);
                pontos = cursor.getInt(2);
                dificuldade = cursor.getInt(3);

                if (dificuldade == 2){
                    descricaoDificuldade = "Dificil";
                }
                if (dificuldade == 1){
                    descricaoDificuldade = "Medio";
                }
                if (dificuldade == 0){
                    descricaoDificuldade = "Facil";
                }

                ranking.add( "Nome: " + nome + " \nTempo: " + tempo + " \nPontos: " + pontos + " \nDificuldade: " + descricaoDificuldade);
                listaderanking.notifyDataSetChanged();
            } while (cursor.moveToNext());
        }

        db.close();
    }

    public void init() {
        buttonVoltar = (Button) findViewById(R.id.buttonVoltar);
        ltListaClassificacao = (ListView) findViewById(R.id.ltListaClassificacao);

        buttonVoltar.setOnClickListener(new voltarMenu());

        //Adapter da listview
        listaderanking = new ArrayAdapter<String>(Placar.this, android.R.layout.simple_list_item_1, ranking);
        ltListaClassificacao.setAdapter(listaderanking);

        mostraRanking();

    }

}

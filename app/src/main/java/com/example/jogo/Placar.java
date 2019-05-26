package com.example.jogo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jogo.JogoDAO.DBAdapter;

public class Placar extends AppCompatActivity {
    private Button buttonVoltar;
    private Cursor cursor;

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

//    public void mostraRanking(Cursor cursor) {
//        String jogador = "Nome: " + cursor.getString(0) + "\n";
//        jogador = jogador + "Tempo: " + cursor.getString(1) + "\n";
//        jogador = jogador + "Dificuldade: " + cursor.getString(2) + "\n";
//        jogador = jogador + "Pontos: " + cursor.getString(3) + "\n";
//
//    }

    public void init() {
        buttonVoltar = (Button) findViewById(R.id.buttonVoltar);
        buttonVoltar.setOnClickListener(new voltarMenu());

        DBAdapter db = new DBAdapter(this);

        db.open();
        cursor = db.getRanking();
        if (cursor.moveToFirst() == true) {
            do {
                //mostraRanking(cursor);
            } while (cursor.moveToNext());
        }
        db.close();

    }

}

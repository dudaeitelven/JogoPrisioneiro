package com.example.jogo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Jogo(this));
    }

}

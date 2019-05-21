package com.example.jogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonIniciar, buttonPlacar,  buttonSobre, buttonSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public class  iniciarJogo implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent activityJogo = new Intent(MainActivity.this, JogoActivity.class);
            startActivity(activityJogo);
        }
    }

    public class  placarJogo implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent activityPlacar = new Intent(MainActivity.this, Placar.class);
            startActivity(activityPlacar);
        }
    }

    public class  sobreJogo implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent activitySobre = new Intent(MainActivity.this, Sobre.class);
            startActivity(activitySobre);
        }
    }

    public class  sairJogo implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void init() {
        buttonIniciar   = (Button)      findViewById(R.id.buttonIniciar);
        buttonPlacar    = (Button)      findViewById(R.id.buttonPlacar);
        buttonSobre     = (Button)      findViewById(R.id.buttonSobre);
        buttonSair      = (Button)      findViewById(R.id.buttonSair);

        buttonIniciar.setOnClickListener(new iniciarJogo());

        buttonPlacar.setOnClickListener(new placarJogo());

        buttonSobre.setOnClickListener(new sobreJogo());

        buttonSair.setOnClickListener(new sairJogo());
    }
}

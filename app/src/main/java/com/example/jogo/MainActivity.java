package com.example.jogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Button buttonIniciar, buttonPlacar,  buttonSobre, buttonSair;
    private EditText editTextNomeJogador;
    private Spinner  spinnerDificuldade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public class  iniciarJogo implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Player player = Player.getInstance();
            player.setNome(editTextNomeJogador.getText().toString());
            player.setDificuldade(spinnerDificuldade.getSelectedItemPosition());

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
        buttonIniciar       = (Button)   findViewById(R.id.buttonIniciar);
        buttonPlacar        = (Button)   findViewById(R.id.buttonPlacar);
        buttonSobre         = (Button)   findViewById(R.id.buttonSobre);
        buttonSair          = (Button)   findViewById(R.id.buttonSair);
        editTextNomeJogador = (EditText) findViewById(R.id.editTextNomeJogador);
        spinnerDificuldade  = (Spinner)  findViewById(R.id.spinnerDificuldade);

        //Popula o spinner com o array do xml.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dificuldade_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificuldade.setAdapter(adapter);

        buttonIniciar.setOnClickListener(new iniciarJogo());
        buttonPlacar.setOnClickListener(new placarJogo());
        buttonSobre.setOnClickListener(new sobreJogo());
        buttonSair.setOnClickListener(new sairJogo());
    }
}

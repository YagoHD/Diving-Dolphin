package com.example.divingdolphin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    //Boton de jugar de nuevo
    private Button empezarJuego;
    //Texto con la puntuacion final
    private TextView puntuacion;
    private String puntuacionStr;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        puntuacionStr = getIntent().getExtras().get("puntuacion").toString();
        empezarJuego = (Button) findViewById(R.id.playAgain);
        puntuacion = (TextView) findViewById(R.id.textoPuntuacion);


        empezarJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent (GameOver.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
        puntuacion.setText("Puntuacion: " + puntuacionStr);
    }
}
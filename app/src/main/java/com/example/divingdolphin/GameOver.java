package com.example.divingdolphin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends AppCompatActivity {
    //Boton de jugar de nuevo
    private Button empezarJuego;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        empezarJuego = (Button) findViewById(R.id.playAgain);

        empezarJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent (GameOver.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
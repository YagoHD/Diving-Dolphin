package com.example.divingdolphin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Creamos el personaje que tendra la funcion del delfin
    private Delfin personajeDelfin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personajeDelfin = new Delfin(this);
        setContentView(personajeDelfin);
    }
}
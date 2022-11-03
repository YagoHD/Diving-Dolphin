package com.example.divingdolphin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Creamos el personaje que tendra la funcion del delfin
    private Delfin personajeDelfin;
    private Handler handler = new Handler();
    private final static long Interval = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personajeDelfin = new Delfin(this);
        setContentView(personajeDelfin);

        //Hacemos que el delfin se caiga cuando no se pulsa el click
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        personajeDelfin.invalidate();
                    }
                });
            }
        },0, Interval);
    }
}
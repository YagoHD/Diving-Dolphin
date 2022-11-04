package com.example.divingdolphin;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Creamos el personaje que tendra la funcion del delfin
    private Delfin personajeDelfin;
    private Handler handler = new Handler();
    private final static long Interval = 30;

    //Repodductor de musica
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mp = MediaPlayer.create(this,R.raw.infierno);
        if (mp.isPlaying()){
            mp.stop();
        }
        mp.start();


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}
package com.example.divingdolphin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //HILO PARA PASAR DEL ACTIVIITY INICIAL AL MAIN ACTIVITY A LOS 5 SEGUNDOS DE INICIAR EL JUEGO
        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    Intent mainIntent = new Intent (InicioActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
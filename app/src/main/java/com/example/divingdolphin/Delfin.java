package com.example.divingdolphin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class Delfin extends View {
    //Creamos el Bitmap Delfin
    private Bitmap delfin;
    //Creamos el fondo del nivel
    private Bitmap imagenDeFondo;
    //Creamos el marcador de puntuacion
    private Paint marcadorPuntuacion = new Paint();
    //Creamos la vida del personaje
    private Bitmap vida[] = new Bitmap[2];

    public Delfin(Context context) {
        super(context);

        //Instanciamos el bitmap del delfin
        delfin = BitmapFactory.decodeResource(getResources(), R.drawable.delfinarriba);
        //Instanciamos la imagen de fondo
        imagenDeFondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo1);
        //Instanciamos el marcador de puntuacion
        marcadorPuntuacion.setColor(Color.WHITE);
        marcadorPuntuacion.setTextSize(70);
        marcadorPuntuacion.setTypeface(Typeface.DEFAULT_BOLD);
        marcadorPuntuacion.setAntiAlias(true);
        //Instanciamos la vida
        vida[0] = BitmapFactory.decodeResource(getResources(), R.drawable.corazonredimensionado);
        vida[1] = BitmapFactory.decodeResource(getResources(), R.drawable.corazonroto2);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Añadimos la imagen de fondo a la pantalla
        canvas.drawBitmap(imagenDeFondo,0,0,null);
        //Añadimos el delfin a la pantalla
        canvas.drawBitmap(delfin,0,0, null);
        //Añadimos los corazones y la puntuacion en la pantalla
        canvas.drawText("Puntuación: ", 20, 100, marcadorPuntuacion);
        canvas.drawBitmap(vida[0], 600, 35, null);
        canvas.drawBitmap(vida[0], 700, 35, null);
        canvas.drawBitmap(vida[0], 800, 35, null);


    }
}

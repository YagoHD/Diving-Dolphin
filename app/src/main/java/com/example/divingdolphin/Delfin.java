package com.example.divingdolphin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class Delfin extends View {
    //Creamos el Bitmap Delfin
    private Bitmap delfin[] = new Bitmap[2];
    private int delfinX = 10;
    private int delfinY;
    private int velocidadDelfin;

    private int canvasWidth, canvasHeight;
    //Creamos el fondo del nivel
    private Bitmap imagenDeFondo;
    //Creamos el marcador de puntuacion
    private Paint marcadorPuntuacion = new Paint();
    //Creamos la vida del personaje
    private Bitmap vida[] = new Bitmap[2];
    //Creacion del toque en la pantalla
    private boolean click = false;


    public Delfin(Context context) {
        super(context);

        //Instanciamos el bitmap del delfin
        delfin[0] = BitmapFactory.decodeResource(getResources(), R.drawable.delfinarriba);
        delfin[1] = BitmapFactory.decodeResource(getResources(), R.drawable.delfinabajo);
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
        //Punto de partida del delfin
        delfinY = 550;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        //Añadimos la imagen de fondo a la pantalla
        canvas.drawBitmap(imagenDeFondo,0,0,null);
        //Añadimos y posidionamos el delfin a la pantalla
        int minDelfinY = delfin[0].getHeight();
        int maxDelfinY = canvasHeight - delfin[0].getHeight() * 3;
        delfinY = delfinY + velocidadDelfin;

        if (delfinY < minDelfinY){
            delfinY = minDelfinY;
        }
        if (delfinY > maxDelfinY){
            delfinY = maxDelfinY;
        }
        velocidadDelfin = velocidadDelfin + 2;
        if (click){
            canvas.drawBitmap(delfin[1], delfinX,delfinY, null);
            click = false;
        }else{
        canvas.drawBitmap(delfin[0], delfinX, delfinY, null);
        }

        //Añadimos los corazones y la puntuacion en la pantalla
        canvas.drawText("Puntuación: ", 20, 100, marcadorPuntuacion);
        canvas.drawBitmap(vida[0], 600, 35, null);
        canvas.drawBitmap(vida[0], 700, 35, null);
        canvas.drawBitmap(vida[0], 800, 35, null);
    }

    //Creamos el evento de cuando clicamos en pantalla y el delfin suba
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            click = true;
            velocidadDelfin = -21;
        }
        return true;
    }
}

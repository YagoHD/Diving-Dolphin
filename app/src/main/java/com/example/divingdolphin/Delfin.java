package com.example.divingdolphin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
    //Añadimos los peces que seran la comida y la manera de conseguir puntos
    private int pezTropicalX, pezTropicalY, pezTropicalVelocidad = 7;
    private int pezDoradoX, pezDoradoY, pezDoradoVelocidad = 14;
    private Bitmap pezDorado;
    private Bitmap pezTropical;
    //Añadimos el anzuelo que es el enemigo del juego
    private int anzueloX, anzueloY, anzueloVelocidad = 17;
    private Bitmap anzuelo;
    //Añadimos la variable de la puntuacion y la vida
    private int puntuacionNumero, vidaDelfin;


    public Delfin(Context context) {
        super(context);

        //Instanciamos el bitmap del delfin
        delfin[0] = BitmapFactory.decodeResource(getResources(), R.drawable.delfinarriba);
        delfin[1] = BitmapFactory.decodeResource(getResources(), R.drawable.delfinabajo);
        //Instanciamos la imagen de fondo
        imagenDeFondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo1);
        //Instanciamos los pedez/comida
        pezDorado = BitmapFactory.decodeResource(getResources(), R.drawable.pezdorado);
        pezTropical = BitmapFactory.decodeResource(getResources(), R.drawable.pez);
        //Instanciamos el anzuelo
        anzuelo = BitmapFactory.decodeResource(getResources(),R.drawable.anzuelo);
        //Instanciamos el marcador de puntuacion
        marcadorPuntuacion.setColor(Color.WHITE);
        marcadorPuntuacion.setTextSize(70);
        marcadorPuntuacion.setTypeface(Typeface.DEFAULT_BOLD);
        marcadorPuntuacion.setAntiAlias(true);
        //Instanciamos la vida
        vida[0] = BitmapFactory.decodeResource(getResources(), R.drawable.corazonredimensionado);
        vida[1] = BitmapFactory.decodeResource(getResources(), R.drawable.corazonroto2);


        //Punto de partida del delfin y inicio de la puntuacion, numero de vidas
        delfinY = 550;
        puntuacionNumero = 0;
        vidaDelfin = 3;
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
        //Añadimos posicion de los peces
        pezDoradoX = pezDoradoX - pezDoradoVelocidad;
        pezTropicalX = pezTropicalX - pezTropicalVelocidad;
        //Añadimos el metodo de comer los peces
        //PEZ DORADO
        if (comerPez(pezDoradoX, pezDoradoY)){
            puntuacionNumero = puntuacionNumero + 50;
            pezDoradoY = -100;
        }
        if(pezDoradoX < 0){
            pezDoradoX = canvasWidth + 21;
            pezDoradoY = (int) Math.floor(Math.random() * (maxDelfinY - minDelfinY) + minDelfinY);
        }
        canvas.drawBitmap(pezDorado, pezDoradoX, pezDoradoY, null);
        //PEZ TROPICAL
        if (comerPez(pezTropicalX, pezTropicalY)){
            puntuacionNumero = puntuacionNumero + 10;
            pezTropicalY = -100;
        }
        if(pezTropicalX < 0){
            pezTropicalX = canvasWidth + 21;
            pezTropicalY = (int) Math.floor(Math.random() * (maxDelfinY - minDelfinY) + minDelfinY);
        }
        canvas.drawBitmap(pezTropical, pezTropicalX, pezTropicalY, null);
        //Añadimos ANZUELO y la posicion del anzuelo
        anzueloX = anzueloX - anzueloVelocidad;
        if (comerPez(anzueloX, anzueloY)){
            puntuacionNumero = puntuacionNumero - 50;
            anzueloY = -100;
            vidaDelfin --;
            if(vidaDelfin == 0){
                Toast.makeText(getContext(),"Capturado!!😭", Toast.LENGTH_LONG).show();
                //Al perder los 3 corazones nos manda a la pantalla de Game Over
                Intent gameOver = new Intent(getContext(), GameOver.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameOver);
            }
        }
        if(anzueloX < 0){
            anzueloX = canvasWidth + 21;
            anzueloY = (int) Math.floor(Math.random() * (maxDelfinY - minDelfinY) + minDelfinY);
        }
        canvas.drawBitmap(anzuelo, anzueloX, anzueloY, null);

        //Metodo para hacer que los corazones bajen a medida que comes un anzuelo
        for(int i=0; i<3;i++){
            int x = (int) (600 + vida[0].getWidth() * 1.5 * i);
            int y = 30;
            if (i < vidaDelfin){
                canvas.drawBitmap(vida[0], x, y, null);
            }else{
                canvas.drawBitmap(vida[1], x, y, null);
            }
        }

        //Añadimos los corazones y la puntuacion en la pantalla
        canvas.drawText("Puntuación: " + puntuacionNumero, 20, 100, marcadorPuntuacion);
    }

    //Creamos el evento de comer los peces
    public boolean comerPez(int x, int y){
        if (delfinX < x && x <(delfinX + delfin[0].getWidth()) && delfinY < y && y < (delfinY + delfin[0].getHeight())){
            return true;
        }
        return false;
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

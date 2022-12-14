package com.example.divingdolphin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Delfin extends View {
    //Medimos el tamaño de la pantalla para ajustar la velocidad de los elementos
    int anchoPantallaVelocidad = getResources().getDisplayMetrics().widthPixels;

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
    //Creamos los anzuelos
    private int anzueloX, anzueloY, anzueloVelocidad = 17, anzuelo2X, anzuelo2Y, anzuelo2Velocidad = 17;
    private Bitmap anzuelo, anzuelo2;
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
        //Instanciamos los anzuelos
        anzuelo = BitmapFactory.decodeResource(getResources(),R.drawable.anzuelo);
        anzuelo2 = BitmapFactory.decodeResource(getResources(),R.drawable.anzuelo);
        //Instanciamos el marcador de puntuacion
        marcadorPuntuacion.setColor(Color.WHITE);
        marcadorPuntuacion.setShadowLayer(2,2,2, Color.BLACK);
        final float TAMANHO = 16.0f;
        final float escala = getContext().getResources().getDisplayMetrics().density;
        marcadorPuntuacion.setTextSize((int) (TAMANHO * escala + 0.5f));
        marcadorPuntuacion.setTypeface(Typeface.DEFAULT_BOLD);
        marcadorPuntuacion.setAntiAlias(true);

        //Creamos las vidas que son 2 imagenes una para vida sin perder y una para la vida perdida
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
        int maxDelfinY = canvasHeight - delfin[0].getHeight();
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

        //Añadimos los metodos de comer los peces
        //PEZ DORADO
        if (comerPez(pezDoradoX, pezDoradoY)){
            puntuacionNumero = puntuacionNumero + 50;
            pezDoradoY = -1000;
        }
        if(pezDoradoX < 0){
            pezDoradoX = canvasWidth + 21;
            pezDoradoY = (int) Math.floor(Math.random() * (maxDelfinY - minDelfinY) + minDelfinY);
        }
        canvas.drawBitmap(pezDorado, pezDoradoX, pezDoradoY, null);
        //PEZ TROPICAL
        if (comerPez(pezTropicalX, pezTropicalY)){
            puntuacionNumero = puntuacionNumero + 10;
            pezTropicalY = -1000;
        }
        if(pezTropicalX < 0){
            pezTropicalX = canvasWidth + 21;
            pezTropicalY = (int) Math.floor(Math.random() * (maxDelfinY - minDelfinY) + minDelfinY);
        }
        canvas.drawBitmap(pezTropical, pezTropicalX, pezTropicalY, null);

        //Añadimos los anzuelos y hacemos que nos quiten vida al comerlos
        anzueloX = anzueloX - anzueloVelocidad;
        if (comerPez(anzueloX, anzueloY)){
            anzueloY = -1000;
            vidaDelfin --;
            if(vidaDelfin == 0){
                Toast.makeText(getContext(),"Capturado!!😭", Toast.LENGTH_LONG).show();
                //Al perder los 3 corazones nos manda a la pantalla de Game Over
                Intent gameOver = new Intent(getContext(), GameOver.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOver.putExtra("puntuacion", puntuacionNumero);
                getContext().startActivity(gameOver);
            }
        }
        if(anzueloX < 0){
            anzueloX = canvasWidth + 21;
            anzueloY = (int) Math.floor(Math.random() * (maxDelfinY - minDelfinY) + minDelfinY);
        }
        canvas.drawBitmap(anzuelo, anzueloX, anzueloY, null);

        anzuelo2X = anzuelo2X - anzuelo2Velocidad;
        if (comerPez(anzuelo2X, anzuelo2Y)){
            anzuelo2Y = -1000;
            vidaDelfin --;
            if(vidaDelfin == 0){
                Toast.makeText(getContext(),"Capturado!!😭", Toast.LENGTH_LONG).show();
                //Al perder los 3 corazones nos manda a la pantalla de Game Over
                Intent gameOver = new Intent(getContext(), GameOver.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOver.putExtra("puntuacion", puntuacionNumero);
                getContext().startActivity(gameOver);
            }
        }
        if(anzuelo2X < 0){
            anzuelo2X = canvasWidth + 21;
            anzuelo2Y = (int) Math.floor(Math.random() * (maxDelfinY - minDelfinY) + minDelfinY);
        }


        //Metodo para hacer que los corazones bajen a medida que comes un anzuelo
        for(int i=0; i<3;i++){
            int x = (int) (0 + vida[0].getWidth() * 1.5 * i);
            int y = 0;
            if (i < vidaDelfin){
                canvas.drawBitmap(vida[0], x, y, null);
            }else{
                canvas.drawBitmap(vida[1], x, y, null);
            }
        }

        //Añadimos los corazones y la puntuacion en la pantalla
        canvas.drawText("Puntuación: " + puntuacionNumero, 0, vida[0].getHeight() + 25, marcadorPuntuacion);

        //Creacion de difentes niveles de dificultad
        //PRIMER NIVEL
        if ((puntuacionNumero >= -150) && (puntuacionNumero < 799) ){
            if((anchoPantallaVelocidad >=1080) && (anchoPantallaVelocidad<2200)){
                pezDoradoVelocidad = 15;
                pezTropicalVelocidad = 17;
                anzueloVelocidad = 8;
                anzuelo2Velocidad = 0;
            } else if ((anchoPantallaVelocidad >=480) && (anchoPantallaVelocidad<1080)) {
                pezDoradoVelocidad = 7;
                pezTropicalVelocidad = 8;
                anzueloVelocidad = 4;
                anzuelo2Velocidad = 0;
            } else if (anchoPantallaVelocidad<480){
                pezDoradoVelocidad = 3;
                pezTropicalVelocidad = 4;
                anzueloVelocidad = 2;
                anzuelo2Velocidad = 0;
            } else if(anchoPantallaVelocidad >= 2200){
                pezDoradoVelocidad = 20;
                pezTropicalVelocidad = 22;
                anzueloVelocidad = 13;
                anzuelo2Velocidad = 0;
            }
        //SEGUNDO NIVEL, apartir de aqui se añade un nuevo enemigo
        }else if((puntuacionNumero >= 700) && (puntuacionNumero < 850)) {
            imagenDeFondo = BitmapFactory.decodeResource(getResources(), R.drawable.profundidades);
            pezDorado = BitmapFactory.decodeResource(getResources(), R.drawable.dorado2);
            pezTropical = BitmapFactory.decodeResource(getResources(), R.drawable.pez2);

            canvas.drawBitmap(anzuelo2, anzuelo2X, anzuelo2Y, null);
        }else if((puntuacionNumero >= 850) && (puntuacionNumero < 1599)){
            canvas.drawBitmap(anzuelo2, anzuelo2X, anzuelo2Y, null);
            if((anchoPantallaVelocidad >=1080) && (anchoPantallaVelocidad<2200)){
                pezDoradoVelocidad = 6;
                pezTropicalVelocidad = 12;
                anzueloVelocidad = 12;
                anzuelo2Velocidad = 15;
            } else if ((anchoPantallaVelocidad >=480) && (anchoPantallaVelocidad<1080)) {
                pezDoradoVelocidad = 3;
                pezTropicalVelocidad = 4;
                anzueloVelocidad = 5;
                anzuelo2Velocidad = 7;
            } else if (anchoPantallaVelocidad<480){
                pezDoradoVelocidad = 2;
                pezTropicalVelocidad = 2;
                anzueloVelocidad = 2;
                anzuelo2Velocidad = 3;
            } else if(anchoPantallaVelocidad >= 2200){
                pezDoradoVelocidad = 11;
                pezTropicalVelocidad = 17;
                anzueloVelocidad = 17;
                anzuelo2Velocidad = 22;
            }
        //TERCER NIVEL
        }else if((puntuacionNumero >= 1600) && (puntuacionNumero < 1650)){
            imagenDeFondo = BitmapFactory.decodeResource(getResources(), R.drawable.abismo);
            pezDorado = BitmapFactory.decodeResource(getResources(), R.drawable.dorado3);
            pezTropical = BitmapFactory.decodeResource(getResources(), R.drawable.pez3);
            anzuelo = BitmapFactory.decodeResource(getResources(), R.drawable.ancla1);
            anzuelo2 = BitmapFactory.decodeResource(getResources(), R.drawable.ancla1);
            canvas.drawBitmap(anzuelo2, anzuelo2X, anzuelo2Y, null);
        }else if((puntuacionNumero >= 1650) && (puntuacionNumero < 2399)){
            canvas.drawBitmap(anzuelo2, anzuelo2X, anzuelo2Y, null);
            if((anchoPantallaVelocidad >=1080) && (anchoPantallaVelocidad<2200)){
                pezDoradoVelocidad = 18;
                pezTropicalVelocidad = 12;
                anzueloVelocidad = 25;
                anzuelo2Velocidad = 13;
            } else if ((anchoPantallaVelocidad >=480) && (anchoPantallaVelocidad<1080)) {
                pezDoradoVelocidad = 9;
                pezTropicalVelocidad = 5;
                anzueloVelocidad = 3;
            } else if (anchoPantallaVelocidad<480){
                pezDoradoVelocidad = 4;
                pezTropicalVelocidad = 2;
                anzueloVelocidad = 2;
                anzuelo2Velocidad = 4;
            } else if(anchoPantallaVelocidad >= 2200){
                pezDoradoVelocidad = 23;
                pezTropicalVelocidad = 17;
                anzueloVelocidad = 30;
                anzuelo2Velocidad = 15;
            }
        //CUARTO NIVEL
        }else if((puntuacionNumero >= 2400) && (puntuacionNumero < 2450)){
            imagenDeFondo = BitmapFactory.decodeResource(getResources(), R.drawable.infierno);
            pezDorado = BitmapFactory.decodeResource(getResources(), R.drawable.dorado4);
            pezTropical = BitmapFactory.decodeResource(getResources(), R.drawable.pez4);
            anzuelo = BitmapFactory.decodeResource(getResources(), R.drawable.ancla2);
            anzuelo2 = BitmapFactory.decodeResource(getResources(), R.drawable.ancla2);
            canvas.drawBitmap(anzuelo2, anzuelo2X, anzuelo2Y, null);
        }else if((puntuacionNumero >= 2450) && (puntuacionNumero < 999999)){
            canvas.drawBitmap(anzuelo2, anzuelo2X, anzuelo2Y, null);
            if((anchoPantallaVelocidad >=1080) && (anchoPantallaVelocidad<2200)){
                pezDoradoVelocidad = 17;
                pezTropicalVelocidad = 7;
                anzueloVelocidad = 27;
                anzuelo2Velocidad = 22;
            } else if ((anchoPantallaVelocidad >=480) && (anchoPantallaVelocidad<1080)) {
                pezDoradoVelocidad = 8;
                pezTropicalVelocidad = 4;
                anzueloVelocidad = 13;
                anzuelo2Velocidad = 10;
            } else if (anchoPantallaVelocidad<480){
                pezDoradoVelocidad = 5;
                pezTropicalVelocidad = 4;
                anzueloVelocidad = 3;
                anzuelo2Velocidad = 4;
            } else if(anchoPantallaVelocidad >= 2200){
                pezDoradoVelocidad = 22;
                pezTropicalVelocidad = 12;
                anzueloVelocidad = 32;
                anzuelo2Velocidad = 22;
            }
        }
    }

    //Creamos el evento de comer los peces y los anzuelos cuando coincidan las cordenadas
    public boolean comerPez(int x, int y){
        if (delfinX < x && x <(delfinX + delfin[0].getWidth()) && delfinY < y && y < (delfinY + delfin[0].getHeight())){
            return true;
        }
        return false;
    }

    //Creamos el evento para ascender cada vez que clicamos la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            click = true;
            if((anchoPantallaVelocidad >=1080) && (anchoPantallaVelocidad<2200)){
                velocidadDelfin = -30;
            } else if ((anchoPantallaVelocidad >=480) && (anchoPantallaVelocidad<1080)) {
                velocidadDelfin = -21;
            } else if (anchoPantallaVelocidad<480){
                velocidadDelfin = -13;
            } else if(anchoPantallaVelocidad >= 2200){
                velocidadDelfin = -40;
            }
        }
        return true;
    }
}

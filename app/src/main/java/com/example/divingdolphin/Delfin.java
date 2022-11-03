package com.example.divingdolphin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class Delfin extends View {
    //Creamos el Bitmap Delfin
    private Bitmap delfin;

    public Delfin(Context context) {
        super(context);

        //Instanciamos el bitmap del delfin
        delfin = BitmapFactory.decodeResource(getResources(), R.drawable.delfinarriba);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Añadimos el delfin a la pantalla
        canvas.drawBitmap(delfin,0,0, null);
    }
}

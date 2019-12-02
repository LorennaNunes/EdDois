package com.example.a18190_18343_projetoed2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class MinhaView extends View {

    Paint paint;

    public MinhaView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mapaTotalX = 717;
        int mapaTotalY = 578;

        Bitmap setaMapa = BitmapFactory.decodeResource(getResources(),R.drawable.mapaep);
        canvas.drawBitmap(setaMapa,0,0,null);


    }
}

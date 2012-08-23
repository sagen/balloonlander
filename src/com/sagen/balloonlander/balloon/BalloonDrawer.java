package com.sagen.balloonlander.balloon;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import static android.graphics.Color.TRANSPARENT;

class BalloonDrawer {
    private BitmapDrawable balloon;
    private Paint paint = new Paint();

    BalloonDrawer(Drawable balloon){
        this.balloon = (BitmapDrawable) balloon;
    }

    boolean transparent(int x, int y){
        return TRANSPARENT == this.balloon.getBitmap().getPixel(x, y);
    }

    int width(){
        return balloon.getBitmap().getWidth();
    }

    int height(){
        return balloon.getBitmap().getHeight();
    }

    int getLandingAreaXStart(){
        return 15;
    }

    int getLandingAreaXEnd(){
        return 19;
    }


    void drawOnCanvas(Canvas c, int x, int y){
        c.drawBitmap((balloon).getBitmap(), (float) x, (float) y, paint);
    }

}

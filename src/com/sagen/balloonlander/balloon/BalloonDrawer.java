package com.sagen.balloonlander.balloon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.sagen.R;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.TRANSPARENT;

public class BalloonDrawer {
    private BitmapDrawable balloon;

    public BalloonDrawer(Drawable balloon){
        this.balloon = (BitmapDrawable) balloon;
    }

    public boolean transparent(int x, int y){
        return TRANSPARENT == this.balloon.getBitmap().getPixel(x, y);
    }

    int width(){
        return balloon.getBitmap().getWidth();
    }
    int height(){
        return balloon.getBitmap().getHeight();
    }


    protected void drawOnCanvas(Canvas c, int x, int y){
        c.drawBitmap(((BitmapDrawable) balloon).getBitmap(), (float) x, (float) y, new Paint());
//        c.drawCircle(x, y, 10, new Paint());
    }

}

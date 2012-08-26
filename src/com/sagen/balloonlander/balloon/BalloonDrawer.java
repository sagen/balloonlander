package com.sagen.balloonlander.balloon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import static android.graphics.Color.TRANSPARENT;
import static com.sagen.balloonlander.ZoomUtil.zoomBoxXPos;
import static com.sagen.balloonlander.ZoomUtil.zoomBoxYPos;

class BalloonDrawer {
    private Paint paint = new Paint();
    private Bitmap bitmap, zoomed3Bitmap;

    BalloonDrawer(Drawable balloon, Drawable zoomed3Balloon){
        this.bitmap = ((BitmapDrawable) balloon).getBitmap();
        this.zoomed3Bitmap = ((BitmapDrawable) zoomed3Balloon).getBitmap();
    }

    boolean transparent(int x, int y){
        return TRANSPARENT == bitmap.getPixel(x, y);
    }

    int width(){
        return bitmap.getWidth();
    }

    int height(){
        return bitmap.getHeight();
    }

    int getLandingAreaXStart(){
        return 15;
    }

    int getLandingAreaXEnd(){
        return 19;
    }

    void drawOnCanvas(Canvas c, int x, int y, int zoomLevel){
        if(zoomLevel > 1){
            c.drawBitmap(zoomed3Bitmap, (float) (x * zoomLevel - zoomBoxXPos(zoomLevel, c.getWidth(), x)),
                    (float) (y * zoomLevel - zoomBoxYPos(zoomLevel, c.getHeight(), y)), paint);
        }else{
            c.drawBitmap(bitmap, (float) x, (float) y, paint);
        }
    }

}

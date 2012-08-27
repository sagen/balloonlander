package com.sagen.balloonlander.client.balloon;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;
import static com.sagen.balloonlander.client.balloon.BalloonPhysics.INITIAL_FUEL;

class FuelGaugeDrawer {
    Paint paint;

    FuelGaugeDrawer() {
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    void drawOnCanvas(Canvas c, double fuel){
        int left = (int) (c.getWidth() * 0.7);
        int right = (int) (c.getWidth() * 0.9);
        int fillWidth = fuel <= 0 ? 0  : (int)((right - left) * (fuel / INITIAL_FUEL));
        paint.setStyle(STROKE);
        c.drawRect(left, 10, right, 30, paint);
        paint.setStyle(FILL);
        c.drawRect(left, 10, left + fillWidth, 30, paint);
    }

}



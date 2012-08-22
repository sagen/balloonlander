package com.sagen.balloonlander.terrain;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import static android.graphics.Color.GREEN;
import static android.graphics.Paint.Style.FILL;

public class TerrainDrawer {

    public void draw(Path path, Canvas c) {
        Paint paint = new Paint();
        paint.setStyle(FILL);
        paint.setColor(GREEN);
        c.drawPath(path, paint);
    }
}

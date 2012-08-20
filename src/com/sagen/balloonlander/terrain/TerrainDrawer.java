package com.sagen.balloonlander.terrain;

import android.graphics.Canvas;
import android.graphics.Paint;

import static android.graphics.Color.GREEN;
import static android.graphics.Paint.Style.FILL;

public class TerrainDrawer {
    private final Terrain terrain;

    public TerrainDrawer(Terrain terrain) {
        this.terrain = terrain;
    }

    public void draw(Canvas c) {
        Paint paint = new Paint();
        paint.setStyle(FILL);
        paint.setColor(GREEN);
        c.drawPath(terrain.getPath(c), paint);
    }
}

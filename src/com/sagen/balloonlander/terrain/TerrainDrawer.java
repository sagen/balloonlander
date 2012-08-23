package com.sagen.balloonlander.terrain;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import static android.graphics.Color.*;
import static android.graphics.Paint.Style.FILL;

public class TerrainDrawer {
    private Paint terrainPaint;
    private Paint landingPaint;

    public TerrainDrawer(){
        terrainPaint = new Paint();
        terrainPaint.setStyle(FILL);
        terrainPaint.setColor(GREEN);
        landingPaint = new Paint();
        landingPaint.setColor(Color.BLUE);
    }

    public void draw(Terrain terrain, Canvas c) {
        c.drawPath(terrain.path, terrainPaint);
        c.drawRect(terrain.startLandingSpot().x(), terrain.startLandingSpot().y(), terrain.endLandingSpot().x(),
                terrain.startLandingSpot().y() + 5, landingPaint);
        c.drawLine(terrain.startLandingSpot().x(), terrain.startLandingSpot().y(), terrain.endLandingSpot().x(),
                terrain.endLandingSpot().y(), landingPaint);

    }
}

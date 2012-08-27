package com.sagen.balloonlander.terrain;

import android.graphics.*;

import static android.graphics.Color.GREEN;
import static android.graphics.Paint.Style.FILL;
import static com.sagen.balloonlander.util.ZoomUtil.zoomBoxXPos;
import static com.sagen.balloonlander.util.ZoomUtil.zoomBoxYPos;

public class TerrainDrawer {
    public static final int LANDING_SPOT_HEIGHT = 5;
    private Paint terrainPaint;
    private Paint landingPaint;
    Path zoomedPath;
    Matrix zoomTransformMatrix;

    public TerrainDrawer(){
        terrainPaint = new Paint();
        terrainPaint.setStyle(FILL);
        terrainPaint.setColor(GREEN);
        landingPaint = new Paint();
        landingPaint.setColor(Color.BLUE);
        zoomedPath = new Path();
        zoomTransformMatrix = new Matrix();
    }

    public void draw(Terrain terrain, Canvas c){
        c.drawPath(terrain.orgPath, terrainPaint);
        TerrainPoint start = terrain.landingStart;
        c.drawRect(start.x, start.y, terrain.landingEnd.x, start.y + LANDING_SPOT_HEIGHT, landingPaint);
    }

    public void draw(Terrain terrain, Canvas c, int zoomLevel, int balloonX, int balloonY, int balloonWidth, int balloonHeight) {
        int zoomX = zoomBoxXPos(zoomLevel, c.getWidth(), balloonX, balloonWidth);
        int zoomY = zoomBoxYPos(zoomLevel, c.getHeight(), balloonY, balloonHeight);
        zoomTransformMatrix.setScale(zoomLevel, zoomLevel);
        zoomTransformMatrix.postTranslate(-zoomX, -zoomY);
        terrain.orgPath.transform(zoomTransformMatrix, zoomedPath);
        c.drawPath(zoomedPath, terrainPaint);
        c.drawRect(terrain.landingStart.x * zoomLevel - zoomX, terrain.landingStart.y * zoomLevel - zoomY,
                terrain.landingEnd.x * zoomLevel - zoomX,
                terrain.landingStart.y * zoomLevel - zoomY + (LANDING_SPOT_HEIGHT * zoomLevel), landingPaint);

    }
}

package com.sagen.balloonlander.client.terrain;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.TreeSet;


public class Terrain extends TreeSet<TerrainPoint> {
    public Path orgPath, transformedPath;
    public int highestYPos;
    TerrainPoint landingStart;
    TerrainPoint landingEnd;
    private TerrainDrawer terrainDrawer = new TerrainDrawer();
    private TerrainPoint[] arrayCache;

    public void generateCacheAndGraphicsPath(int canvasHeight) {
        orgPath = new Path();
        this.arrayCache = toArray(new TerrainPoint[size()]);
        for(TerrainPoint point : this){
            if(point.y < highestYPos){
                highestYPos = point.y;
            }
            if(this.first().equals(point)){
                orgPath.moveTo(point.x, point.y);
                continue;
            }
            orgPath.lineTo(point.x, point.y);
        }
        orgPath.lineTo(last().x, canvasHeight);
        orgPath.lineTo(0, canvasHeight);
        orgPath.close();
        transformedPath = new Path(orgPath);
    }

    public TerrainPoint[] asArray(){
        return arrayCache;
    }

    public static TerrainPoint xPoint(int x) {
        return new TerrainPoint(x, 0);
    }

    public void setOffsetY(int offset) {
        for(TerrainPoint point : this){
            point.y += offset;
        }
    }

    public void setLandingSpot(TerrainPoint from, TerrainPoint to){
        add(from);
        add(to);
        this.landingStart = from;
        this.landingEnd = to;
    }

    public boolean isWithinLandingArea(int xPosFrom, int xPosTo, int yPos){
        return !(landingStart == null || landingEnd == null)
                && yPos >= landingStart.y && xPosFrom < landingEnd.x && xPosTo > landingStart.x;
    }

    public void drawOnCanvas(Canvas c){
        terrainDrawer.draw(this, c);
    }

    public void drawOnCanvas(Canvas c, int zoomLevel, int balloonX, int balloonY, int balloonWidth, int balloonHeight){
        terrainDrawer.draw(this, c, zoomLevel, balloonX, balloonY, balloonWidth, balloonHeight);
    }


}

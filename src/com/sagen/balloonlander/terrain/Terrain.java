package com.sagen.balloonlander.terrain;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.TreeSet;

import static java.lang.Integer.MAX_VALUE;

public class Terrain extends TreeSet<TerrainPoint> {
    public Path path;
    int highestYPos;
    private TerrainPoint landingSpotStart;
    private TerrainPoint landingSpotEnd;
    private TerrainDrawer terrainDrawer = new TerrainDrawer();
    private TerrainPoint[] arrayCache;

    public void generateCacheAndGraphicsPath(int sceneHeight) {
        path = new Path();
        this.arrayCache = toArray(new TerrainPoint[size()]);
        for(TerrainPoint point : this){
            if(this.first().equals(point)){
                path.moveTo(point.x(), point.y());
                continue;
            }
            path.lineTo(point.x(), point.y());
        }
        path.lineTo(this.last().x(), sceneHeight);
        path.lineTo(0, sceneHeight);
        path.close();
    }

    public TerrainPoint[] asArray(){
        return arrayCache;
    }

    public static TerrainPoint xPoint(int x) {
        return new TerrainPoint(x, 0);
    }

    public void setOffsetY(int offset) {
        for(TerrainPoint point : this){
            point.offsetY = offset;
        }
    }

    public int highestYPos(){
        if(highestYPos == MAX_VALUE){
            for(TerrainPoint point : this){
                if(point.y() <  highestYPos){
                    highestYPos = point.y();
                }
            }
        }
        return highestYPos;
    }

    public void setLandingSpot(TerrainPoint from, TerrainPoint to){
        add(from);
        add(to);
        this.landingSpotStart = from;
        this.landingSpotEnd = to;
    }

    public boolean isWithinLandingArea(int xPosFrom, int xPosTo, int yPos){
        return !(landingSpotStart == null || landingSpotEnd == null)
                && yPos >= landingSpotStart.y() && xPosFrom < landingSpotEnd.x() && xPosTo > landingSpotStart.x();
    }

    public void drawOnCanvas(Canvas c){
        terrainDrawer.draw(path, c);
    }


}

package com.sagen.balloonlander.terrain;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.TreeSet;

import static java.lang.Integer.MAX_VALUE;

public class Terrain extends TreeSet<TerrainPoint> {
    public Path pathCache;
    int highestYPos;
    private TerrainPoint landingSpotStart;
    private TerrainPoint landingSpotEnd;

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

    public Path getPath(Canvas c){
        if(pathCache == null){
            pathCache = new Path();
            for(TerrainPoint point : this){
                if(this.first().equals(point)){
                    pathCache.moveTo(point.x(), point.y());
                    continue;
                }
                pathCache.lineTo(point.x(), point.y());
            }
            pathCache.lineTo(c.getWidth(), c.getHeight());
            pathCache.lineTo(0, c.getHeight());
            pathCache.close();
        }
        return pathCache;
    }


}

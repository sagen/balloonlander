package com.sagen.balloonlander;


import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;
import com.sagen.balloonlander.terrain.TerrainPoint;

public class CollisionDetector {
    public static boolean collides(Balloon balloon, Terrain terrain){
        if(balloon.y() + balloon.height() < terrain.highestYPos())
            return false;
        for(TerrainPoint p : terrain.asArray()){
            if(p.x() > balloon.x() + balloon.width())
                return false;
            if(!balloon.transparent(p.x() - balloon.x(), p.y() - balloon.y()))
                return true;
        }
        return false;
    }
}

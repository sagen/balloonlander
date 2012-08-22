package com.sagen.balloonlander;


import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;
import com.sagen.balloonlander.terrain.TerrainPoint;

import java.util.NavigableSet;

import static com.sagen.balloonlander.terrain.Terrain.xPoint;

public class CollisionDetector {
    public static boolean collides(Balloon balloon, Terrain terrain){
        if(balloon.y() + balloon.height() < terrain.highestYPos())
            return false;

        NavigableSet<TerrainPoint> subTerrain = terrain.subSet(xPoint(balloon.x()), true, xPoint(balloon.x() + balloon.width()), true);
        boolean belowHighestTerrainSpot = false;

        for(TerrainPoint p : subTerrain){
            if(p.y() <= balloon.y() + balloon.height()){
                belowHighestTerrainSpot = true;
            }
        }

        if(!belowHighestTerrainSpot){
            return false;
        }

        for(TerrainPoint p : subTerrain){
            if(!balloon.transparent(p.x() - balloon.x(), p.y() - balloon.y())){
                return true;
            }
        }

        return false;
    }
}

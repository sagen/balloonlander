package com.sagen.balloonlander;


import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;
import com.sagen.balloonlander.terrain.TerrainPoint;

import static java.lang.Math.abs;

public class ProximityDetector {

    public static boolean collides(Balloon balloon, Terrain terrain){
        if(balloon.physics.roundedX + balloon.height < terrain.highestYPos)
            return false;
        for(TerrainPoint p : terrain.asArray()){
            if(p.x > balloon.physics.roundedX + balloon.width)
                return false;
            if(!balloon.transparent(p.x - balloon.physics.roundedX, p.y - balloon.physics.roundedY))
                return true;
        }
        return false;
    }

    public static boolean lands(Balloon balloon, Terrain terrain){
        return terrain.isWithinLandingArea(balloon.landingAreaXPosStart(), balloon.landingAreaXPosEnd(),
                balloon.physics.roundedY + balloon.height);
    }

    public static boolean landedHard(Balloon balloon){
        return abs(balloon.dx()) > 0.5 || abs(balloon.dy()) > 1;
    }

    public static boolean shouldZoom(Balloon balloon, Terrain terrain){
        if(balloon.physics.roundedY + (balloon.height * 4) < terrain.highestYPos)
            return false;

        for(TerrainPoint p : terrain.asArray()){
            if(p.x > balloon.physics.roundedX + (balloon.width * 3))
                return false;

            if(p.x > balloon.physics.roundedX - (balloon.width * 3) && p.y < balloon.physics.roundedY + balloon.height * 4)
                return true;
        }
        return false;
    }
}

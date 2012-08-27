package com.sagen.balloonlander.client.util;


import com.sagen.balloonlander.client.balloon.BalloonController;
import com.sagen.balloonlander.client.terrain.Terrain;
import com.sagen.balloonlander.client.terrain.TerrainPoint;

import static java.lang.Math.abs;

public class ProximityDetector {

    public static boolean collides(BalloonController balloon, Terrain terrain){
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

    public static boolean lands(BalloonController balloon, Terrain terrain){
        return terrain.isWithinLandingArea(balloon.landingAreaXPosStart(), balloon.landingAreaXPosEnd(),
                balloon.physics.roundedY + balloon.height);
    }

    public static boolean landedHard(BalloonController balloon){
        return abs(balloon.dx()) > 0.5 || abs(balloon.dy()) > 0.7;
    }

    public static boolean shouldZoom(BalloonController balloon, Terrain terrain){
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

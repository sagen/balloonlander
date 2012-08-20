package com.sagen.balloonlander;


import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;

public class LandingDetector {
    public static boolean landed(Terrain terrain, Balloon balloon){
        return terrain.isWithinLandingArea(balloon.landingAreaXPosStart(), balloon.landingAreaXPosEnd(), balloon.y() + balloon.height());
    }
}

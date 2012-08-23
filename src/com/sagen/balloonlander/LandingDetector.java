package com.sagen.balloonlander;


import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;

import static java.lang.Math.abs;

public class LandingDetector {
    public static boolean lands(Balloon balloon, Terrain terrain){
        return terrain.isWithinLandingArea(balloon.landingAreaXPosStart(), balloon.landingAreaXPosEnd(), balloon.y() + balloon.height());
    }

    public static boolean landedHard(Balloon balloon){
        return abs(balloon.dx()) > 0.5 || abs(balloon.dy()) > 1;
    }
}

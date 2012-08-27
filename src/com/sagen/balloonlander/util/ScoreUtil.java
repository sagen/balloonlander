package com.sagen.balloonlander.util;

import com.sagen.balloonlander.balloon.BalloonController;

public class ScoreUtil {
    public static int getScoreAfterLanding(BalloonController balloon){
        return (int) (1000000 - ((100 - balloon.fuel()) * 6700) - (balloon.dx() * 330000) - (balloon.dy() * 230000));
    }
}

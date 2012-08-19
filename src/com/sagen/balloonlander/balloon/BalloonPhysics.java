package com.sagen.balloonlander.balloon;


import static java.lang.Math.*;

public class BalloonPhysics {
    public final static int MAX_Y_UP_SPEED = -3;
    public final static int MAX_Y_DOWN_SPEED = 7;
    private double x, y;
    private double dx, dy;


    public BalloonPhysics() {}

    public void tick(boolean propulseUp, boolean propulseRight, boolean propulseLeft, int width, int height, int balloonWidth, int balloonHeight) {
        updateY(propulseUp, height, balloonHeight);
        updateX(propulseRight, propulseLeft, width, balloonWidth);
    }

    private void updateX(boolean propulseRight, boolean propulseLeft, int width, int balloonWidth) {
        dx += propulseRight ? 0.15 : 0;
        dx -= propulseLeft ? 0.15 : 0;
        if(x < 0 && dx < 0){
            dx = 0;
        }else if(x + balloonWidth > width && dx > 0){
            dx = 0;
        }else if(!propulseLeft && !propulseRight && dx != 0){
            if(dx > -0.05 && dx < 0.05){
                dx = 0;
            }else{
                dx += (dx > 0) ? -0.05 : 0.05;
            }
        }
        x += dx;
    }

    private void updateY(boolean propulseUp, int height, int balloonHeight) {
        dy -= propulseUp ? 0.1 : -0.03;
        dy = min(MAX_Y_DOWN_SPEED, max(dy, MAX_Y_UP_SPEED));

        if(y + balloonHeight >= height && dy > 0){
            dy = 0;
            y = height;
        }else{
            y += dy;
        }
    }

    public int x() {
        return (int) round(x);
    }

    public int y() {
        return (int) round(y);
    }
}

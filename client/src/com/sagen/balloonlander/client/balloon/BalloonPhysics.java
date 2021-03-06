package com.sagen.balloonlander.client.balloon;


import static java.lang.Math.*;
import static java.lang.System.nanoTime;

public class BalloonPhysics {
    private final static int MAX_Y_UP_SPEED = -2;
    private final static int MAX_Y_DOWN_SPEED = 4;
    private static final int MAX_X_SPEED = 4;
    private static final double HORIZONTAL_ACCELERATION_INCREASE_PER_SECOND = 7;
    private static final double HORIZONTAL_ACCELERATION_DECREASE_PER_SECOND = 0.6;
    private static final double VERTICAL_ACCELERATION_INCREASE_PER_SECOND = 2.5;
    private static final double GRAVITY_PER_SECOND = 0.8;
    static final int INITIAL_FUEL = 100;
    private static final double DECREASE_FUEL_PER_CONSUMPTION_SECOND = 20;

    private double fuel;
    private double x, y;
    private double dx, dy;
    private long lastUpdate = nanoTime();
    private int sceneWidth;
    private int balloonWidth;
    private boolean movingUp;
    private boolean movingRight;
    private boolean movingLeft;
    public int roundedX, roundedY;

    BalloonPhysics(int sceneWidth, int balloonWidth) {
        this.sceneWidth = sceneWidth;
        this.balloonWidth = balloonWidth;
        fuel = INITIAL_FUEL;
        x = sceneWidth / 2 - balloonWidth / 2;
    }

    void update(long now) {
        yMovementSinceLastUpdate(now);
        xMovementSinceLastUpdate(now);
        fuelSinceLastUpdate(now);
        lastUpdate = now;
    }

    private void fuelSinceLastUpdate(long now) {
        double decrease = secondsSinceLastUpdate(now) * DECREASE_FUEL_PER_CONSUMPTION_SECOND;
        if(movingUp) {
            fuel -= decrease;
        }
        if(movingLeft){
            fuel -= decrease;
        }
        if(movingRight){
            fuel -= decrease;
        }
        if(fuel < 0){
            fuel = 0;
        }
    }

    private double secondsSinceLastUpdate(long now) {
        return ((double) (now - lastUpdate)) / (1000d * 1000d * 1000d);
    }

    private void xMovementSinceLastUpdate(long now) {
        accelerateHorizontally(now);
        decreaseSpeedIfIdle(now);
        limitHorizontalAcceleration();
        stopIfHittingBoundaries();
        x += dx;
        roundedX = (int) round(x);
    }

    private void limitHorizontalAcceleration() {
        dx = max(min(dx, MAX_X_SPEED), -MAX_X_SPEED);
    }

    private void yMovementSinceLastUpdate(long now) {
        updateVerticalAcceleration(now);
        limitVerticalAcceleration();
        y += dy;
        roundedY = (int) round(y);
    }

    private void decreaseSpeedIfIdle(long now) {
        if (movingLeft || movingRight || dx == 0)
            return;
        if (abs(dx) < HORIZONTAL_ACCELERATION_DECREASE_PER_SECOND * secondsSinceLastUpdate(now)) {
            dx = 0;
        } else if (dx > 0) {
            dx -= HORIZONTAL_ACCELERATION_DECREASE_PER_SECOND * secondsSinceLastUpdate(now);
        } else {
            dx += HORIZONTAL_ACCELERATION_DECREASE_PER_SECOND * secondsSinceLastUpdate(now);
        }
    }

    private void stopIfHittingBoundaries() {
        if (x < 0 && dx < 0 || (x + balloonWidth > sceneWidth && dx > 0)) {
            dx = 0;
        }
    }

    private void accelerateHorizontally(long now) {
        if (movingRight) {
            dx += secondsSinceLastUpdate(now) * HORIZONTAL_ACCELERATION_INCREASE_PER_SECOND;
        }
        if (movingLeft) {
            dx -= secondsSinceLastUpdate(now) * HORIZONTAL_ACCELERATION_INCREASE_PER_SECOND;
        }
    }

    private void limitVerticalAcceleration() {
        dy = min(MAX_Y_DOWN_SPEED, max(dy, MAX_Y_UP_SPEED));
    }

    private void updateVerticalAcceleration(long now) {
        if(movingUp){
            dy -= VERTICAL_ACCELERATION_INCREASE_PER_SECOND * secondsSinceLastUpdate(now);
        }else{
            dy += GRAVITY_PER_SECOND * secondsSinceLastUpdate(now);
        }
    }

    public void up(boolean enable) {
        this.movingUp = enable;
    }

    public void right(boolean enable) {
        this.movingRight = enable;
    }

    public void left(boolean enable) {
        this.movingLeft = enable;
    }

    double dx(){
        return dx;
    }

    double dy(){
        return dy;
    }

    double fuel(){
        return fuel;
    }

}

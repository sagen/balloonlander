package com.sagen.balloonlander.balloon;


import static java.lang.Math.*;
import static java.lang.System.nanoTime;

class BalloonPhysics {
    private final static int MAX_Y_UP_SPEED = -3;
    private final static int MAX_Y_DOWN_SPEED = 7;
    private static final int MAX_X_SPEED = 5;
    public static final double HORIZONTAL_ACCELERATION_INCREASE_PER_SECOND = 9;
    public static final double HORIZONTAL_ACCELERATION_DECREASE_PER_SECOND = 3;
    public static final double VERTICAL_ACCELERATION_INCREASE_PER_TICK = 6;

    public static final double GRAVITY_PER_TICK = 1.8;
    private double x, y;
    private double dx, dy;
    private long lastUpdate = nanoTime();
    private int sceneWidth;
    private int balloonWidth;
    private boolean movingUp;
    private boolean movingRight;
    private boolean movingLeft;

    BalloonPhysics(int sceneWidth, int balloonWidth) {
        this.sceneWidth = sceneWidth;
        this.balloonWidth = balloonWidth;
    }

    void update(long now) {
        yMovementSinceLastUpdate(now);
        xMovementSinceLastUpdate(now);
        lastUpdate = now;
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
    }

    private void limitHorizontalAcceleration() {
        dx = max(min(dx, MAX_X_SPEED), -MAX_X_SPEED);
    }

    private void yMovementSinceLastUpdate(long now) {
        updateVerticalAcceleration(now);
        limitVerticalAcceleration();
        y += dy;
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
            dy -= VERTICAL_ACCELERATION_INCREASE_PER_TICK * secondsSinceLastUpdate(now);
        }else{
            dy += GRAVITY_PER_TICK * secondsSinceLastUpdate(now);
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

    int x() {
        return (int) round(x);
    }

    int y() {
        return (int) round(y);
    }

    double dx(){
        return dx;
    }

    double dy(){
        return dy;
    }

}

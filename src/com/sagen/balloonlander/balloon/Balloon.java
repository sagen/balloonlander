package com.sagen.balloonlander.balloon;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Balloon  {
    BalloonDrawer drawer;
    BalloonPhysics physics;

    public Balloon(Drawable balloonImage, int sceneWidth) {
        drawer = new BalloonDrawer(balloonImage);
        physics = new BalloonPhysics(sceneWidth, drawer.width());
    }

    public int x() {
        return physics.x();
    }

    public boolean transparent(int x, int y) {
        return x < 0 || y < 0 || x >= drawer.width() || y >= drawer.height() || drawer.transparent(x, y);
    }

    public int y() {
        return physics.y();
    }

    public int width() {
        return drawer.width();
    }

    public int height() {
        return drawer.height();
    }

    public int landingAreaXPosStart() {
        return drawer.getLandingAreaXStart() + physics.x();
    }

    public int landingAreaXPosEnd() {
        return drawer.getLandingAreaXEnd() + physics.x();
    }

    public void updatePhysics(long now) {
        physics.update(now);
    }

    public void drawOnCanvas(Canvas c) {
        drawer.drawOnCanvas(c, physics.x(), physics.y());
    }

    public void left(boolean enable) {
        physics.left(enable);
    }

    public void right(boolean enable) {
        physics.right(enable);
    }

    public void up(boolean enable) {
        physics.up(enable);
    }

    public double dx() {
        return physics.dx();
    }

    public double dy() {
        return physics.dy();
    }
}

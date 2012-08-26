package com.sagen.balloonlander.balloon;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Balloon  {
    final BalloonDrawer drawer;
    final FuelGaugeDrawer fuelDrawer;
    final public BalloonPhysics physics;
    public final int width, height;

    public Balloon(Drawable balloonImage, Drawable zoomed3BalloonImage, int sceneWidth) {
        drawer = new BalloonDrawer(balloonImage, zoomed3BalloonImage);
        physics = new BalloonPhysics(sceneWidth, drawer.width());
        fuelDrawer = new FuelGaugeDrawer();
        width = drawer.width();
        height = drawer.height();
    }

    public boolean transparent(int x, int y) {
        return x < 0 || y < 0 || x >= width || y >= height || drawer.transparent(x, y);
    }

    public int landingAreaXPosStart() {
        return drawer.getLandingAreaXStart() + physics.roundedX;
    }

    public int landingAreaXPosEnd() {
        return drawer.getLandingAreaXEnd() + physics.roundedX;
    }

    public void updatePhysics(long now) {
        physics.update(now);
    }

    public void drawOnCanvas(Canvas c, int zoomLevel) {
        drawer.drawOnCanvas(c, physics.roundedX, physics.roundedY, zoomLevel);
        fuelDrawer.drawOnCanvas(c, physics.fuel());
    }

    public void left(boolean enable) {
        physics.left(enable);
    }

    public double fuel() {
        return physics.fuel();
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

package com.sagen.balloonlander.client.balloon;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class BalloonController {
    public static final int LANDABLE_SURFACE_X_START = 15;
    public static final int LANDABLE_SURFACE_X_END = 19;
    final BalloonDrawer drawer;
    final FuelGaugeDrawer fuelDrawer;
    final public BalloonPhysics physics;
    public final int width, height;

    public BalloonController(Drawable balloonImage, Drawable balloonImageZoomed, int sceneWidth) {
        drawer = new BalloonDrawer(balloonImage, balloonImageZoomed);
        physics = new BalloonPhysics(sceneWidth, drawer.width());
        fuelDrawer = new FuelGaugeDrawer();
        width = drawer.width();
        height = drawer.height();
    }

    public boolean transparent(int x, int y) {
        return x < 0 || y < 0 || x >= width || y >= height || drawer.transparent(x, y);
    }

    public int landingAreaXPosStart() {
        return LANDABLE_SURFACE_X_START + physics.roundedX;
    }

    public int landingAreaXPosEnd() {
        return LANDABLE_SURFACE_X_END + physics.roundedX;
    }

    public void updatePhysics(long now) {
        physics.update(now);
    }

    public void drawOnCanvas(Canvas c, int zoomLevel) {
        drawer.drawOnCanvas(c, physics.roundedX, physics.roundedY, zoomLevel);
        fuelDrawer.drawOnCanvas(c, physics.fuel());
    }

    public double fuel() {
        return physics.fuel();
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

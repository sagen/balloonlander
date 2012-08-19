package com.sagen.balloonlander.balloon;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Balloon {
    BalloonDrawer drawer;
    BalloonPhysics physics;

    public Balloon(Drawable balloonImage) {
        drawer = new BalloonDrawer(balloonImage);
        physics = new BalloonPhysics();
    }

    public int x(){
        return physics.x();
    }

    public boolean transparent(int x, int y) {
        return x < 0 || y < 0 || x >= drawer.width() || y >= drawer.height() || drawer.transparent(x, y);
    }

    public int y(){
        return physics.y();
    }

    public int width(){
        return drawer.width();
    }

    public int height() {
        return drawer.height();
    }

    public void tick(boolean upPropulsion, boolean rightPropulsion, boolean leftPropulsion, int width, int height) {
        physics.tick(upPropulsion, rightPropulsion, leftPropulsion, width, height, drawer.width(), drawer.height());
    }

    public void drawOnCanvas(Canvas c) {
        drawer.drawOnCanvas(c, physics.x(), physics.y());
    }
}

package com.sagen.balloonlander;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;

import static android.graphics.Color.BLACK;
import static android.view.MotionEvent.ACTION_UP;
import static com.sagen.balloonlander.CollisionDetector.collides;
import static com.sagen.balloonlander.DebugDrawer.*;
import static com.sagen.balloonlander.LandingDetector.landedHard;
import static com.sagen.balloonlander.LandingDetector.lands;
import static com.sagen.balloonlander.terrain.TerrainCreator.generateTerrain;
import static java.lang.System.nanoTime;


public class GameLoop extends Thread implements View.OnTouchListener {
    private Balloon balloon;
    private SurfaceHolder surfaceHolder;
    private final Terrain terrain;

    public GameLoop(Balloon balloon, SurfaceHolder surfaceHolder, int width, int height) {
        this.balloon = balloon;
        this.surfaceHolder = surfaceHolder;
        terrain = generateTerrain(width, height);
    }

    @Override
    public void run() {
        double fps = -1;
        double lastNow = nanoTime();
        double now;
        Canvas c;
        while (true) {
            now = nanoTime();
            if (fps == -1) {
                fps = 1000000000d / (now - lastNow);
            } else {
                fps = fps * 0.9d + (1000000000d / (now - lastNow)) * 0.1d;
            }
            c = surfaceHolder.lockCanvas();
            if (c == null) {
                return;
            }
            balloon.updatePhysics((long) now);
            c.drawColor(BLACK);
            terrain.drawOnCanvas(c);
            balloon.drawOnCanvas(c);
            drawDebugInfo(c, balloon, fps);
            if (balloon.fuel() <= 0) {
                DebugDrawer.drawDebugInfoOutOfFuel(c);
                break;
            } else if (collides(balloon, terrain)) {
                drawDebugInfoCrashed(c);
                break;
            } else if (lands(balloon, terrain)) {
                if (landedHard(balloon)) {
                    drawDebugInfoLandedTooHard(c);
                } else {
                    drawDebugInfoLanded(c);
                }
                break;
            }
            surfaceHolder.unlockCanvasAndPost(c);
            lastNow = now;
        }
        surfaceHolder.unlockCanvasAndPost(c);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        if (motionEvent.getY() > surfaceHolder.getSurfaceFrame().height() * 0.75) {
            balloon.up(motionEvent.getAction() != ACTION_UP);
        } else if (motionEvent.getX() < surfaceHolder.getSurfaceFrame().width() * 0.3) {
            balloon.right(motionEvent.getAction() != ACTION_UP);
        } else if (motionEvent.getX() > surfaceHolder.getSurfaceFrame().width() * 0.6) {
            balloon.left(motionEvent.getAction() != ACTION_UP);
        }
        return true;

    }
}

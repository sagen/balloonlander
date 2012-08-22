package com.sagen.balloonlander;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;

import static android.graphics.Color.BLACK;
import static android.view.MotionEvent.ACTION_UP;
import static com.sagen.balloonlander.CollisionDetector.collides;
import static com.sagen.balloonlander.DebugDrawer.drawDebugInfo;
import static com.sagen.balloonlander.DebugDrawer.drawDebugInfoCrashed;
import static com.sagen.balloonlander.DebugDrawer.drawDebugInfoLanded;
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
        double fps = 0;
        double lastNow = nanoTime();
        Canvas c = null;
        while (true) {
            double now = nanoTime();
            fps = fps * 0.9d + (1000000000d / (now - lastNow)) * 0.1d;
            c = surfaceHolder.lockCanvas();
            if (c == null) {
                return;
            }
            balloon.updatePhysics((long)now);
            clearCanvas(c);
            terrain.drawOnCanvas(c);
            balloon.drawOnCanvas(c);
            drawDebugInfo(c, balloon, fps);
            if (collides(balloon, terrain)) {
                drawDebugInfoCrashed(c);
                break;
            }else if(lands(balloon, terrain)){
                drawDebugInfoLanded(c);
                break;
            }
            surfaceHolder.unlockCanvasAndPost(c);
            lastNow = now;
        }
        surfaceHolder.unlockCanvasAndPost(c);
    }

    private void clearCanvas(Canvas c) {
        c.drawColor(BLACK);
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

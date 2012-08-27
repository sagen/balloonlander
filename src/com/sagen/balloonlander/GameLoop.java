package com.sagen.balloonlander;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import com.sagen.R;
import com.sagen.balloonlander.balloon.BalloonController;
import com.sagen.balloonlander.terrain.Terrain;

import static android.graphics.Color.BLACK;
import static android.view.MotionEvent.*;
import static com.sagen.R.drawable.*;
import static com.sagen.balloonlander.DebugDrawer.*;
import static com.sagen.balloonlander.ProximityDetector.*;
import static com.sagen.balloonlander.terrain.TerrainCreator.generateTerrain;
import static java.lang.System.nanoTime;


public class GameLoop extends Thread implements View.OnTouchListener {
    public static final int ZOOM_LEVEL = 2;
    private final BalloonController balloon;
    private final SurfaceHolder surfaceHolder;
    private final Terrain terrain;
    private final ArrowDrawer arrowDrawer;
    private final int width;

    public GameLoop(Panel view, SurfaceHolder surfaceHolder) {
        this.width = view.getWidth();
        balloon = new BalloonController(view.drawable(R.drawable.balloon), view.drawable(balloon_zoom2), view.getWidth());
        this.surfaceHolder = surfaceHolder;
        terrain = generateTerrain(view.getWidth(), view.getHeight());
        this.arrowDrawer = new ArrowDrawer(view.drawable(arrow));
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
            if (shouldZoom(balloon, terrain)) {
                terrain.drawOnCanvas(c, ZOOM_LEVEL, balloon.physics.roundedX, balloon.physics.roundedY,
                        balloon.width, balloon.height);
                balloon.drawOnCanvas(c, ZOOM_LEVEL);
            } else {
                terrain.drawOnCanvas(c);
                balloon.drawOnCanvas(c, 1);
            }

            drawDebugInfo(c, balloon, fps);
            arrowDrawer.drawOnCanvas(c);
            //drawGuideLines(c);
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
    public boolean onTouch(View v, MotionEvent m) {
        boolean up = false, right = false, left = false;
        for (int i = 0; i < m.getPointerCount(); i++) {
            if (m.getActionIndex() == i && m.getActionMasked() == ACTION_POINTER_UP || m.getAction() == ACTION_UP) {
                continue;
            }
            if (m.getX(i) < width * 0.33) {
                right = true;
            } else if (m.getX(i) > width * 0.67) {
                left = true;
            } else {
                up = true;
            }
        }
        balloon.up(up);
        balloon.left(left);
        balloon.right(right);
        return true;

    }
}

package com.sagen.balloonlander;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;
import com.sagen.balloonlander.terrain.TerrainDrawer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.sagen.balloonlander.CollisionDetector.collides;
import static com.sagen.balloonlander.LandingDetector.lands;
import static com.sagen.balloonlander.terrain.TerrainCreator.generateTerrain;


public class GameLoop extends Thread {
    private Balloon balloon;
    private SurfaceHolder surfaceHolder;
    final Semaphore mutexRefresh = new Semaphore(0);
    final Semaphore mutexRefreshing = new Semaphore(1);
    boolean upPropulsion = false;
    boolean rightPropulsion = false;
    boolean leftPropulsion = false;
    private TerrainDrawer terrainDrawer;
    private final Terrain terrain;

    public GameLoop(Balloon balloon, SurfaceHolder surfaceHolder, int width, int height) {
        this.balloon = balloon;
        this.surfaceHolder = surfaceHolder;
        terrain = generateTerrain(width, height);
        this.terrainDrawer = new TerrainDrawer(terrain);
    }

    @Override
    public void run() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mutexRefreshing.tryAcquire()) {
                    mutexRefreshing.release();
                    mutexRefresh.release();
                }
            }
        }, 0, 1000 / 60);
        boolean collision = false, landed = false;
        Canvas c = null;
        while (true) {
            try {
                mutexRefresh.acquire();
                mutexRefreshing.acquire();
                c = surfaceHolder.lockCanvas();
                if (c == null) {
                    return;
                }
                balloon.tick(upPropulsion, rightPropulsion, leftPropulsion, c.getWidth(), c.getHeight());
                if(lands(balloon, terrain)){
                    landed = true;
                }else if (collides(balloon, terrain)) {
                    collision = true;
                }
                clearCanvas(c);
                terrainDrawer.draw(c);
                balloon.drawOnCanvas(c);
                balloon.drawDebugInfo(c);
                if (collision || landed) {
                    Paint paint = new Paint();
                    paint.setColor(WHITE);
                    c.drawText(collision? "Kaputttt!!" : "WEEEEEEE!!!", 10, 60, paint);
                    return;
                }
            } catch (InterruptedException irEx) {
                break;
            }finally{
                if(c != null){
                    surfaceHolder.unlockCanvasAndPost(c);
                }
                mutexRefreshing.release();
            }
        }
    }

    private void clearCanvas(Canvas c) {
        c.drawColor(BLACK);
    }

}

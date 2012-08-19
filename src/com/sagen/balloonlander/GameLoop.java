package com.sagen.balloonlander;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.widget.Toast;
import com.sagen.balloonlander.balloon.Balloon;
import com.sagen.balloonlander.terrain.Terrain;
import com.sagen.balloonlander.terrain.TerrainPoint;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.WHITE;
import static android.graphics.Paint.Style.FILL;
import static com.sagen.balloonlander.terrain.TerrainCreator.generateTerrain;


public class GameLoop extends Thread {
    private Balloon balloon;
    private SurfaceHolder surfaceHolder;
    final Semaphore mutexRefresh = new Semaphore(0);
    final Semaphore mutexRefreshing = new Semaphore(1);
    boolean upPropulsion = false;
    boolean rightPropulsion = false;
    boolean leftPropulsion = false;
    private Terrain terrain;
    private MyActivity context;

    public GameLoop(MyActivity context,Balloon balloon, SurfaceHolder surfaceHolder, int width, int height) {
        this.context = context;
        this.balloon = balloon;
        this.surfaceHolder = surfaceHolder;
        this.terrain = generateTerrain(width, height);
    }

    @Override
    public void run() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mutexRefreshing.tryAcquire()){
                    mutexRefreshing.release();
                    mutexRefresh.release();
                }
            }
        }, 0, 1000/60);

        while (true) {
            try{
                mutexRefresh.acquire();
                mutexRefreshing.acquire();
                Canvas c = surfaceHolder.lockCanvas();
                balloon.tick(upPropulsion, rightPropulsion, leftPropulsion, c.getWidth(), c.getHeight());
                if(CollisionDetector.collides(balloon, terrain)){
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Paint paint = new Paint();
                            paint.setColor(WHITE);
                            Canvas c = surfaceHolder.lockCanvas();
                            c.drawText("Kaputttt!!", 10, 10, paint);
                            surfaceHolder.unlockCanvasAndPost(c);
                        }
                    });
                }
                clearCanvas(c);
                drawTerrain(c);
                balloon.drawOnCanvas(c);
                surfaceHolder.unlockCanvasAndPost(c);
                mutexRefreshing.release();
            }catch(InterruptedException irEx){
                break;
            }
        }
    }
    private void clearCanvas(Canvas c) {
        c.drawColor(Color.BLACK);
    }

    private void drawTerrain(Canvas c) {
        Paint paint = new Paint();
        paint.setStyle(FILL);
        paint.setColor(GREEN);
        c.drawPath(terrain.getPath(c), paint);
    }
}

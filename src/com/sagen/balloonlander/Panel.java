package com.sagen.balloonlander;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.sagen.R;
import com.sagen.balloonlander.balloon.Balloon;


public class Panel extends SurfaceView implements SurfaceHolder.Callback {
    GameLoop gameLoop;
    private GameStarter gameStarter;

    public Panel(MainActivity context) {
        super(context);
        while (getHolder() == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                return;
            }
        }
        getHolder().addCallback(this);
    }

    class GameStarter extends Thread {
        private SurfaceHolder surfaceHolder;

        GameStarter(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            while (true) {
                Balloon balloon = new Balloon(getResources().getDrawable(R.drawable.balloon),
                        getResources().getDrawable(R.drawable.balloon_zoom3), getWidth());
                gameLoop = new GameLoop(balloon, surfaceHolder, getWidth(), getHeight());
                setOnTouchListener(gameLoop);
                gameLoop.start();
                try {
                    gameLoop.join();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Log.e("GameStarter", "Interrupted", e);
                    e.printStackTrace();
                    return;
                }

            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.gameStarter = new GameStarter(surfaceHolder);
        this.gameStarter.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.gameStarter.interrupt();
    }
}


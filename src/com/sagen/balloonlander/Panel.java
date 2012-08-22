package com.sagen.balloonlander;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.sagen.R;
import com.sagen.balloonlander.balloon.Balloon;



public class Panel extends SurfaceView implements SurfaceHolder.Callback {
    GameLoop gameLoop;

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

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Balloon balloon = new Balloon(getResources().getDrawable(R.drawable.balloon), getWidth());
        gameLoop = new GameLoop(balloon, surfaceHolder, getWidth(), getHeight());
        setOnTouchListener(gameLoop);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}


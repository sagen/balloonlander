package com.sagen.balloonlander;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.sagen.R;
import com.sagen.balloonlander.balloon.Balloon;

import static android.view.MotionEvent.ACTION_UP;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {
    GameLoop gameLoop;

    public Panel(MainActivity context) {
        super(context);
        while(getHolder() == null){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameLoop = new GameLoop(new Balloon(getResources().getDrawable(R.drawable.balloon)), surfaceHolder, getWidth(), getHeight());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == ACTION_UP) {
                    gameLoop.upPropulsion = false;
                    gameLoop.leftPropulsion = false;
                    gameLoop.rightPropulsion = false;
                } else if (motionEvent.getY() > ((float) getHeight()) * 0.75f) {
                    gameLoop.upPropulsion = true;
                } else if (motionEvent.getX() < ((float) getWidth()) * 0.3f) {
                    gameLoop.rightPropulsion = true;
                } else if (motionEvent.getX() > ((float) getWidth()) * 0.6f) {
                    gameLoop.leftPropulsion = true;
                }
                return true;
            }
        });
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}
}


package com.sagen.balloonlander.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import static android.graphics.Bitmap.createBitmap;

public class ArrowDrawer {
    private Bitmap up, left, right;
    private Paint paint;

    public ArrowDrawer(Drawable arrow) {
        this.up = ((BitmapDrawable)arrow).getBitmap();
        Matrix rightTransformation = new Matrix();
        rightTransformation.setRotate(90);
        Matrix leftTransformation = new Matrix();
        leftTransformation.setRotate(270);
        this.left = createBitmap(this.up, 0, 0, this.up.getWidth(), this.up.getHeight(), leftTransformation, false);
        this.right = createBitmap(this.up, 0, 0, this.up.getWidth(), this.up.getHeight(), rightTransformation, false);
        this.paint = new Paint();
    }

    public void drawOnCanvas(Canvas c){
        c.drawBitmap(right, 10, c.getHeight() - 10 - right.getHeight(), paint);
        c.drawBitmap(left, c.getWidth() - 10 - left.getWidth(), c.getHeight() - 10 - left.getHeight(), paint);
        c.drawBitmap(up, c.getWidth() / 2 - up.getWidth() / 2, c.getHeight() - 10 - up.getHeight(), paint);

    }
}

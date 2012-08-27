package com.sagen.balloonlander.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.sagen.balloonlander.balloon.BalloonController;

import java.text.NumberFormat;
import java.util.Locale;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.WHITE;
import static java.lang.Math.abs;

public class DebugDrawer {
    private static Paint paint = new Paint();
    private static char[] horText, verText, fpsText; // Avoiding string allocations

    static {
        paint.setColor(GREEN);
        horText = "Hor:  -0.0".toCharArray();
        verText = "Ver:  -0.0".toCharArray();
        fpsText = "FPS: 000".toCharArray();
    }

    public static void drawDebugInfo(Canvas c, BalloonController b, double fps) {
        drawAcceleration(c, b.dx(), 10, horText);
        drawAcceleration(c, b.dy(), 25, verText);
        drawFPS(c, fps);
    }

    private static void drawFPS(Canvas c, double fps) {
        fpsText[5] = (fps > 99) ? (char) (((int) '0') + (int) fps / 100) : ' ';
        fpsText[6] = (fps > 9) ? (char) (((int) '0') + (int) (fps % 100) / 10) : ' ';
        fpsText[7] = (char) (((int) '0') + (int) fps % 10);
        c.drawText(fpsText, 0, fpsText.length, 10, 40, paint);
    }

    public static void drawGuideLines(Canvas c){
        c.drawLine(c.getWidth() / 2, 0, c.getWidth() / 2, c.getHeight(), paint);
        c.drawLine(0, c.getHeight() / 2, c.getWidth(), c.getHeight() / 2, paint);
    }

    private static void drawAcceleration(Canvas c, double acc, int y, char[] text) {
        text[6] = acc < 0 ? '-' : ' ';
        text[7] = (char) (((int) '0') + (int) abs(acc));
        text[9] = (char) (((int) '0') + (int) (abs(acc) * 10 - ((int) abs(acc)) * 10));
        c.drawText(text, 0, text.length, 10, y, paint);
    }

    public static void drawDebugInfoCrashed(Canvas c) {
        c.drawText("Kappoooww!!", 10, 65, paint);
    }

    public static void drawDebugInfoOutOfFuel(Canvas c) {
        c.drawText("Out of fuel!!", 10, 65, paint);
    }

    public static void drawDebugInfoLanded(Canvas c, int score) {
        Paint highScorePaint = new Paint();
        highScorePaint.setTextSize(20);
        highScorePaint.setColor(WHITE);
        NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));
        String scoreText = "Score: " + formatter.format(score);
        Rect bounds = new Rect();
        highScorePaint.getTextBounds(scoreText, 0, scoreText.length(), bounds);
        c.drawText(scoreText, c.getWidth() / 2 - bounds.width() / 2, c.getHeight() / 4 - bounds.height() / 2, highScorePaint);
    }

    public static void drawDebugInfoLandedTooHard(Canvas c) {
        c.drawText("Hard landing!!", 10, 65, paint);
    }
}

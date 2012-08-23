package com.sagen.balloonlander;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.sagen.balloonlander.balloon.Balloon;

import static android.graphics.Color.GREEN;
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

    static void drawDebugInfo(Canvas c, Balloon b, double fps) {
        drawAcceleration(c, b.dx(), 10, horText);
        drawAcceleration(c, b.dy(), 25, verText);
        drawFPS(c, fps);
    }

    private static void drawFPS(Canvas c, double fps) {
        fpsText[5] = (char) (((int) '0') + (int) fps / 100);
        fpsText[5] = fpsText[5] == '0' ? ' ' : fpsText[5];
        fpsText[6] = (char) (((int) '0') + (int) (fps % 100) / 10);
        fpsText[6] = fpsText[6] == '0' ? ' ' : fpsText[6];
        fpsText[7] = (char) (((int) '0') + (int) fps % 10);
        c.drawText(fpsText, 0, fpsText.length, 10, 40, paint);
    }

    private static void drawAcceleration(Canvas c, double acc, int y, char[] text) {
        text[6] = acc < 0 ? '-' : ' ';
        text[7] = (char) (((int) '0') + (int) abs(acc));
        text[9] = (char) (((int) '0') + (int) (abs(acc) * 10 - ((int) abs(acc)) * 10));
        c.drawText(text, 0, text.length, 10, y, paint);
    }

    static void drawDebugInfoCrashed(Canvas c) {
        c.drawText("Kappoooww!!", 10, 65, paint);
    }

    static void drawDebugInfoLanded(Canvas c) {
        c.drawText("Weeeeeeeee", 10, 65, paint);
    }

    static void drawDebugInfoLandedTooHard(Canvas c) {
        c.drawText("Hard landing!!", 10, 65, paint);
    }
}

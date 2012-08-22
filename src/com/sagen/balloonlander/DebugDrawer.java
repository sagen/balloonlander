package com.sagen.balloonlander;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.sagen.balloonlander.balloon.Balloon;

import static android.graphics.Color.GREEN;
import static java.lang.String.format;

public class DebugDrawer {
    private static Paint paint = new Paint();

    static {
        paint.setColor(GREEN);
    }

    static void drawDebugInfo(Canvas c, Balloon b, double fps) {
        c.drawText(format("Hor: %.2f", b.dx()), 10, 10, paint);
        c.drawText(format("Ver: %.2f", b.dy()), 10, 25, paint);
        c.drawText(format("FPS: %.2f", fps), 10, 40, paint);

    }

    static void drawDebugInfoCrashed(Canvas c) {
        c.drawText("Kappoooww!!", 10, 65, paint);
    }

    static void drawDebugInfoLanded(Canvas c) {
        c.drawText("Weeeeeeeee", 10, 65, paint);
    }
}

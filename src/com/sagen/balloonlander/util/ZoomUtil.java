package com.sagen.balloonlander.util;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ZoomUtil {
    private static int lastZoomLevel, lastCanvasWidth, lastCanvasHeight, lastBalloonXPos, lastBalloonYPos;
    private static int lastZoomBoxXPos, lastZoomBoxYPos;

    public static int zoomBoxXPos(int zoomLevel, int canvasWidth, int balloonXPos, int balloonWidth) {
        if (zoomLevel == lastZoomLevel && canvasWidth == lastCanvasWidth && balloonXPos == lastBalloonXPos) {
            return lastZoomBoxXPos;
        }
        lastZoomLevel = zoomLevel;
        lastCanvasWidth = canvasWidth;
        lastBalloonXPos = balloonXPos;
        lastZoomBoxXPos = min(canvasWidth * (zoomLevel - 1), max(0, (balloonXPos + balloonWidth / 2) * zoomLevel - canvasWidth / 2));
        return lastZoomBoxXPos;
    }

    public static int zoomBoxYPos(int zoomLevel, int canvasHeight, int balloonYPos, int balloonHeight) {
        if (zoomLevel == lastZoomLevel && canvasHeight == lastCanvasHeight && balloonYPos == lastBalloonYPos) {
            return lastZoomBoxYPos;
        }
        lastZoomLevel = zoomLevel;
        lastCanvasHeight = canvasHeight;
        lastBalloonYPos = balloonYPos;
        lastZoomBoxYPos = min(canvasHeight * (zoomLevel - 1), (balloonYPos + balloonHeight / 2) * zoomLevel - canvasHeight / 2);
        return lastZoomBoxYPos;
    }
}
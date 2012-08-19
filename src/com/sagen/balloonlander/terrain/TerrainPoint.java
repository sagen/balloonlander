package com.sagen.balloonlander.terrain;


import static java.lang.Integer.valueOf;

public class TerrainPoint implements Comparable<TerrainPoint>{
    int offsetY;
    final int x, y;
    public TerrainPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y + offsetY;
    }

    @Override
    public int compareTo(TerrainPoint o) {
        return o == null ? -1 : valueOf(x).compareTo(o.x);
    }

}

package com.sagen.balloonlander.client.terrain;


public class TerrainPoint implements Comparable<TerrainPoint>{
    public int x, y;
    public TerrainPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(TerrainPoint o) {
        return o == null ? -1 : x - o.x;
    }

}

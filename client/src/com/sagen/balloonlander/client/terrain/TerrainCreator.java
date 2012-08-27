package com.sagen.balloonlander.client.terrain;

import java.util.NavigableSet;
import java.util.Random;

import static com.sagen.balloonlander.client.terrain.Terrain.xPoint;


public class TerrainCreator {
    private static Random rand = new Random();

    public static Terrain generateTerrain(int width, int height){
        int upperBoundYPos = (int) (height * 0.6);
        int lowerBoundYPos = height - 30;
        Terrain terrain = new Terrain();
        terrain.add(new TerrainPoint(0, randomWithin(lowerBoundYPos, upperBoundYPos)));
        terrain.add(new TerrainPoint(width, randomWithin(lowerBoundYPos, upperBoundYPos)));
        generateTerrainPoints(width / 2, terrain.first().y, terrain.last().y, (int) ((lowerBoundYPos - upperBoundYPos) * 0.9), width / 2, 3, lowerBoundYPos, terrain);
        createLandingSpot(terrain);
        lowerTerrainToLowerBoundPos(terrain, lowerBoundYPos);
        terrain.generateCacheAndGraphicsPath(height);
        return terrain;
    }

    private static int randomWithin(int upper, int lower) {
        return rand.nextInt(upper - lower) + lower;
    }

    private static void lowerTerrainToLowerBoundPos(Terrain terrain, int lowerBound) {
        int lowest = 0;
        for(TerrainPoint p : terrain){
            if(p.y > lowest){
                lowest = p.y;
            }
        }
        terrain.setOffsetY(lowerBound - lowest);
    }

    private static void createLandingSpot(Terrain terrain) {
        int pointXSpace = terrain.last().x / terrain.size();
        int spotXSpace = pointXSpace * 10;
        int startXPos = randomWithin(terrain.last().x - spotXSpace, 0);
        startXPos -= startXPos % pointXSpace;
        NavigableSet<TerrainPoint> toRemove = terrain.subSet(xPoint(startXPos), true, xPoint(startXPos + spotXSpace), true);
        int centerYPos = (toRemove.first().y + toRemove.last().y) / 2;
        toRemove.clear();
        terrain.setLandingSpot(new TerrainPoint(startXPos, centerYPos), new TerrainPoint(startXPos + spotXSpace, centerYPos));
    }



    private static void generateTerrainPoints(int centerX, int prevY, int nextY, int rangeY, int currentXSpace, int targetXSpace, int lowerYPosBound, Terrain path){
        int y = rand.nextInt(rangeY) - (rangeY / 2) + ((prevY + nextY) / 2);
        if(y > lowerYPosBound){
            y = lowerYPosBound - rand.nextInt(rangeY / 2);
        }
        path.add(new TerrainPoint(centerX, y));
        if(currentXSpace > targetXSpace){
            generateTerrainPoints(centerX - (currentXSpace / 2), prevY, y, (int) (rangeY * 0.6), (currentXSpace / 2), targetXSpace, lowerYPosBound, path);
            generateTerrainPoints(centerX + (currentXSpace / 2), y, nextY, (int) (rangeY * 0.6), (currentXSpace / 2), targetXSpace, lowerYPosBound, path);
        }
    }

}

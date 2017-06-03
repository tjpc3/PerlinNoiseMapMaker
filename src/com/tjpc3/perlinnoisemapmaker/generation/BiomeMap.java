package com.tjpc3.perlinnoisemapmaker.generation;

import java.util.List;
import java.util.Random;

import com.tjpc3.perlinnoisemapmaker.io.SpriteSheet;
import com.tjpc3.perlinnoisemapmaker.noise.Noise;
import com.tjpc3.perlinnoisemapmaker.noise.PerlinNoise;
import com.tjpc3.perlinnoisemapmaker.pathfinding.Grid2d;
import com.tjpc3.perlinnoisemapmaker.pathfinding.Grid2d.MapNode;
import com.tjpc3.perlinnoisemapmaker.util.Vector2D;

public class BiomeMap extends Map {
	public BiomeMap(int width, int height, long seed) {
		super(width, height);
		
		Noise elevation = new PerlinNoise(width, height, seed);
		elevation.stretch();
		
		Noise moisture = new PerlinNoise(width, height, seed + 1);
		moisture.stretch();
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color(elevation.pixels[i], moisture.pixels[i]);
		}
	}
	
	public BiomeMap(int width, int height, double centerMultiplier, long seed) {
		super(width, height);
		
		Random rand = new Random(seed);
		
		Noise elevation = new PerlinNoise(width, height, seed);
		elevation.stretch();
		elevation.center(centerMultiplier);
		elevation.stretch();
		
		Noise moisture = new PerlinNoise(width, height, seed + 1);
		moisture.stretch();
	
		for (int c = 0; c < 1; c++) {
			// Start source selection
			Vector2D sourceVector = null;
			double sourceElevation = -1.0;
			while(sourceVector == null) {
				int randX = rand.nextInt(width);
				int randY = rand.nextInt(height);
				
				sourceElevation = elevation.getPixel(randX, randY);
				if (sourceElevation >= 0.9) { // The minimum elevation rivers must start at
					sourceVector = new Vector2D(randX, randY);
				}
			}
			// End source selection
			
			// Start source selection
			Vector2D endVector = null;
			double endElevation = -1.0;
			while(endVector == null) {
				int randX = rand.nextInt(width);
				int randY = rand.nextInt(height);
					
				endElevation = elevation.getPixel(randX, randY);
				if (endElevation < 0.3) { // The minimum elevation rivers must start at
					endVector = new Vector2D(randX, randY);
				}
			}
			// End source selection
			
			double[][] map2d = new double[width][height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					map2d[x][y] = elevation.pixels[x + y * width];
				}
			}
			
			Grid2d grid2d = new Grid2d(map2d, false);
			List<MapNode> nodes = grid2d.findPath(sourceVector.getX(), sourceVector.getY(), endVector.getX(), endVector.getY());
			
			for (int i = 0; i < nodes.size(); i++) {
				elevation.pixels[nodes.get(i).getX() + nodes.get(i).getY() * elevation.getWidth()] = 0.2;
			}
		}
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color(elevation.pixels[i], moisture.pixels[i]);
		}
	}
	
	private int color(double e, double m) {
		return SpriteSheet.biomes.getPixel((int) (m * 100), (int) (99 - e * 100)); // Because the y axis on pictures is at the top
	}
}

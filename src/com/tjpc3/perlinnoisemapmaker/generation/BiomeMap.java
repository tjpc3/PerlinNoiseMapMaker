package com.tjpc3.perlinnoisemapmaker.generation;

import java.util.List;
import java.util.Random;

import com.tjpc3.perlinnoisemapmaker.io.SpriteSheet;
import com.tjpc3.perlinnoisemapmaker.noise.Noise;
import com.tjpc3.perlinnoisemapmaker.noise.PerlinNoise;
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
	
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color(elevation.pixels[i], moisture.pixels[i]);
		}
		
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
			
			double currentLowestElevation = Double.MAX_VALUE;
			Vector2D currentLowestPos = new Vector2D(sourceVector.getX(), sourceVector.getY());
			
			int count = 0;
			while (count < 1000) {
				count++;
				pixels[sourceVector.getX() + sourceVector.getY() * width] = 0x0000FF;
				sourceElevation = elevation.getPixel(sourceVector.getX(), sourceVector.getY());
				
				for (int y = -1; y < 2; y++) {
					for (int x = -1; x < 2; x++) {
						if (x == 0 && y == 0)
							continue;
						//System.out.println("X: " + x + " Y: " + y);
						double testElevation = elevation.pixels[(sourceVector.getX() + x) + (sourceVector.getY() + y) * elevation.getWidth()];
						
						if (testElevation == -1.0)
							continue;
						
						if (testElevation < currentLowestElevation) {
							currentLowestElevation = testElevation;
							currentLowestPos = new Vector2D(sourceVector.getX() + x, sourceVector.getY() + y);
						}
					}
				}
				
				if (currentLowestElevation < 0.3)
					break;
				
				if (currentLowestElevation >= sourceElevation) { // If river got into a hole
					System.out.println("test");
					elevation.pixels[sourceVector.getX() + sourceVector.getY() * elevation.getWidth()] = -1.0;
				}
				
				sourceVector = currentLowestPos;
			}
		}
	}
	
	private int color(double e, double m) {
		return SpriteSheet.biomes.getPixel((int) (m * 100), (int) (99 - e * 100)); // Because the y axis on pictures is at the top
	}
}

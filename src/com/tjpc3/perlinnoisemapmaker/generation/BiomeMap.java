package com.tjpc3.perlinnoisemapmaker.generation;

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
		
		boolean carved[] = new boolean[width * height];
		
		
		
		// Begin river carving
//		int count = 0;
//		while (count < 100) {
//			count++;
//			int index = sourceVector.getX() + sourceVector.getY() * width;
//			pixels[index] = 0x0000FF; // Make pixel blue
//			carved[index] = true;
//			
//			System.out.print("Count: " + count + " X: " + sourceVector.getX() + " Y: " + sourceVector.getY() + " Source Elevation: " + sourceElevation);
//			
//			int rightIndex = sourceVector.getX() + 1 + sourceVector.getY() * width;
//			int leftIndex = rightIndex - 2;
//			int upIndex = sourceVector.getX() + (sourceVector.getY() + 1) * width;
//			int downIndex = sourceVector.getX() + (sourceVector.getY() - 1) * width;
//			
//			double rightElevation = carved[rightIndex] ? 1.0 : elevation.pixels[rightIndex];
//			double leftElevation = carved[leftIndex] ? 1.0 : elevation.pixels[leftIndex];
//			double upElevation = carved[upIndex] ? 1.0 : elevation.pixels[upIndex];
//			double downElevation = carved[downIndex] ? 1.0 : elevation.pixels[downIndex];
//			
//			System.out.println(" " + rightElevation);
//			
////			double rightElevation = elevation.getPixel(sourceVector.getX() + 1, sourceVector.getY());
////			double leftElevation = elevation.getPixel(sourceVector.getX() - 1, sourceVector.getY());
////			double upElevation = elevation.getPixel(sourceVector.getX(), sourceVector.getY() - 1);
////			double downElevation = elevation.getPixel(sourceVector.getX(), sourceVector.getY() + 1);
//			
//			double lowestElevation = Math.min(Math.min(rightElevation, leftElevation), Math.min(upElevation, downElevation));
//			
//			if (lowestElevation < 0.3) break; // Ocean Level
//				
//			double carveDepth = 0.9;
//			
//			if (lowestElevation == rightElevation) {
//				System.out.println(" Right");
//				if (rightElevation > sourceElevation) 
//					elevation.pixels[(sourceVector.getX() + 1) + sourceVector.getY() * width] = sourceElevation * carveDepth; 
//				sourceVector = new Vector2D(sourceVector.getX() + 1, sourceVector.getY());
//				sourceElevation = elevation.pixels[(sourceVector.getX() + 1) + sourceVector.getY() * width];
//			} else if (lowestElevation == leftElevation) {
//				System.out.println(" Left");
//				if (leftElevation > sourceElevation) 
//					elevation.pixels[(sourceVector.getX() - 1) + sourceVector.getY() * width] = sourceElevation * carveDepth; 
//				sourceVector = new Vector2D(sourceVector.getX() - 1, sourceVector.getY());
//				sourceElevation = elevation.pixels[(sourceVector.getX() - 1) + sourceVector.getY() * width];
//			} else if (lowestElevation == upElevation) {
//				System.out.println(" Up");
//				if (upElevation > sourceElevation) 
//					elevation.pixels[sourceVector.getX() + (sourceVector.getY() - 1) * width] = sourceElevation * carveDepth; 
//				sourceVector = new Vector2D(sourceVector.getX(), sourceVector.getY() - 1);
//				sourceElevation = elevation.pixels[sourceVector.getX() + (sourceVector.getY() - 1) * width];
//			} else { // Down elevation
//				System.out.println(" Down");
//				if (downElevation > sourceElevation)
//					elevation.pixels[sourceVector.getX() + (sourceVector.getY() + 1) * width] = sourceElevation * carveDepth; 
//				sourceVector = new Vector2D(sourceVector.getX(), sourceVector.getY() + 1);
//				sourceElevation = elevation.pixels[sourceVector.getX() + (sourceVector.getY() + 1) * width];
//			}
//		}
		// End river carving
	}
	
	private int color(double e, double m) {
		return SpriteSheet.biomes.getPixel((int) (m * 100), (int) (99 - e * 100)); // Because the y axis on pictures is at the top
	}
}

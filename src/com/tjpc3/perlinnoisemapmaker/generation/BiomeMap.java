package com.tjpc3.perlinnoisemapmaker.generation;

import com.tjpc3.perlinnoisemapmaker.io.SpriteSheet;
import com.tjpc3.perlinnoisemapmaker.noise.Noise;
import com.tjpc3.perlinnoisemapmaker.noise.PerlinNoise;

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
		
		Noise elevation = new PerlinNoise(width, height, seed);
		elevation.center(centerMultiplier);
		elevation.stretch();
		
		Noise moisture = new PerlinNoise(width, height, seed + 1);
		moisture.stretch();
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color(elevation.pixels[i], moisture.pixels[i]);
		}
	}
	
	private int color(double e, double m) {
		return SpriteSheet.biomes.getPixel((int) (m * 100), (int) (100 - e * 100)); // Because the y axis on pictures is at the top
	}
}

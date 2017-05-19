package com.tjpc3.perlinnoisemapmaker.generation;

import com.tjpc3.perlinnoisemapmaker.noise.PerlinNoise;

public class BasicMap extends Map {

	public BasicMap(int width, int height, double oceanLevel, long seed) {
		super(width, height);
		PerlinNoise perlin = new PerlinNoise(width, height, seed);
		
		perlin.stretch();
		
		// 0x00FF00 = Land, 0x0000FF = Water
		
		for (int i = 0; i < pixels.length; i++) {
			if (perlin.pixels[i] > oceanLevel) {
				pixels[i] = 0x00FF00;
			} else {
				pixels[i] = 0x0000FF;
			}
		}
	}

}

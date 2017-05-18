package com.tjpc3.perlinnoisemapmaker.noise;

public class PerlinNoise extends Noise {
	public PerlinNoise (int size, long seed) {
		super(size, size);
		
		int octaves = (int) (Math.log(width) / Math.log(2));
		
		for (int i = 1; i < octaves; i++) {
			Noise basicNoise = new BasicNoise(width, height, new WhiteNoise((int) Math.pow(2, i), (int) Math.pow(2, i), seed + i));
			add(basicNoise, 1 / Math.pow(2, i));
		}
	}
	
	public PerlinNoise (int width, int height, long seed) {
		super(width, height);
		
		int octaves;
		
		if (width > height) {
			octaves = (int) (Math.log(width) / Math.log(2));
			
			for (int x = 1; x < octaves; x++) {
				Noise basicNoise = new BasicNoise(width, width, new WhiteNoise((int) Math.pow(2, x), (int) Math.pow(2, x), seed + x));
				add(basicNoise, 1 / Math.pow(2, x));
			}
		} else {
			octaves = (int) (Math.log(height) / Math.log(2));
			
			for (int y = 1; y < octaves; y++) {
				Noise basicNoise = new BasicNoise(height, height, new WhiteNoise((int) Math.pow(2, y), (int) Math.pow(2, y), seed + y));
				add(basicNoise, 1 / Math.pow(2, y));
			}
		}
	}
}

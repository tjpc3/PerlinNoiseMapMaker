package com.tjpc3.perlinnoisemapmaker.noise;

import java.util.Random;

import com.tjpc3.perlinnoisemapmaker.util.Interpolation;

public class BasicNoise extends Noise {

	private Random rand = new Random();
	
	public BasicNoise(int width, int height, int freq, long seed) {
		super(width, height);
		rand.setSeed(seed);
		int xOffset = rand.nextInt(), yOffset = rand.nextInt();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double xa = (x + xOffset) / freq, ya = (y + yOffset) / freq; // This will be the coordinate to figure out suing bilinear interpolation
				
				int x1 = (int) Math.floor(xa);
				int y1 = (int) Math.floor(ya);
				
				int x2 = x1 + 1;
				int y2 = y1 + 1;
				
				// intNoise(x1, y1);
				// intNoise(x2, y1);
				// intNoise(x1, y2);
				// intNoise(x2, y2);
				
				pixels[x + y * width] = Interpolation.bilerp(xa - x1, ya - y1, intNoise(x1, y1), intNoise(x2, y1), intNoise(x1, y2), intNoise(x2, y2));
			}
		}
	}
	
	private double intNoise(int x, int y) {
		rand.setSeed(((long)x << 32) & y);
		return rand.nextDouble();
	}
}

package com.tjpc3.perlinnoisemapmaker.noise;

import java.util.Random;

public class WhiteNoise extends Noise {

	private Random rand = new Random();
	
	public WhiteNoise(int width, int height, long seed) {
		super(width, height);
		rand.setSeed(seed);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = rand.nextDouble();
		}
	}

}

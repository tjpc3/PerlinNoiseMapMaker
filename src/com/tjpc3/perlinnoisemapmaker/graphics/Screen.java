package com.tjpc3.perlinnoisemapmaker.graphics;

import com.tjpc3.perlinnoisemapmaker.noise.Noise;

public class Screen {
	private int width, height;
	public int[] pixels;
	
	public Screen (int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderNoise(int x, int y, Noise noise) {
		
	}
}

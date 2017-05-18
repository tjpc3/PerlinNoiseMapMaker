package com.tjpc3.perlinnoisemapmaker.graphics;

import com.tjpc3.perlinnoisemapmaker.noise.Noise;
import com.tjpc3.perlinnoisemapmaker.util.Interpolation;

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

	public void renderNoise(int xa, int ya, Noise noise) {
		for (int y = 0; y < noise.getHeight(); y++) {
			for (int x = 0; x < noise.getWidth(); x++) {
				if (x < 0 || y < 0 || x >= noise.getWidth() || y >= noise.getHeight()) continue;
				int lerped = (int)Interpolation.lerp(0, 255, noise.pixels[x + y * noise.getWidth()]);
				int color = (lerped << 24) | (lerped << 16) | (lerped << 8) | lerped;
				System.out.println(Integer.toHexString(color));
				pixels[(x + xa) + (y + ya) * width] = color;
			}
		}
	}
}

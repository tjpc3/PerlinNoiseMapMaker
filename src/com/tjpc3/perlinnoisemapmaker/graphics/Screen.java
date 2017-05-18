package com.tjpc3.perlinnoisemapmaker.graphics;

import com.tjpc3.perlinnoisemapmaker.generation.Map;
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
			int yb = y + ya;
			for (int x = 0; x < noise.getWidth(); x++) {
				int xb = x + xa;
				if (xb < 0 || yb < 0 || xb > width || yb >= height) continue;
				int lerped = (int)Interpolation.lerp(0, 255, noise.getPixel(x, y));
				int color = (lerped << 24) | (lerped << 16) | (lerped << 8) | lerped;
				//System.out.println(Integer.toHexString(color));
				pixels[xb + yb * width] = color;
			}
		}
	}

	public void renderMap(int xa, int ya, Map map) {
		for (int y = 0; y < map.getHeight(); y++) {
			int yb = y + ya;
			for (int x = 0; x < map.getWidth(); x++) {
				int xb = x + xa;
				if (xb < 0 || yb < 0 || xb > width || yb >= height) continue;
				
				pixels[xb + yb * width] = map.getPixel(x, y);
			}
		}
	}
}

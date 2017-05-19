package com.tjpc3.perlinnoisemapmaker.noise;

import com.tjpc3.perlinnoisemapmaker.graphics.Screen;
import com.tjpc3.perlinnoisemapmaker.util.Interpolation;

public abstract class Noise {
	protected int width, height;
	public double[] pixels;
	
	public Noise(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new double[width * height];
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen, int x, int y) {
		screen.renderNoise(x, y, this);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public double getPixel(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return 0.5;
		return pixels[x + y * width];
	}
	
	public void add(Noise noise, double weight) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > noise.width || y > noise.height) continue;
				pixels[x + y * width] += noise.pixels[x + y * width] * weight;
			}
		}
	}
	
	public void stretch() { // Stretches noise so that the highest value is 1.0 and the lowest value is 0.0		
		double min = Double.MAX_VALUE;
		double max = 0.0;
		
		for (int i = 0; i < pixels.length; i++) {
			max = Double.max(pixels[i], max);
			min = Double.min(pixels[i], min);
		}
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = Interpolation.unlerp(min, max, pixels[i]);
		}
	}
}

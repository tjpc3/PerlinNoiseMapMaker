package com.tjpc3.perlinnoisemapmaker.noise;

import com.tjpc3.perlinnoisemapmaker.graphics.Screen;

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
}

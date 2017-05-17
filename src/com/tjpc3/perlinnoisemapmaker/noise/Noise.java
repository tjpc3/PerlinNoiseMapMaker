package com.tjpc3.perlinnoisemapmaker.noise;

import com.tjpc3.perlinnoisemapmaker.graphics.Screen;

public class Noise {
	private int width, height;
	private int[] pixels;
	
	public Noise(int width, int height) {
		
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen, int x, int y) {
		screen.renderNoise(x, y, this);
	}
}

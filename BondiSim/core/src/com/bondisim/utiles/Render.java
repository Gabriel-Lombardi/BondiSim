package com.bondisim.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bondisim.BondiSim;

public class Render {

	public static SpriteBatch batch;
	public static BondiSim app;
	
	public static void limpiarPantalla(float r, float g, float b) {
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public static void setFullscreen() {
	    if (!Gdx.graphics.isFullscreen()) {
	        DisplayMode currentMode = Gdx.graphics.getDisplayMode();
	        Gdx.graphics.setFullscreenMode(currentMode);
	    }
	}
	
	public static void setFullscreenConTecla() {
	    if (Gdx.graphics.isFullscreen()) {
	        Gdx.graphics.setWindowedMode(Config.ANCHO, Config.ALTO);
	    } else {
	        DisplayMode currentMode = Gdx.graphics.getDisplayMode();
	        Gdx.graphics.setFullscreenMode(currentMode);
	    }
	}
}

package com.bondisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bondisim.pantallas.*;
import com.bondisim.utiles.Render;

public class BondiSim extends Game {
	
	@Override
	public void create () {
		Render.app = this;
		Render.batch = new SpriteBatch();
		this.setScreen(new PantallaMenu());
		
	}
	
	@Override
	public void render () {
		if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
			Gdx.app.log("Debug", "F11 presionado");
			Render.setFullscreenConTecla();
		}
		
		super.render();
	}
	
	@Override
	public void dispose () {
		Render.batch.dispose();
		super.dispose();
	}
}

package com.bondisim.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bondisim.elementos.Imagen;
import com.bondisim.io.Entrada;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;

public class PantallaOpciones implements Screen {

	private Imagen fondo;
	private Texture fondoNegro;
	private TextureRegion fondoNegroTR;
	private SpriteBatch b;
	
	private int anchoPantalla, altoPantalla;
	private float fondoX, fondoY;
	
	Entrada entrada = new Entrada(this);
	
	@Override
	public void show() {
		fondo = new Imagen(Recursos.FOTO_68);
		fondoNegro = new Texture(Recursos.FONDO_NEGRO);
		b = Render.batch;

		anchoPantalla = Gdx.graphics.getWidth();
		altoPantalla = Gdx.graphics.getHeight();
		
		fondoX = anchoPantalla / 8;
		fondoY = altoPantalla / 8;

		fondoNegroTR = new TextureRegion(fondoNegro, 0, 0, anchoPantalla / 5, 200);
		
		Gdx.input.setInputProcessor(entrada);
	}

	@Override
	public void render(float delta) {
		b.begin();
			fondo.dibujar();
			b.setColor(1,1,1,0.85f);
			b.draw(fondoNegroTR, fondoX, fondoY);
			
		b.end();
		
		if (entrada.isEscape()) {
			Render.app.setScreen(new PantallaMenu());
			Gdx.app.log("Pantalla", "Cambio a PantallaMenu");
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}

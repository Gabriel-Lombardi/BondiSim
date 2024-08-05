package com.bondisim.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bondisim.elementos.Imagen;
import com.bondisim.elementos.Texto;
import com.bondisim.io.Entrada;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;

public class PantallaMultijugador implements Screen {

	Imagen fondo;
	SpriteBatch b;
	
	Texto aviso, volver;
	
	private float posicionXTexto, posicionYTexto;
	
	private float width, height;
	
	Entrada entrada = new Entrada(this);
	
	@Override
	public void show() {
		fondo = new Imagen(Recursos.FOTO_62);
		b = Render.batch;
		
		aviso = new Texto(Recursos.FUENTE_MINECRAFT, 100, Color.RED, true);
		
		volver = new Texto(Recursos.FUENTE_MINECRAFT, 50, Color.WHITE, true);
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		Gdx.input.setInputProcessor(entrada);
	}

	@Override
	public void render(float delta) {
		b.begin();
			fondo.dibujar();
			
			aviso.setTexto("¡PROXIMAMENTE!");
			posicionXTexto = (width - aviso.getAncho()) / 2;
			posicionYTexto = (height + aviso.getAlto()) / 2;
			aviso.setPosicion(posicionXTexto, posicionYTexto);

			volver.setTexto("Presiona ESC para volver");
			volver.setPosicion( (width - volver.getAncho()) / 2, posicionYTexto - aviso.getAlto() * 3);
			
			aviso.dibujar();
			volver.dibujar();
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

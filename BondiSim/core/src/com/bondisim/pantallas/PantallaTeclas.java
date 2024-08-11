package com.bondisim.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bondisim.elementos.Imagen;
import com.bondisim.elementos.Texto;
import com.bondisim.io.Entrada;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;

public class PantallaTeclas implements Screen {
	private Texto[] controles;
	private SpriteBatch b;
	
	private Imagen fondo;
	private Texture fondoNegro;
	private TextureRegion fondoNegroTR;
	
	private int anchoPantalla, altoPantalla;
	private float fondoX, fondoY;
	
	private float avance = 130;
	
	Entrada entrada = new Entrada(this);

	@Override
	public void show() {
	    fondo = new Imagen(Recursos.FOTO_68);
		fondoNegro = new Texture(Recursos.FONDO_NEGRO);
		b = Render.batch;

		anchoPantalla = Gdx.graphics.getWidth();
		altoPantalla = Gdx.graphics.getHeight();
		
		fondoX = anchoPantalla / 10;
		fondoY = altoPantalla / 10;

		fondoNegroTR = new TextureRegion(fondoNegro, 0, 0, anchoPantalla - anchoPantalla / 6, altoPantalla - altoPantalla / 6);
		
	    String[] textosControles = {
	        "WASD o Flechas: Mover",
	        "Espacio: Freno de mano",
	        "Esc: Opciones/Salir",
	        "H: Bocina",
	        "C: Chifle",
	        "1: Abrir puertas delantera",
	        "2: Abrir puertas traseras",
	        "G: Encender colectivo"
	    };

	    controles = new Texto[textosControles.length];
	    for (int i = 0; i < textosControles.length; i++) {
	        controles[i] = new Texto(Recursos.FUENTE_RETRO_GAMING, 50, Color.WHITE, true);
	        controles[i].setTexto(textosControles[i]);
	        controles[i].setPosicion((anchoPantalla / 2) - (controles[i].getAncho() / 2),
    								 (anchoPantalla / 2) + (controles[i].getAlto() / 2) - ((controles[i].getAlto() + (avance * i)) / 2));
	    }
	    
	    Gdx.input.setInputProcessor(entrada);
	}

	@Override
	public void render(float delta) {
	    b.begin();
	    	Render.limpiarPantalla(0,0,0);
			fondo.dibujar();
			b.setColor(1,1,1,0.85f);
			b.draw(fondoNegroTR, fondoX, fondoY);
		    for (int i = 0; i < controles.length; i++) {
		        controles[i].dibujar();
		    }
	    b.end();
	    
	    if (entrada.isEscape()) {
	        Render.app.setScreen(new PantallaOpcionesDesdeMenu());
	    }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}

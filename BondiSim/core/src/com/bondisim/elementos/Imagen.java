package com.bondisim.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bondisim.utiles.Render;

public class Imagen {

	private Texture t;
	private Sprite s;
	
	public Imagen(String ruta) {
		t = new Texture(ruta);
		s = new Sprite(t);
	}
	
	public void dibujar() {
		s.draw(Render.batch);
	}
	
	public void setTransparencia(float a) {
		this.s.setAlpha(a);
	}
	
	public void setSize(float ancho, float alto) {
		s.setSize(ancho, alto);
	}

	public void dispose() {
	}
	
}

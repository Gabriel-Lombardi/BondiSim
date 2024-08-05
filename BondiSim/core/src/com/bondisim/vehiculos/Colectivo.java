package com.bondisim.vehiculos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bondisim.utiles.Render;

public class Colectivo {

	public Texture textura;
	public Sprite spr;
	
	public Colectivo(float x, float y, float ancho, float alto) {
		textura = new Texture("BondiSim512.png");
		spr 	= new Sprite(textura);
		spr.setPosition(x, y);
		spr.setSize(ancho, alto);
	}
	
	public void dibujar() {
		spr.draw(Render.batch);
	}
	
	public void setX(float x) {
		spr.setX(x);
	}
	
	public void setY(float y) {
		spr.setY(y);
	}
	
}

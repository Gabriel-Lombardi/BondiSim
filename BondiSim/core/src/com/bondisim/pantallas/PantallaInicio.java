package com.bondisim.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bondisim.elementos.Imagen;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;

public class PantallaInicio implements Screen {

	Imagen fondo;
	SpriteBatch b;
	boolean fadeInTerminado = false, termina = false;
	float a = 0, contTiempo = 0, tiempoEspera = 10, contTiempoTermina = 0, tiempoTermina = 5;
	
	@Override
	public void show() {
		fondo = new Imagen(Recursos.LOGO);
		b = Render.batch;
	}

	@Override
	public void render(float delta) {
		Render.limpiarPantalla(0,0,0);
				
		b.begin();
			fondo.dibujar();
		b.end();
		
		procesarFade();
	}

	private void procesarFade() {
		if (!fadeInTerminado) {
			a += 0.01f;
			if (a > 1) {
				a = 1;
				fadeInTerminado = true;
				Gdx.app.log("Carga", "FadeIn terminado");
			}
		} else {
			contTiempo += 0.1f;
			if (contTiempo > tiempoEspera) {
				a -= 0.01f;
				if (a < 0) {
					a = 0;
					termina = true;
				}
			}
		}
		fondo.setTransparencia(a);
		
		if (termina) {
			contTiempoTermina += 0.04f;
			if (contTiempoTermina > tiempoTermina) {
				Gdx.app.log("Pantalla", "Cambio de pantalla");
				Render.app.setScreen(new PantallaMenu());
			}
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

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
import com.bondisim.utiles.GestorMusica;
import com.bondisim.utiles.GestorSonido;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;

public class PantallaOpcionesDesdeJuego implements Screen {

	private Imagen fondo;
	private Texture fondoNegro;
	private TextureRegion fondoNegroTR;
	private SpriteBatch b;
	
	private int anchoPantalla, altoPantalla;
	private float fondoX, fondoY;
	
	private Texto opciones[] = new Texto[8];
	private Texto resoluciones[] = new Texto[7];
	
	private float volumenGeneral = GestorSonido.obtenerVolumen(), volumenMusica = GestorMusica.obtenerVolumen();

	private String textos_opciones[] = {"Opciones", "Reanudar", "Resolución", "Pantalla Completa", "Teclas",
										"V. General: " + (int) (volumenGeneral*200) + "%", "V. Música: " + (int) (volumenMusica*200) + "%", "Menu"};

	private int resolucionesInt[][] = {
		{1920, 1080},
		{1440, 1080},
		{1366, 768},
		{1280, 1024},
		{1280, 720},
		{1024, 768},
		{800, 600}
	};
	
	private float avance = 150;
	
	public float tiempo = 0;
	private int opc = 1, opcResolucion = 3;
	private boolean mouseArriba = false,
					enterPresionado = false;
	
	private boolean pantallaCompleta = Gdx.graphics.isFullscreen();
	
	
	
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
		
		// FUENTE, TAMAÑO DE LA FUENTE, COLOR, SOMBRA
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTE_RETRO_GAMING, 50, Color.WHITE, true);
			opciones[i].setTexto(textos_opciones[i]);
			opciones[i].setPosicion((anchoPantalla / 2) - (opciones[i].getAncho() / 2),
                    				(anchoPantalla / 2) + (opciones[i].getAlto() / 2) - ((opciones[i].getAlto() + (avance * i)) / 2));
		}
		
		opciones[0].setColor(Color.TEAL);
		
		resoluciones[0] = new Texto(Recursos.FUENTE_RETRO_GAMING, 40, Color.WHITE, true);
		resoluciones[0].setTexto(anchoPantalla + "x" + altoPantalla);
		
		float anchoTotal = opciones[2].getAncho() + resoluciones[0].getAncho() + 10;	
		opciones[2].setPosicion((anchoPantalla / 2) - (anchoTotal / 2), opciones[2].getY());
	    resoluciones[0].setPosicion(opciones[2].getX() + opciones[2].getAncho() + 10, opciones[2].getY() - 15);
		
		Gdx.input.setInputProcessor(entrada);
	}

	@Override
	public void render(float delta) {
		b.begin();
			Render.limpiarPantalla(0,0,0);
			fondo.dibujar();
			b.setColor(1,1,1,0.85f);
			b.draw(fondoNegroTR, fondoX, fondoY);
			
			for (int i = 0; i < opciones.length; i++) {
				opciones[i].dibujar();
				if(i == 1) {
					resoluciones[0].dibujar();
				}
			}
			
		b.end();
		
		if (entrada.isEscape()) {
			int hola;
		}
		
		tiempo += delta;
		
		if (entrada.isAbajo()) {
			if (tiempo > 0.2f) {
				tiempo = 0;
				opc++;
				if (opc > opciones.length - 1) {
					opc = 1;
				}
			}
		} else if (entrada.isArriba()) {
			if (tiempo > 0.2f) {
				tiempo = 0;
				opc--;
				if (opc < 1) {
					opc = opciones.length - 1;
				}
			}
		}
		
		int cont = 0;
		
		for (int i = 0; i < opciones.length; i++) {
			if ((entrada.getMouseX() >= opciones[i].getX())
					&& (entrada.getMouseX() <= opciones[i].getX() + opciones[i].getAncho())
					&& (entrada.getMouseY() >= opciones[i].getY() - opciones[i].getAlto())
					&& (entrada.getMouseY() <= opciones[i].getY())) {
				
				opc = i;
				
				cont++;
			}
		}
		
		if (cont > 0) {
			mouseArriba = true;
		} else {
			mouseArriba = false;
		}
		
		for (int i = 0; i < opciones.length; i++) {
			if (i == opc || i == 0) {
				opciones[i].setColor(Color.ROYAL);
			} else {
				opciones[i].setColor(Color.WHITE);
			}
		}

		switch(opc) {
		case 1: // reanudar juego
			if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
				Render.app.setScreen(new PantallaJuego());
				Gdx.app.log("Pantalla", "Cambio a PantallaJuego");
			}
			break;
		case 2: // resolucion
			if (entrada.isIzquierda() || entrada.isDerecha()) {
			    if (tiempo > 0.2f) {
			        tiempo = 0;
			        if (entrada.isIzquierda()) {
			            opcResolucion--;
			            if (opcResolucion < 0) {
			                opcResolucion = resolucionesInt.length - 1;
			            }
			        } else {
			            opcResolucion++;
			            if (opcResolucion >= resolucionesInt.length - 1) {
			                opcResolucion = 0;
			            }
			        }
			        resoluciones[0].setTexto(resolucionesInt[opcResolucion][0] + "x" + resolucionesInt[opcResolucion][1]);
			        float anchoTotalResol = opciones[1].getAncho() + resoluciones[0].getAncho() + 10;
			        opciones[1].setPosicion((anchoPantalla / 2) - (anchoTotalResol / 2), opciones[1].getY());
			        resoluciones[0].setPosicion(opciones[1].getX() + opciones[1].getAncho() + 10, opciones[1].getY() - 15);
			    }
			}
			if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
			    Gdx.graphics.setWindowedMode(resolucionesInt[opcResolucion][0], resolucionesInt[opcResolucion][1]);
			}
			break;
		case 3: // pantallaCompleta
			if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
			    pantallaCompleta = !pantallaCompleta;
			    if (pantallaCompleta) {
			        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			    } else {
			        Gdx.graphics.setWindowedMode(resolucionesInt[opcResolucion][0], resolucionesInt[opcResolucion][1]);
			    }
			}
			opciones[2].setTexto("Pantalla Completa [" + (pantallaCompleta ? "X" : " ") + "]");
			break;
		case 4: // teclas
			if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
			    Render.app.setScreen(new PantallaTeclas());
			}
			break;
		case 5: // volumen general
			if (entrada.isIzquierda() || entrada.isDerecha()) {
			    if (tiempo > 0.2f) {
			        tiempo = 0;
			        if (entrada.isIzquierda()) {
			        	volumenGeneral = Math.max(volumenGeneral - 0.05f, 0);
			            if (volumenGeneral < 0.05f && volumenGeneral != 0) {
			            	volumenGeneral = 0.05f;
			            } else if (volumenGeneral == 0.5f) {
			            	volumenGeneral = 0;
			            }
			        } else {
			            volumenGeneral = Math.min(volumenGeneral + 0.05f, 0.5f);
			        }
			        opciones[4].setTexto("V. General: " + Math.round(volumenMusica*200) + "%");
//			        GestorMusica.ajustarVolumen(volumenGeneral);
			    }
			}
			break;
		case 6: // volumen musica
			if (entrada.isIzquierda() || entrada.isDerecha()) {
			    if (tiempo > 0.2f) {
			        tiempo = 0;
			        if (entrada.isIzquierda()) {
			            volumenMusica = Math.max(volumenMusica - 0.05f, 0);
			            if (volumenMusica < 0.05f && volumenMusica != 0) {
			            	volumenMusica = 0.05f;
			            } else if (volumenMusica == 0.5f) {
			            	volumenMusica = 0;
			            }
			        } else {
			            volumenMusica = Math.min(volumenMusica + 0.05f, 0.5f);
			        }
			        opciones[5].setTexto("V. Música: " + Math.round(volumenMusica*200) + "%");
			        GestorMusica.ajustarVolumen(volumenMusica);
			    }
			}
			break;
		case 7: // volver
			if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
				Render.app.setScreen(new PantallaMenu());
				Gdx.app.log("Pantalla", "Cambio a PantallaMenu");
				break;
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

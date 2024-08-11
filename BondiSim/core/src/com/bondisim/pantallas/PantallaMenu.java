package com.bondisim.pantallas;

<<<<<<< HEAD
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bondisim.BondiSim;
import com.bondisim.elementos.Imagen;
import com.bondisim.elementos.Texto;
import com.bondisim.io.Entrada;
import com.bondisim.utiles.Config;
import com.bondisim.utiles.GestorMusica;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;

public class PantallaMenu implements Screen {
	
	private Imagen fondo;
	private Texture fondoNegro;
	private TextureRegion fondoNegroTR;
	private SpriteBatch b;

	private Texto  opciones[] = new Texto[4],
				   opcionesSalir[] = new Texto[2],
				   textoSalir;
	
	private String textos[] = { "Un jugador", "Multijugador", "Opciones", "Salir" },
				   salirOpciones[] = {"Si", "No"},
				   salir = "¿Seguro que queres salir?";

	private int opc = 1, opcSalir = 1;
	private boolean mouseArriba = false,
					saliendo = false, escapeSalir = false,
					enterPresionado = false;
	private float avance = 120;
	
	private float fondoSalirX, fondoSalirY;
	
	private float altoPantalla, anchoPantalla;

	public float tiempo = 0;
	
	Random r = new Random();

	Entrada entrada = new Entrada(this);

	@Override
	public void show() {
		fondo = new Imagen(Recursos.FONDOMENU);
		fondo.setSize(Config.ANCHO, Config.ALTO);
		fondoNegro = new Texture(Recursos.FONDO_NEGRO);
		b = Render.batch;

		Gdx.input.setInputProcessor(entrada);


		altoPantalla = Gdx.graphics.getWidth();
		anchoPantalla = Gdx.graphics.getHeight();
		
		// opciones: un jugador | multijugador | opciones | salir
		// FUENTE, TAMAÑO DE LA FUENTE, COLOR, SOMBRA
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTE_RETRO_GAMING, 60, Color.WHITE, true);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosicion((altoPantalla / 2) - (opciones[i].getAncho() / 2),
									(anchoPantalla / 2) + (opciones[0].getAlto() / 2) - ((opciones[i].getAlto() + (avance * i)) / 2));
		}
		
		// opciones salir: si | no
		for (int i = 0; i < opcionesSalir.length; i++) {
			opcionesSalir[i] = new Texto(Recursos.FUENTE_RETRO_GAMING, 40, Color.WHITE, true);
			opcionesSalir[i].setTexto(salirOpciones[i]);
			opcionesSalir[i].setPosicion((altoPantalla / 2) - (opcionesSalir[i].getAncho() / 2),
										 (anchoPantalla / 2) + (opcionesSalir[i].getAlto() / 2) - ((opcionesSalir[i].getAlto() + (avance * i)) / 2));
		}
		
		// Seguro que quieres salir?
		textoSalir = new Texto(Recursos.FUENTE_RETRO_GAMING, 50, Color.WHITE, true);
		textoSalir.setTexto(salir);
		textoSalir.setPosicion((altoPantalla / 2) - (textoSalir.getAncho() / 2),
								opcionesSalir[0].getY() + avance / 2);
		
		fondoSalirX = textoSalir.getX() - 50;
		fondoSalirY = opcionesSalir[opcionesSalir.length - 1].getY() - opcionesSalir[opcionesSalir.length - 1].getAlto() - 100;

		fondoNegroTR = new TextureRegion(fondoNegro, 0, 0, 950, 350);
		
		int nroRandom = r.nextInt(1,11);
		if (!GestorMusica.hayMusica()) {
			GestorMusica.reproducirMusica(Recursos.SOUNDTRACK[nroRandom][1]);
		}
		
	}

	@Override
	public void render(float delta) {

		b.begin();
			Render.limpiarPantalla(0,0,0);
			fondo.dibujar();
			for (int i = 0; i < opciones.length; i++) {
				opciones[i].dibujar();
			}
			
			if (saliendo) {
				b.setColor(1,1,1,0.85f);
				b.draw(fondoNegroTR, fondoSalirX, fondoSalirY);
				textoSalir.dibujar();
				for (int i = 0; i < opcionesSalir.length; i++) {
					opcionesSalir[i].dibujar();
				}
			}
		b.end();

		tiempo += delta;
		
		if (entrada.isEscape()) {
			opc = 4;
			escapeSalir = true;
		}

		if (entrada.isAbajo()) {
	        if (tiempo > 0.2f) {
	            tiempo = 0;
	            if (saliendo) {
	                opcSalir++;
	                if (saliendo && opcSalir > opcionesSalir.length) {
	                    opcSalir = 1;
	                }
	            } else {
	                opc++;
	                if (opc > opciones.length) {
	                    opc = 1;
	                }
	            }
	        }
	    } else if (entrada.isArriba()) {
	        if (tiempo > 0.2f) {
	            tiempo = 0;
	            if (saliendo) {
	                opcSalir--;
	                if (saliendo && opcSalir < 1) {
	                    opcSalir = opcionesSalir.length;
	                }
	            } else {
	                opc--;
	                if (opc < 1) {
	                    opc = opciones.length;
	                }
	            }
	        }
	    }

		int cont = 0;

		if (!saliendo) {
			for (int i = 0; i < opciones.length; i++) {
				if ((entrada.getMouseX() >= opciones[i].getX())
						&& (entrada.getMouseX() <= opciones[i].getX() + opciones[i].getAncho())
						&& (entrada.getMouseY() >= opciones[i].getY() - opciones[i].getAlto())
						&& (entrada.getMouseY() <= opciones[i].getY())) {
					
					opc = i + 1;
					
					cont++;
				}
			}
		} else {
			for (int i = 0; i < opcionesSalir.length; i++) {
				if ((entrada.getMouseX() >= opcionesSalir[i].getX())
						&& (entrada.getMouseX() <= opcionesSalir[i].getX() + opcionesSalir[i].getAncho())
						&& (entrada.getMouseY() >= opcionesSalir[i].getY() - opcionesSalir[i].getAlto())
						&& (entrada.getMouseY() <= opcionesSalir[i].getY())) {
					
					opcSalir = i + 1;
					
					cont++;
				}
			}
		}
		if (cont > 0) {
			mouseArriba = true;
		} else {
			mouseArriba = false;
		}

		if (saliendo) {
			for (int i = 0; i < opcionesSalir.length; i++) {
				if (i == opcSalir - 1) {
					opcionesSalir[i].setColor(Color.ROYAL);
				} else {
					opcionesSalir[i].setColor(Color.WHITE);
				}
			}
		} else {
			for (int i = 0; i < opciones.length; i++) {
				if (i == opc - 1) {
					opciones[i].setColor(Color.ROYAL);
				} else {
					opciones[i].setColor(Color.WHITE);
				}
			}
		}

		if (!saliendo) {
			switch (opc) {
			// un jugador
			case 1:
				if (entrada.isEnter() && !enterPresionado || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					Render.app.setScreen(new PantallaJuego());
					Gdx.app.log("Pantalla", "Cambio a PantallaUnJugador");
				}
				break;
				// multijugador
			case 2:
				if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					Render.app.setScreen(new PantallaMultijugador());
					Gdx.app.log("Pantalla", "Cambio a PantallaMultijugador");
				}
				break;
				// opciones
			case 3:
				if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					Render.app.setScreen(new PantallaOpcionesDesdeMenu());
					Gdx.app.log("Pantalla", "Cambio a PantallaOpcionesDesdeMenu");
=======
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.bondisim.elementos.Imagen;
import com.bondisim.elementos.Texto;
import com.bondisim.io.Entrada;
import com.bondisim.utiles.Config;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;

public class PantallaMenu implements Screen {

	private Imagen fondo;
	private Texture fondoNegro;
	private TextureRegion fondoNegroTR;
	private SpriteBatch b;

	private Texto  opciones[] = new Texto[4],
				   opcionesSalir[] = new Texto[2],
				   textoSalir;
	
	private String textos[] = { "Un jugador", "Multijugador", "Opciones", "Salir" },
				   salirOpciones[] = {"Si", "No"},
				   salir = "¿Seguro que queres salir?";

	private int opc = 1, opcSalir = 1;
	private boolean mouseArriba = false,
					saliendo = false, escapeSalir = false,
					enterPresionado = false;
	private float avance = 120;
	
	private float fondoSalirX, fondoSalirY;
	
	private float width, height;

	public float tiempo = 0;

	Entrada entrada = new Entrada(this);

	@Override
	public void show() {
		fondo = new Imagen(Recursos.FONDOMENU);
		fondo.setSize(Config.ANCHO, Config.ALTO);
		fondoNegro = new Texture(Recursos.FONDO_NEGRO);
		b = Render.batch;

		Gdx.input.setInputProcessor(entrada);

		// FUENTE, TAMAÑO DE LA FUENTE, COLOR, SOMBRA

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		// opciones: un jugador | multijugador | opciones | salir
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTE_RETRO_GAMING, 60, Color.WHITE, true);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosicion((width / 2) - (opciones[i].getAncho() / 2),
									(height / 2) + (opciones[0].getAlto() / 2) - ((opciones[i].getAlto() + (avance * i)) / 2));
		}
		
		// opciones salir: si | no
		for (int i = 0; i < opcionesSalir.length; i++) {
			opcionesSalir[i] = new Texto(Recursos.FUENTE_RETRO_GAMING, 40, Color.WHITE, true);
			opcionesSalir[i].setTexto(salirOpciones[i]);
			opcionesSalir[i].setPosicion((width / 2) - (opcionesSalir[i].getAncho() / 2),
										 (height / 2) + (opcionesSalir[i].getAlto() / 2) - ((opcionesSalir[i].getAlto() + (avance * i)) / 2));
		}
		
		// Seguro que quieres salir?
		textoSalir = new Texto(Recursos.FUENTE_RETRO_GAMING, 50, Color.WHITE, true);
		textoSalir.setTexto(salir);
		textoSalir.setPosicion((width / 2) - (textoSalir.getAncho() / 2),
								opcionesSalir[0].getY() + avance / 2);
		
		fondoSalirX = textoSalir.getX() - 50;
		fondoSalirY = opcionesSalir[opcionesSalir.length - 1].getY() - opcionesSalir[opcionesSalir.length - 1].getAlto() - 100;

		fondoNegroTR = new TextureRegion(fondoNegro, 0, 0, 950, 350);
	}

	@Override
	public void render(float delta) {

		b.begin();
			fondo.dibujar();
			for (int i = 0; i < opciones.length; i++) {
				opciones[i].dibujar();
			}
			
			if (saliendo) {
				b.setColor(1,1,1,0.85f);
				b.draw(fondoNegroTR, fondoSalirX, fondoSalirY);
				textoSalir.dibujar();
				for (int i = 0; i < opcionesSalir.length; i++) {
					opcionesSalir[i].dibujar();
				}
			}
		b.end();

		tiempo += delta;
		
		if (entrada.isEscape()) {
			opc = 4;
			escapeSalir = true;
		}

		if (entrada.isAbajo()) {
	        if (tiempo > 0.2f) {
	            tiempo = 0;
	            if (saliendo) {
	                opcSalir++;
	                if (saliendo && opcSalir > opcionesSalir.length) {
	                    opcSalir = 1;
	                }
	            } else {
	                opc++;
	                if (opc > opciones.length) {
	                    opc = 1;
	                }
	            }
	        }
	    } else if (entrada.isArriba()) {
	        if (tiempo > 0.2f) {
	            tiempo = 0;
	            if (saliendo) {
	                opcSalir--;
	                if (saliendo && opcSalir < 1) {
	                    opcSalir = opcionesSalir.length;
	                }
	            } else {
	                opc--;
	                if (opc < 1) {
	                    opc = opciones.length;
	                }
	            }
	        }
	    }

		int cont = 0;

		if (!saliendo) {
			for (int i = 0; i < opciones.length; i++) {
				if ((entrada.getMouseX() >= opciones[i].getX())
						&& (entrada.getMouseX() <= opciones[i].getX() + opciones[i].getAncho())
						&& (entrada.getMouseY() >= opciones[i].getY() - opciones[i].getAlto())
						&& (entrada.getMouseY() <= opciones[i].getY())) {
					
					opc = i + 1;
					
					cont++;
				}
			}
		} else {
			for (int i = 0; i < opcionesSalir.length; i++) {
				if ((entrada.getMouseX() >= opcionesSalir[i].getX())
						&& (entrada.getMouseX() <= opcionesSalir[i].getX() + opcionesSalir[i].getAncho())
						&& (entrada.getMouseY() >= opcionesSalir[i].getY() - opcionesSalir[i].getAlto())
						&& (entrada.getMouseY() <= opcionesSalir[i].getY())) {
					
					opcSalir = i + 1;
					
					cont++;
				}
			}
		}
		if (cont > 0) {
			mouseArriba = true;
		} else {
			mouseArriba = false;
		}

		if (saliendo) {
			for (int i = 0; i < opcionesSalir.length; i++) {
				if (i == opcSalir - 1) {
					opcionesSalir[i].setColor(Color.ROYAL);
				} else {
					opcionesSalir[i].setColor(Color.WHITE);
				}
			}
		} else {
			for (int i = 0; i < opciones.length; i++) {
				if (i == opc - 1) {
					opciones[i].setColor(Color.ROYAL);
				} else {
					opciones[i].setColor(Color.WHITE);
				}
			}
		}

		if (!saliendo) {
			switch (opc) {
			// un jugador
			case 1:
				if (entrada.isEnter() && !enterPresionado || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					Render.app.setScreen(new PantallaJuego());
					Gdx.app.log("Pantalla", "Cambio a PantallaUnJugador");
				}
				break;
				// multijugador
			case 2:
				if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					Render.app.setScreen(new PantallaMultijugador());
					Gdx.app.log("Pantalla", "Cambio a PantallaMultijugador");
				}
				break;
				// opciones
			case 3:
				if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					Render.app.setScreen(new PantallaOpciones());
					Gdx.app.log("Pantalla", "Cambio a PantallaOpciones");
>>>>>>> branch 'main' of file:///C:\Users\gabi\git\BondiSim
				}
				break;
				// salir
			case 4:
				if (escapeSalir || entrada.isEnter() && !enterPresionado || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					saliendo = true;
				}
				break;
			}
		} else {
			switch (opcSalir) {
			case 1:
				if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					Gdx.app.exit();
				}
				break;
			case 2:
				if (entrada.isEnter() && !enterPresionado  || (entrada.isClick() && mouseArriba == true)) {
					enterPresionado = true;
					saliendo = false;
					opcSalir = 1;
				}
				break;
			}
		}

		if (!entrada.isEnter()) {
	        enterPresionado = false;
	    }
		
	}

	@Override
	public void resize(int width, int height) {
		fondo.setSize(width, height);

	    for (int i = 0; i < opciones.length; i++) {
	        opciones[i].setPosicion((width / 2) - (opciones[i].getAncho() / 2),
	                				(height / 2) + (opciones[0].getAlto() / 2) - ((opciones[i].getAlto() + (avance * i)) / 2));
	    }
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

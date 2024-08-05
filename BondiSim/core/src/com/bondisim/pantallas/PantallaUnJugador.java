package com.bondisim.pantallas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bondisim.elementos.EstadoPartida;
import com.bondisim.elementos.Imagen;
import com.bondisim.elementos.Texto;
import com.bondisim.io.CargarPartida;
import com.bondisim.io.Entrada;
import com.bondisim.utiles.Config;
import com.bondisim.utiles.LeerPartidas;
import com.bondisim.utiles.Recursos;
import com.bondisim.utiles.Render;

public class PantallaUnJugador implements Screen {
	
	private Imagen fondo;
	private Texture fondoMenuTN, fondoMenuTB;
	private TextureRegion fondoMenuTRN, fondoMenuTRB;
	private SpriteBatch b;
	FileHandle partidas;
	
	List<String> partidasGuardadas = new ArrayList<>();
	
	String textos[] = { "Crear partida", "Cargar partida", "Volver" },
		   textosCrearPartida[] = { "Nombre partida: ", "Nombre jugador: ", "Jugar", "Volver" },
		   informacionPartidas[],
		   nombresPartidas[];
	
	Texto  opciones[] = new Texto[textos.length],
		   opcionesCrearPartida[] = new Texto[textosCrearPartida.length],
	       opcionesCargarPartida[];
	
	Texto test;
	
	int opc = 1, opcPartida = 1;
	boolean mouseArriba = false;
	float avance = 120;
	int posFondoMenu = 100,
		posXFondoMenuTRB = posFondoMenu * 5,
		posYFondoMenuTRB = posFondoMenu + 40,
		texRegFondoMenu = 200;
	boolean crearPartida = false, cargarPartida = false, enterPresionado = false;
	
	String nombreJugador = "Jugador";
	
	public float tiempo = 0;
	
	Entrada entrada = new Entrada(this);

	@Override
	public void show() {
		fondo = new Imagen(Recursos.FOTO_61);
		fondo.setSize(Config.ANCHO, Config.ALTO);
		fondoMenuTN = new Texture(Recursos.FONDO_NEGRO);
		fondoMenuTRN = new TextureRegion(fondoMenuTN, 0, 0, Config.ANCHO - texRegFondoMenu, Config.ALTO - texRegFondoMenu);
		fondoMenuTB = new Texture(Recursos.FONDO_BLANCO);
		fondoMenuTRB = new TextureRegion(fondoMenuTB, 0, 0, Config.ANCHO - texRegFondoMenu * 4 + 150, Config.ALTO - texRegFondoMenu - 80);
		
		b = Render.batch;
		
		Gdx.input.setInputProcessor(entrada);
		
		// FUENTE, TAMAÑO DE LA FUENTE, COLOR, SOMBRA
		
		// menu
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTE_UPHEAVTT, 40, Color.WHITE, false);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosicion(Math.round(Config.ANCHO * 0.1),
								   (Math.round(Config.ALTO * 0.8) - ((opciones[i].getAlto() + (avance * i)) / 2))
								   );
		}
				
		// crear partida
		for (int i = 0; i < opcionesCrearPartida.length; i++) {
		    opcionesCrearPartida[i] = new Texto(Recursos.FUENTE_RETRO_GAMING, 25, Color.BLACK, false);
		    opcionesCrearPartida[i].setTexto(textosCrearPartida[i]);
		    opcionesCrearPartida[i].setPosicion(posXFondoMenuTRB + 20,
		    								   (posYFondoMenuTRB + 400) - ((opcionesCrearPartida[i].getAlto() + (avance * i)) / 2)
		    								   );
		}
        
        // cargar partida
        partidas = Gdx.files.local("partidas.bin");
        
        if (partidas.exists()) {
        	
        	nombresPartidas = LeerPartidas.obtenerNombresPartidas();

        	if (nombresPartidas.length > 0) {
        		opcionesCargarPartida = new Texto[nombresPartidas.length];
        		informacionPartidas = new String[nombresPartidas.length];
        		
        		for (int i = 0; i < nombresPartidas.length; i++) {
        			opcionesCargarPartida[i] = new Texto(Recursos.FUENTE_ARIAL, 25, Color.BLACK, false);
        			opcionesCargarPartida[i].setTexto(nombresPartidas[i]);
        			opcionesCargarPartida[i].setPosicion(posXFondoMenuTRB + 20,
							   							(posYFondoMenuTRB + 220) - ((opcionesCargarPartida[i].getAlto() + (avance * i)) / 2)
							   							);
        			informacionPartidas[i] = EstadoPartida.getEstadoPartida();
        		}
        	}
        }
        
        
		Gdx.app.log("PantallaUnJugador", "Show - recursos cargados");
	}

	@Override
	public void render(float delta) {
		b.begin();
			fondo.dibujar();
			b.setColor(1,1,1,0.75f);
			b.draw(fondoMenuTRN, posFondoMenu, posFondoMenu);
			
			for (int i = 0; i < opciones.length; i++) {
                opciones[i].dibujar();
            }
			
			if (crearPartida) {
				b.setColor(1,1,1,0.85f);
				b.draw(fondoMenuTRB, posXFondoMenuTRB, posYFondoMenuTRB);
				for (int i = 0; i < opcionesCrearPartida.length; i++) {
					opcionesCrearPartida[i].dibujar();
				}
			} else if (cargarPartida) {
				b.setColor(1,1,1,0.85f);
				b.draw(fondoMenuTRB, posXFondoMenuTRB, posYFondoMenuTRB);
				if (partidas.exists()) {
					for (int i = 0; i < opcionesCargarPartida.length; i++) {
						opcionesCargarPartida[i].dibujar();
					}
				} else {
	                test.setTexto("No hay partidas guardadas");
	                test.setPosicion(posXFondoMenuTRB + posXFondoMenuTRB / 2,
	                				 posYFondoMenuTRB + posYFondoMenuTRB / 2);
	                test.dibujar();
	            }
			}
			
		b.end();
		
		tiempo += delta;

		// detectar flecha abajo / flecha arriba
		if (entrada.isAbajo()) {
	        if (tiempo > 0.2f) {
	            tiempo = 0;
	            if (crearPartida || cargarPartida) {
	                opcPartida++;
	                if (crearPartida && opcPartida > opcionesCrearPartida.length) {
	                    opcPartida = 1;
	                } else if (cargarPartida && opcPartida > opcionesCargarPartida.length) {
	                    opcPartida = 1;
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
	            if (crearPartida || cargarPartida) {
	                opcPartida--;
	                if (crearPartida && opcPartida < 1) {
	                    opcPartida = opcionesCrearPartida.length;
	                } else if (cargarPartida && opcPartida < 1) {
	                    opcPartida = opcionesCargarPartida.length;
	                }
	            } else {
	                opc--;
	                if (opc < 1) {
	                    opc = opciones.length;
	                }
	            }
	        }
	    }

		// pintar la seleccion
		if (crearPartida) {
	        for (int i = 0; i < opcionesCrearPartida.length; i++) {
	            if (i == opcPartida - 1) {
	                opcionesCrearPartida[i].setColor(Color.ROYAL);
	            } else {
	                opcionesCrearPartida[i].setColor(Color.BLACK);
	            }
	        }
	    } else if (cargarPartida) {
	        for (int i = 0; i < opcionesCargarPartida.length; i++) {
	            if (i == opcPartida - 1) {
	                opcionesCargarPartida[i].setColor(Color.ROYAL);
	            } else {
	                opcionesCargarPartida[i].setColor(Color.BLACK);
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

		// detectar cursor
		int cont = 0;

		for (int i = 0; i < opciones.length; i++) {
			if ((entrada.getMouseX() >= opciones[i].getX())
					&& (entrada.getMouseX() <= opciones[i].getX() + opciones[i].getAncho())
					&& (entrada.getMouseY() >= opciones[i].getY() - opciones[i].getAlto())
					&& (entrada.getMouseY() <= opciones[i].getY())) {
				opc = i + 1;
				cont++;
			}
		}
		if (cont > 0) {
			mouseArriba = true;
		} else {
			mouseArriba = false;
		}

		// seleccion de opciones
		if ( (entrada.isEnter() && !enterPresionado) || (entrada.isClick() && mouseArriba) ) {
			enterPresionado = true;
			
			System.out.println("CrearPartida: " + crearPartida);
			System.out.println("CargarPartida: " + cargarPartida);
			System.out.println(opc);
			System.out.println(opcPartida);
			
			if (crearPartida) {
				switch (opcPartida) {
					case 1: // Nombre partida
						Gdx.input.getTextInput(new TextInputListener() {
							@Override
							public void input(String text) {
								// nombresPartidas[nombresPartidas.length] = text;
								nombresPartidas = Arrays.copyOf(nombresPartidas, nombresPartidas.length + 1);
								nombresPartidas[nombresPartidas.length - 1] = text;
							}
	
							@Override
							public void canceled() {
								// Acción cuando se cancela la entrada de texto
							}
						}, "Nombre de la Partida", "", "Introduce el nombre de la partida");
						break;
					case 2: // Nombre jugador
						Gdx.input.getTextInput(new TextInputListener() {
							@Override
							public void input(String text) {
								nombreJugador = text;
							}
	
							@Override
							public void canceled() {
								// Acción cuando se cancela la entrada de texto
							}
						}, "Nombre del Jugador", "", "Introduce el nombre del jugador");
						break;
					case 3: // Jugar
						Render.app.setScreen(new PantallaMenu());
						Gdx.app.log("Pantalla", "Cambio a PantallaMenu");
						break;
					case 4: // Volver
						crearPartida = false;
						opcPartida = 1;
						break;
				}
			} else if (cargarPartida) {
				String nombrePartidaSeleccionada = opcionesCargarPartida[opcPartida - 1].getTexto();
				if (opcPartida <= opcionesCargarPartida.length) {
					EstadoPartida partidaCargada = CargarPartida.cargarPartida(nombrePartidaSeleccionada);
					if (partidaCargada != null) {
						// Continuar con la partida cargada
						Gdx.app.log("CargarPartida", "Partida cargada: " + partidaCargada);
					} else {
						Gdx.app.log("CargarPartida", "No se pudo cargar la partida");
					}
				} else if (opcPartida == opcionesCargarPartida.length + 1) {
					cargarPartida = false;
					opcPartida = 1;
				}
			} else if (!crearPartida && !cargarPartida) {
				switch (opc) {
				case 1: // Crear partida
					crearPartida = true;
					opcPartida = 1;
					break;
				case 2: // Cargar partida
					cargarPartida = true;
					opcPartida = 1;
					break;
				case 3: // Volver
					Render.app.setScreen(new PantallaMenu());
					Gdx.app.log("Pantalla", "Cambio a PantallaMenu");
					break;
				}
			}
		}
		
		if (!entrada.isEnter()) {
	        enterPresionado = false;
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
		fondoMenuTN.dispose();
		fondoMenuTB.dispose();
        for (Texto opcion : opciones) {
            opcion.dispose();
        }
        Gdx.app.log("PantallaUnJugador", "Recursos liberados");
	}

}	

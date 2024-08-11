package com.bondisim.vehiculos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bondisim.utiles.GestorSonido;
import com.bondisim.utiles.Recursos;

public class ColectivoJugador extends Sprite {
    private float velocidad = 0, aceleracion = 0;
    private static final float VELOCIDAD_MAXIMA = 300, VELOCIDAD_MAXIMA_REVERSA = 75;
    private float anguloGiroRuedas = 0f;
    private static final float MAX_ANGULO_GIRO = 85f;
    private static final float LONGITUD_VEHICULO = 170f;

    private TextureRegion[] bondiFrames;
    private int spriteBondi = 0;

    public float tiempo = 0;

    private boolean motorEncendido = false,
    				puertaDAbierta = false, puertaTAbiertas = false;
    private boolean gPresionada = false;;

    private GestorSonido gestorSonido;
    private float volumenSonido = 0.5f;

    public ColectivoJugador(Texture bondi, TextureRegion[] bondiFrames) {
        super(bondi, 0, 0, bondi.getWidth(), bondi.getHeight() / 3);
        this.bondiFrames = bondiFrames;
        setPosition(612.82965f, 375.46686f);
        setRotation(5.16311f);

        gestorSonido = new GestorSonido(volumenSonido);
        gestorSonido.cargar("encenderMotor", Recursos.ENCENDER_MOTOR);
        gestorSonido.cargar("apagarMotor", Recursos.APAGAR_MOTOR);
        gestorSonido.cargar("quietoMotorPrendido", Recursos.QUIETO_MOTOR_ENCENDIDO);
        gestorSonido.cargar("bocina1", Recursos.BOCINA1);
        gestorSonido.cargar("chifle", Recursos.CHIFLE);
        gestorSonido.cargar("frenada", Recursos.FRENADA);
        gestorSonido.cargar("acelerador1", Recursos.ACELERADOR1);
        gestorSonido.cargar("acelerador2", Recursos.ACELERADOR2);
        gestorSonido.cargar("acelerador3", Recursos.ACELERADOR3);
        gestorSonido.cargar("acelerador4", Recursos.ACELERADOR4);
        gestorSonido.cargar("acelerador5", Recursos.ACELERADOR5);
        gestorSonido.cargar("acelerador6", Recursos.ACELERADOR6);
        gestorSonido.cargar("andandoLento", Recursos.VELOCIDAD_BAJA);
        gestorSonido.cargar("andandoMedio", Recursos.VELOCIDAD_MEDIA);
        gestorSonido.cargar("andandoRapido", Recursos.VELOCIDAD_ALTA);
        gestorSonido.cargar("abrirPuertasD", Recursos.ABRIR_PUERTAS_DELANTERAS);
        gestorSonido.cargar("cerrarPuertasD", Recursos.CERRAR_PUERTAS_DELANTERAS);
        gestorSonido.cargar("abrirPuertasT", Recursos.ABRIR_PUERTAS_TRASERAS);
        gestorSonido.cargar("cerrarPuertasT", Recursos.CERRAR_PUERTAS_TRASERAS);
    }

    public void procesarEntrada() {
        entradaTeclado();
        actualizarPosicionVehiculo();
    }

    private void entradaTeclado() {
        tiempo += Gdx.graphics.getDeltaTime();

        boolean adelante  = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean atras     = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean izq       = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean dcha      = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean frenoDeMano = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        boolean accionMotor = Gdx.input.isKeyPressed(Input.Keys.G);
        boolean adelanteJP  = Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP);
        boolean atrasJP     = Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN);
        boolean isBocina 	= Gdx.input.isKeyPressed(Input.Keys.H);
        boolean isChifle 	= Gdx.input.isKeyPressed(Input.Keys.C);
        boolean puertasD = Gdx.input.isKeyPressed(Input.Keys.NUM_1);
        boolean puertasT = Gdx.input.isKeyPressed(Input.Keys.NUM_2);

        if (accionMotor && !motorEncendido && !gPresionada) {
        	gPresionada = true;
            if (tiempo > 0.2f) {
                tiempo = 0;
                gestorSonido.detener("apagarMotor");
                gestorSonido.reproducir("encenderMotor");
                gestorSonido.reproducirEnLoop("quietoMotorPrendido");
            }
            motorEncendido = true;
        } else if (accionMotor && motorEncendido && !gPresionada) {
        	gPresionada = true;
            if (tiempo > 0.2f) {
                tiempo = 0;
                gestorSonido.reproducir("apagarMotor");
                gestorSonido.detener("quietoMotorPrendido");
            }
            motorEncendido = false;
        }

        if (isBocina) {
            if (tiempo > 0.3825f) {
                tiempo = 0;
                gestorSonido.reproducir("bocina1");
            }
        }

        if (isChifle) {
            if (tiempo > 1f) {
                tiempo = 0;
                gestorSonido.reproducir("chifle");
            }
        }

        if (motorEncendido) {
        	
        	if (!puertaDAbierta && puertasD) {
        		if (tiempo > 0.1f) {
                    tiempo = 0;
                    gestorSonido.reproducir("abrirPuertasD");
                }
        	} else if (puertaDAbierta && puertasD) {
        		if (tiempo > 0.1f) {
                    tiempo = 0;
                    gestorSonido.reproducir("cerrarPuertasD");
                }
        	}
        	
        	if (!puertaTAbiertas && puertasT) {
        		if (tiempo > 0.1f) {
                    tiempo = 0;
                    gestorSonido.reproducir("abrirPuertasT");
                }
        	} else if (puertaTAbiertas && puertasT) {
        		if (tiempo > 0.1f) {
                    tiempo = 0;
                    gestorSonido.reproducir("cerrarPuertasT");
                }
        	}
        	
            if ((atrasJP && velocidad > 0) || (adelanteJP && velocidad < 0) || (atrasJP && frenoDeMano && velocidad == 0)) {
                if (tiempo > 0.2f) {
                    tiempo = 0;
                    gestorSonido.reproducir("frenada");
                }
            }

            if (adelante && !frenoDeMano) {
                if (velocidad < 0) {
                    frenarHastaDetener();
                } else {
                    acelerar();
                }
            } else if (atras && !frenoDeMano) {
                if (velocidad > 0) {
                    frenarHastaDetener();
                } else {
                    marchaAtras();
                }
            } else if (frenoDeMano) {
                frenarHastaDetener();
            } else {
                desacelerar();
            }
        }

        if (izq) {
            anguloGiroRuedas = Math.min(anguloGiroRuedas + 100000000f, MAX_ANGULO_GIRO);
            spriteBondi = 1;
        } else if (dcha) {
            anguloGiroRuedas = Math.max(anguloGiroRuedas - 100000000f, -MAX_ANGULO_GIRO);
            spriteBondi = 2;
        } else {
            anguloGiroRuedas = 0;
            spriteBondi = 0;
        }
        setRegion(bondiFrames[spriteBondi]);

        if (!accionMotor) {
        	gPresionada = false;
        }
    }

    private void acelerar() {
        if (aceleracion < 80f) {
            aceleracion += 10f;
        }
    }

    private void marchaAtras() {
        if (aceleracion > -40f) {
            aceleracion -= 5f;
        }
    }

    private void frenarHastaDetener() {
        if (Math.abs(velocidad) > 0.5f) {
            aceleracion = -velocidad * 5f;
        } else {
            aceleracion = 0;
            velocidad = 0;
        }
    }

    private void desacelerar() {
        if (Math.abs(aceleracion) > 0.5f) {
            aceleracion *= 0.9f;
        } else {
            aceleracion = 0;
        }

        if (Math.abs(velocidad) > 0.5f) {
            velocidad *= 0.99f;
        } else {
            velocidad = 0;
        }
    }

    private void actualizarPosicionVehiculo() {
        float tiempo = Gdx.graphics.getDeltaTime();
        velocidad += aceleracion * tiempo;

        if (velocidad > VELOCIDAD_MAXIMA) {
            velocidad = VELOCIDAD_MAXIMA;
        } else if (velocidad < -VELOCIDAD_MAXIMA_REVERSA) {
            velocidad = -VELOCIDAD_MAXIMA_REVERSA;
        }

        float radioGiro;
        if (Math.abs(anguloGiroRuedas) < 0.01f) {
            radioGiro = Float.MAX_VALUE;
        } else {
            radioGiro = LONGITUD_VEHICULO / (float) Math.tan(Math.toRadians(anguloGiroRuedas));
        }

        float deltaRotacion = (velocidad * 3) * tiempo / radioGiro;
        float anguloRotacion = getRotation();

        float centroX = getX() - radioGiro * (float) Math.sin(Math.toRadians(anguloRotacion));
        float centroY = getY() + radioGiro * (float) Math.cos(Math.toRadians(anguloRotacion));

        float nuevaRotacion = anguloRotacion + deltaRotacion;
        float deltaX = velocidad * (float) Math.cos(Math.toRadians(nuevaRotacion)) * tiempo;
        float deltaY = velocidad * (float) Math.sin(Math.toRadians(nuevaRotacion)) * tiempo;

        setPosition(getX() + deltaX, getY() + deltaY);
        setRotation(nuevaRotacion);
    }

    public float getVelocidad() {
        return velocidad;
    }
    
    public boolean isMotorEncendido() {
		return motorEncendido;
	}
}

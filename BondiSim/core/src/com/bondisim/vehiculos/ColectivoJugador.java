package com.bondisim.vehiculos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ColectivoJugador extends Sprite {
    private float velocidad = 0, aceleracion = 0;
    private static final float VELOCIDAD_MAXIMA = 300, VELOCIDAD_MAXIMA_REVERSA = 75;
    private float anguloGiroRuedas = 0f;
    private static final float MAX_ANGULO_GIRO = 85f;
    private static final float LONGITUD_VEHICULO = 170f;

    private TextureRegion[] bondiFrames;
    private int spriteBondi = 0;

    public ColectivoJugador(Texture bondi, TextureRegion[] bondiFrames) {
        super(bondi, 0, 0, bondi.getWidth(), bondi.getHeight() / 3);
        this.bondiFrames = bondiFrames;
        setPosition(612.82965f, 375.46686f);
        setRotation(5.16311f);
    }

    public void procesarEntrada() {
        entradaTeclado();
        actualizarPosicionVehiculo();
    }

    private void entradaTeclado() {
        boolean adelante = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean atras     = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean izq       = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean dcha      = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean frenoDeMano = Gdx.input.isKeyPressed(Input.Keys.SPACE);

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

        if (izq) {
            anguloGiroRuedas = Math.min(anguloGiroRuedas + 100000000f, MAX_ANGULO_GIRO);
            spriteBondi = 1; // Cambiar al sprite de giro a la izquierda
        } else if (dcha) {
            anguloGiroRuedas = Math.max(anguloGiroRuedas - 100000000f, -MAX_ANGULO_GIRO);
            spriteBondi = 2; // Cambiar al sprite de giro a la derecha
        } else {
            anguloGiroRuedas = 0;
            spriteBondi = 0; // Cambiar al sprite recto
        }
        setRegion(bondiFrames[spriteBondi]);
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
            velocidad *= 0.99f; // Desacelera suavemente
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
}

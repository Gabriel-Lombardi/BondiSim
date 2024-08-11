package com.bondisim.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GestorMusica {
    public static Music musica;
    private static final float INCREMENTO = 0.1f;
    private static float volumen = 0.05f;

    public static void reproducirMusica(String archivo) {
        try {
            if (musica != null) {
                musica.stop();
                musica.dispose();
            }
            musica = Gdx.audio.newMusic(Gdx.files.internal(archivo));
            musica.setLooping(true);
            musica.setVolume(volumen);
            musica.play();
        } catch (Exception e) {
            Gdx.app.log("GestorMusica", "Error al reproducir música: " + e.getMessage());
        }
    }

    public static void ajustarVolumen(float nuevoVolumen) {
    	volumen = Math.round(nuevoVolumen / INCREMENTO) * INCREMENTO;
        if (musica != null) {
            musica.setVolume(volumen);
        }
    }

    public static float obtenerVolumen() {
        return volumen;
    }

    public static void pausar() {
        if (musica != null) {
            musica.pause();
        }
    }

    public static void reanudar() {
        if (musica != null) {
            musica.play();
        }
    }

    public static void detener() {
        if (musica != null) {
            musica.stop();
            musica.dispose();
            musica = null;
        }
    }
    
    public static boolean hayMusica() {
    	return musica != null && musica.isPlaying();
    }
}

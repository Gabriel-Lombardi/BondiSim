package com.bondisim.utiles;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GestorSonido {

	private static Map<String, Sound> sonidos = new HashMap<>();
    private static Map<String, Long> sonidosEnLoop = new HashMap<>(); // Para rastrear los sonidos en loop
    private static final float INCREMENTO = 0.1f;
    private static float volumen = 0.5f;

    public GestorSonido(float volumen) {
        ajustarVolumen(volumen);
    }

    public void cargar(String clave, String rutaArchivo) {
        Sound sonido = Gdx.audio.newSound(Gdx.files.internal(rutaArchivo));
        sonidos.put(clave, sonido);
    }

    public void reproducir(String clave) {
        Sound sonido = sonidos.get(clave);
        if (sonido != null) {
            sonido.play(volumen);
        }
    }
    
    public void reproducirEnLoop(String clave) {
    	Sound sonido = sonidos.get(clave);
    	if (sonido != null) {
    		sonido.loop(volumen);
    	}
    }
    
    public void detener(String clave) {
    	Sound sonido = sonidos.get(clave);
    	if (sonido != null) {
    		sonido.stop();
    	}
    }
    
    public void liberar() {
        for (Sound sonido : sonidos.values()) {
            sonido.dispose();
        }
        sonidos.clear();
    }

    public static void ajustarVolumen(float nuevoVolumen) {
        volumen = Math.round(nuevoVolumen / INCREMENTO) * INCREMENTO;
        for (Map.Entry<String, Long> entry : sonidosEnLoop.entrySet()) {
            Sound sonido = sonidos.get(entry.getKey());
            if (sonido != null) {
                sonido.setVolume(entry.getValue(), volumen);
            }
        }
    }
    
    public static float obtenerVolumen() {
    	return volumen;
    }
	
}

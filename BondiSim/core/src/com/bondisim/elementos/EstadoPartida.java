package com.bondisim.elementos;

import java.io.Serializable;

public class EstadoPartida implements Serializable {
	private static final long serialVersionUID = 1L;

    private static int nivel;
    private static int puntuacion;
    private static String nombreJugador;
    private static String nombrePartida;

    public EstadoPartida(int nivel, int puntuacion, String nombreJugador, String nombrePartida) {
        EstadoPartida.nivel = nivel;
        EstadoPartida.puntuacion = puntuacion;
        EstadoPartida.nombreJugador = nombreJugador;
        EstadoPartida.nombrePartida = nombrePartida;
    }

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		EstadoPartida.nivel = nivel;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		EstadoPartida.puntuacion = puntuacion;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		EstadoPartida.nombreJugador = nombreJugador;
	}
	
	public String getNombrePartida() {
		return nombrePartida;
	}

	public void setNombrePartida(String nombrePartida) {
		EstadoPartida.nombrePartida = nombrePartida;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public static String getEstadoPartida() {
		return "Chofer: " + nombreJugador + "\n" +
			   "Nivel: " + nivel + "\n" +
			   "Puntuación: " + puntuacion + "\n";
	}
    
}

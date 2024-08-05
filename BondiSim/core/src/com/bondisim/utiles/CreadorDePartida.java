package com.bondisim.utiles;

import com.bondisim.elementos.EstadoPartida;

public class CreadorDePartida {

	public static EstadoPartida crearNuevaPartida(String nombreP, String nombreJ) {
		
		String nombreJugador = nombreJ;
		String nombrePartida = nombreP;
		int nivelInicial = 1;
		int puntuacionInicial = 0;

		return new EstadoPartida(nivelInicial, puntuacionInicial, nombreJugador, nombrePartida);
	}
	
}

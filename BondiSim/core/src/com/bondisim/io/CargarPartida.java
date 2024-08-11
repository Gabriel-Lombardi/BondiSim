package com.bondisim.io;

import java.util.List;

import com.bondisim.elementos.EstadoPartida;
import com.bondisim.utiles.LeerPartidas;

public class CargarPartida {

	public static EstadoPartida cargarPartida(String nombrePartida) {
        List<EstadoPartida> partidas = LeerPartidas.leerPartidas();

        if (partidas != null) {
            for (EstadoPartida partida : partidas) {
                if (partida.getNombrePartida().equals(nombrePartida)) {
                    return partida;
                }
            }
        }

        return null;
    }
	
}

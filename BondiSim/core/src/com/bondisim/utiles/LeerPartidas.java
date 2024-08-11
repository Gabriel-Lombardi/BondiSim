package com.bondisim.utiles;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.bondisim.elementos.EstadoPartida;

public class LeerPartidas {

	private static final String NOMBRE_ARCHIVO = "partidas.bin";

    public static List<EstadoPartida> leerPartidas() {
        List<EstadoPartida> partidas = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(NOMBRE_ARCHIVO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    EstadoPartida partida = (EstadoPartida) ois.readObject();
                    partidas.add(partida);
                } catch (IOException e) {
                    break; // Salir del bucle al final del archivo
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return partidas;
    }

    public static String[] obtenerNombresPartidas() {
        List<EstadoPartida> partidas = leerPartidas();
        String[] nombresPartidas = new String[partidas.size()];

        for (int i = 0; i < partidas.size(); i++) {
            nombresPartidas[i] = partidas.get(i).getNombrePartida();
        }

        return nombresPartidas;
    }
    
}

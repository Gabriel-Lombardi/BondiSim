package com.bondisim.utiles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.bondisim.elementos.EstadoPartida;
import com.bondisim.io.GuardarPartida;

public class GestionPartidas {

    public static List<EstadoPartida> leerPartidas() {
        return LeerPartidas.leerPartidas();
    }

    public static void agregarPartida(EstadoPartida nuevaPartida) {
        List<EstadoPartida> partidas = leerPartidas();
        if (partidas == null) {
            partidas = new ArrayList<>();
        }
        partidas.add(nuevaPartida);
        GuardarPartida.guardarPartida(nuevaPartida);
    }

    public static void guardarPartidas(EstadoPartida estadoPartida) {
        GuardarPartida.guardarPartida(estadoPartida);
    }
    
    public static void borrarPartida(String nombrePartida) {
        List<EstadoPartida> partidas = leerPartidas();
        if (partidas != null) {
            partidas.removeIf(partida -> partida.getNombrePartida().equals(nombrePartida));
            // Volver a guardar las partidas restantes en el archivo
            try (FileOutputStream fos = new FileOutputStream("partidas.bin");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                for (EstadoPartida partida : partidas) {
                    oos.writeObject(partida);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.bondisim.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.bondisim.elementos.EstadoPartida;

public class GuardarPartida {

	private static final String NOMBRE_ARCHIVO = "partidas.bin";

    public static void guardarPartida(EstadoPartida estadoPartida) {
        try (FileOutputStream fos = new FileOutputStream(NOMBRE_ARCHIVO);
             AppendingObjectOutputStream oos = new AppendingObjectOutputStream(fos)) {
            oos.writeObject(estadoPartida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class AppendingObjectOutputStream extends ObjectOutputStream {

        public AppendingObjectOutputStream(FileOutputStream fos) throws IOException {
            super(fos);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
	
}

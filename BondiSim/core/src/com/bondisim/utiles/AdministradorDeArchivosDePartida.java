package com.bondisim.utiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDeArchivosDePartida {

	public static List<String> listarJuegosGuardados(String directorio) {
		File dir = new File(directorio);
		File[] archivos = dir.listFiles((d, nombre) -> nombre.endsWith(".sav"));
		List<String> nombresArchivos = new ArrayList<>();
		
		if (archivos != null) {
			for (File archivo : archivos) {
				nombresArchivos.add(archivo.getName());
			}
		}
		
		return nombresArchivos;
	}
	
}

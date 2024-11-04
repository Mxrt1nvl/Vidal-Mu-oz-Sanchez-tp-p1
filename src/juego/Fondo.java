package juego;

import java.awt.Image; // Importa la clase Image para manejar imágenes
import entorno.Entorno; // Importa la clase Entorno para la gestión del entorno gráfico

public class Fondo {
	Image fondo; // Imagen del fondo
	Entorno e; // Referencia al entorno

	// Constructor de la clase Fondo
	public Fondo(String ruta, Entorno ent) {
		this.fondo = entorno.Herramientas.cargarImagen(ruta); // Carga la imagen
																// del fondo
																// desde la ruta
																// proporcionada
		this.e = ent; // Asigna el entorno
	}

	// Método para dibujar el fondo
	public void dibujar() {
		// Calcula la escala del fondo en ancho y alto
		double escalaAncho = (double) e.ancho() / fondo.getWidth(null);
		double escalaAlto = (double) e.alto() / fondo.getHeight(null);

		// Aplicar el escalado correcto para ambos ejes, centrando la imagen en
		// el entorno
		e.dibujarImagen(fondo, e.ancho() / 2, e.alto() / 2, 0,
				Math.max(escalaAncho, escalaAlto));
	}
}

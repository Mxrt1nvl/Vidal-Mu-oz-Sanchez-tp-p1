package juego;

import java.awt.Image; // Importa la clase Image para manejar imágenes
import entorno.Entorno; // Importa la clase Entorno para la gestión del entorno gráfico

public class Casa {
	double x; // Posición en el eje X
	double y; // Posición en el eje Y
	double tamanio; // Tamaño de la casa
	double ancho; // Ancho de la casa
	double alto; // Alto de la casa

	Entorno e; // Referencia al entorno
	Image imgCasa; // Imagen de la casa

	// Constructor de la clase Casa
	public Casa(double x, double y, Entorno ent) {
		this.x = x; // Inicializa la posición X
		this.y = y; // Inicializa la posición Y
		this.tamanio = 0.25; // Establece el tamaño de la casa
		this.e = ent; // Asigna el entorno
		this.imgCasa = entorno.Herramientas.cargarImagen("imagenes/casita.png"); // Carga
																					// la
																					// imagen
																					// de
																					// la
																					// casa
		this.ancho = imgCasa.getHeight(null) * tamanio; // Calcula el ancho
														// basado en la imagen y
														// el tamaño
		this.alto = imgCasa.getWidth(null) * tamanio; // Calcula el alto basado
														// en la imagen y el
														// tamaño
	}

	// Método para mostrar la casa
	public void dibujarCasa(Entorno e) {
		e.dibujarImagen(imgCasa, x, y, 0, tamanio); // Dibuja la imagen de la
													// casa en el entorno
	}

	// Método para obtener la posición X de la casa
	public double getX() {
		return this.x; // Retorna la posición X
	}

	// Método para obtener la posición Y de la casa
	public double getY() {
		return this.y; // Retorna la posición Y
	}
}

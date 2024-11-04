package juego;

import java.awt.Image; // Importa la clase Image para manejar im�genes
import entorno.Entorno; // Importa la clase Entorno para la gesti�n del entorno gr�fico

public class Casa {
	double x; // Posici�n en el eje X
	double y; // Posici�n en el eje Y
	double tamanio; // Tama�o de la casa
	double ancho; // Ancho de la casa
	double alto; // Alto de la casa

	Entorno e; // Referencia al entorno
	Image imgCasa; // Imagen de la casa

	// Constructor de la clase Casa
	public Casa(double x, double y, Entorno ent) {
		this.x = x; // Inicializa la posici�n X
		this.y = y; // Inicializa la posici�n Y
		this.tamanio = 0.25; // Establece el tama�o de la casa
		this.e = ent; // Asigna el entorno
		this.imgCasa = entorno.Herramientas.cargarImagen("imagenes/casita.png"); // Carga
																					// la
																					// imagen
																					// de
																					// la
																					// casa
		this.ancho = imgCasa.getHeight(null) * tamanio; // Calcula el ancho
														// basado en la imagen y
														// el tama�o
		this.alto = imgCasa.getWidth(null) * tamanio; // Calcula el alto basado
														// en la imagen y el
														// tama�o
	}

	// M�todo para mostrar la casa
	public void dibujarCasa(Entorno e) {
		e.dibujarImagen(imgCasa, x, y, 0, tamanio); // Dibuja la imagen de la
													// casa en el entorno
	}

	// M�todo para obtener la posici�n X de la casa
	public double getX() {
		return this.x; // Retorna la posici�n X
	}

	// M�todo para obtener la posici�n Y de la casa
	public double getY() {
		return this.y; // Retorna la posici�n Y
	}
}

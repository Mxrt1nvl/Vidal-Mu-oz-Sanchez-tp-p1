package juego;

import java.awt.Image; // Importa la clase Image para manejar imágenes
import entorno.Entorno; // Importa la clase Entorno para la gestión del entorno gráfico

public class Isla {
	double x; // Posición en el eje X
	double y; // Posición en el eje Y

	// Aspecto
	Image img; // Imagen de la isla
	double tamanio; // Tamaño de la isla
	Entorno e; // Referencia al entorno

	// Ancho y Alto
	double ancho; // Ancho de la isla
	double alto; // Alto de la isla

	// Comportamientos del Personaje
	boolean direccion; // Dirección de la isla (derecha = false, izquierda =
						// true)
	boolean estaApoyado; // Estado de si la isla está apoyada o no

	// Constructor de la clase Isla
	public Isla(double x, double y, Entorno ent) {
		this.x = x; // Inicializa la posición en X
		this.y = y; // Inicializa la posición en Y

		this.tamanio = 0.1; // Establece el tamaño de la isla
		this.img = entorno.Herramientas.cargarImagen("imagenes/isla.jpg"); // Carga
																			// la
																			// imagen
																			// de
																			// la
																			// isla

		// Calcula el ancho y alto de la isla en función de la imagen y el
		// tamaño
		this.ancho = img.getWidth(null) * tamanio;
		this.alto = img.getHeight(null) * tamanio;

		this.direccion = false; // Inicializa la dirección como falsa (derecha)
		this.estaApoyado = false; // Inicializa el estado como no apoyado

		this.e = ent; // Asigna el entorno
	}

	// Devuelve el borde derecho de la isla
	public double getBordeDer() {
		return x + this.ancho / 2;
	}

	// Devuelve el borde izquierdo de la isla
	public double getBordeIzq() {
		return x - this.ancho / 2;
	}

	// Devuelve el borde superior de la isla
	public double getBordeSup() {
		return y - this.alto / 2;
	}

	// Devuelve el borde inferior de la isla
	public double getBordeInf() {
		return y + this.alto / 2;
	}

	// Dibuja la isla en el entorno
	public void mostrar() {
		this.e.dibujarImagen(img, x, y, 0, tamanio);
	}

	// Devuelve la posición en X
	public double getX() {
		return this.x;
	}

	// Devuelve la posición en Y
	public double getY() {
		return this.y;
	}
}

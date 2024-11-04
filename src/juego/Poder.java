package juego;

import java.awt.Image; // Importa la clase Image para manejar imágenes
import entorno.Entorno; // Importa la clase Entorno para la gestión del entorno gráfico

public class Poder {
	double x; // Coordenada X del poder
	double y; // Coordenada Y del poder
	double tamanio; // Tamaño del poder
	double velocidad; // Velocidad a la que se mueve el poder
	double ancho; // Ancho del poder
	double alto; // Alto del poder
	boolean direccion; // Dirección del movimiento (true = izquierda, false =
						// derecha)
	Image bolaIzq; // Imagen del poder lanzado hacia la izquierda
	Image bolaDer; // Imagen del poder lanzado hacia la derecha
	Entorno e; // Referencia al entorno

	// Constructor de la clase Poder
	public Poder(double x, double y, Entorno ent, boolean direccion) {
		this.x = x; // Inicializa la posición en X
		this.y = y; // Inicializa la posición en Y
		this.tamanio = 0.50; // Establece el tamaño del poder
		this.velocidad = 5; // Establece la velocidad del poder
		this.direccion = direccion; // Inicializa la dirección del poder
		this.bolaDer = entorno.Herramientas
				.cargarImagen("imagenes/bolaDer.png"); // Carga la imagen del
														// poder a la derecha
		this.bolaIzq = entorno.Herramientas
				.cargarImagen("imagenes/bolaIzq.png"); // Carga la imagen del
														// poder a la izquierda
		this.ancho = bolaDer.getHeight(null) * tamanio; // Calcula el ancho
														// basado en la imagen
		this.alto = bolaDer.getWidth(null) * tamanio; // Calcula el alto basado
														// en la imagen
		this.e = ent; // Asigna el entorno
	}

	// Método para dibujar el poder en el entorno
	public void dibujarBola(Entorno e) {
		if (direccion) {
			e.dibujarImagen(bolaIzq, x, y, 0, tamanio); // Dibuja la imagen del
														// poder a la izquierda
		} else {
			e.dibujarImagen(bolaDer, x, y, 0, tamanio); // Dibuja la imagen del
														// poder a la derecha
		}
	}

	// Método para lanzar el poder en la dirección correspondiente
	public void lanzarBola() {
		if (direccion) {
			x -= velocidad; // Mueve el poder hacia la izquierda
		} else {
			x += velocidad; // Mueve el poder hacia la derecha
		}
	}

	// Getters para las coordenadas
	public double getX() {
		return x; // Devuelve la coordenada X
	}

	public double getY() {
		return y; // Devuelve la coordenada Y
	}
}

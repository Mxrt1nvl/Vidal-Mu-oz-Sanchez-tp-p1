package juego;

import java.awt.Image; // Importa la clase Image para manejar im�genes
import entorno.Entorno; // Importa la clase Entorno para la gesti�n del entorno gr�fico

public class Poder {
	double x; // Coordenada X del poder
	double y; // Coordenada Y del poder
	double tamanio; // Tama�o del poder
	double velocidad; // Velocidad a la que se mueve el poder
	double ancho; // Ancho del poder
	double alto; // Alto del poder
	boolean direccion; // Direcci�n del movimiento (true = izquierda, false =
						// derecha)
	Image bolaIzq; // Imagen del poder lanzado hacia la izquierda
	Image bolaDer; // Imagen del poder lanzado hacia la derecha
	Entorno e; // Referencia al entorno

	// Constructor de la clase Poder
	public Poder(double x, double y, Entorno ent, boolean direccion) {
		this.x = x; // Inicializa la posici�n en X
		this.y = y; // Inicializa la posici�n en Y
		this.tamanio = 0.50; // Establece el tama�o del poder
		this.velocidad = 5; // Establece la velocidad del poder
		this.direccion = direccion; // Inicializa la direcci�n del poder
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

	// M�todo para dibujar el poder en el entorno
	public void dibujarBola(Entorno e) {
		if (direccion) {
			e.dibujarImagen(bolaIzq, x, y, 0, tamanio); // Dibuja la imagen del
														// poder a la izquierda
		} else {
			e.dibujarImagen(bolaDer, x, y, 0, tamanio); // Dibuja la imagen del
														// poder a la derecha
		}
	}

	// M�todo para lanzar el poder en la direcci�n correspondiente
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

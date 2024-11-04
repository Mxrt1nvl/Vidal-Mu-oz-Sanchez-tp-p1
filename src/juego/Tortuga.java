package juego;

import java.awt.Image; // Importa la clase Image para manejar imágenes
import entorno.Entorno; // Importa la clase Entorno para la gestión del entorno gráfico

public class Tortuga {
	double x; // Coordenada X de la tortuga
	double y; // Coordenada Y de la tortuga
	double tamanio; // Tamaño de la tortuga
	double alto; // Altura de la tortuga
	double ancho; // Ancho de la tortuga
	double velocidad; // Velocidad de movimiento de la tortuga
	boolean direccion; // Dirección de movimiento (true = izquierda, false =
						// derecha)
	boolean estaApoyado; // Indica si la tortuga está apoyada
	Image imagenDer; // Imagen de la tortuga mirando a la derecha
	Image imagenIzq; // Imagen de la tortuga mirando a la izquierda
	Entorno e; // Referencia al entorno

	// Constructor de la clase Tortuga
	public Tortuga(double x, double y, Entorno ent) {
		this.x = Math.random() * 800.0; // Inicializa la posición X de manera
										// aleatoria
		boolean posicionValida = false;

		// Asegura que la posición de la tortuga sea válida
		while (!posicionValida) {
			if ((this.x > 5 && this.x < 350) || (this.x > 380 && this.x < 800)) {
				posicionValida = true; // Posición válida
			} else {
				this.x = Math.random() * 800.0; // Genera una nueva posición
			}
		}

		this.y = 200; // Inicializa la posición Y
		this.e = ent; // Asigna el entorno
		this.direccion = false; // Inicializa la dirección (derecha)
		this.tamanio = 0.10; // Establece el tamaño de la tortuga
		this.velocidad = 0.1; // Establece la velocidad de la tortuga
		this.imagenDer = entorno.Herramientas
				.cargarImagen("imagenes/tortugaDer.png"); // Carga la imagen
															// hacia la derecha
		this.imagenIzq = entorno.Herramientas
				.cargarImagen("imagenes/tortugaIzq.png"); // Carga la imagen
															// hacia la
															// izquierda
		this.ancho = imagenDer.getWidth(null) * this.tamanio; // Calcula el
																// ancho basado
																// en la imagen
		this.alto = imagenDer.getHeight(null) * this.tamanio; // Calcula el alto
																// basado en la
																// imagen
		this.estaApoyado = false; // Inicializa el estado de apoyo
	}

	// Método para mover la tortuga
	public void movimientoTortuga(Isla i) {
		if (direccion) {
			x -= velocidad; // Mueve hacia la izquierda
			if (x < i.getBordeIzq()) {
				this.direccion = !this.direccion; // Cambia dirección al
													// alcanzar el borde
													// izquierdo
			}
		} else {
			x += velocidad; // Mueve hacia la derecha
			if (x > i.getBordeDer()) {
				this.direccion = !this.direccion; // Cambia dirección al
													// alcanzar el borde derecho
			}
		}
	}

	// Método para dibujar la tortuga en el entorno
	public void dibujarTortuga(Entorno e) {
		if (direccion) {
			e.dibujarImagen(imagenIzq, x, y, 0, tamanio); // Dibuja imagen hacia
															// la izquierda
		} else {
			e.dibujarImagen(imagenDer, x, y, 0, tamanio); // Dibuja imagen hacia
															// la derecha
		}
	}

	// Método para aplicar gravedad a la tortuga
	public void gravedad() {
		if (!estaApoyado) {
			y += 2; // Aplica gravedad si no está apoyada
		}
	}

	// Getters para los bordes de la tortuga
	public double getBordeDer() {
		return x + this.ancho / 2; // Devuelve el borde derecho
	}

	public double getBordeIzq() {
		return x - this.ancho / 2; // Devuelve el borde izquierdo
	}

	public double getBordeSup() {
		return y - this.alto / 2; // Devuelve el borde superior
	}

	public double getBordeInf() {
		return y + this.alto / 2; // Devuelve el borde inferior
	}
}

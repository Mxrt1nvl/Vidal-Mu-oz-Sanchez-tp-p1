package juego;

import java.awt.Image; // Importa la clase Image para manejar im�genes
import entorno.Entorno; // Importa la clase Entorno para la gesti�n del entorno gr�fico

public class Gnomo {
	double x; // Posici�n en el eje X
	double y; // Posici�n en el eje Y
	double tamanio; // Tama�o del gnomo
	double ancho; // Ancho de la imagen del gnomo
	double alto; // Alto de la imagen del gnomo
	boolean direccion; // Direcci�n en la que mira el gnomo (true = izquierda,
						// false = derecha)
	boolean estaApoyado; // Estado de si el gnomo est� apoyado o no
	Image imagenDer; // Imagen del gnomo mirando a la derecha
	Image imagenIzq; // Imagen del gnomo mirando a la izquierda
	Entorno e; // Referencia al entorno

	// Constructor de la clase Gnomo
	public Gnomo(double x, double y, Entorno ent) {
		this.x = x; // Inicializa la posici�n en X
		this.y = y; // Inicializa la posici�n en Y
		this.tamanio = 0.10; // Establece el tama�o del gnomo
		this.direccion = Math.random() < 0.5; // Asigna una direcci�n aleatoria
		this.estaApoyado = false; // Inicializa el estado como no apoyado
		this.e = ent; // Asigna el entorno
		// Carga las im�genes del gnomo
		this.imagenDer = entorno.Herramientas
				.cargarImagen("imagenes/gnomoDer.png");
		this.imagenIzq = entorno.Herramientas
				.cargarImagen("imagenes/gnomoIzq.png");
		this.ancho = imagenDer.getHeight(null) * tamanio; // Calcula el ancho
															// del gnomo
		this.alto = imagenDer.getWidth(null) * tamanio; // Calcula el alto del
														// gnomo
	}

	// Cambia la direcci�n del gnomo de forma aleatoria
	public void cambiarDireccion() {
		this.direccion = Math.random() < 0.5;
	}

	// Actualiza la posici�n del gnomo en funci�n de su direcci�n
	public void movimientoGnomo() {
		if (this.direccion) {
			x--; // Mueve el gnomo a la izquierda
		} else {
			x++; // Mueve el gnomo a la derecha
		}
	}

	// Aplica gravedad al gnomo si no est� apoyado
	public void gravedad() {
		if (!estaApoyado) {
			y += 2; // Aumenta la posici�n Y para simular la ca�da
		}
	}

	// Dibuja el gnomo en el entorno seg�n su direcci�n
	public void dibujarGnomo(Entorno e) {
		if (direccion) {
			e.dibujarImagen(imagenIzq, x, y, 0, tamanio); // Dibuja la imagen
															// mirando a la
															// izquierda
		} else {
			e.dibujarImagen(imagenDer, x, y, 0, tamanio); // Dibuja la imagen
															// mirando a la
															// derecha
		}
	}

	// Devuelve el borde derecho del gnomo
	public double getBordeDer() {
		return x + this.ancho / 2;
	}

	// Devuelve el borde izquierdo del gnomo
	public double getBordeIzq() {
		return x - this.ancho / 2;
	}

	// Devuelve el borde superior del gnomo
	public double getBordeSup() {
		return y - this.alto / 2;
	}

	// Devuelve el borde inferior del gnomo
	public double getBordeInf() {
		return y + this.alto / 2;
	}
}

package juego;

import java.awt.Image; // Importa la clase Image para manejar imágenes
import entorno.Entorno; // Importa la clase Entorno para la gestión del entorno gráfico

public class Gnomo {
	double x; // Posición en el eje X
	double y; // Posición en el eje Y
	double tamanio; // Tamaño del gnomo
	double ancho; // Ancho de la imagen del gnomo
	double alto; // Alto de la imagen del gnomo
	boolean direccion; // Dirección en la que mira el gnomo (true = izquierda,
						// false = derecha)
	boolean estaApoyado; // Estado de si el gnomo está apoyado o no
	Image imagenDer; // Imagen del gnomo mirando a la derecha
	Image imagenIzq; // Imagen del gnomo mirando a la izquierda
	Entorno e; // Referencia al entorno

	// Constructor de la clase Gnomo
	public Gnomo(double x, double y, Entorno ent) {
		this.x = x; // Inicializa la posición en X
		this.y = y; // Inicializa la posición en Y
		this.tamanio = 0.10; // Establece el tamaño del gnomo
		this.direccion = Math.random() < 0.5; // Asigna una dirección aleatoria
		this.estaApoyado = false; // Inicializa el estado como no apoyado
		this.e = ent; // Asigna el entorno
		// Carga las imágenes del gnomo
		this.imagenDer = entorno.Herramientas
				.cargarImagen("imagenes/gnomoDer.png");
		this.imagenIzq = entorno.Herramientas
				.cargarImagen("imagenes/gnomoIzq.png");
		this.ancho = imagenDer.getHeight(null) * tamanio; // Calcula el ancho
															// del gnomo
		this.alto = imagenDer.getWidth(null) * tamanio; // Calcula el alto del
														// gnomo
	}

	// Cambia la dirección del gnomo de forma aleatoria
	public void cambiarDireccion() {
		this.direccion = Math.random() < 0.5;
	}

	// Actualiza la posición del gnomo en función de su dirección
	public void movimientoGnomo() {
		if (this.direccion) {
			x--; // Mueve el gnomo a la izquierda
		} else {
			x++; // Mueve el gnomo a la derecha
		}
	}

	// Aplica gravedad al gnomo si no está apoyado
	public void gravedad() {
		if (!estaApoyado) {
			y += 2; // Aumenta la posición Y para simular la caída
		}
	}

	// Dibuja el gnomo en el entorno según su dirección
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

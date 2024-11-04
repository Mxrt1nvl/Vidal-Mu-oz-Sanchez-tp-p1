package juego;

import java.awt.Image; // Importa la clase Image para manejar im�genes
import entorno.Entorno; // Importa la clase Entorno para la gesti�n del entorno gr�fico

public class Personaje {

	// Posici�n del personaje
	double x; // Coordenada X
	double y; // Coordenada Y
	int contadorSalto; // Contador para la altura del salto

	// Aspecto
	Image imagenDerecha; // Imagen del personaje mirando a la derecha
	Image imagenIzquierda; // Imagen del personaje mirando a la izquierda

	double tamanio; // Tama�o del personaje
	Entorno e; // Referencia al entorno

	// Ancho y Alto
	double ancho; // Ancho del personaje
	double alto; // Alto del personaje

	// Comportamientos del Personaje
	boolean direccion; // Direcci�n del personaje (derecha = false, izquierda =
						// true)
	boolean estaApoyado; // Estado de si el personaje est� apoyado en el suelo
	boolean estaSaltando; // Estado de si el personaje est� saltando

	// Constructor de la clase Personaje
	public Personaje(double x, double y, Entorno ent) {
		this.x = x; // Inicializa la posici�n en X
		this.y = y; // Inicializa la posici�n en Y

		// Inicializa variables del personaje
		this.contadorSalto = 0; // Establece el contador de salto en 0
		this.tamanio = 0.20; // Establece el tama�o del personaje
		this.imagenDerecha = entorno.Herramientas
				.cargarImagen("imagenes/pepDer.png"); // Carga imagen derecha
		this.imagenIzquierda = entorno.Herramientas
				.cargarImagen("imagenes/pepIzq.png"); // Carga imagen izquierda
		this.ancho = imagenDerecha.getWidth(null) * tamanio; 
		// Calcula ancho
																// basado en
																// imagen
		this.alto = imagenIzquierda.getHeight(null) * tamanio; // Calcula alto
																// basado en
																// imagen

		// Inicializa comportamiento del personaje
		this.direccion = false; // Inicializa direcci�n como falsa (derecha)
		this.estaApoyado = false; // Inicializa estado como no apoyado
		this.estaSaltando = false; // Inicializa estado como no saltando

		this.e = ent; // Asigna el entorno
	}

	// M�todos

	// Dibuja el personaje en el entorno
	public void dibujarPersonaje(Entorno e) {
		if (direccion) {
			e.dibujarImagen(imagenIzquierda, x, y, 0, tamanio); // Dibuja imagen
																// izquierda
		} else {
			e.dibujarImagen(imagenDerecha, x, y, 0, tamanio); // Dibuja imagen
																// derecha
		}
	}

	// Movimiento horizontal del personaje
	public void movHorizontal(double m) {
		x += m; // Actualiza la posici n en X
		
		
		// Limita el movimiento dentro del ancho del entorno
		if (getBordeDer() > this.e.ancho()) {
			this.x = e.ancho() - (this.ancho / 2); // Ajusta posici n si se sale
													// por la derecha
		}
		if (getBordeIzq() < 0) {
			this.x = 0 + (this.ancho / 2); // Ajusta posici n si se sale por la
											// izquierda
		}

		// Establece la direcci n del movimiento
		if (m >= 0) {
			direccion = false; // Derecha
		} else {
			direccion = true; // Izquierda
		}
	}

	// Aplica gravedad al personaje
	public void gravedad() {
		if (!this.estaApoyado && !estaSaltando) {
			y += 2; // Aplica gravedad si no est� apoyado ni saltando
		}
		if (estaSaltando) {
			y -= 4; // Eleva el personaje mientras salta
			contadorSalto++; // Incrementa el contador de salto
		}
		// Termina el salto despu�s de un cierto tiempo
		if (contadorSalto == 45) {
			estaSaltando = false; // Finaliza el salto
			contadorSalto = 0; // Resetea el contador
		}
	}

	// Getters y Setters

	// Devuelve el borde derecho del personaje
	public double getBordeDer() {
		return x + this.ancho / 2;
	}

	// Devuelve el borde izquierdo del personaje
	public double getBordeIzq() {
		return x - this.ancho / 2;
	}

	// Devuelve el borde superior del personaje
	public double getBordeSup() {
		return y - this.alto / 2;
	}

	// Devuelve el borde inferior del personaje
	public double getBordeInf() {
		return y + this.alto / 2;
	}

	// Hace saltar al personaje
	public void saltar() {
		this.estaSaltando = true; // Activa el estado de salto
		this.estaApoyado = true; // El personaje est� apoyado cuando salta
	}

	// Cancela el salto del personaje
	public void cancelarSalto() {
		estaSaltando = false; // Desactiva el estado de salto
		contadorSalto = 0; // Resetea el contador
	}
}

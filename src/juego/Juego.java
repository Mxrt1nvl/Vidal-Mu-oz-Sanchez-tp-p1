package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    private Entorno entorno; // Objeto que maneja el entorno del juego
    private Personaje pep; // Personaje principal (Pep)
    private Tortuga[] tortugas; // Arreglo de tortugas
    private Gnomo[] gnomos; // Arreglo de gnomos
    private Isla[] islas; // Arreglo de islas
    private Casa casita; // Objeto de la casa
    private Poder[] bolas = new Poder[25]; // Arreglo de poderes (bolas)
    private Fondo fondo; // Objeto del fondo del juego
    private boolean pepVivo; // Estado de vida del personaje principal
    private int gnomosRescatados = 0; // Contador de gnomos rescatados
    private int gnomosPerdidos = 0; // Contador de gnomos perdidos
    private int enemigosEliminados = 0; // Contador de enemigos eliminados
    private boolean finJuego = false; // Estado del juego (fin o no)
    private String mensajeFinal = ""; // Mensaje final del juego

    // Constructor de la clase Juego
    Juego() {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Pep al rescate de Gnomos", 800, 600);
        // Inicia el juego
        iniciarJuego();
        this.entorno.iniciar();
    }
 // Método que se ejecuta en cada tick del juego
    public void tick() {
        // Verificar si el juego ha terminado
        if (finJuego) {
            // Mostrar mensaje de finalización del juego o detener otras acciones
            double rectX = entorno.ancho() / 2; // Coordenada X del rectángulo
            double rectY = entorno.alto() / 2; // Coordenada Y del rectángulo
            double rectAncho = 400; // Ancho del rectángulo
            double rectAlto = 300; // Alto del rectángulo
            
            // Dibujar fondo y casa
            fondo.dibujar();
            casita.dibujarCasa(entorno);

            // Dibujar gnomos
            for (Gnomo gnomo : gnomos) {
                if (gnomo != null) {
                    gnomo.dibujarGnomo(entorno); // Dibujar cada gnomo
                }
            }

            // Mostrar islas
            for (Isla isla : islas) {
                isla.mostrar();
            }

            // Dibujar tortugas
            for (Tortuga tortuga : tortugas) {
                if (tortuga != null) {
                    tortuga.dibujarTortuga(entorno); // Dibujar cada tortuga
                }
            }

            // Dibujar al personaje Pep si está vivo
            if (pepVivo) {
                pep.dibujarPersonaje(entorno);
            }

            // Dibujar el rectángulo de fin del juego
            entorno.dibujarRectangulo(rectX, rectY, rectAncho, rectAlto, 0, Color.WHITE);
            entorno.cambiarFont("Arial", 26, Color.BLACK); // Cambiar fuente para el mensaje
            entorno.escribirTexto("¡Juego Terminado!", rectX - 130, rectY - 50); // Mensaje de juego terminado
            entorno.escribirTexto(mensajeFinal, rectX - 130, rectY); // Mensaje final del juego
            entorno.escribirTexto("Presione R para volver a jugar", rectX - 160, rectY + 50); // Instrucciones para reiniciar

            // Reiniciar el juego si se presiona 'R'
            if (entorno.sePresiono('r')) {
                iniciarJuego();
            }
            return; // Detiene el procesamiento adicional
        }

        // Dibujar fondo y estadísticas
        fondo.dibujar();
        drawStatistics(); // Dibuja las estadísticas del juego

        // DIBUJAR PERSONAJES
        // Dibujar casa
        casita.dibujarCasa(entorno);

        // Verificar y dibujar gnomos
        verificarGnomosPisandoIsla(gnomos, islas); // Verificar si los gnomos están en las islas
        for (Gnomo gnomo : gnomos) {
            if (gnomo != null) {
                gnomo.dibujarGnomo(entorno); // Dibujar cada gnomo
                gnomo.movimientoGnomo(); // Movimiento del gnomo
                gnomo.gravedad(); // Aplicar gravedad al gnomo
            }
        }
        
        // Rescatar gnomos si están disponibles
        if (gnomos != null) {
            rescatarGnomo(pep, gnomos);
        }
        
        // Verificar gnomos perdidos
        for (int g = 0; g < gnomos.length; g++) {
            if (gnomos[g] != null && gnomos[g].y > 800) {
                gnomos[g] = null; // El gnomo se perdió
                gnomosPerdidos++;
            }
        }

        // Generar nuevos gnomos cada 100 ticks
        if (entorno.numeroDeTick() % 100 == 0) {
            for (int i = 0; i < gnomos.length; i++) {
                if (gnomos[i] == null) {
                    gnomos[i] = new Gnomo(400 + i * 10, 71, entorno); // Crear nuevo gnomo
                    break;
                }
            }
        }

        // Mostrar islas
        for (Isla i : this.islas) {
            i.mostrar(); // Mostrar las islas
        }

        // Lanzar y dibujar poderes (bolas)
        for (Poder poder : bolas) {
            if (poder != null) {
                poder.lanzarBola(); // Lanzar la bola
                poder.dibujarBola(entorno); // Dibujar la bola
            }
        }
        
        // Verificar si algún enemigo ha muerto
        if (bolas != null) {
            enemigoMuerto(bolas, tortugas);
        }
        
        // Eliminar poderes que se han perdido
        for (Poder poder : bolas) {
            if (poder != null && poder.y > 1300) {
                poder = null; // El poder se pierde
            }
        }

        // DIBUJAR A PEP
        if (pepVivo) {
            pep.dibujarPersonaje(entorno); // Dibujar a Pep

            // Verificar si Pep está apoyado en una isla
            if (pisandoIsla(pep, islas)) {
                this.pep.estaApoyado = true; // Pep está apoyado
            } else {
                this.pep.estaApoyado = false; // Pep no está apoyado
                if (pep.y > entorno.alto()) {
                    pepVivo = false; // Pep ha caído
                }
            }

            // Verificar si Pep toca el techo
            if (tocaTecho(pep, islas)) {
                pep.cancelarSalto(); // Cancelar salto si toca el techo
            }

            // Aplicar gravedad a Pep
            pep.gravedad();
            verificarTeclas(); // Verificar las teclas presionadas

            // Verificar si Pep ha muerto
            if (pepMurio(pep, tortugas)) {
                pepVivo = false; // Pep ha muerto
            }
        }

        // DIBUJAR TORTUGAS
        for (Tortuga t : tortugas) {
            if (t != null) {
                // Verificar si la tortuga está apoyada en una isla
                if (pisandoIsla(t, islas) != -1) {
                    t.estaApoyado = true; // Tortuga está apoyada
                } else {
                    t.estaApoyado = false; // Tortuga no está apoyada
                }
                t.gravedad(); // Aplicar gravedad a la tortuga
            }
        }

        // Dibujar tortugas
        for (Tortuga t : tortugas) {
            if (t != null) {
                t.dibujarTortuga(entorno); // Dibujar la tortuga
            }
        }
        
        // Verificar si algún gnomo ha muerto por las tortugas
        if (tortugas != null) {
            GnomoMuerto(tortugas, gnomos);
        }

        // Mover islas
        movimientoIsla();
        verificarFinDelJuego(); // Verificar si el juego ha terminado
    }

    
 // Método para verificar las condiciones de fin de juego
    private void verificarFinDelJuego() {
        // Verificar si se han rescatado al menos 5 gnomos
        if (gnomosRescatados == 5) {
            finJuego = true; // Marcar que el juego ha terminado
            mensajeFinal = "Ganaste, ¡has rescatado al menos un gnomo!"; // Mensaje de victoria
        } 
        // Verificar si Pep ha muerto
        else if (pepMurio(pep, tortugas)) {
            finJuego = true; // Marcar que el juego ha terminado
            mensajeFinal = "Perdiste, Pep ha muerto."; // Mensaje de derrota
        } 
        // Verificar si se han perdido 4 gnomos
        else if (gnomosPerdidos == 4) {
            finJuego = true; // Marcar que el juego ha terminado
            mensajeFinal = "Perdiste, " + gnomosPerdidos + " gnomos murieron."; // Mensaje de derrota con número de gnomos perdidos
        } 
        // Verificar si Pep ha caído fuera del límite
        else if (pep != null && pep.y > 603) {
            finJuego = true; // Marcar que el juego ha terminado
            mensajeFinal = "Perdiste, Pep ha muerto."; // Mensaje de derrota
        }
    }

 
 // Método para iniciar el juego y inicializar todos los elementos necesarios
    private void iniciarJuego() {
        // Inicializar el personaje principal (Pep)
        this.pep = new Personaje(600, 450, entorno);
        
        // Generar un número aleatorio de tortugas entre 4 y 6
        int numeroTortugas = (int) (Math.random() * 3) + 4;
        this.tortugas = new Tortuga[numeroTortugas]; // Crear el array de tortugas
        
        // Inicializar islas y otros elementos del juego
        this.islas = new Isla[15]; // Crear el array de islas
        this.casita = new Casa(400, 75, entorno); // Crear la casa
        this.gnomos = new Gnomo[5]; // Crear el array de gnomos
        this.fondo = new Fondo("imagenes/fondo.jpg", entorno); // Establecer el fondo
        this.pepVivo = true; // Indicar que Pep está vivo
        this.gnomosRescatados = 0; // Inicializar contador de gnomos rescatados
        this.gnomosPerdidos = 0; // Inicializar contador de gnomos perdidos
        this.enemigosEliminados = 0; // Inicializar contador de enemigos eliminados
        this.finJuego = false; // Indicar que el juego no ha terminado

        // CREAR ISLAS EN FORMA DE PIRÁMIDE
        int k = 0; // Contador para las islas
        for (int i = 1; i <= 5; i++) { // Para cada nivel de la pirámide
            for (int j = 1; j <= i; j++) { // Para cada isla en el nivel
                // Posiciones de las islas según el nivel
                if (i == 5) {
                    this.islas[k] = new Isla((j * entorno.ancho() / (i + 1)) - 92 + (2 * j) * (i * 3), i * 110, entorno);
                }
                if (i == 4) {
                    this.islas[k] = new Isla((j * entorno.ancho() / (i + 1)) - 60 + (2 * j) * (i * 3), i * 110, entorno);
                }
                if (i == 3) {
                    this.islas[k] = new Isla((j * entorno.ancho() / (i + 1)) - (2 * j) + 5, i * 110, entorno);
                }
                if (i == 2) {
                    this.islas[k] = new Isla((j * entorno.ancho() / (i + 1)) - (2 * j) + 5, i * 110, entorno);
                }
                if (i == 1) {
                    this.islas[k] = new Isla(entorno.ancho() / 2, 110, entorno);
                }
                k++; // Incrementar el contador de islas
            }
        }

        // Crear las tortugas en el juego
        crearTortugas();
    }


 // Variables para controlar el lanzamiento de poderes
    int tiempoUltimoLanzamiento = 0; // Tiempo del último lanzamiento
    int intervaloLanzamiento = 1000; // Intervalo mínimo entre lanzamientos (en milisegundos)
    boolean primerLanzamiento = true; // Indica si es el primer lanzamiento

    // MÉTODO PARA CHEQUEAR TECLAS PARA LAS ACCIONES DEL PERSONAJE
    private void verificarTeclas() {
        // Movimiento a la derecha
        if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) {
        	
            pep.movHorizontal(3); // Mover a la derecha
        }
        

        // Movimiento a la izquierda
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a')) {
        	
        	pep.movHorizontal(-3); // Mover a la izquierda
        }
        

        // Salto
        if (entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono('w')) {
       	if(pep.estaApoyado) {
            pep.saltar(); // Realizar salto
        	}
      }

        // Verificar si se presionó el botón izquierdo del mouse o 'c' para lanzar un poder
        if ((entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) || entorno.sePresiono('c')) &&
            (primerLanzamiento || (entorno.tiempo() - tiempoUltimoLanzamiento >= intervaloLanzamiento))) {

            // Crear un nuevo poder en la posición del personaje
            Poder nuevoPoder = new Poder(pep.getBordeDer(), pep.getBordeSup() + 20, entorno, pep.direccion);
            
            // Buscar un espacio vacío para lanzar el nuevo poder
            for (int i = 0; i < bolas.length; i++) {
                if (bolas[i] == null) { 
                    bolas[i] = nuevoPoder; // Asignar el nuevo poder al primer espacio vacío
                    tiempoUltimoLanzamiento = entorno.tiempo(); // Actualizar el tiempo del último lanzamiento
                    primerLanzamiento = false; // Indicar que ya no es el primer lanzamiento
                    break; // Salir del bucle una vez que se ha lanzado
                }
            }
        }

        // Calcular el tiempo transcurrido desde el último lanzamiento
        double tiempoTranscurridoDesdeUltimoLanzamiento = entorno.tiempo() - tiempoUltimoLanzamiento;
        double tiempoRestante = intervaloLanzamiento - tiempoTranscurridoDesdeUltimoLanzamiento;

        // Asegurarse de que el tiempo restante no sea negativo 
        if (tiempoRestante < 0) {
            tiempoRestante = 0; // Si es negativo, establecerlo a cero
        }
    }

 // Método para verificar si el personaje está pisando alguna isla
    private boolean pisandoIsla(Personaje p, Isla[] islas) {
        for (Isla isla : islas) {
            if (pisandoIsla(p, isla)) {
                return true; // Retorna verdadero si el personaje está pisando alguna isla
            }
        }
        return false; // Retorna falso si no está pisando ninguna isla
    }

    // Método que verifica si el personaje está pisando una isla específica
    private boolean pisandoIsla(Personaje p, Isla isla) {
        return (Math.abs(p.getBordeInf() - isla.getBordeSup()) < 5) // Verifica la colisión vertical
                && (p.getBordeIzq() < isla.getBordeDer() - 5) // Verifica el borde izquierdo
                && (p.getBordeDer() > isla.getBordeIzq() + 5); // Verifica el borde derecho
    }

    // Método para verificar si el personaje toca el techo de alguna isla
    private boolean tocaTecho(Personaje p, Isla[] islas) {
        for (Isla i : islas) {
            // Verifica si el personaje está dentro del rango horizontal de la isla
            if (p.getBordeIzq() < i.getBordeDer() - 5 && p.getBordeDer() > i.getBordeIzq() + 5) {
                // Verifica si el personaje está debajo del borde superior de la isla y encima del borde inferior
             if (p.getBordeSup() <= i.getBordeInf()&& p.getBordeInf() >= i.getBordeSup() +2 ) {
                    return true; // Retorna verdadero si toca el techo
           
                }
            }
        }
        return false; // Retorna falso si no toca el techo de ninguna isla
    }

    // Método para verificar si la tortuga está pisando una isla
    private boolean pisandoIsla(Tortuga t, Isla i) {
        return (Math.abs(t.getBordeInf() - i.getBordeSup()) < 1) // Verifica colisión vertical
                && t.getBordeDer() > i.getBordeIzq() // Verifica el borde derecho de la tortuga
                && t.getBordeIzq() < i.getBordeDer(); // Verifica el borde izquierdo de la tortuga
    }

    // Método para verificar si la tortuga está pisando alguna isla y retornar el índice de la isla
    private int pisandoIsla(Tortuga t, Isla[] islas) {
        for (int i = 0; i < islas.length; i++) {
            if (pisandoIsla(t, islas[i])) {
                return i; // Retorna el índice de la isla donde está pisando la tortuga
            }
        }
        return -1; // Retorna -1 si no está pisando ninguna isla
    }

    // Método para gestionar el movimiento de las tortugas en las islas
    private void movimientoIsla() {
        int islaActual = 0; // Variable para almacenar la isla actual
        for (int i = 0; i < tortugas.length; i++) {
            if (tortugas[i] != null) {
                int r = pisandoIsla(tortugas[i], islas); // Verifica si la tortuga está pisando una isla
                if (r >= 0) {
                    if (r != islaActual) {
                        this.tortugas[i].estaApoyado = true; // Marca que la tortuga está apoyada
                        tortugas[i].movimientoTortuga(islas[r]); // Llama al método de movimiento de la tortuga
                        islaActual = r; // Actualiza la isla actual
                    }
                } else {
                    this.tortugas[i].estaApoyado = false; // Marca que la tortuga no está apoyada
                }
            }
        }
    }

//Método para crear tortugas en las islas
 private void crearTortugas() {
     // Asegúrate de no crear más tortugas que islas disponibles
     int numTortugas = Math.min(this.tortugas.length, this.islas.length);

     // Itera para crear una tortuga por cada isla disponible
     for (int i = 0; i < numTortugas; i++) {
         if (islas[i] != null) {
             // Obtén las coordenadas de cada isla
             double xTortuga = islas[i].getX(); // Posición en X de la isla
             double yTortuga = islas[i].getBordeSup() - 10; // Ajusta para que esté encima de la isla

             // Crea cada tortuga en la posición de una isla diferente
             this.tortugas[i] = new Tortuga(xTortuga, yTortuga, entorno);
         }
     }
 }

 // Método para verificar si los gnomos están pisando alguna isla y ajustar su dirección en consecuencia
 private void verificarGnomosPisandoIsla(Gnomo[] gnomos, Isla[] islas) {
     // Itera sobre el arreglo de gnomos
     for (Gnomo gnomo : gnomos) {
         boolean estaApoyado = false; // Inicializa la variable para verificar si el gnomo está apoyado en una isla
         
         // Itera sobre el arreglo de islas
         for (Isla isla : islas) {
             // Verifica si el gnomo no es nulo y si está pisando la isla actual
             if (gnomo != null && pisandoIsla(gnomo, isla)) {
                 estaApoyado = true; // Marca que el gnomo está apoyado
                 break; // Si está pisando una isla, no necesitamos seguir buscando
             }
         }

         // Si el gnomo está apoyado y no estaba previamente, cambia su dirección
         if (gnomo != null && estaApoyado && !gnomo.estaApoyado) {
             gnomo.cambiarDireccion();
         }

         // Actualiza el estado de apoyo del gnomo
         if (gnomo != null) {
             gnomo.estaApoyado = estaApoyado;
         }
     }
 }


 // Método para verificar si un gnomo está pisando una isla
 private boolean pisandoIsla(Gnomo g, Isla i) {
     // Verifica si el borde inferior del gnomo está cerca del borde superior de la isla
     // y si los bordes izquierdo y derecho del gnomo están dentro de los límites de la isla
     return (Math.abs(g.getBordeInf() - i.getBordeSup()) < 1) 
             && (g.getBordeIzq() < i.getBordeDer())
             && (g.getBordeDer() > i.getBordeIzq());
 }


//Método para verificar si un enemigo (tortuga) ha sido eliminado por una bola de poder
 private boolean enemigoMuerto(Poder[] bolas, Tortuga[] tortugas) {
     // Itera sobre el arreglo de bolas de poder
     for (int i = 0; i < bolas.length; i++) {
         Poder bola = bolas[i]; // Obtiene la bola de poder actual
         
         // Verifica si la bola no es nula
         if (bola == null)
             continue; // Continúa con la siguiente iteración si es nula
         
         // Itera sobre el arreglo de tortugas
         for (int j = 0; j < tortugas.length; j++) {
             Tortuga t = tortugas[j]; // Obtiene la tortuga actual
             
             // Verifica si la tortuga no es nula y si hay colisión con la bola de poder
             if (t != null && 
                 (bola.getX() - t.x) * (bola.getX() - t.x) + 
                 (bola.getY() - t.y) * (bola.getY() - t.y) < 400) {
                 tortugas[j] = null; // Marca la tortuga como eliminada
                 bolas[i] = null; // Marca la bola de poder como utilizada
                 enemigosEliminados++; // Incrementa el contador de enemigos eliminados
                 return true; // Devuelve verdadero si se ha eliminado un enemigo
             }
         }
     }
     return false; // No hubo colisión entre bolas de poder y tortugas
 }

 // Método para verificar si Pep ha muerto al colisionar con una tortuga
 private boolean pepMurio(Personaje p, Tortuga[] tortugas) {
     // Itera sobre el arreglo de tortugas
     for (Tortuga t : tortugas) {
         // Verifica si la tortuga no es nula y si hay colisión con Pep
         if (t != null && 
             (p.x - t.x) * (p.x - t.x) + 
             (p.y - t.y) * (p.y - t.y) < 400) {
             return true; // Devuelve verdadero si Pep ha colisionado con una tortuga
         }
     }
     return false; // Pep no ha colisionado con ninguna tortuga
 }


//Método para verificar si un gnomo ha muerto al colisionar con una tortuga
 private boolean GnomoMuerto(Tortuga[] tortugas, Gnomo[] gnomos) {
     // Itera sobre el arreglo de tortugas
     for (int i = 0; i < tortugas.length; i++) {
         Tortuga t = tortugas[i]; // Obtiene la tortuga actual
         
         // Verifica si la tortuga no es nula
         if (t != null) {
             // Itera sobre el arreglo de gnomos
             for (int j = 0; j < gnomos.length; j++) {
                 // Verifica si el gnomo no es nulo y si hay colisión entre la tortuga y el gnomo
                 if (gnomos[j] != null && 
                     (t.x - gnomos[j].x) * (t.x - gnomos[j].x) + 
                     (t.y - gnomos[j].y) * (t.y - gnomos[j].y) < 400) {
                     gnomos[j] = null; // Marca el gnomo como perdido
                     gnomosPerdidos++; // Incrementa el contador de gnomos perdidos
                     return true; // Devuelve verdadero si se ha colisionado
                 }
             }
         }
     }
     return false; // No hubo colisión entre tortugas y gnomos
 }


//Método para rescatar un gnomo si Pep está en la posición correcta
 private boolean rescatarGnomo(Personaje pep, Gnomo[] gnomos) {
     // Itera sobre el arreglo de gnomos
     for (int i = 0; i < gnomos.length; i++) {
         // Verifica si el gnomo actual no es nulo
         if (gnomos[i] != null) {
             boolean enTercerFila = false; // Variable para verificar si el gnomo está en la tercera fila
             
             // Itera sobre las islas para verificar la posición del gnomo
             for (Isla isla : islas) {
                 // Comprueba si la isla está en la tercera fila (y >= 300) y si Pep está pisando la isla
                 if (isla.getY() >= 300 && pisandoIsla(gnomos[i], isla)) {
                     enTercerFila = true; // Se establece a verdadero si se encuentra en la tercera fila
                     break; // Sale del bucle al encontrar la isla correspondiente
                 }
             }

             // Verifica si el gnomo está en la tercera fila y si Pep está lo suficientemente cerca del gnomo
             if (enTercerFila && 
                 (pep.x - gnomos[i].x) * (pep.x - gnomos[i].x) + 
                 (pep.y - gnomos[i].y) * (pep.y - gnomos[i].y) < 400) {
                 gnomos[i] = null; // Marca el gnomo como rescatado
                 gnomosRescatados++; // Incrementa el contador de gnomos rescatados
                 return true; // Devuelve verdadero si se ha rescatado un gnomo
             }
         }
     }
     return false; // Devuelve falso si no se ha rescatado ningún gnomo
 }

	
//Método para dibujar las estadísticas del juego
 private void drawStatistics() {
     // Calcula el tiempo total transcurrido en segundos
     double TotalSeg = (entorno.tiempo() / 1000.0);
     int minutos = (int) (TotalSeg / 60); // Calcula los minutos
     int segundos = (int) (TotalSeg % 60); // Calcula los segundos restantes

     double tiempoRestante; // Variable para almacenar el tiempo restante

     // Cambia la fuente y el tamaño del texto para las estadísticas
     entorno.cambiarFont("Arial", 18, Color.BLACK);

     // Escribe el tiempo transcurrido en la pantalla
     entorno.escribirTexto("Tiempo: " + minutos + ":" + String.format("%02d", segundos), 10, 30);
     // Escribe la cantidad de gnomos rescatados
     entorno.escribirTexto("Gnomos Rescatados: " + gnomosRescatados, 10, 50);
     // Escribe la cantidad de gnomos perdidos
     entorno.escribirTexto("Gnomos Perdidos: " + gnomosPerdidos, 10, 70);
     // Escribe la cantidad de enemigos eliminados
     entorno.escribirTexto("Enemigos Eliminados: " + enemigosEliminados, 10, 90);

     // Verifica si es el primer lanzamiento
     if (primerLanzamiento) {
         tiempoRestante = 0.0; // Si es el primer lanzamiento, no hay tiempo restante
     } else {
         // Calcula el tiempo transcurrido desde el último lanzamiento
         double tiempoTranscurridoDesdeUltimoLanzamiento = entorno.tiempo() - tiempoUltimoLanzamiento;
         // Calcula el tiempo restante hasta el próximo lanzamiento
         tiempoRestante = (intervaloLanzamiento - tiempoTranscurridoDesdeUltimoLanzamiento) / 1000;
         
         // Si el tiempo restante es negativo, se establece a cero
         if (tiempoRestante < 0) {
             tiempoRestante = 0;
         }
         
         // Escribe el tiempo restante para el próximo disparo
         entorno.escribirTexto("Tiempo para próximo disparo: " + String.format("%.1f", tiempoRestante) + " Seg", 10, 110);
     }
 }


 @SuppressWarnings("unused")
 public static void main(String[] args) {
Juego juego = new Juego();
 }
}
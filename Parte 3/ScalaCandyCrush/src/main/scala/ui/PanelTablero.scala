package ui

import interfaz.Juego
import scala.swing._
class PanelTablero(numFilas: Int, numCol: Int, juego: Juego, modo: Int) extends GridPanel(numFilas, numCol){
  private val caramelos: List[CaramelosLabel] = crearCaramelos(0) // Crear los caramelos
  background = new Color(0,0,0,26) // Color de fondo

  /**
   * Método que crea los caramelos
   * @param pos Posición del caramelo
   * @return Lista de caramelos
   */
  private def crearCaramelos(pos: Int): List[CaramelosLabel] = {
    if (pos != numCol*numFilas) { // Si no se han creado todos los caramelos
      val fila = pos / numCol // Obtener la fila y la columna
      val col = pos % numCol // del caramelo

      val caramelo = new CaramelosLabel(fila, col, juego, modo) // Crear el caramelo
      contents += caramelo // Añadir el caramelo al panel
      caramelo :: crearCaramelos(pos+1) // Crear el siguiente caramelo
    } else {
      Nil // Devolver Nil
    }
  }

  /**
   * Método que actualiza el tablero
   *
   * @param tab Tablero
  */
  def actualizarTablero(tab: List[Char]): Unit = {
    actualizarCaramelos(caramelos, tab) // Actualizar los caramelos
  }

  /**
   * Método que actualiza los caramelos
   * @param car Lista de caramelos
   * @param tablero Tablero
   */
  private def actualizarCaramelos(car: List[CaramelosLabel], tablero: List[Char]): Unit = {
    if (car != Nil) { // Si la lista de caramelos no está vacía
      val anchuraCaramelo: Int = this.size.width / numCol // Obtener la anchura y la altura
      val alturaCaramelo: Int = this.size.height / numFilas // del caramelo
      car.head.actualizarCaramelo(tablero.head, anchuraCaramelo, alturaCaramelo) // Actualizar el caramelo
      actualizarCaramelos(car.tail, tablero.tail) // Actualizar el siguiente caramelo
    }
  }
}

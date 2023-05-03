package ui

import interfaz.Juego
import scala.swing._
class PanelTablero(numFilas: Int, numCol: Int, juego: Juego, modo: Int) extends GridPanel(numFilas, numCol){
  private val caramelos: List[CaramelosLabel] = crearCaramelos(0)
  background = new Color(0,0,0,26)


  private def crearCaramelos(pos: Int): List[CaramelosLabel] = {
    if (pos != numCol*numFilas) {
      val fila = pos / numCol
      val col = pos % numCol

      val caramelo = new CaramelosLabel(fila, col, juego, modo)
      contents += caramelo
      caramelo :: crearCaramelos(pos+1)
    } else {
      Nil
    }
  }

  def actualizarTablero(tab: List[Char]): Unit = {
    actualizarCaramelos(caramelos, tab)
  }

  private def actualizarCaramelos(car: List[CaramelosLabel], tablero: List[Char]): Unit = {
    if (car != Nil) {
      val anchuraCaramelo: Int = this.size.width / numCol
      val alturaCaramelo: Int = this.size.height / numFilas
      car.head.actualizarCaramelo(tablero.head, anchuraCaramelo, alturaCaramelo)
      actualizarCaramelos(car.tail, tablero.tail)
    }
  }
}

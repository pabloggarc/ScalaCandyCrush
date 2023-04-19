package ui

import java.awt.Font
import scala.swing._

class PanelPuntuacion extends FlowPanel {
  private val textoPuntuacion: Label = new Label("Puntuacion: 0") {
    font = new Font("Arial", java.awt.Font.PLAIN, 30)
  }
  opaque = false
  contents += textoPuntuacion

  private var puntos: Int = 0

  def actualizarPuntos(puntosNuevos: Int): Unit = {
    puntos += puntosNuevos // Aumentar los puntos actuales
    textoPuntuacion.text = s"Puntuacion: $puntos" // Actualizar el Label con los nuevos puntos
  }

}


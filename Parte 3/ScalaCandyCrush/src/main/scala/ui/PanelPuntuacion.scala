package ui

import java.awt.{Font, Image}
import javax.swing.ImageIcon
import scala.swing._

class PanelPuntuacion extends FlowPanel {
  private val textoPuntuacion: Label = new Label("Puntuación: 0") {
    font = new Font("Arial", java.awt.Font.PLAIN, 30)
  }
  opaque = false
  contents += textoPuntuacion

  private var puntos: Int = 0

  def actualizarPuntos(puntosNuevos: Int): Unit = {
    puntos += puntosNuevos // Aumentar los puntos actuales
    textoPuntuacion.text = s"Puntuación: $puntos" // Actualizar el Label con los nuevos puntos
    actualizarEstrellas(puntos)
  }

  opaque = false

  private val estrellaVacia: String = "src/img/EstrellaVacia.png"
  private val estrellaLlena: String = "src/img/EstrellaLlena.png"
  private val estrellaAzul: String = "src/img/EstrellaAzul.png"
  private val estrellas: List[Label] = setEstrellas(3) // Lista de corazones

  private def setEstrellas(n: Int): List[Label] = { // Método que crea los corazones
    if (n != 0) { // Si aún no se han creado todos los corazones
      val estrella = new Label { // Corazón
        val imagenEstrella = new Imagenes(estrellaVacia) // Imagen del corazón
        icon = new ImageIcon(imagenEstrella.getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)) // Icono del corazón
      }
      contents += estrella // Agregar el corazón
      estrella :: setEstrellas(n - 1) // Agregar el corazón a la lista
    } else {
      Nil // Devolver Nil
    }
  }


  def actualizarEstrellas(puntos: Int): Unit = {
    actualizarEstrellas(puntos, 3, estrellas)
  }


  private def actualizarEstrellas(puntos: Int, pos: Int, estr: List[Label]): Unit = {

    if (pos > 0) { // Si aún no se han actualizado todas las estrellas
      if (puntos >= 15000 && puntos < 30000) {
        estrellas(0).icon = new ImageIcon(new Imagenes(estrellaLlena).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))

      } else if (puntos >= 30000 && puntos < 45000) {
        estrellas(1).icon = new ImageIcon(new Imagenes(estrellaLlena).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))
      } else if (puntos >= 45000 && puntos < 60000) {
        estrellas(2).icon = new ImageIcon(new Imagenes(estrellaLlena).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))
      } else if (puntos >= 100000) {
        estrellas(0).icon = new ImageIcon(new Imagenes(estrellaAzul).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))
        estrellas(1).icon = new ImageIcon(new Imagenes(estrellaAzul).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))
        estrellas(2).icon = new ImageIcon(new Imagenes(estrellaAzul).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))
      }
      estr.head.visible = true // Hacer visible la primera estrella
      actualizarEstrellas(puntos, pos - 1, estr.tail) // Actualizar las siguientes estrellas
    }
  }

}


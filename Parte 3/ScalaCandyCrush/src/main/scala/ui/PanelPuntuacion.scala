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
    puntos += puntosNuevos
    textoPuntuacion.text = s"Puntuación: $puntos"
    actualizarEstrellas(puntos)
  }

  opaque = false

  private val estrellaVacia: String = "src/img/EstrellaVacia.png"
  private val estrellaLlena: String = "src/img/EstrellaLlena.png"
  private val estrellaAzul: String = "src/img/EstrellaAzul.png"
  private val estrellas: List[Label] = setEstrellas(3)

  private def setEstrellas(n: Int): List[Label] = {
    if (n != 0) {
      val estrella = new Label {
        val imagenEstrella = new Imagenes(estrellaVacia)
        icon = new ImageIcon(imagenEstrella.getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))
      }
      contents += estrella
      estrella :: setEstrellas(n - 1)
    } else {
      Nil
    }
  }


  def actualizarEstrellas(puntos: Int): Unit = {
    actualizarEstrellas(puntos, 3, estrellas)
  }


  private def actualizarEstrellas(puntos: Int, pos: Int, estr: List[Label]): Unit = {

    if (pos > 0) {
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
      estr.head.visible = true
      actualizarEstrellas(puntos, pos - 1, estr.tail)
    }
  }

}


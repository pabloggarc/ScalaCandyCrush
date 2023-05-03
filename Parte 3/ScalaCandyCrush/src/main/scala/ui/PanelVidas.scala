package ui

import java.awt.Image
import javax.swing.ImageIcon
import scala.swing._


class PanelVidas extends FlowPanel {
  private val textoVidas: Label = new Label("Vidas: "){
    font = new Font("Arial", java.awt.Font.PLAIN, 30)
  }
  opaque = false // Hacer transparente el panel
  contents += textoVidas // Agregar el texto de vidas

  private val rutaCorazonLleno: String = "src/img/VidaLlena.png"
  private val rutaCorazonVacio: String = "src/img/VidaVacia.png"
  private val corazones: List[Label] = setCorazones(5)

  private def setCorazones(n: Int): List[Label]= {
    if (n != 0) {
      val corazon = new Label {
        val imagenCaramelo = new Imagenes(rutaCorazonLleno)
        icon = new ImageIcon(imagenCaramelo.getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))
      }
      contents += corazon
      corazon :: setCorazones(n-1)
    } else {
      Nil
    }
  }

  def actualizarVidas(vidas: Int): Unit = actualizarVidas(vidas, 5, corazones)

  private def actualizarVidas(vidas: Int, pos: Int, cor: List[Label]): Unit = {
    if (pos != 0) {
      if (vidas > 0) {
        cor.head.icon = new ImageIcon(new Imagenes(rutaCorazonLleno).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)) // Icono del corazón lleno
      } else {
        cor.head.icon = new ImageIcon(new Imagenes(rutaCorazonVacio).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)) // Icono del corazón vacío
        if (cor.tail.exists(_.icon == new ImageIcon(new Imagenes(rutaCorazonLleno).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)))) { // Si hay algún corazón lleno
          cor.tail.reverse.find(_.icon == new ImageIcon(new Imagenes(rutaCorazonLleno).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))).get.icon = new ImageIcon(new Imagenes(rutaCorazonVacio).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)) // Cambiar el último corazón lleno por un corazón vacío
        }
      }
      cor.head.visible = true
      actualizarVidas(vidas - 1, pos - 1, cor.tail)
    }
  }


}

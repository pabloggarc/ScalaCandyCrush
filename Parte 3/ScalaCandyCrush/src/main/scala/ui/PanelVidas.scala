package ui

import java.awt.Image
import javax.swing.ImageIcon
import scala.swing._


class PanelVidas extends FlowPanel {
  private val textoVidas: Label = new Label("Vidas: "){ // Texto de vidas
    font = new Font("Arial", java.awt.Font.PLAIN, 30) // Fuente
  }
  opaque = false // Hacer transparente el panel
  contents += textoVidas // Agregar el texto de vidas

  private val rutaCorazonLleno: String = "src/img/VidaLlena.png" // Ruta del corazón
  private val rutaCorazonVacio: String = "src/img/VidaVacia.png" // Ruta del corazón
  private val corazones: List[Label] = setCorazones(5) // Lista de corazones

  private def setCorazones(n: Int): List[Label]= { // Método que crea los corazones
    if (n != 0) { // Si aún no se han creado todos los corazones
      val corazon = new Label { // Corazón
        val imagenCaramelo = new Imagenes(rutaCorazonLleno) // Imagen del corazón
        icon = new ImageIcon(imagenCaramelo.getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)) // Icono del corazón
      }
      contents += corazon // Agregar el corazón
      corazon :: setCorazones(n-1) // Agregar el corazón a la lista
    } else {
      Nil  // Devolver Nil
    }
  }

  def actualizarVidas(vidas: Int): Unit = actualizarVidas(vidas, 5, corazones)

  private def actualizarVidas(vidas: Int, pos: Int, cor: List[Label]): Unit = {
    if (pos != 0) { // Si aún no se han actualizado todos los corazones
      if (vidas > 0) {
        cor.head.icon = new ImageIcon(new Imagenes(rutaCorazonLleno).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)) // Icono del corazón lleno
      } else {
        cor.head.icon = new ImageIcon(new Imagenes(rutaCorazonVacio).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)) // Icono del corazón vacío
        if (cor.tail.exists(_.icon == new ImageIcon(new Imagenes(rutaCorazonLleno).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)))) { // Si hay algún corazón lleno
          cor.tail.reverse.find(_.icon == new ImageIcon(new Imagenes(rutaCorazonLleno).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE))).get.icon = new ImageIcon(new Imagenes(rutaCorazonVacio).getImage.getScaledInstance(25, 25, Image.SCALE_REPLICATE)) // Cambiar el último corazón lleno por un corazón vacío
        }
      }
      cor.head.visible = true // Hacer visible el corazón
      actualizarVidas(vidas - 1, pos - 1, cor.tail) // Actualizar el siguiente corazón
    }
  }


}

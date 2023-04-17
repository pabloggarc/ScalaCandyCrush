package ui
import scala.swing._
import interfaz.Juego

import java.awt.Image
import javax.swing.ImageIcon


/**
 * Clase que representa un caramelo
 */
class CaramelosLabel(fila: Int, col: Int, juego: Juego, modo: Int) extends Label {
  if(modo == 1){
    listenTo(this.mouse.clicks) // Escuchar los clicks del mouse

    reactions += { // Acción al hacer click en el caramelo
      case event: event.MouseClicked => juego.buclePrincipal(fila, col) // Tratar los caramelos
    }
  }

  /**
   * Método que actualiza el caramelo
   * @param caramelo Caramelo a actualizar
   * @param anchura Anchura del caramelo
   * @param altura Altura del caramelo
   */
  def actualizarCaramelo(caramelo: Char, anchura: Int, altura: Int): Unit = {
    val imagenCaramelo = new Imagenes("src/imagenesCandy/Caramelo" + caramelo + ".png") // Obtener la imagen del caramelo
    icon = new ImageIcon(imagenCaramelo.getImage.getScaledInstance(anchura, altura, Image.SCALE_REPLICATE)) // Cambiar el icono del caramelo

    if (caramelo == '8'){ //para TNT
      val imagenCaramelo2 = new Imagenes("src/imagenesCandy/TNT.png")
      icon = new ImageIcon(imagenCaramelo2.getImage.getScaledInstance(anchura, altura, Image.SCALE_REPLICATE)) // Cambiar el icono del caramelo

    } else if (caramelo == '7') { //para bomba
      val imagenCaramelo3 = new Imagenes("src/imagenesCandy/Bomba.png")
      icon = new ImageIcon(imagenCaramelo3.getImage.getScaledInstance(anchura, altura, Image.SCALE_REPLICATE)) // Cambiar el icono del caramelo

    }else if (caramelo == '9') { //para rompecabezas
      val imagenCaramelo4 = new Imagenes("src/imagenesCandy/Rompecabezas.png")
      icon = new ImageIcon(imagenCaramelo4.getImage.getScaledInstance(anchura, altura, Image.SCALE_REPLICATE)) // Cambiar el icono del caramelo

    }
  }
}


package ui

import scala.swing._
import interfaz.Juego
import java.awt.Image
import javax.swing.ImageIcon


class CaramelosLabel(fila: Int, col: Int, juego: Juego, modo: Int) extends Label {
  if(modo == 1){
    listenTo(this.mouse.clicks)
    reactions += {
      case event: event.MouseClicked => {
        juego.buclePrincipal(fila, col)
      }
    }
  }


  def actualizarCaramelo(caramelo: Char, anchura: Int, altura: Int): Unit = {
    val imagenCaramelo = new Imagenes("src/img/Caramelo" + caramelo + ".png")  //para un caramelo normal
    icon = new ImageIcon(imagenCaramelo.getImage.getScaledInstance(anchura, altura, Image.SCALE_REPLICATE))

    if (caramelo == '8'){ //para TNT
      val imagenCaramelo2 = new Imagenes("src/img/TNT.png")
      icon = new ImageIcon(imagenCaramelo2.getImage.getScaledInstance(anchura, altura, Image.SCALE_REPLICATE))

    } else if (caramelo == '7') { //para bomba
      val imagenCaramelo3 = new Imagenes("src/img/Bomba.png")
      icon = new ImageIcon(imagenCaramelo3.getImage.getScaledInstance(anchura, altura, Image.SCALE_REPLICATE))

    }else if (caramelo == '9') { //para rompecabezas
      val imagenCaramelo4 = new Imagenes("src/img/Rompecabezas.png")
      icon = new ImageIcon(imagenCaramelo4.getImage.getScaledInstance(anchura, altura, Image.SCALE_REPLICATE))
    }
  }
}


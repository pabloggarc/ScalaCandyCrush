package ui

import scala.swing._
import scala.swing.event._
import java.io.File
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JFrame, UIManager}
import java.awt.Font




class VentanaPrincipal extends MainFrame {


  title = "Scala Candy Crush" //Titulo de la ventana
  val imagen = ImageIO.read(new File("src/img/Portada2.png"))
  resizable = false //tamano fijo de la ventana
  preferredSize = new Dimension((imagen.getWidth() * 0.7).toInt, (imagen.getHeight() * 0.7).toInt) //tamano de la ventana

  val jugar = new Button {  //Boton para jugar
    text = "JUGAR"
    font = new Font("Dialog", Font.PLAIN, 16) // Tamaño de fuente de 16

  }

  jugar.reactions += {
    case ButtonClicked(_) =>
      val parametros = new ObtencionParametros //Ventana para obtener los parametros
      VentanaPrincipal.this.close()
      parametros.centerOnScreen()
      parametros.visible = true

  }

/*
  for (info <- javax.swing.UIManager.getInstalledLookAndFeels) {
    if ("Windows" == info.getName) {
      javax.swing.UIManager.setLookAndFeel(info.getClassName)
    }
  }*/

  def ponerFormatoWindows(lista: List[UIManager.LookAndFeelInfo]): Unit = {
    if (tieneElementos(lista)) {
      val info = lista.head
      if ("Windows" == info.getName) {
        UIManager.setLookAndFeel(info.getClassName)
      } else {
        ponerFormatoWindows(lista.tail)
      }
    }
  }

  def tieneElementos[A](lst: List[A]): Boolean = lst match {
    case _ :: _ => true // si la lista tiene al menos un elemento
    case Nil => false // si la lista está vacía
  }

  val listaCambio = UIManager.getInstalledLookAndFeels.toList
  ponerFormatoWindows(listaCambio)


  val icono = new ImageIcon("src/img/icono.png")
  iconImage = icono.getImage

  contents = new PanelVentanaPrincipal(jugar) //Panel principal de la ventana (para poner el fondo y el boton en transparencia)
  centerOnScreen() //Centrar la ventana en la pantalla
}

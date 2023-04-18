package ui

import scala.swing._
import scala.swing.event._
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame

class VentanaPrincipal extends MainFrame {
  title = "Scala Candy Crush" //Titulo de la ventana
  val imagen = ImageIO.read(new File("src/img/Portada2.png"))
  resizable = false //tamano fijo de la ventana
  preferredSize = new Dimension((imagen.getWidth() * 0.7).toInt, (imagen.getHeight() * 0.7).toInt) //tamano de la ventana

  val jugar = new Button {  //Boton para jugar
    text = "JUGAR"
  }

  jugar.reactions += {
    case ButtonClicked(_) =>
      val parametros = new ObtencionParametros //Ventana para obtener los parametros
      VentanaPrincipal.this.close()
      parametros.centerOnScreen()
      parametros.visible = true

  }

  for (info <- javax.swing.UIManager.getInstalledLookAndFeels) {
    if ("Windows" == info.getName) {
      javax.swing.UIManager.setLookAndFeel(info.getClassName)
    }
  }

  import javax.swing.ImageIcon

  val icono = new ImageIcon("src/img/icono.png")
  iconImage = icono.getImage

  contents = new PanelVentanaPrincipal(jugar) //Panel principal de la ventana (para poner el fondo y el boton en transparencia)
  centerOnScreen() //Centrar la ventana en la pantalla
}

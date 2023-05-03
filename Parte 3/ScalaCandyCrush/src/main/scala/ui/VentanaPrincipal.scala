package ui
import scala.swing._
import scala.swing.event._
import java.io.File
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JFrame, UIManager}
import java.awt.Font
import javax.sound.sampled.{AudioSystem, Clip, DataLine, FloatControl}

class VentanaPrincipal extends MainFrame {


  title = "Scala Candy Crush"
  val imagen = ImageIO.read(new File("src/img/Portada2.png"))
  resizable = false
  preferredSize = new Dimension((imagen.getWidth() * 0.55).toInt, (imagen.getHeight() * 0.55).toInt)

  val jugar = new Button {
    text = "JUGAR"
    font = new Font("Dialog", Font.PLAIN, 16)

  }

  jugar.reactions += {
    case ButtonClicked(_) =>
      val parametros = new ObtencionParametros
      VentanaPrincipal.this.close()
      parametros.centerOnScreen()
      parametros.visible = true
      playMusica()
  }

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
    case _ :: _ => true
    case Nil => false
  }

  def playMusica(): Unit = {
    val file = new File("src/audio/candy.wav")
    val audioInputStream = AudioSystem.getAudioInputStream(file)
    val clip = AudioSystem.getClip()
    clip.open(audioInputStream)
    clip.loop(Clip.LOOP_CONTINUOUSLY)
    clip.start()
  }

  val listaCambio = UIManager.getInstalledLookAndFeels.toList
  ponerFormatoWindows(listaCambio)


  val icono = new ImageIcon("src/img/icono.png")
  iconImage = icono.getImage

  contents = new PanelVentanaPrincipal(jugar)
  centerOnScreen()
}

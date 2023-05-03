package ui
import java.io.File
import javax.imageio.ImageIO
import java.awt.Font
import javax.swing.border.EmptyBorder
import scala.swing._

class PanelParametros (numFilas: TextField,
                       numCol : TextField,
                       nivelDificultadComboBox: Component,
                       modoJuegoComboBox: Component,
                       parametrosButton: Button) extends BoxPanel (Orientation.Vertical){
  contents += new GridPanel(1, 1) {
    contents += new Label("Parámetros del tablero") {
      font = new Font("Dialog", Font.BOLD, 16)
      xAlignment = scala.swing.Alignment.Center
    }
    opaque = false
  }
  contents += Swing.VStrut(50)
  contents += new GridPanel(1, 2) {
    contents += new Label("Número de filas:") {
      font = new Font("Dialog", Font.BOLD, 16)
    }
    contents += numFilas
    opaque = false
  }

  contents += Swing.VStrut(20)

  contents += new GridPanel(1, 2) {
    contents += new Label("Número de columnas:") {
      font = new Font("Dialog", Font.BOLD, 16)
    }
    contents += numCol
    opaque = false
  }

  contents += Swing.VStrut(20)

  contents += new GridPanel(1, 2) {
    contents += new Label("Nivel de dificultad:") {
      font = new Font("Dialog", Font.BOLD, 16)
    }
    contents += nivelDificultadComboBox
    opaque = false
  }

  contents += Swing.VStrut(20)

  contents += new GridPanel(1, 2) {
    contents += new Label("Modo de juego:") {
      font = new Font("Dialog", Font.BOLD, 16)
    }
    contents += modoJuegoComboBox
    opaque = false
  }
  contents += Swing.VStrut(50)


  contents += new FlowPanel(FlowPanel.Alignment.Center)(parametrosButton) {
    opaque = false
  }

  border = Swing.EmptyBorder(20, 20, 20, 20)
  opaque = false

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)

    val imagen = ImageIO.read(new File("src/img/fondo2.jpg"))
    g.drawImage(imagen, 0, 0, size.width, size.height, null)
  }



}

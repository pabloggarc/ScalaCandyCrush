package ui
import java.io.File
import javax.imageio.ImageIO
import scala.swing._

class PanelParametros (numFilas: TextField,
                       numCol : TextField,
                       nivelDificultadComboBox: Component,
                       modoJuegoComboBox: Component,
                       parametrosButton: Button) extends BoxPanel (Orientation.Vertical){ //panel de parametros para poner fondo
  contents += Swing.VStrut(100) // Espacio vertical

  contents += new Label("Parametros del tablero") // Etiqueta para los parámetros del tablero
  contents += Swing.VStrut(110) // Espacio vertical
  contents += new GridPanel(1, 2) { // Panel con dos columnas
    contents += new Label("Número de filas:") // Etiqueta para el número de filas
    contents += numFilas // Campo de texto para el número de filas
    opaque = false // Hacer transparente el panel
  }

  contents += Swing.VStrut(20) // Espacio vertical

  contents += new GridPanel(1, 2) { // Panel con dos columnas
    contents += new Label("Número de columnas:") // Etiqueta para el número de columnas
    contents += numCol // Campo de texto para el número de columnas
    opaque = false // Hacer transparente el panel
  }

  contents += Swing.VStrut(20) // Espacio vertical

  contents += new GridPanel(1, 2) { // Panel con dos columnas
    contents += new Label("Nivel de dificultad:") // Etiqueta para el nivel de dificultad
    contents += nivelDificultadComboBox // ComboBox para el nivel de dificultad
    opaque = false // Hacer transparente el panel
  }

  contents += Swing.VStrut(20) // Espacio vertical

  contents += new GridPanel(1, 2) { // Panel con dos columnas
    contents += new Label("Modo de juego:") // Etiqueta para el modo de juego
    contents += modoJuegoComboBox // ComboBox para el modo de juego
    opaque = false // Hacer transparente el panel
  }
  contents += Swing.VStrut(220) // Espacio vertical

  // Panel con los botones
  contents += new FlowPanel(FlowPanel.Alignment.Center)(parametrosButton) {
    opaque = false // Hacer transparente el panel
  }

  border = Swing.EmptyBorder(20, 20, 20, 20) // Espacio entre los bordes y los componentes
  opaque = false // Hacer transparente el panel

  override def paintComponent(g: Graphics2D): Unit = { // Método para pintar el panel
    super.paintComponent(g)

    val imagen = ImageIO.read(new File("src/imagenesCandy/FondoTablero.png")) // Obtener la imagen de fondo
    g.drawImage(imagen, 0, 0, size.width, size.height, null) // Pintar la imagen de fondo
  }

}

package ui
import java.io.File
import javax.imageio.ImageIO
import java.awt.Font
import scala.swing._

class PanelJuego(titulo: Label,
                          panelTablero: PanelTablero,
                          vidas: PanelVidas,
                          botonAutomatico: Button,
                          modoJuego: Int) extends BorderPanel{
  private val botonSalir = new Button("SALIR") {
    reactions += {
      case _ => sys.exit(0)
    }
  }

  titulo.font = new Font("Calibri", java.awt.Font.BOLD, 20) // Fuente del título

  layout(titulo) = BorderPanel.Position.North // Posición del título
  layout(panelTablero) = BorderPanel.Position.Center // Posición del panel del tablero

  layout(new GridPanel(1,2) { // Panel de dos columnas
    contents += vidas // Agregar el panel de las vidas
    if(modoJuego == 0){
      contents += botonAutomatico // Agregar el botón automático
    }
    contents += botonSalir //Agregar el botón de salir
    opaque = false //Transparente
  }
  ) = BorderPanel.Position.South // Posición del panel de las vidas

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)

    val imagen = ImageIO.read(new File("src/img/FondoTablero.png")) // Obtener la imagen de fondo
    g.drawImage(imagen, 0, 0, size.width, size.height, null) // Pintar la imagen de fondo
  }
}

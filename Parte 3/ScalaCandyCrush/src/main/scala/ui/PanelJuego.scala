package ui
import java.io.File
import javax.imageio.ImageIO
import java.awt.Font
import scala.swing._

class PanelJuego(panelTablero: PanelTablero,
                          vidas: PanelVidas,
                          puntuaciones: PanelPuntuacion,
                          modoJuego: Int) extends BorderPanel{

  val puntos = new Label("Puntuación: ") {font = new Font("Arial", Font.PLAIN,20)}
  layout(panelTablero) = BorderPanel.Position.Center
  layout(new GridPanel(1, 2) {
    contents += vidas
    contents += puntuaciones
    opaque = false
  }
  ) = BorderPanel.Position.South

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)

    val imagen = ImageIO.read(new File("src/img/fondo3.jpg"))
    g.drawImage(imagen, 0, 0, size.width, size.height, null)
  }
}

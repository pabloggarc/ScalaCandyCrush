package ui
import java.io.File
import javax.imageio.ImageIO
import scala.swing._

class PanelVentanaPrincipal(botonJugar: Button) extends BorderPanel{

  layout(Swing.VStrut(100)) = BorderPanel.Position.South
  layout(new FlowPanel(FlowPanel.Alignment.Center)(botonJugar) {
    opaque = false
  }) = BorderPanel.Position.South
  border = Swing.EmptyBorder(20, 20, 20, 20)

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)
    val imagen = ImageIO.read(new File("src/img/Portada2.png"))
    g.drawImage(imagen, 0, 0, size.width, size.height, null)
  }
}

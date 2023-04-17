package ui
import java.io.File
import javax.imageio.ImageIO
import scala.swing._

class PanelVentanaPrincipal(botonJugar: Button) extends BorderPanel{ // Panel principal de la ventana (para poner el fondo y el boton en transparencia)
  layout(Swing.VStrut(100)) = BorderPanel.Position.South // Espacio entre el panel y el boton

  layout(new FlowPanel(FlowPanel.Alignment.Center)(botonJugar) { // Panel con el boton
    opaque = false // Hacer transparente el panel
  }) = BorderPanel.Position.South // Posición del panel con los botones
  border = Swing.EmptyBorder(20, 20, 20, 20) // Espacio alrededor del panel

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)

    val imagen = ImageIO.read(new File("src/imagenesCandy/portada.png")) // Obtener la imagen de fondo
    g.drawImage(imagen, 0, 0, size.width, size.height, null) // Pintar la imagen de fondo

  }
}

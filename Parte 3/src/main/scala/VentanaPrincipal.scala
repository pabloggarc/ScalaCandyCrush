import java.awt.Image
import javax.swing.ImageIcon
import scala.swing._
import scala.swing.event._


class VentanaPrincipal extends MainFrame {

  val button = new Button {
    text = "JUGAR"
  }

  button.reactions += {
    case ButtonClicked(_) =>
      val secondaryFrame = new ObtencionParametros
      secondaryFrame.visible = true
      VentanaPrincipal.super.visible = false
  }

  val imagenPortada = new ImageIcon("src/imagenesCandy/portada.png")
  val fondo = new Label {
    icon = new ImageIcon(imagenPortada.getImage.getScaledInstance(600, 600, Image.SCALE_REPLICATE))
  }


  contents = new BorderPanel {
    layout(new FlowPanel(FlowPanel.Alignment.Center)(Swing.HStrut((size.width / 4) * 3), button)) = BorderPanel.Position.South
    layout(new FlowPanel(FlowPanel.Alignment.Center)(fondo)) = BorderPanel.Position.Center
    border = Swing.EmptyBorder(20, 20, 20, 20)
  }

  title = "Scala Candy Crush"
  size = new Dimension(600, 600)
  centerOnScreen()
}

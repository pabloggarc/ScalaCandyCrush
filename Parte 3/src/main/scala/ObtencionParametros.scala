import scala.swing._
import scala.swing.event._


class ObtencionParametros extends MainFrame {

  val filasField = new TextField(3)
  val columnasField = new TextField(3)
  val dificultadCombo = new ComboBox(List(1, 2))
  val modoCombo = new ComboBox(List("-m", "-a"))

  contents = new BorderPanel {
    layout(new BoxPanel(Orientation.Vertical) {
      contents += new Label("Filas:")
      contents += filasField
      contents += new Label("Columnas:")
      contents += columnasField
      contents += new Label("Dificultad:")
      contents += dificultadCombo
      contents += new Label("Modo:")
      contents += modoCombo
    }) = BorderPanel.Position.Center
    layout(new FlowPanel(FlowPanel.Alignment.Center)(new Button("A por esos caramelos") {
      reactions += {
        case ButtonClicked(_) =>
          val filas = filasField.text.toInt
          val columnas = columnasField.text.toInt
          val dificultad = dificultadCombo.selection.item
          val modo = modoCombo.selection.item
          val juegoFrame = new Juego(filas, columnas, dificultad, modo)
          juegoFrame.visible = true
          ObtencionParametros.this.dispose()
      }
    })) = BorderPanel.Position.South
    border = Swing.EmptyBorder(20, 20, 20, 20)
  }

  title = "Obtención de Parámetros"
  size = new Dimension(400, 300)
  centerOnScreen()

}

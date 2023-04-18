import ui.VentanaPrincipal

object GuiProgramThree {
  def main(args: Array[String]) {
    val principal = new VentanaPrincipal // Ventana principal
    principal.centerOnScreen()
    principal.visible = true
  }
}

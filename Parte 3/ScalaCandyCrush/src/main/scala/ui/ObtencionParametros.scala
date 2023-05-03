package ui
import interfaz.Juego

import scala.swing._
import scala.swing.event._
import java.awt.Font
import javax.swing.ImageIcon


class ObtencionParametros extends MainFrame {
  title = "Configuración"
  preferredSize = new Dimension(500, 500)
  resizable = false

  val filasField = new TextField(3)
  val columnasField = new TextField(3)
  val dificultadCombo = new ComboBox(List("Fácil", "Difícil"))
  val modoCombo = new ComboBox(List("Manual", "Automático"))

    val parametros = new Button("¡A por esos caramelos!") {
      font = new Font("Dialog", Font.PLAIN, 16)
      reactions += {
        case ButtonClicked(_) =>
          try {
            val filas = filasField.text.toInt
            val columnas = columnasField.text.toInt
            val dificultad = dificultadCombo.selection.item match {
              case "Fácil" => 1
              case _ => 2
            }
            val modo = modoCombo.selection.item match {
              case "Manual" => 1
              case _ => 2
            }
            val juegoFrame = new Juego(filas, columnas, dificultad, modo)
            ObtencionParametros.this.dispose()
            juegoFrame.iniciar()
          } catch {
            case _: Throwable =>
              Dialog.showMessage(null, "Ingresa datos validos", "Error", Dialog.Message.Error)
          }
      }
    }

  val icono = new ImageIcon("src/img/icono.png")
  iconImage = icono.getImage

  contents = new PanelParametros(filasField, columnasField, dificultadCombo, modoCombo, parametros)

  centerOnScreen()

}

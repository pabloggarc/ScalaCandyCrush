package ui
import interfaz.Juego

import scala.swing._
import scala.swing.event._
import java.awt.Font
import javax.swing.ImageIcon


class ObtencionParametros extends MainFrame {
  title = "Configuración" // Título de la ventana
  preferredSize = new Dimension(500, 500)   // Tamaño de la ventana
  resizable = false // Tamaño fijo de la ventana

  val filasField = new TextField(3) // Campo para ingresar las filas
  val columnasField = new TextField(3) // Campo para ingresar las columnas
  val dificultadCombo = new ComboBox(List(1, 2)) // Combo para seleccionar la dificultad
  val modoCombo = new ComboBox(List("-m", "-a")) // Combo para seleccionar el modo de juego

    val parametros = new Button("¡A por esos caramelos!") { // Botón para iniciar el juego
      font = new Font("Dialog", Font.PLAIN, 16) // Tamaño de fuente de 16
      reactions += {
        case ButtonClicked(_) =>
          val filas = filasField.text.toInt // Obtener las filas
          val columnas = columnasField.text.toInt // Obtener las columnas
          val dificultad = dificultadCombo.selection.item match { // Obtener el nivel de dificultad
            case 1 => 1
            case _ => 2
          }
          val modo = modoCombo.selection.item match { // Obtener el modo de juego
            case "-m" => 1
            case _ => 0
          }
          val juegoFrame = new Juego(filas, columnas, dificultad, modo) // Crear el juego

          ObtencionParametros.this.dispose() // Cerrar la ventana de obtención de parámetros
          juegoFrame.iniciar() // Iniciar el juego

      }
    }

  val icono = new ImageIcon("src/img/icono.png")
  iconImage = icono.getImage

  // Agregar los componentes a la ventana
  contents = new PanelParametros(filasField, columnasField, dificultadCombo, modoCombo, parametros) // Panel principal de la ventana (transparencia)

  centerOnScreen() // Centrar la ventana en la pantalla

}

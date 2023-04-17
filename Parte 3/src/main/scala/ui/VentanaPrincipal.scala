package ui

import java.awt.Image
import javax.swing.ImageIcon
import scala.swing._
import scala.swing.event._
import java.awt.{Color, Component, Font, Graphics, Image, Insets}
import javax.swing.border.AbstractBorder
import scala.swing._

class VentanaPrincipal extends MainFrame {
  title = "Scala Candy Crush" //Titulo de la ventana
  resizable = false //tamano fijo de la ventana
  preferredSize = new Dimension(600,600) //tamano de la ventana

  val jugar = new Button {  //Boton para jugar
    text = "JUGAR"
  }

  jugar.reactions += {
    case ButtonClicked(_) =>
      val parametros = new ObtencionParametros //Ventana para obtener los parametros
      VentanaPrincipal.this.close()
      parametros.centerOnScreen()
      parametros.visible = true

  }


  contents = new PanelVentanaPrincipal(jugar) //Panel principal de la ventana (para poner el fondo y el boton en transparencia)

  centerOnScreen() //Centrar la ventana en la pantalla
}

package ui

import javax.swing.ImageIcon

class Imagenes(ruta: String) extends ImageIcon(ruta) {
  override def getIconWidth(): Int = getImage.getWidth(null)
  override def getIconHeight(): Int = getImage.getHeight(null)
}

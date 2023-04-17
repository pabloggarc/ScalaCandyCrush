package ui

import javax.swing.ImageIcon

class Imagenes(ruta: String) extends ImageIcon(ruta) { //Clase para obtener las imagenes
  override def getIconWidth(): Int = getImage.getWidth(null) //Obtener el ancho de la imagen
  override def getIconHeight(): Int = getImage.getHeight(null) //Obtener el alto de la imagen
}

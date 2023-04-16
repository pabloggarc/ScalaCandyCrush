import java.awt.Color
import scala.swing._
import scala.util.Random

class Juego(filas: Int, columnas: Int, dificultad: Int, modo: String) extends MainFrame {

  val modoJuego : Int = argsModo()
  val dificultadJuego : Int = argsDificultad()
  private val operaciones = new Operaciones(filas, columnas, selectNumColores())


  val panelTablero = new Panel {

    def crearTablero(filas: Int, columnas: Int, dificultad: Int, modo: String): Array[Array[Int]] = {
      if (dificultad == 1) {
        Array.fill(filas, columnas)(scala.util.Random.nextInt(4) + 1)
      }
      else {
        Array.fill(filas, columnas)(scala.util.Random.nextInt(6) + 1)
      }
    }

    val tablero = crearTablero(filas, columnas, dificultadJuego, modo)

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)
      val casillaSize = Math.min(size.width / (columnas + 2), size.height / (filas + 2))
      dibujarTablero(g, 0, 0, casillaSize)
      dibujarNumerosColumna(g, 0, casillaSize, casillaSize)
      dibujarNumerosFila(g, 0, casillaSize, casillaSize)
    }

    @scala.annotation.tailrec
    private def dibujarTablero(g: Graphics2D, i: Int, j: Int, casillaSize: Double): Unit = {
      if (i < filas) {
        if (j < columnas) {
          g.setColor(tablero(i)(j) match {
            case 1 => Color.RED
            case 2 => Color.BLUE
            case 3 => Color.GREEN
            case 4 => Color.YELLOW
            case 5 => Color.PINK
            case 6 => Color.ORANGE
          })
          g.fillRect((j + 1) * casillaSize.toInt, (i + 1) * casillaSize.toInt, casillaSize.toInt, casillaSize.toInt)
          g.setColor(Color.BLACK)
          g.drawRect((j + 1) * casillaSize.toInt, (i + 1) * casillaSize.toInt, casillaSize.toInt, casillaSize.toInt)
          dibujarTablero(g, i, j + 1, casillaSize)
        } else {
          dibujarTablero(g, i + 1, 0, casillaSize)
        }
      }
    }

    @scala.annotation.tailrec
    private def dibujarNumerosColumna(g: Graphics2D, j: Int, casillaSize: Double, offset: Double): Unit = {
      if (j < columnas) {
        g.drawString(j.toString, ((j + 1) * casillaSize + casillaSize / 2).toFloat, (casillaSize / 2).toFloat)
        dibujarNumerosColumna(g, j + 1, casillaSize, offset)
      }
    }

    @scala.annotation.tailrec
    private def dibujarNumerosFila(g: Graphics2D, i: Int, casillaSize: Double, offset: Double): Unit = {
      if (i < filas) {
        g.drawString(i.toString, (0.5 * casillaSize).toFloat, ((i + 1) * casillaSize + casillaSize / 2).toFloat)
        dibujarNumerosFila(g, i + 1, casillaSize, offset)
      }
    }


    /*
        //buclePrincipal(tablero, 5)


        def buclePrincipal(tablero: List[Char], vidasJuego: Int): Unit = {

          println(operaciones.mostrarTablero(tablero, vidasJuego, columnas))

          vidasJuego match {
            case 0 =>
              println("Fin del juego, te has quedado sin vidas!")
            case _ =>

              val filaUsuario = Option(filaField).map(_.text.toInt).getOrElse(0)
              val colUsuario = Option(columnaField).map(_.text.toInt).getOrElse(0)

              val caramelo_seleccionado = operaciones.obtenerCaramelo(tablero, filaUsuario, colUsuario)

              if (caramelo_seleccionado.isDigit && caramelo_seleccionado.asDigit >= 1 & caramelo_seleccionado.asDigit <= 6) {
                println("voy a buscar un camino ")
                val caminoEncontrado = operaciones.buscarCamino(tablero, filaUsuario * columnas + colUsuario)


                val tableroConX = operaciones.marcar(tablero, caminoEncontrado, 0)
                val borrados: Int = operaciones.contarX(tableroConX)
                println("Se han borrado " + borrados + " caramelos")


                println(operaciones.mostrarTablero(tableroConX, vidasJuego, columnas))

                if (borrados == 0) {
                  buclePrincipal(tableroConX, vidasJuego - 1) // Si no se han borrado caramelos se pierde una vida

                } else if (borrados >= 1 && borrados < 5) {
                  val nuevoTablero2: List[Char] = operaciones.actualizarTablero(tableroConX, operaciones.cambiosTablero(tableroConX))
                  buclePrincipal(nuevoTablero2, vidasJuego)

                } else {
                  val nuevoTablero = operaciones.reemplazarCaramelo(tableroConX, filaUsuario * columnas + colUsuario, borrados, 0) // Si se han borrado 5 caramelos se convierte en B
                  println(operaciones.mostrarTablero(nuevoTablero, vidasJuego, columnas))

                  val nuevoTablero2: List[Char] = operaciones.actualizarTablero(nuevoTablero, operaciones.cambiosTablero(nuevoTablero))
                  buclePrincipal(nuevoTablero2, vidasJuego)

                }

              }
              else if (caramelo_seleccionado == '7' || caramelo_seleccionado == '8' || caramelo_seleccionado == '9') { //estamos ante un caramelo_seleccionado NO ESPECIAL
                val tablero4 = caramelo_seleccionado match {

                  case '7' => operaciones.bloqueBomba(tablero, filaUsuario * columnas + colUsuario)
                  case '8' => operaciones.bloqueTNT(tablero, filaUsuario * columnas + colUsuario)
                  case '9' => operaciones.bloqueRompecabezas(tablero, filaUsuario * columnas + colUsuario, dificultadJuego)
                }
                println(operaciones.mostrarTablero(tablero4, vidasJuego, columnas))
                val nuevoTablero2: List[Char] = operaciones.actualizarTablero(tablero4, operaciones.cambiosTablero(tablero4))
                buclePrincipal(nuevoTablero2, vidasJuego)
              }
          }

          def pedirUsuarioPos(tipo: String, tablero: List[Char]): Int = {
            modoJuego match {
              case 0 =>
                val posicion = operaciones.mejorCamino(tablero)
                tipo match {
                  case "Fila" =>
                    posicion / filas

                  case "Columna" =>
                    posicion % columnas

                }
            }
          }
        }*/



  }

  val filaField = new TextField(5)
  val columnaField = new TextField(5)
  val panelEntrada = new FlowPanel {
    contents += new Label("Fila:")
    contents += filaField
    contents += new Label("Columna:")
    contents += columnaField
  }

  val panelTableroConCabeceraYEntrada = new BorderPanel {
    layout(panelTablero) = BorderPanel.Position.Center
    layout(panelEntrada) = BorderPanel.Position.South
    preferredSize = new Dimension((columnas + 2) * 50, (filas + 2) * 50)
    border = Swing.EmptyBorder(20, 50, 50, 50)
  }

  val panelJuego = new BoxPanel(Orientation.Vertical) {
    contents += panelTableroConCabeceraYEntrada
    preferredSize = new Dimension((columnas + 1) * 50, (filas + 3) * 50)
  }

  panelTablero.listenTo(panelTablero.mouse.clicks)
  panelTablero.reactions += {
    case event: event.MouseClicked =>
      val mouseX = event.point.x
      val mouseY = event.point.y
      val casillaSize = Math.min(panelTablero.size.width / (columnas + 2), panelTablero.size.height / (filas + 2))
      val fila = ((mouseY - casillaSize) / casillaSize).toInt
      val columna = ((mouseX - casillaSize) / casillaSize).toInt
      filaField.text = fila.toString
      columnaField.text = columna.toString
  }

  private def selectNumColores(): Int = {
    dificultadJuego match {
      case 2 => 6
      case 1 => 4
    }
  }

  private def argsModo(): Int = {
    modo match {
      case "-a" => 0
      case "-m" => 1
      case _ => throw new Error("Introducir un modo correcto: -a o -m")
    }
  }

  private def argsDificultad(): Int = {
    dificultad match {
      case 1 => 1
      case 2 => 2
      case _ => throw new Error("Introducir una dificultad correcta: 1 o 2")
    }
  }


  contents = panelJuego

}

package interfaz
import com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener
import logica.Operaciones
import ui.{PanelJuego, PanelPuntuacion, PanelTablero, PanelVidas}

import java.awt.event.{WindowAdapter, WindowEvent}
import scala.util.Random
import java.io.File
import javax.sound.sampled._
import javax.swing.ImageIcon
import scala.swing._

class Juego(filas: Int, columnas: Int, dificultad: Int, modo: Int) extends MainFrame {

  val numFilas = filas
  val numCol = columnas
  val modoJuego: Int = modo
  val dificultadJuego: Int = dificultad
  private val operaciones = new Operaciones(numFilas, numCol, selectNumColores())

  title = "Candy Crush"
  preferredSize = new Dimension(800, 800)
  resizable = false
  visible = true
  centerOnScreen()

  private def selectNumColores(): Int = {//En función de la dificultad determinamos la cantidad de tipos de caramelos
    dificultadJuego match {
      case 2 => 6
      case 1 => 4
    }
  }

  private val panelTablero = new PanelTablero(numFilas, numCol, this, modoJuego)
  private val vidas = new PanelVidas
  private val puntuaciones = new PanelPuntuacion
  private val titulo = new Label("SCALA CANDY CRUSH")


  contents = new PanelJuego(panelTablero, vidas, puntuaciones, modoJuego)

  private var tablero: List[Char] = Nil
  private var vidasJuego: Int = 5
  private var puntos: Int = 0

  def iniciar(): Unit = {
    tablero = operaciones.rellenarTablero(operaciones.generarTablero(numFilas * numCol, dificultadJuego, operaciones.generarElemento))
    bucleJuego()

    if (modoJuego == 2) {
      // Llamada al hilo del modo automático
      val hiloJuego = new HiloJuego(this)
      hiloJuego.start()
    }  }

  private def recargarComponentes(): Unit = {
    println(operaciones.mostrarTablero(tablero, vidasJuego, numCol))
    panelTablero.actualizarTablero(tablero) //Actualizar el tablero
    vidas.actualizarVidas(vidasJuego) //Actualizar las vidas
    puntuaciones.actualizarPuntos(puntos) //Actualizar los puntos
    pintar() //Repintar la ventana
  }


  def modoAutomatico(): Unit = {
    val posOptimo = operaciones.mejorCamino(tablero) //Obtener la posicion del caramelo optimo

    val filaOpt = posOptimo / numCol //Obtener la fila del caramelo optimo
    val colOpt = posOptimo % numCol //Obtener la columna del caramelo optimo

    Dialog.showMessage(contents.head, "Fila: " + filaOpt + ", columna: " + colOpt, title = "Posición óptima:")

    Thread.sleep(2000)
    buclePrincipal(filaOpt, colOpt) //Ejecutamos el resto de metodos con el caramelo optimo
  }

  private def elegirAudio(borrados: Int): File = {
    borrados match {
      case 0 => new File("src/audio/error.wav")
      case _ => {
        val r = new Random()
        val id = r.nextInt(6)
        new File("src/audio/" + id + ".wav")
      }
    }
  }

  private def playAudio(borrados: Int): Unit = {
    val file = elegirAudio(borrados)
    val audioInputStream = AudioSystem.getAudioInputStream(file)
    val format = audioInputStream.getFormat()
    val dataLineInfo = new DataLine.Info(classOf[Clip], format)
    val clip = AudioSystem.getLine(dataLineInfo).asInstanceOf[Clip]
    clip.open(audioInputStream)
    clip.start()
    audioInputStream.close()
  }

  def pintar(): Unit = {
    this.repaint() //pintamos de nuevo la ventana
  }


   def buclePrincipal( filaUser : Int, colUser : Int): Unit = {

        val caramelo_seleccionado = operaciones.obtenerCaramelo( tablero,filaUser, colUser)

        if (caramelo_seleccionado.isDigit && caramelo_seleccionado.asDigit >= 1 & caramelo_seleccionado.asDigit <= 6) {
          val caminoEncontrado = operaciones.buscarCamino(tablero, filaUser * numCol + colUser)
          tablero = operaciones.marcar(tablero, caminoEncontrado, 0)
          val borrados: Int = operaciones.contarX(tablero)
          playAudio(borrados)
          println("Se ha seleccionado la fila " + filaUser + " y la columna " + colUser)
          println("Se han borrado " + borrados + " caramelos")
          println(operaciones.mostrarTablero(tablero, vidasJuego, numCol))
          puntos += (150 * borrados) - puntos




          if (borrados == 0) {
            println("¡Has perdido una vida " + "❤" * (vidasJuego - 1) + "\uD83D\uDC94!")
            vidasJuego -= 1
            bucleJuego()
          }
          else if (borrados >= 1 && borrados < 5) {
            tablero = operaciones.actualizarTablero(tablero, operaciones.cambiosTablero(tablero))
            bucleJuego()
          }
          else {
            tablero =operaciones.reemplazarCaramelo(tablero, filaUser * numCol + colUser, borrados, 0)
            println(operaciones.mostrarTablero(tablero, vidasJuego, numCol))

            tablero = operaciones.actualizarTablero(tablero, operaciones.cambiosTablero(tablero))
            bucleJuego()
          }
        }
        else if (caramelo_seleccionado == '7' || caramelo_seleccionado == '8' || caramelo_seleccionado == '9') {
          playAudio(1)
          tablero = caramelo_seleccionado match {
            case '7' => operaciones.bloqueBomba(tablero, filaUser * numCol + colUser)
            case '8' => operaciones.bloqueTNT(tablero, filaUser * numCol + colUser)
            case '9' => operaciones.bloqueRompecabezas(tablero, filaUser * numCol + colUser, dificultadJuego)
          }

          println(operaciones.mostrarTablero(tablero, vidasJuego, numCol))
          tablero = operaciones.actualizarTablero(tablero, operaciones.cambiosTablero(tablero))

          bucleJuego()
        }
     if (modoJuego == 2) modoAutomatico() // Llamada a la función del botón automático si estamos en el modo automatico
    }

  private def bucleJuego(): Unit = {
    if (vidasJuego == 0) { //Si nos quedamos sin vidas, se acaba la partida
      sys.exit(0)
    }
    recargarComponentes() //Recargamos los componentes
  }

  val icono = new ImageIcon("src/img/icono.png") //para mostrar el icono de la ventana
  iconImage = icono.getImage

  centerOnScreen()
}


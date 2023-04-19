package interfaz
import logica.Operaciones
import ui.{PanelJuego, PanelTablero, PanelVidas}

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

  title = "Candy Crush" //Titulo de la ventana
  preferredSize = new Dimension(800, 800) //Tamaño de la ventana
  resizable = false
  visible = true //Mostrar la ventana
  centerOnScreen() //Centrar la ventana en la pantalla

  private def selectNumColores(): Int = { //Funcion para seleccionar el numero de colores segun la dificultad
    dificultadJuego match {
      case 2 => 6
      case 1 => 4
    }
  }

  private val panelTablero = new PanelTablero(numFilas, numCol, this, modoJuego)
  private val vidas = new PanelVidas //Panel para las
  private val titulo = new Label("SCALA CANDY CRUSH") //Titulo del juego
  private val botonAutomatico = new Button("PASO SIGUIENTE") { //Boton para el modo automatico
    reactions += { //Reaccionar al pulsar el boton
      case _ => funcionBotonAutomatico() //Llamar a la funcion del boton
    }
  }

  contents = new PanelJuego(panelTablero, vidas, botonAutomatico, modoJuego)
  //--------------------------------------------------ATENCION ESTACION EN CURVA---------------------------------------
  private var tablero: List[Char] = Nil
  private var vidasJuego: Int = 5
  //----------------------------------------AL SALIR TENGA CUIDADO PARA NO INTRODUCIR EL PIE ENTRE COCHE Y ANDEN----------------------------------------------------

  def iniciar(): Unit = {
    tablero = operaciones.rellenarTablero(operaciones.generarTablero(numFilas*numCol,dificultadJuego,operaciones.generarElemento)) //Crea un tablero nuevo con colores aleatorios
    bucleJuego() //Iniciar el juego
  }

  private def recargarComponentes(): Unit = {
    panelTablero.actualizarTablero(tablero) //Actualizar el tablero
    vidas.actualizarVidas(vidasJuego) //Actualizar las vidas
    operaciones.mostrarTablero(tablero, vidasJuego, numCol) //Imprimir el tablero por consola

    this.repaint() //Repintar la ventana
  }

  def funcionBotonAutomatico(): Unit = {
    val posOptimo = operaciones.mejorCamino(tablero) //Obtener la posicion del caramelo optimo

    val filaOpt = posOptimo / numCol //Obtener la fila del caramelo optimo
    val colOpt = posOptimo % numCol //Obtener la columna del caramelo optimo

    //Mostrar la posicion del caramelo optimo
    Dialog.showMessage(contents.head, "Fila: " + filaOpt + ", columna: " + colOpt, title = "Encontrado posición óptima")

    buclePrincipal(filaOpt, colOpt) //Tratar el caramelo optimo
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

   def buclePrincipal( filaUser : Int, colUser : Int): Unit = {

        val caramelo_seleccionado = operaciones.obtenerCaramelo( tablero,filaUser, colUser)

        if (caramelo_seleccionado.isDigit && caramelo_seleccionado.asDigit >= 1 & caramelo_seleccionado.asDigit <= 6) {
          val caminoEncontrado = operaciones.buscarCamino(tablero, filaUser * numCol + colUser)
          //val tableroConX = operaciones.marcar(tablero, caminoEncontrado, 0)
          tablero = operaciones.marcar(tablero, caminoEncontrado, 0)
          val borrados: Int = operaciones.contarX(tablero)
          playAudio(borrados)
          println("Se ha seleccionado la fila " + filaUser + " y la columna " + colUser)
          println("Se han borrado " + borrados + " caramelos")
          println(operaciones.mostrarTablero(tablero, vidasJuego, numCol))

          if (borrados == 0) {
            println("¡Has perdido una vida " + "❤" * (vidasJuego - 1) + "\uD83D\uDC94!")
            vidasJuego -= 1
            //buclePrincipal(tableroConX, vidasJuego - 1) // Si no se han borrado caramelos se pierde una vidaç
            bucleJuego()
          }
          else if (borrados >= 1 && borrados < 5) {
            //val nuevoTablero2: List[Char] = operaciones.actualizarTablero(tableroConX, operaciones.cambiosTablero(tableroConX))
            //buclePrincipal(nuevoTablero2, vidasJuego,filaUser, colUser)
            tablero = operaciones.actualizarTablero(tablero, operaciones.cambiosTablero(tablero))
            bucleJuego()
          }
          else {
            //val nuevoTablero = operaciones.reemplazarCaramelo(tableroConX, filaUser * numCol + colUser, borrados, 0) // Si se han borrado 5 caramelos se convierte en B
            tablero =operaciones.reemplazarCaramelo(tablero, filaUser * numCol + colUser, borrados, 0)
            //println(operaciones.mostrarTablero(nuevoTablero, vidasJuego, numCol))
            println(operaciones.mostrarTablero(tablero, vidasJuego, numCol))

            //val nuevoTablero2: List[Char] = operaciones.actualizarTablero(nuevoTablero, operaciones.cambiosTablero(nuevoTablero))
            //buclePrincipal(nuevoTablero2, vidasJuego, filaUser, colUser)
            tablero = operaciones.actualizarTablero(tablero, operaciones.cambiosTablero(tablero))
            bucleJuego()
          }
        }
        else if (caramelo_seleccionado == '7' || caramelo_seleccionado == '8' || caramelo_seleccionado == '9') { //estamos ante un caramelo_seleccionado NO ESPECIAL
          playAudio(1)
          tablero = caramelo_seleccionado match {
            case '7' => operaciones.bloqueBomba(tablero, filaUser * numCol + colUser)
            case '8' => operaciones.bloqueTNT(tablero, filaUser * numCol + colUser)
            case '9' => operaciones.bloqueRompecabezas(tablero, filaUser * numCol + colUser, dificultadJuego)
          }

          //println(operaciones.mostrarTablero(tablero4, vidasJuego, numCol))
          println(operaciones.mostrarTablero(tablero, vidasJuego, numCol))
          //val nuevoTablero2: List[Char] = operaciones.actualizarTablero(tablero4, operaciones.cambiosTablero(tablero4))
          tablero = operaciones.actualizarTablero(tablero, operaciones.cambiosTablero(tablero))

          //buclePrincipal(nuevoTablero2, vidasJuego,filaUser, colUser)
          bucleJuego()
        }
    }

  private def bucleJuego(): Unit = {
    if (vidasJuego == 0) { //Si se llega a 0 vidas
      sys.exit(0) //Terminamos el juego
    }
    recargarComponentes() //Recargamos los componentes
  }

  val icono = new ImageIcon("src/img/icono.png")
  iconImage = icono.getImage

  centerOnScreen()
}


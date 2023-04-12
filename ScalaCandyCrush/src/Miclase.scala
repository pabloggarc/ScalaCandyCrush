import scala.util.Random

class Miclase (args: Array[String]){
  private val numFilasTab: Int = argsFilas()
  private val numColsTab: Int = argsColumnas()
  private val modoCundy: Int = argsModo()
  private val dificultadJuego: Int = argsDificultad()
  private val operaciones = new Operaciones(numFilasTab, numColsTab, selectNumColores())

  def iniciar(): Unit = {
    imprimirArgumentos()
    val tablero = operaciones.generarTablero(numFilasTab * numColsTab, dificultadJuego, operaciones.generarElemento)
    buclePrincipal(tablero, 5) //le pasamos el tablero con las cinco vidas
  }

  private def buclePrincipal(tablero: List[Char], vidasJuego: Int): Unit = {

    println(operaciones.mostrarTablero(tablero, vidasJuego, numColsTab))

    vidasJuego match {
      case 0 =>
        println("Fin del juego, te has quedado sin vidas!")
      case _ =>
        val filaUsuario = pedirUsuarioPos("Fila")
        val colUsuario = pedirUsuarioPos("Columna")
        val caramelo_seleccionado = operaciones.obtenerCaramelo(tablero, filaUsuario, colUsuario)
        println(caramelo_seleccionado," VALOR este es el caramelo q he seleccionado")

        if (caramelo_seleccionado.isDigit && caramelo_seleccionado.asDigit >= 1 & caramelo_seleccionado.asDigit <= 6) {
          val caminoEncontrado = operaciones.buscarCamino(tablero, filaUsuario*numColsTab+colUsuario, numFilasTab, numColsTab)
          println(caminoEncontrado, "camino ENCONTRADO en MAIN")
          val tableroConX = operaciones.marcar(tablero, caminoEncontrado,0)
          val borrados: Int = operaciones.contarX(tableroConX)
          println("Se han borrado " + borrados + " caramelos")
          println(operaciones.mostrarTablero(tableroConX, vidasJuego, numColsTab))

          if (borrados == 0) {
            buclePrincipal(tableroConX, vidasJuego - 1) // Si no se han borrado caramelos se pierde una vida

          } else if (borrados >= 1 && borrados < 5) {
            val cambios: List[List[Int]] = operaciones.cambiosTablero(tableroConX)
            val nuevoTablero2: List[Char] = operaciones.actualizarTablero(tableroConX, operaciones.cambiosTablero(tableroConX))
            buclePrincipal(nuevoTablero2, vidasJuego)

          } else {
            println("la cantidad de borrados es mayor que 5")
            val nuevoTablero = operaciones.reemplazarCaramelo(tableroConX, filaUsuario*numColsTab+colUsuario, borrados, 0) // Si se han borrado 5 caramelos se convierte en B
            println("tablero tras introducir bloque especial")
            println(operaciones.mostrarTablero(tableroConX, vidasJuego, numColsTab))

            val cambios: List[List[Int]] = operaciones.cambiosTablero(nuevoTablero)
            val nuevoTablero2: List[Char] = operaciones.actualizarTablero(nuevoTablero, operaciones.cambiosTablero(nuevoTablero))
            buclePrincipal(nuevoTablero2, vidasJuego)

          }

        }
        else if(filaUsuario*numColsTab+colUsuario == 'B' || filaUsuario*numColsTab+colUsuario == 'T' || filaUsuario*numColsTab+colUsuario == 'R'){ //estamos ante un caramelo_seleccionado NO ESPECIAL
          filaUsuario*numColsTab+colUsuario match {
            case 'B' => operaciones.bloqueBomba(tablero, filaUsuario*numColsTab+colUsuario)
            case 'T' => operaciones.bloqueTNT(tablero, filaUsuario*numColsTab+colUsuario)
            case 'R' => operaciones.bloqueRompecabezas(tablero, filaUsuario*numColsTab+colUsuario, dificultadJuego)
          }
          buclePrincipal(tablero, vidasJuego)
        }
    }
  }

  private def pedirUsuarioPos(tipo: String): Int = {
    modoCundy match {
      case 0 =>
        tipo match {
          case "Fila" =>
            val rand = new Random()
            val fila = rand.nextInt(numFilasTab)
            println("Fila: " + fila)
            fila
          case "Columna" =>
            val rand = new Random()
            val col = rand.nextInt(numColsTab)
            println("Columna: " + col)
            col
        }
      case 1 =>
        import scala.io.StdIn
        println("Introduce " + tipo + ": ")
        val usuario = StdIn.readInt()
        tipo match {
          case "Fila" =>
            if (usuario < numFilasTab && usuario >= 0) {
              usuario
            } else {
              println("Error: Numero de fila invalida")
              pedirUsuarioPos(tipo)
            }
          case "Columna" =>
            if (usuario < numColsTab && usuario >= 0) {
              usuario
            } else {
              println("Error: Numero de columna invalida")
              pedirUsuarioPos(tipo)
            }
        }
    }
  }

  //---------------------------------------------------FUNCIONES CON LOS ARGUMENTOS DEL JUEGO ---------------------------------------------------------
  private def selectNumColores(): Int = {
    dificultadJuego match {
      case 2 => 6
      case 1 => 4
    }
  }

  private def argsModo(): Int = {
    args(0) match {
      case "-a" => 0
      case "-m" => 1
      case _ => throw new Error("Introducir un modo correcto: -a o -m")
    }
  }

  private def argsDificultad(): Int = {
    args(1) match {
      case "1" => 1
      case "2" => 2
      case _ => throw new Error("Introducir una dificultad correcta: 1 o 2")
    }
  }

  private def argsFilas(): Int = {
    val filas = args(2).toInt
    if (filas > 0) {
      filas
    } else {
      throw new Error("Numero negativo de filas")
    }
  }

  private def argsColumnas(): Int = {
    val cols = args(3).toInt
    if (cols > 0) {
      cols
    } else {
      throw new Error("Numero negativo de columnas")
    }
  }

  private def imprimirArgumentos(): Unit = {
    println("Modo de ejecucion: " + {
      modoCundy match {
        case 0 => "automatico";
        case 1 => "manual"
      }
    })
    println("Dificultad: " + dificultadJuego)
    println("Numero de filas: " + numFilasTab)
    println("Numero de columnas: " + numColsTab)
  }
}

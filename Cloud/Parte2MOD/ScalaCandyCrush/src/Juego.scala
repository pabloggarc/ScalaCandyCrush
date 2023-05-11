import scala.sys.process._
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Juego (args: Array[String]){
    private val numFilasTab: Int = argsFilas()
    private val numColsTab: Int = argsColumnas()
    private val modoCundy: Int = argsModo()
    private val dificultadJuego: Int = argsDificultad()
    private val operaciones = new Operaciones(numFilasTab, numColsTab, selectNumColores())
    val fechaInicial = LocalDateTime.now()
    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val fechaInicialFormateada = fechaInicial.format(formato)


    def iniciar(): Unit = {
      imprimirArgumentos()
      val tablero = operaciones.generarTablero(numFilasTab * numColsTab, dificultadJuego, operaciones.generarElemento)

      buclePrincipal(tablero, 5, 0)
    }

    private def buclePrincipal(tablero: List[Char], vidasJuego: Int, puntos: Int): Unit = {
      println(operaciones.mostrarTablero(tablero, vidasJuego, numColsTab, puntos))
      vidasJuego match {
        case 0 =>
          println("Fin del juego, te has quedado sin vidas!")

          val nombre = pedirUsuarioNombre()
          val fechaFinal = LocalDateTime.now()
          val duracionPartida = ChronoUnit.SECONDS.between(fechaInicial, fechaFinal)
          println(nombre + " has obtenido un total de " + puntos + " puntos!")

          println(s"La duracion de la partida ha sido de: $duracionPartida y la partida se ha realizado en la siguiente fecha: $fechaInicialFormateada")

          Seq("cmd", "/c", s"curl \"https://api-postgres.azurewebsites.net/api/api-postgres?nombre=$nombre&puntos=$puntos&fecha=$fechaInicialFormateada&duracion=$duracionPartida\" --ssl-no-revoke").!

        case _ =>
          val filaUsuario = pedirUsuarioPos("fila", tablero)
          val colUsuario = pedirUsuarioPos("columna", tablero)
          val caramelo_seleccionado = operaciones.obtenerCaramelo(tablero, filaUsuario, colUsuario)

          if (caramelo_seleccionado.isDigit && caramelo_seleccionado.asDigit >= 1 & caramelo_seleccionado.asDigit <= 6) {
            val caminoEncontrado = operaciones.buscarCamino(tablero, filaUsuario * numColsTab + colUsuario)
            val tableroConX = operaciones.marcar(tablero, caminoEncontrado,0)
            val borrados: Int = operaciones.contarX(tableroConX)
            println("Se ha seleccionado la fila " + filaUsuario + " y la columna " + colUsuario)
            println("Se han borrado " + borrados + " caramelos")
            println(operaciones.mostrarTablero(tableroConX, vidasJuego, numColsTab, puntos))

            if (borrados == 0) {
              println("¡Has perdido una vida " + "❤" * (vidasJuego - 1) + "\uD83D\uDC94!")
              buclePrincipal(tableroConX, vidasJuego - 1, puntos) // Si no se han borrado caramelos se pierde una vida
            }
            else if (borrados >= 1 && borrados < 5) {
              val nuevoTablero2: List[Char] = operaciones.actualizarTablero(tableroConX, operaciones.cambiosTablero(tableroConX))
              val puntosActualizar: Int = 0
              val puntosNuevos: Int = operaciones.actualizarPuntos(puntosActualizar, borrados, caramelo_seleccionado, dificultadJuego)
              val nuevoTotalPuntos: Int = puntos + puntosNuevos
              buclePrincipal(nuevoTablero2, vidasJuego, nuevoTotalPuntos)
            }
            else {
              val nuevoTablero = operaciones.reemplazarCaramelo(tableroConX, filaUsuario*numColsTab+colUsuario, borrados, 0) // Si se han borrado 5 caramelos se convierte en B
              val puntosActualizar: Int = 0
              val puntosNuevos: Int = operaciones.actualizarPuntos(puntosActualizar, borrados, caramelo_seleccionado, dificultadJuego)
              val nuevoTotalPuntos: Int = puntos + puntosNuevos

              println(operaciones.mostrarTablero(nuevoTablero, vidasJuego, numColsTab, nuevoTotalPuntos))

              val nuevoTablero2: List[Char] = operaciones.actualizarTablero(nuevoTablero, operaciones.cambiosTablero(nuevoTablero))
              buclePrincipal(nuevoTablero2, vidasJuego, nuevoTotalPuntos)
            }
          }
          else if(caramelo_seleccionado == '7' || caramelo_seleccionado == '8' || caramelo_seleccionado == '9'){ //estamos ante un caramelo_seleccionado NO ESPECIAL
             val tablero4 = caramelo_seleccionado match {
              case '7' => operaciones.bloqueBomba(tablero, filaUsuario*numColsTab+colUsuario)
              case '8' => operaciones.bloqueTNT(tablero,filaUsuario*numColsTab+colUsuario)
              case '9' => operaciones.bloqueRompecabezas(tablero, filaUsuario*numColsTab+colUsuario, dificultadJuego)
            }
            val borrados: Int = operaciones.contarX(tablero4)
            val puntosActualizar: Int = 0
            val puntosNuevos: Int = operaciones.actualizarPuntos(puntosActualizar, borrados, caramelo_seleccionado, dificultadJuego)
            val nuevoTotalPuntos: Int = puntos + puntosNuevos
            println(operaciones.mostrarTablero(tablero4, vidasJuego, numColsTab, nuevoTotalPuntos))
            val nuevoTablero2: List[Char] = operaciones.actualizarTablero(tablero4, operaciones.cambiosTablero(tablero4))
            buclePrincipal(nuevoTablero2, vidasJuego, nuevoTotalPuntos)
          }
      }
    }

  private def pedirUsuarioNombre(): String = {
    print("Introduzca su nombre: ")
    val usuario: String = scala.io.StdIn.readLine()
    val nombre: String = usuario.replace(" ", "")
    nombre
  }


    private def pedirUsuarioPos(tipo: String, tablero : List[Char]): Int = {
      modoCundy match {
        case 0 =>
          val posicion = operaciones.mejorCamino(tablero)
          tipo match {
            case "fila" => posicion / numFilasTab
            case "columna" => posicion % numColsTab
          }

        case 1 =>
          import scala.io.StdIn
          print("Introduzca " + tipo + ": ")
          val usuario = StdIn.readInt()

          tipo match {
            case "fila" =>
              if (usuario < numFilasTab && usuario >= 0) {
                usuario
              }
              else {
                println("Error: Número de fila inválida")
                pedirUsuarioPos(tipo, tablero)
              }
            case "columna" =>
              if (usuario < numColsTab && usuario >= 0) {
                usuario
              }
              else {
                println("Error: Número de columna inválida")
                pedirUsuarioPos(tipo, tablero)
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
        throw new Error("Número negativo de filas")
      }
    }

    private def argsColumnas(): Int = {
      val cols = args(3).toInt
      if (cols > 0) {
        cols
      } else {
        throw new Error("Número negativo de columnas")
      }
    }

    private def imprimirArgumentos(): Unit = {
      println("Modo de ejecución: " + {
        modoCundy match {
          case 0 => "automatico";
          case 1 => "manual"
        }
      })
      println("Dificultad: " + dificultadJuego)
      println("Número de filas: " + numFilasTab)
      println("Número de columnas: " + numColsTab)
    }
}

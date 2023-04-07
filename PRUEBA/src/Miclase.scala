import scala.util.Random

class Miclase (args: Array[String]){
  private val numFilasTab: Int = argsFilas()
  private val numColsTab: Int = argsColumnas()
  private val modoCundy: Int = argsModo()
  private val dificultadJuego: Int = argsDificultad()
  private val operaciones = new Operaciones(numFilasTab, numColsTab, selectNumColores())

  def iniciar(): Unit = {
    imprimirArgumentos()
    val tablero = operaciones.rellenarTablero(operaciones.crearTablero())
    buclePrincipal(tablero, 5) //le pasamos el tablero con las cinco vidas
  }

  private def buclePrincipal(tablero: List[Int], vidasJuego: Int): Unit = {
    operaciones.imprimirTablero(tablero, vidasJuego)

    vidasJuego match {
      case 0 =>
        println("GAME OVER") //caso base, si te quedas sin vidas FUERA
      //caso default
      case _ =>
        val filaUsuario = pedirUsuarioPos("Fila")
        val colUsuario = pedirUsuarioPos("Columna")
        val caramelo = operaciones.obtenerCaramelo(tablero, filaUsuario, colUsuario) //que pablo mire como podeis apañar esto

        //los caramelos van del 1 al 6 entonces si es mayor q 6 estamos ante un bloque especial
        if (caramelo > 6) {
          /*val tabAux = caramelo match {
            case 7 =>
              val rand = new Random()
              rand.nextInt(2) match {
                case 0 =>
                  println("Se ha utilizado el bloque bomba para eliminar una fila")
                  operaciones.usarCarameloEspecial(tablero, operaciones.bombaFila, filaUsuario, colUsuario, caramelo)
                case _ =>
                  println("Se ha utilizado el bloque bomba para eliminar una columna")
                  operaciones.usarCarameloEspecial(tablero, operaciones.bombaColumna, filaUsuario, colUsuario, caramelo)
              }
            case 8 =>
              println("Se ha utilizado el bloque TNT")
              operaciones.usarCarameloEspecial(tablero, operaciones.bombaTNT, filaUsuario, colUsuario, caramelo)
            case _ =>
              println("Se ha utilizado el bloque rompecabezas para eliminar el color " + caramelo % 8)
              operaciones.usarCarameloEspecial(tablero, operaciones.rompecabezas, filaUsuario, colUsuario, caramelo % 8)
          }*/

          //sino seria llamar a gravedad y rellenarmos y VOLVEMOS A LLAMAR AL BUCLE PRINCIPAL
          buclePrincipal(tablero, vidasJuego)

        } else { //estamos ante un caramelo NO ESPECIAL
          //elimina el bloque ubicado en las coordenadas indicadas por filaUsuario y colUsuario del tablero
          //contar la cantidad de bloques eliminados en el tablero.
          //evaluar si la cantidad de bloques eliminados es menor o igual a 1. Si es así, se imprime "Tienes una vida menos".
          //que no es asi, usar gravedad para desplazar los bloques q estan en una posicion vacia y rellenar los vacios genreados
          //se llama a blucle con vidas -1
          //Si la cantidad de bloques eliminados es mayor a 1, se evalúa si es igual a 5, 6 o mayor a 6

          //Si la cantidad de bloques eliminados no es igual a 5, 6 ni mayor a 6, se retorna el tab original.
          //llamar a gravedad y rellenamos el tablero
          //buclePrincipal(tabRellenar, vidasJuego)
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

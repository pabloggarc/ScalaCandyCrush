import scala.util.Random
import Operaciones._
object Main {
  def main(args: Array[String]): Unit = {
    Random.setSeed(System.currentTimeMillis()) // semilla para la ejecución automática

    val modo: Int = 0
    val dif: Int = 0
    val n: Int = 5
    val m: Int = 7

    val tablero = generarTablero(n * m, dif, generarElemento)
    println(mostrarTablero(tablero, m))
  }
}

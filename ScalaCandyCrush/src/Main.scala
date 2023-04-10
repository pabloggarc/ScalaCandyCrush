import scala.util.Random

/*object Main {
  def main(args: Array[String]): Unit = {
    Random.setSeed(System.currentTimeMillis()) // semilla para la ejecución automática

    val modo: Int = 0
    val dif: Int = 0
    val n: Int = 5
    val m: Int = 7

    val tablero = generarTablero(n * m, dif, generarElemento)
    println(mostrarTablero(tablero, m))
  }
}*/
object Main {
  def main(args: Array[String]): Unit = {
    if (args.length == 4){
      val cundy = new Miclase(args)
      cundy.iniciar()
    } else {
      throw new Error("ERROR -> No se ha introducido un numero correcto de argumentos")
    }
  }
}
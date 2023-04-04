import scala.util.Random
object Main {


  def cargarArgumentos(args: Array[String]): Unit = {

    if (args.length != 4) {
      println("Se esperaban argumentos -a/-m 1/2 n m.")
      System.exit(-1)
    } else {
      var error = false
      if (args(1) == "-a") {
        val modo = 0
      } else if (args(1) == "-m") {
        val modo = 1
      } else {
        error = true
      }

      if (args(2) == "1") {
        val dificultad = 0
      } else if (args(2) == "2") {
       val dificultad = 1
      } else {
        error = true
      }

      if (args(3) < "1" || args(4) < "1"){
        error = true
      } else{
       val N = args(3).toInt
       val M = args(4).toInt
      }

      if (error) {
        println("Valor de argumento inválido.")
        System.exit(-1)
      }
    }
  }




  def generar_elemento(dificultad: Int): Char = {
    val r = new Random()
    if (dificultad == 1) {
      (r.nextInt(6 - 1 + 1) + 1).toChar
    } else {
      (r.nextInt(4 - 1 + 1) + 1).toChar
    }
  }


  def mostrarTablero(tablero: Array[Char], N: Int, M: Int): Unit = {
    def imprimirFila(fila: Array[Char], numFila: Int, filaStr: String): String = {
      if (fila == null || fila.size == 0) filaStr
      else imprimirFila(fila.tail, numFila, filaStr + " " + fila.head)
    }


  def contarElementos(tablero: Array[Array[Char]]): Int = {
    if (tablero == null) 0 // si la lista es nula o está vacía, devuelve un mensaje indicando que la lista está vacía
    else if (tablero.tail == null) 1 // si la cola de la lista está vacía, significa que solo tiene un elemento
    else 2 // en cualquier otro caso, la lista tiene más de un elemento
  }

  def main(args: Array[String]): Unit = {

    Random.setSeed(System.currentTimeMillis()) // semilla para la ejecución automática
    cargarArgumentos(args)
    val N: Int = 2
    val M: Int = 2
    val dificultad: Int = 1
    val tam_tablero = N * M
    val tablero = Array.tabulate(N, M)((i, j) => generar_elemento(dificultad)) // crear matriz y llenarla con la función generar_elemento

    def medirVidas(lista: List[String], vidas: Int): Int = {
      if (lista == null) vidas // caso base: si la lista es nula, devuelve el número de vidas restantes
      else if (contarElementos(tablero) == 1){ medirVidas(lista.tail, vidas - 1)} // si el primer elemento de la lista tiene longitud 1, resta una vida y continúa con el resto de la lista
      else {
        mostrarTablero(tablero, N, M)



        medirVidas(lista.tail, vidas)
      }
    }


  }
}
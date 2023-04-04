import scala.util.Random
object Main {

  var dificultad: Int = -1
  var modo: Int = -1
  var N: Int = 0
  var M: Int = 0

  def cargarArgumentos(args: Array[String]): Unit = {

    if (args.length != 4) {
      println("Se esperaban argumentos -a/-m 1/2 n m.")
      System.exit(-1)
    } else {
      var error = false
      if (args(1) == "-a") {
        modo = 0
      } else if (args(1) == "-m") {
        modo = 1
      } else {
        error = true
      }

      if (args(2) == "1") {
        dificultad = 0
      } else if (args(2) == "2") {
        dificultad = 1
      } else {
        error = true
      }

      if (args(3) < "1" || args(4) < "1"){
        error = true
      } else{
        N = args(3).toInt
        M = args(4).toInt
      }


      if (error) {
        println("Valor de argumento inválido.")
        System.exit(-1)
      }
    }
  }




  def generar_elemento(): Char = {
    val r = new Random()
    if (dificultad == 1) {
      (r.nextInt(6 - 1 + 1) + 1).toChar
    } else {
      (r.nextInt(4 - 1 + 1) + 1).toChar
    }
  }


  def mostrarTablero(tablero: Array[Char], n: Int, m: Int): Unit = {
    def imprimirFila(fila: Array[Char], numFila: Int, filaStr: String): String = {
      if (fila == null || fila.size == 0) filaStr
      else imprimirFila(fila.tail, numFila, filaStr + " " + fila.head)
    }

    def imprimirTablero(filas: Array[Array[Char]], numFila: Int, tableroStr: String): String = {
      if (filas == null || filas.size == 0) tableroStr
      else {
        val filaStr = imprimirFila(filas.head, numFila, "")
        imprimirTablero(filas.drop(1), numFila + 1, tableroStr + s"\n\t\t${numFila}\t${filaStr}")
      }
    }

    def contar_elementos(arr: Array[Char]): Int = {
      if (arr == null) 0
      else if (arr.head == '\u0000') 0
      else 1 + contar_elementos(arr.drop(1))
    }

    val filaBorde = (0 to m).map(_ => "-").mkString("   ")
    val cabecera = (0 to m).map(i => f"${i}%3d").mkString("  ")

    println("\nTABLERO: ")
    println(s"\t\t   $cabecera")
    println(s"\t\t   $filaBorde")
    val filas = tablero.grouped(m).toArray
    val tableroStr = imprimirTablero(filas, 0, "")
    println(s"\t\t   $filaBorde")
    println(tableroStr)
  }


  def main(args: Array[String]): Unit = {
    println("Hello world!")
  }
}
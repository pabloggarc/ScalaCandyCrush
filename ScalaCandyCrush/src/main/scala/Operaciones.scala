import scala.util.Random

object Operaciones {

  def longitud(array: List[Any]): Int = {
    array match {
      case Nil => 0
      case _ => 1 + longitud(array.tail)
    }
  }

  def concatenar(array1: List[Char], array2: List[Char]): List[Char] = {
    array2 match {
      case Nil => array1
      case _ => concatenar(array1 :+ array2.head, array2.tail)
    }
  }

  def generarElemento(dificultad: Int): Char = {
    val r = new Random()
    if (dificultad == 1) {
      (r.nextInt(6 - 1 + 1) + 1).toString.charAt(0)
    }
    else {
      (r.nextInt(4 - 1 + 1) + 1).toString.charAt(0)
    }
  }

  def generarTablero(i: Int, dif: Int, f: Int => Char): List[Char] = {
    i match {
      case 0 => Nil
      case _ => concatenar(List(f(dif)), generarTablero(i - 1, dif, f))
    }
  }

  private def mostrarTableroAux(tablero: List[Char], i: Int, m: Int, mFila: Boolean): String = {
    i match {
      case _ if tablero == Nil => ""
      case _ if i % m == 0 => " | " + tablero.head + " | \n" +  mostrarTableroAux(tablero.tail, i + 1, m, true)
      case _ if mFila => i / m + " | " + tablero.head + mostrarTableroAux(tablero.tail, i + 1, m, false)
      case _ => " | " + tablero.head + mostrarTableroAux(tablero.tail, i + 1, m, false)
    }
  }

  private def cabeceraTablero(m: Int, inicio: Int, total: Int): String = {
    inicio match {
      case 1 => " " + cabeceraTablero(m, 0, total)
      case 0 => m match {
        case 0 => " | \n " + " " + "-" * ((total * 4) + 1) + " \n"
        case _ => " | " + (total - m).toString + cabeceraTablero(m - 1, inicio, total)
      }
    }
  }

  def mostrarTablero(tablero: List[Char], m: Int): String = {
    cabeceraTablero(m, 1, m) + mostrarTableroAux(tablero, 1, m, true)
  }
}
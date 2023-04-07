import scala.util.Random
import Console.{BLUE, GREEN, MAGENTA, RED, RESET, YELLOW, BLACK}
import scala.annotation.{tailrec, unused}

class Operaciones (numFila: Int, numCol: Int, numColores: Int) {

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
        //para rellenar tablero
    def rellenarTablero(tablero: List[Int]): List[Int] = {
        tablero match {
            case Nil => Nil
            case head :: tail if head == 0 =>
                val rand = new Random()
                1 + rand.nextInt(numColores) :: rellenarTablero(tail)
            case head :: tail => head :: rellenarTablero(tail)
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
    //funcion para crear un tablero con n*m dimensiones
    def generarTablero(i: Int, dif: Int, f: Int => Char): List[Char] = {
        i match {
            case 0 => Nil
            case _ => concatenar(List(f(dif)), generarTablero(i - 1, dif, f))
        }
    }

    def crearTablero(): List[Int] = crearTableroAux(numCol * numFila)

    private def crearTableroAux(n: Int): List[Int] = {
        n match {
            case 0 => Nil
            case _ => 0 :: crearTableroAux(n - 1)
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
        //funcion para imprimir tablero
    def imprimirTablero(tablero: List[Int], vidas: Int): Unit = {
        imprimirCabezaTablero(0)
        println("-----" * (numCol + 1))
        imprimirTableroAux(tablero, 0)
        println(s"VIDAS: $RESET$RED" + "♥" * vidas + s"$RESET")

    }

    def numCifras(n: Int): Int = {
        n match {
            case _ if 0 <= n && n < 10 => 1
            case _ => 1 + numCifras(n / 10)
        }
    }
    @tailrec
    private def imprimirCabezaTablero(n: Int): Unit = {
        if (n != numCol) {
            n match {
                case 0 =>
                    print("    |   0|")
                    imprimirCabezaTablero(n + 1)
                case _ =>
                    val cifras = numCifras(n)
                    print(" " * (4 - cifras) + n + "|")
                    imprimirCabezaTablero(n + 1)
            }
        } else {
            print("\n")
        }
    }

    @tailrec
    private def imprimirTableroAux(tablero: List[Int], n: Int): Unit = {
        if (tablero != Nil) {
            n match {
                case _ if n % numCol == 0 =>
                    val cifras = numCifras(n / numCol)
                    print(" " * (4 - cifras) + (n / numCol) + "|")
                    imprimirCaramelo(tablero.head)
                    imprimirTableroAux(tablero.tail, n + 1)
                case _ if n % numCol == numCol - 1 =>
                    imprimirCaramelo(tablero.head)
                    println("")
                    imprimirTableroAux(tablero.tail, n + 1)
                case _ =>
                    imprimirCaramelo(tablero.head)
                    imprimirTableroAux(tablero.tail, n + 1)
            }
        }
    }
    /*def mostrarTablero(tablero: List[Char], m: Int): String = {
        cabeceraTablero(m, 1, m) + mostrarTableroAux(tablero, 1, m, true)
        println(s"VIDAS: $RESET$RED" + " ♥" * vidas + s"$RESET")
    }*/
        //funcion para obtener la posicion del tablero
    def obtenerPosicionLista(lista: List[Int], pos: Int): Int = obtenerPosicionListaAux(lista, 0, pos)

        //funcion aux de obtener posicion
    private def obtenerPosicionListaAux(lista: List[Int], n: Int, pos: Int): Int = {
        if(lista != Nil){
            if (n == pos){
                lista.head
            } else {
                obtenerPosicionListaAux(lista.tail, n+1, pos)
            }
        } else {
            throw new Error("ERROR")
        }
    }
    
        //funcion para obtener el caramelo de una posicion
    def obtenerCaramelo(tablero: List[Int], fila: Int, col: Int): Int = {
            val pos = fila * numCol + col
            obtenerPosicionLista(tablero, pos)
    }

    //imprimir los caramelos con colores
    private def imprimirCaramelo(caramelo: Int): Unit = {
        caramelo match {
            case 1 =>
                print(s"   $RESET${BLACK}1$RESET ")
            case 2 =>
                print(s"   $RESET${RED}2$RESET ")
            case 3 =>
                print(s"   $RESET${GREEN}3$RESET ")
            case 4 =>
                print(s"   $RESET${BLUE}4$RESET ")
            case 5 =>
                print(s"   $RESET${YELLOW}5$RESET ")
            case 6 =>
                print(s"   $RESET${MAGENTA}6$RESET ")
            case _ => print("   0 ")
        }
    }
}
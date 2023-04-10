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

    def calcularDificultad(elems: Int): Int = {
        elems match {
            case 4 => 1
            case 6 => 2
        }
    }

    def generarElemento(dificultad: Int): Char = {
        val r = new Random()
        if (dificultad == 2) {
            (r.nextInt(6 - 1 + 1) + 1).toString.charAt(0)
        }
        else {
            (r.nextInt(4 - 1 + 1) + 1).toString.charAt(0)
        }
    }

    def boolToInt(booleano: Boolean): Int = {
        booleano match {
            case true => 1
            case false => 0
        }
    }

    //funcion para crear un tablero con n*m dimensiones
    def generarTablero(i: Int, dif: Int, f: Int => Char): List[Char] = {
        i match {
            case 0 => Nil
            case _ => concatenar(List(f(dif)), generarTablero(i - 1, dif, f))
        }
    }

    private def mostrarTableroAux(tablero: List[Char], i: Int, m: Int, mFila: Boolean): String = {
        i match {
            case _ if tablero == Nil => ""
            case _ if i % m == 0 => " | " + imprimirCaramelo(tablero.head.toInt-'0'.toInt)+ " | \n" +  mostrarTableroAux(tablero.tail, i + 1, m, true)
            case _ if mFila => i / m + " | " + imprimirCaramelo(tablero.head.toInt-'0'.toInt) + mostrarTableroAux(tablero.tail, i + 1, m, false)
            case _ => " | " + imprimirCaramelo(tablero.head.toInt-'0'.toInt) + mostrarTableroAux(tablero.tail, i + 1, m, false)
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

    def mostrarTablero(tablero: List[Char], vidas: Int, m: Int): String = {
        cabeceraTablero(m, 1, m) + mostrarTableroAux(tablero, 1, m, true) +
        s"\nVIDAS: $RESET$RED" + " ♥" * vidas + s"$RESET"
    }

    //imprimir los caramelos con colores
    private def imprimirCaramelo(caramelo: Int): String = {
        caramelo match {
            case 1 => s"$RESET${BLACK}1$RESET"
            case 2 => s"$RESET${RED}2$RESET"
            case 3 => s"$RESET${GREEN}3$RESET"
            case 4 => s"$RESET${BLUE}4$RESET"
            case 5 => s"$RESET${YELLOW}5$RESET"
            case 6 => s"$RESET${MAGENTA}6$RESET"
            case _ => "X"
        }
    }

    private def obtenerPosicionLista(tablero: List[Char], i: Int): Char = {
        i match {
            case 0 => tablero.head
            case _ => obtenerPosicionLista(tablero.tail, i - 1)
        }
    }

    def obtenerCaramelo(tablero: List[Char], fila: Int, col: Int): Char = {
        obtenerPosicionLista(tablero, fila * numCol + col)
    }

    /*def buscarCamino(tablero: List[Char], selec: Int, camino: List[Int], visitados: List[Int]): List[Int] ={

        val vecinos: Array[Int] = Array(selec, selec - numCol, selec + numCol, selec - 1, selec + 1)
        if (vecinos(1) < 0) vecinos(1) = -1
        if (vecinos(2) >= numFila * numCol) vecinos(2) = -1
        if (selec % numCol == 0) vecinos(3) = -1
        if ((selec + 1) % numCol == 0) vecinos(4) = -1

        //falta parte del return (ya que si pones un return deja de fallar) y cambiar el for de la funcion original

    }*/


    def buscarCamino(tablero: List[Char], selec: Int, filas: Int, columnas: Int): List[Int] = {
        val valorCaramelo = tablero(selec)
        println(valorCaramelo, "valor del caramelo en funcion BUSCAR")
        def buscarRec(vecinos: List[Int], visitados: List[Int]): List[Int] = {
            vecinos match {
                case Nil =>  Nil // No quedan vecinos por explorar
                case v :: vs if v < 0 || v >= filas * columnas => buscarRec(vs, visitados) // Posición fuera del tablero
                case v :: vs if visitados.contains(v) => buscarRec(vs, visitados) // Posición ya visitada y HACER LA FUNCION DE CONTENER!!!!!
                case v :: vs if valorCaramelo != tablero(v) => buscarRec(vs,visitados) // Posición de distinto tipo
                case v :: vs => // Posición de mismo tipo y no visitada
                    val visitados2 = v::visitados
                    if (v == selec) { // Se ha encontrado el final
                        v::buscarRec(vs, visitados2)
                    } else { // Se añade al camino
                        v :: buscarRec(v - columnas :: v + columnas :: v - 1 :: v + 1 :: vs, visitados2)
                    }
            }
        }
        buscarRec(List(selec - columnas, selec + columnas, selec - 1, selec + 1), Nil)
    }



    def marcar(tablero: List[Char], camino: List[Int],n:Int): List[Char] = {
        tablero match {
            case Nil =>
                Nil
            case _::t if camino.contains(n) => //hacer la funcion CONTENER!!!
                println("voy a poner una X")
                'X' :: marcar(t,camino,n+1)
            case h::t =>
                h::marcar(t,camino,n+1)
        }
    }

    /*def recolocarTablero(tablero : List[Char], dificultad : Int) : List[Char] = {
        if (dificultad == 1){

        }else{

        }
    }*/


    //creo que la parte de comprobar si un elemento pertenece al camino no es realmente necesaria ya que solo hacemos un camino
    //lo hacemos desde el seleccionado, por lo cual se deduce que el elemento siempre se va a encontrar en el camino, por lo que
    //creo que no son necesarios
    def perteneceRec(x: Array[Int], n: Int, y: Int): Boolean = {
        if (n == 0) {
            false
        } else {
            x(n - 1) == y || perteneceRec(x, n - 1, y)
        }
    }

    def pertenece(x: Array[Int], n: Int, y: Int): Boolean = {
        perteneceRec(x, n, y)
    }

    def contarXdebajo(tablero: List[Char], elem: Int): Int = {
        elem match {
            case _ if elem >= longitud(tablero) => 0
            case _ if tablero(elem) == 'X' => 1 + contarXdebajo(tablero, elem + numCol)
            case _ => contarXdebajo(tablero, elem + numCol)
        }
    }

    private def contarNoXencimaAux(tablero: List[Char], inicio: Int, fin: Int): Int = {
        inicio match {
            case _ if inicio == fin => 0
            case _ if tablero(inicio) != 'X' => 1 + contarNoXencimaAux(tablero, inicio + numCol, fin)
            case _ => contarNoXencimaAux(tablero, inicio + numCol, fin)
        }
    }

    def contarNoXencima(tablero: List[Char], elem: Int): Int = {
        contarNoXencimaAux(tablero, elem % numCol, elem)
    }

    private def recolocarTableroAuxAux(tablero: List[Char], elem: Int, XDebajo: Int, valorAnterior: Char): List[Int] = {
        if (elem + numCol * XDebajo < numFila * numCol && XDebajo > 0 && valorAnterior != 'X') {
            //println("El valor de la casilla " + elem + " se tiene que desplazar a " + (elem + numCol * XDebajo))
            List(elem + numCol * XDebajo, valorAnterior.toInt - '0'.toInt)
        }
        else{
            Nil
        }
    }

    private def recolocarTableroAux(tablero: List[Char], elem: Int): List[List[Int]] = {
        if (longitud(tablero) <= elem) {
            Nil
        }
        else {
            val noXEncima: Int = contarNoXencima(tablero, elem)
            val XDebajo: Int = contarXdebajo(tablero, elem)
            val valorAnterior: Char = tablero(elem)

            //println("noXEncima: " + noXEncima + ", XDebajo: " + XDebajo + ", valorAnterior: " + valorAnterior)

            val listaAux = recolocarTableroAuxAux(tablero, elem, XDebajo, valorAnterior)

            if (XDebajo + boolToInt(valorAnterior == 'X') - noXEncima > 0) {
                if(listaAux != Nil){
                    listaAux :: List(elem, generarElemento(calcularDificultad(numColores)).toInt - '0'.toInt) :: recolocarTableroAux(tablero, elem + 1)
                }
                else{
                    List(elem, generarElemento(calcularDificultad(numColores)).toInt - '0'.toInt) :: recolocarTableroAux(tablero, elem + 1)
                }
            }
            else {
                if(listaAux != Nil){
                    listaAux :: List(elem, valorAnterior.toInt - '0'.toInt) :: recolocarTableroAux(tablero, elem + 1)
                }
                else{
                    List(elem, valorAnterior.toInt - '0'.toInt) :: recolocarTableroAux(tablero, elem + 1)
                }
            }
        }
    }

     def cambiosTablero(tablero: List[Char]): List[List[Int]] = {
         recolocarTableroAux(tablero, 0)
     }

     /*private def actualizarElementoTablero(tablero: List[Char], actualizacion: List[Int]): List[Char] = {
        tablero match {
            case Nil => Nil
            case x :: y =>
                if (actualizacion(0) == 0) actualizacion(1).toString.charAt(0) :: y
                else x :: actualizarElementoTablero(y, List(actualizacion(0) - 1, actualizacion(1)))
        }
    }

    private def actualizarTableroAux(tablero: List[Char], actualizaciones: List[List[Int]], i: Int): List[Char] = {
        if (i < longitud(actualizaciones)){
            val tableroActualizado: List[Char] = actualizarElementoTablero(tablero, actualizaciones(i))
            println("Actualizando: " + actualizaciones(i))
            actualizarTableroAux(tableroActualizado, actualizaciones, i + 1)
        }
        else {
            tablero
        }
    }*/

    private def nuevoTablero(nTablero: Array[Char], actualizaciones: List[List[Int]]): List[Char] = {
        actualizaciones match {
            case Nil => nTablero.toList
            case _ => {
                val tActualizado: Array[Char] = nTablero
                tActualizado(actualizaciones.head(0)) = actualizaciones.head(1).toString.charAt(0)
                nuevoTablero(tActualizado, actualizaciones.tail)
            }
        }
    }

    def actualizarTablero(tablero: List[Char], actualizaciones: List[List[Int]]): List[Char] = {
        nuevoTablero(Array.ofDim[Char](longitud(tablero)), actualizaciones)
    }

}
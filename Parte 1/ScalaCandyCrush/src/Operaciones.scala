import scala.util.Random
import Console.{BLUE, GREEN, MAGENTA, RED, RESET, YELLOW, BLACK}
import scala.annotation.{tailrec, unused}

class Operaciones (numFila: Int, numCol: Int, numColores: Int) {

    /*-----------------------UTILIDADES-----------------------*/

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

    private def menorAux(lista: List[Int]): Int = {
        lista match {
            case Nil => Double.PositiveInfinity.toInt
            case _ => lista.head.min(menorAux(lista.tail))
        }
    }

    def menor(lista: List[Int]): Int = {
        lista match {
            case Nil => -1
            case _ => menorAux(lista)
        }
    }

    def union(l1: List[Int], l2: List[Int]): List[Int] = {
        l2 match {
            case Nil => l1
            case _ if contiene(l1, l2.head) => union(l1, l2.tail)
            case _ => union(l1 :+ l2.head, l2.tail)
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

    private def obtenerPosicionLista(tablero: List[Char], i: Int): Char = {
        i match {
            case 0 => tablero.head
            case _ => obtenerPosicionLista(tablero.tail, i - 1)
        }
    }

    def contiene(lista: List[Int], elemento: Int): Boolean = {
        def bucle(lst: List[Int]): Boolean = {
            lst match {
                case Nil => false
                case head :: tail =>
                    if (head == elemento) true
                    else bucle(tail)
            }
        }

        bucle(lista)
    }

    /*-----------------------OPERACIONES TABLERO-----------------------*/

    def obtenerCaramelo(tablero: List[Char], fila: Int, col: Int): Char = {
        obtenerPosicionLista(tablero, fila * numCol + col)
    }

    def contarX(tablero: List[Char]): Int = {
        tablero match {
            case Nil => 0
            case n if n.head == 'X' => 1 + contarX(tablero.tail)
            case _ => contarX(tablero.tail)
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
            case _ if i % m == 0 => " | " + imprimirCaramelo(tablero.head.toInt-'0'.toInt)+ " | \n\t\t\t\t" +  mostrarTableroAux(tablero.tail, i + 1, m, true)
            case _ if mFila => i / m + " | " + imprimirCaramelo(tablero.head.toInt-'0'.toInt) + mostrarTableroAux(tablero.tail, i + 1, m, false)
            case _ => " | " + imprimirCaramelo(tablero.head.toInt-'0'.toInt) + mostrarTableroAux(tablero.tail, i + 1, m, false)
        }
    }

    private def cabeceraTablero(m: Int, inicio: Int, total: Int): String = {
        inicio match {
            case 1 => " " + cabeceraTablero(m, 0, total)
            case 0 => m match {
                case 0 => " | \n\t\t\t\t " + " " + "-" * ((total * 4) + 1) + " \n\t\t\t\t"
                case _ => " | " + (total - m).toString + cabeceraTablero(m - 1, inicio, total)
            }
        }
    }

    def mostrarTablero(tablero: List[Char], vidas: Int, m: Int): String = {
        "\n" + s"\nVIDAS: $RESET$RED" + " ❤" * vidas + s"$RESET\n\n\t\t\t\t" +
          cabeceraTablero(m, 1, m) + mostrarTableroAux(tablero, 1, m, true) + "\n"
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
            case 7 => "B"
            case 8 => "T"
            case 9 => "R"
            case _ => "X"
        }
    }

    def analizarVecinos(vecinos: List[Int], selec: Int, i: Int): List[Int] = {
        i match {
            case n if n > 3 => Nil
            case 0 if vecinos(i) < 0 => analizarVecinos(vecinos, selec, i + 1)
            case 1 if vecinos(i) >= numFila * numCol => analizarVecinos(vecinos, selec, i + 1)
            case 2 if selec % numCol == 0 => analizarVecinos(vecinos, selec, i + 1)
            case 3 if (selec + 1) % numCol == 0 => analizarVecinos(vecinos, selec, i + 1)
            case _ => vecinos(i) :: analizarVecinos(vecinos, selec, i + 1)
        }
    }

    private def explorarVecinos(tablero: List[Char], camino: List[Int], visitados: List[Int], vecinos: List[Int], selec: Int): List[Int] = {
        vecinos match {
            case Nil => camino
            case _ => {
                val tipoCaramelo: Char = tablero(selec)
                tablero(vecinos.head) match {
                    case x if x == tipoCaramelo && !contiene(visitados, vecinos.head) =>
                        union(explorarVecinos(tablero, camino :+ vecinos.head, visitados :+ vecinos.head,
                        vecinos.tail, selec),
                        explorarVecinos(tablero, Nil, visitados :+ vecinos.head,
                        analizarVecinos(List(vecinos.head - numCol, vecinos.head + numCol, vecinos.head - 1, vecinos.head + 1),
                            vecinos.head, 0), vecinos.head))
                    case _ =>
                        explorarVecinos(tablero, camino, visitados :+ vecinos.head, vecinos.tail, selec)
                }
            }
        }
    }

    def buscarCamino(tablero: List[Char], selec: Int): List[Int] = {
        val vecinos: List[Int] = analizarVecinos(List(selec - numCol, selec + numCol, selec - 1, selec + 1), selec, 0)
        val camino: List[Int] = explorarVecinos(tablero, List(selec), List(selec), vecinos, selec)
        camino match {
            case x if longitud(x) > 1 => x
            case _ => Nil
        }
    }

    def marcar(tablero: List[Char], camino: List[Int],n:Int): List[Char] = {
        tablero match {
            case Nil => Nil
            case _ :: t if (contiene(camino, n)) => 'X' :: marcar(t, camino, n + 1)
            case h :: t => h :: marcar(t, camino, n + 1)
        }
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

    private def perteneceSublista(lista: List[List[Int]], elem: Int): Boolean = {
        lista match {
            case Nil => false
            case _ => {
                lista.head match {
                    case x if x.head == elem => true
                    case _ => perteneceSublista(lista.tail, elem)
                }
            }
        }
    }

    private def recolocarTableroAuxAux(tablero: List[Char], elem: Int, XDebajo: Int, valorAnterior: Char): List[Int] = {
        if (elem + numCol * XDebajo < numFila * numCol && XDebajo > 0 && valorAnterior != 'X') {
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

    private def dejarDefinitivos(actualizaciones: List[List[Int]], actualizacionesDefinitivas: List[List[Int]]): List[List[Int]] = {
        actualizaciones match {
            case Nil => actualizacionesDefinitivas
            case head :: tail => {
                if(perteneceSublista(actualizacionesDefinitivas, head.head)){
                    dejarDefinitivos(tail, actualizacionesDefinitivas)
                }
                else{
                    dejarDefinitivos(tail, actualizacionesDefinitivas :+ head)
                }
            }
        }
    }

     def cambiosTablero(tablero: List[Char]): List[List[Int]] = {
         dejarDefinitivos(recolocarTableroAux(tablero, 0), List())
     }

    private def nuevoTablero(nTablero: Array[Char], actualizaciones: List[List[Int]]): List[Char] = {
        actualizaciones match {
            case Nil => nTablero.toList
            case _ => {
                nTablero(actualizaciones.head(0)) = actualizaciones.head(1).toString.charAt(0)
                nuevoTablero(nTablero, actualizaciones.tail)
            }
        }
    }

    def actualizarTablero(tablero: List[Char], actualizaciones: List[List[Int]]): List[Char] = {
        nuevoTablero(Array.ofDim[Char](longitud(tablero)), actualizaciones)
    }

    def reemplazarCaramelo(tablero: List[Char], seleccionado: Int, borrados: Int, pos: Int): List[Char] = {

        if (tablero == Nil) {
            Nil
        } else {
            if (borrados == 5 && pos == seleccionado) {
                '7' :: reemplazarCaramelo(tablero.tail, seleccionado, borrados, pos + 1)

            } else if (borrados == 6 && pos == seleccionado) {

                '8' :: reemplazarCaramelo(tablero.tail, seleccionado, borrados, pos + 1)

            } else if (borrados >= 7 && pos == seleccionado) {

                '9' :: reemplazarCaramelo(tablero.tail, seleccionado, borrados, pos + 1)

            } else {

                tablero.head :: reemplazarCaramelo(tablero.tail, seleccionado, borrados, pos + 1)
            }
        }
    }


    def bloqueBomba(tablero: List[Char], elem: Int): List[Char] = {
        val r = new Random()
        val borrador = r.nextInt(2)
        if (borrador == 0) { //borra las filas
            bloqueBombaFila(tablero, elem / numCol, 0)
        } else { //columnas
            bloqueBombaColumna(tablero, elem % numCol, 0)
        }

    }

    def bloqueBombaFila(tablero: List[Char], fila: Int, pos: Int): List[Char] = {
        if (tablero == Nil) {
            Nil
        } else {
            if (fila * numCol <= pos && (fila + 1) * numCol > pos) {
                'X' :: bloqueBombaFila(tablero.tail, fila, pos + 1)
            } else {
                tablero.head :: bloqueBombaFila(tablero.tail, fila, pos + 1)
            }
        }
    }

    def bloqueBombaColumna(tablero: List[Char], columna: Int, pos: Int): List[Char] = {
        if (tablero == Nil) {
            Nil
        } else {
            if (pos % numCol == columna) {
                'X' :: bloqueBombaColumna(tablero.tail, columna, pos + 1)
            } else {
                tablero.head :: bloqueBombaColumna(tablero.tail, columna, pos + 1)
            }
        }
    }


    def bloqueTNT(tablero: List[Char], elem: Int): List[Char] = {
        val filaElem = elem / numCol
        val colElem = elem % numCol

        def bloqueTNTAux(tableroAux: List[Char], posicion: Int): List[Char] = {
            val filaPos = posicion / numCol
            val colPos = posicion % numCol

            if (tableroAux == Nil){
                Nil

            } else {
                if (filaPos >= filaElem - 4 && filaPos <= filaElem + 4 && colPos >= colElem - 4 && colPos <= colElem + 4) {
                    'X' :: bloqueTNTAux(tableroAux.tail, posicion + 1)
                } else {
                    tableroAux.head :: bloqueTNTAux(tableroAux.tail, posicion + 1)
                }

            }
        }

        bloqueTNTAux(tablero, 0)
    }


    def bloqueRompecabezas(tablero: List[Char], elem: Int, dif: Int): List[Char] = {
        val filaElem = elem / numCol
        val colElem = elem % numCol

        val r = new Random()
        obtenerCaramelo(tablero, filaElem, colElem)

        val tipo = if (dif == 1) {
            (r.nextInt(4 - 1 + 1) + 1).toString.charAt(0)
        } else {
            (r.nextInt(6 - 1 + 1) + 1).toString.charAt(0)
        }

        def bloqueRompecabezasAux(tableroAux: List[Char], pos: Int): List[Char] = {
            if (tableroAux == Nil) {
                Nil
            } else {
                if (tableroAux.head == tipo || pos == elem) {
                    'X' :: bloqueRompecabezasAux(tableroAux.tail, pos + 1)
                } else {
                   tableroAux.head :: bloqueRompecabezasAux(tableroAux.tail, pos + 1)
                }
            }
        }

        bloqueRompecabezasAux(tablero, 0)

    }
}
object Main {
  def main(args: Array[String]): Unit = {
    if (args.length == 4){
      val cundy = new Juego(args)
      cundy.iniciar()
    }
    else {
      throw new Error("ERROR -> No se ha introducido un número correcto de argumentos")
    }
  }
}
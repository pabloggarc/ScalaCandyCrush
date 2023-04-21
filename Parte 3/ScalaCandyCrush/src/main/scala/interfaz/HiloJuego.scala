package interfaz

class HiloJuego(juego: Juego) extends Thread {

  override def run(): Unit = {
    try {
      def loop(): Unit = {
        juego.modoAutomatico()
        Thread.sleep(2000)

          loop()

      }
      loop()
    } catch {
      case _: InterruptedException =>
    }
  }
}
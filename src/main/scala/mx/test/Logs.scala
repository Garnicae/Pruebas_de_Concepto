import org.slf4j.LoggerFactory

object Logs {
  val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    println("Hola, logger")

    logger.debug("Esto es una pruebs de DEBUG")
    logger.info("Esto es una prueba de INFO")
    logger.warn("Esto es una prueba de WARN")
    logger.error("Esto es una prueba de ERROR")
    logger.trace("Esto es una prueba de TRACE")


  }
}
import java.text.SimpleDateFormat
import java.util.Calendar

object fechas {
  def main(args: Array[String]): Unit = {
    println("hola, fechas")

    val format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    println(format.format(Calendar.getInstance().getTime()))
  }
}
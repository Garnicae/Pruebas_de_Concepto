import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object CreateBigFile {

  def main(args: Array[String]): Unit = {
    println("Hola, bigFile")

    val ruta = args(0)
    var i = args(1).asInstanceOf[Int]
    val archivo : File = new File(ruta)
    var bw = new BufferedWriter(new FileWriter(archivo))
    println(s"Se van a escribir $i lineas en el archivo.")

    // Se escriben 1000 lineas en el archivo
    while (i < 1000) {
      bw.write(s"Se escribe la linea numero: $i en el archivo de texto \n")
      i = i + 1
    }

    bw.close(); // Se cierra el archivo de texto

    System.out.println("Se terminó de escribir el archivo de texto")

  }
}
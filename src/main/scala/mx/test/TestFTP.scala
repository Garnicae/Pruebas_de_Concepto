import java.io.{BufferedReader, InputStreamReader}
import java.math.BigInteger
import java.security.MessageDigest

import com.jcraft.jsch.{ChannelSftp, JSch}
import org.slf4j.LoggerFactory

object TestFTP  {
  def main(args: Array[String]): Unit = {

    // Variables de conexión al FileSystem
    val logger = LoggerFactory.getLogger(getClass)
    val user = "root"
    val host = "10.15.191.150"
    val password = "centos"
    val folderFiles = "/root/hadoop/files/in/"

    // Variables y configuración para la conexión con la biblioteca
    val jsch = new JSch()
    val session = jsch.getSession(user, host, 22)
    session.setPassword(password)
    session.setConfig( "StrictHostKeyChecking", "no" )
    session.connect()

    // Variables de Apache Spark
    //val conf = new SparkConf().setAppName("collect").setMaster("local[1]")
    //val sc = new SparkContext(conf)

    if(session.isConnected)
      logger.info(s"Connected to server: $host ... \n")

    val sftp = session.openChannel("sftp")
    sftp.connect()

    if(sftp.isConnected)
      logger.info("Connected to sftp channel ... \n")
    else
      logger.warn("Not connected to sftp channel ... \n")

    // Se leen los archivos del directorio
    var channelSftp : ChannelSftp = null
    channelSftp = sftp.asInstanceOf[ChannelSftp]
    var vector = channelSftp.ls(folderFiles)

    // Se hace un barrido del directorio e imprime los ficheros existentes
    for(i <- 0 to vector.size() - 1){
      var file = vector.get(i).asInstanceOf[ChannelSftp#LsEntry]
      if(!file.getAttrs.isDir)
        println(file.getFilename)
    }
    println("\n")

    // Se lee un archivo del directorio
    //val stream = sftp.asInstanceOf[ChannelSftp].get(folderFiles + "book7.xlsx")
    val stream = sftp.asInstanceOf[ChannelSftp].get(folderFiles + "CONSTANCIABANCO2015.txt")
    val br = new BufferedReader(new InputStreamReader(stream))

    // Variables dentro del recorrido del archivo
    var line: String = null
    var num_line = 1
    var start_flag = "CONTROLRET"
    var rdds = scala.collection.mutable.ListBuffer.empty[(String, Int, String, String)]

    //////////////////////////////////////////////////////
    while ({line = br.readLine; line != null}) {
      println(line)
    }
    /////////////////////////////////////////////////////////

    // Se va leyendo el archivo linea por linea
    while ({line = br.readLine; line != null}) {
      val data = line.split('|')
      if(data(0) == start_flag){
        if(!rdds.isEmpty){
          println("Archivo nuevo")
          println(rdds)
          //var rdd = sc.parallelize(rdds)
          // Antes de vaciar la lista de archivos, se va a almacenar en HIVE
          rdds.clear()
        }
      }
      val tuple1 = (hashString(data(0)), num_line, data(0), line)
      rdds += (tuple1)
      num_line = num_line + 1
    }

    br.close

    sftp.disconnect()
    session.disconnect()
  }

  /***
    * Función que permite generar una clave hash con algun algoritmo
    * a partir de una cadena de entrada
    * @param s cadena de entrada para calcular una clave hash
    * @return clave hash calculada a partir de la cadena de entrada
    */
  def hashString(s: String): String = {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(s.getBytes)
    val bigInt = new BigInteger(1,digest)
    val hashedString = bigInt.toString(16)
    hashedString
  }


  def insertData(): Unit = {

  }
}
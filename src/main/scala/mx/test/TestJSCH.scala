import java.io.{BufferedReader, InputStreamReader}
import java.math.BigInteger
import java.security.MessageDigest

import com.jcraft.jsch.{ChannelSftp, JSch}
import org.slf4j.LoggerFactory

object TestJSCH  {
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
        println(file.getAttrs.getSize + " - " + file.getFilename)
    }
    println("\n")


    channelSftp

    sftp.disconnect()
    session.disconnect()
  }

}
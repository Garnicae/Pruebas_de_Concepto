import java.io.{BufferedReader, InputStreamReader}
import java.math.BigInteger
import java.security.MessageDigest

import com.jcraft.jsch.{ChannelSftp, JSch}
import org.slf4j.LoggerFactory

object TestFTP  {
  def main(args: Array[String]): Unit = {

    val logger = LoggerFactory.getLogger(getClass)
    val user = "root"
    val host = "10.15.191.136"
    val password = "centos"
    val folderFiles = "/root/hadoop/files/in/"

    val jsch = new JSch()
    val session = jsch.getSession(user, host, 22)
    session.setPassword(password)
    session.setConfig( "StrictHostKeyChecking", "no" );
    session.connect()

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

    for(i <- 0 to vector.size() - 1){
      var file = vector.get(i).asInstanceOf[ChannelSftp#LsEntry]
      if(!file.getAttrs.isDir)
        println(file.getFilename)
    }

    println("\n")
    // Se lee un archivo del directorio
    val stream = sftp.asInstanceOf[ChannelSftp].get(folderFiles + "CFDI.txt")
    val br = new BufferedReader(new InputStreamReader(stream))
    // Se imprime cada una de las lineas del archivo leído
    println()
    var line: String = null
    while ({line = br.readLine; line != null}) {
      var lines = line.split('|')
      var keyHash = hashString(lines(0))
      println(s"$keyHash | $line")
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
}
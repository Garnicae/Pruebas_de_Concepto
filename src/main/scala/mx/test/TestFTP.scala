import java.io.{BufferedReader, InputStreamReader}
import com.jcraft.jsch.{ChannelSftp, JSch}

object TestFTP  {
  def main(args: Array[String]): Unit = {

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
      println("Connected to server: " + host + " ...\n")

    val sftp = session.openChannel("sftp")
    sftp.connect()

    if(sftp.isConnected)
      println("Connected to sftp ...\n")
    else
      println("Not connected to sftp \n")

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
    val stream = sftp.asInstanceOf[ChannelSftp].get(folderFiles + "airports.text")
    val br = new BufferedReader(new InputStreamReader(stream))
    // Se imprime cada una de las lineas del archivo leído
    println()
    var line: String = null
    while ({line = br.readLine; line != null}) {
      //println(line)
    }

    br.close

    sftp.disconnect()
    session.disconnect()
  }
}
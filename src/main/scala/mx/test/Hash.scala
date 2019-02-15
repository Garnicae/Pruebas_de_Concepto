import java.security.MessageDigest
import java.math.BigInteger

object Hash {
  def main(args: Array[String]): Unit = {
    println("Scala, hash ...")

    println(hashString("hugo"))
    // 256 bits = 64 caracteres
    // 224, 256, 384, 512
    // MD-5 = 32 caracteres
    // SHA-1 = 40 caracteres
  }

  def hashString(s: String): String = {
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(s.getBytes)
    val bigInt = new BigInteger(1,digest)
    val hashedString = bigInt.toString(16)
    hashedString
  }
}

// d70b489bb97ec09dbafa72e461c8320d04e13ad160dd74c8e69a60ea62dc8ffd
// 478721f1106c2a631a90181bac7efc77767a3903eb9220687bff8a14e940fa7
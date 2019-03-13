
import Models.{DatosPersonales, Domicilio}
import com.google.gson.{Gson, JsonParser}

import scala.util.parsing.json.JSONObject

object Main {
  def main(args: Array[String]): Unit = {

    var direccion = Domicilio("Tecnologico", 401, "Tecnologico.", "Qro.")
    var persona2 = DatosPersonales("Monse", "Hernandez", 23, "0987654321", direccion)
    println(persona2)

    println(persona2.nombre)
    println(persona2.apellidos)
    println(persona2.edad)
    println(persona2.telefono)

    var s = "Monse,Hernandez,23,0987654321"
    var a = "Monse|Hernandez|23|0987654321"

    var datos = a.split('|')
    println(datos.length)

    println(datos(0))
    println(datos(1))
    println(datos.apply(2))
    println(datos.apply(3))

    var dir = DatosPersonales(datos(0), datos(1), datos(2).toInt, datos(3), direccion)
    println("---" +dir)


    println(persona2.direccion.calle)
    println(persona2.direccion.numero)
    println(persona2.direccion.colonia)
    println(persona2.direccion.ciudad)


    var gson = new Gson()
    var json = gson.toJson(persona2)
    println(json)


    var parser = new JsonParser()
    var parse = parser.parse(json).getAsJsonObject

    println(parse.get("nombre").getAsString)
    println(parse.get("apellidos"))
    println(parse.get("edad").getAsInt)
    println(parse.get("telefono"))

    var direcc = parse.get("direccion").getAsJsonObject


  }
}
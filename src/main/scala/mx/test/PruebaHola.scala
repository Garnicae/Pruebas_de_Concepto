import scala.collection.mutable
import scala.collection.immutable

object PruebaHola {
  def main(args: Array[String]): Unit = {
    println("Hola, scala")

    val mes = 1
    // Case en Java, En Scala se usa match
    mes match {
      case 1 => println("Enero")
      case 6 => println("Junio")
      case _ => println("Ninguno") //
    }
    println(if (mes == 6) "Correcto" else "Incorrecto")

    var k = 5
    println(s"El valor de k es: $k+1")
    println(s"El valor de k es: ${k+1}")

    println("")
    var frutas = Array("Pera", "Manzana", "Platano", "Naranja")
    println(frutas.apply(1))
    println(frutas(3))

    val fruta = Array("Naranja", 1, true, true)
    println(fruta.getClass)
    println(fruta.indexOf(true))
    println(fruta indexOf true)

    println("")
    // map: por c/entrada hay una salida
    val numeros = List(4, 8, 15, 16, 23, 42)
    numeros.map( numero => 2 * numero)
    println(numeros)

    val coches = List("BMW", "Mercedez", "Seat", "Toyota")
    println(coches.map( coche => coche.toUpperCase ))
    println(coches.map( _.toLowerCase)) // _ se refiere a c/u de los elentos de la lista


    val lista = List(List(2, 4, 6), List(1, 3, 5), List(7, 8, 9), List(4, 8, 3))
    println(lista.flatten) // Regresa los elementos de las sublistas y los junta en una sola
    println(lista.map( x => x.map( n => n *2)))
    println(lista.map( x => x.map( n => n *2)).flatten)
    println(lista.flatMap( (x: List[Int]) => x.map( n => n *2)))

    println(numeros.flatMap( x => List(x, x *2))) // En este ejemplo recibe una lista de listas y la salida es c/elemento de las listas en una sola
    println(coches.flatMap( coche => coche.toUpperCase()).distinct)

    println("")
    // Tipo de lista que no acepta valores duplicados
    val set = Set(1, 2, 2, 3, 3, 4)
    println(set)
    println(set(2))
    println(set contains 3)
    println(set + 5) // Agrega un elemento nuevo al set (No necesariamente estan ordenados)
    println(set ++ Set(4, 5, 6))
    println(set - 2) // Eliminar el elemento 2 del set
    println(set -- Set(1, 2)) // Elimina el conjunto, del conjunto original (diferencia de conjuntos)
    println(set intersect Set(1, 2)) // Intersección de conjuntos
    println(set diff Set(1, 2)) // Diferencia de conjuntos
    println(set & Set(1, 2)) // Intersección
    println(set | Set(1, 2)) // Unión
    println(set &~ Set(1, 2)) // Diferencia

    println("")
    // Colecciones mutables
    val mset = mutable.Set(1, 2, 3)
    mset += 4 // Inserta un nuevo elemento al set
    println(mset)
    mset ++= Set(4, 5) // Inserta un nuevo set
    println(mset)
    mset -= 3 // Eliminar un elemento del set
    println(mset)
    mset retain( x => x % 2 == 0) // Filtrar los elementos que cumplan con la condición
    println(mset)
    mset += 3
    mset filter( x => x % 2 == 0) // Filtra los elementos, pero no modifica el set original (filtra pero no lo hace mutable)
    println(mset)

    println("")
    // Colecciones inmutable
    val sset = immutable.SortedSet(1, 3, 2, 4, 6, 5)
    println(sset)

    val setOrder = Set(1, 2, 3, 4, 5, 6)
    val mayorAMenor = Ordering.fromLessThan[Int]( _ > _)
    val sortedSetInverso = immutable.SortedSet.empty(mayorAMenor) ++ setOrder
    println(sortedSetInverso)

    println("")
    // Mapas
    val mapa = Map(1 -> "Pepe", 2 -> "Elena", 3 -> "Miguel", 4 -> "Laura", 5 -> "Laura", 3 -> "Eduardo", 6 -> 3)
    println(mapa.keySet)
    println(mapa.values)
    println(mapa(2))
    println(mapa.get(9)) // Si no existe se trae el elemento 'None' de la clase Option[]
    println(mapa.getOrElse(9, "No existe"))

    val mapa1 = Map(1 -> "Pepe", 2 -> "Elena")
    println(mapa1)
    println(mapa1 + (3 -> "Miguel")) // Agregar nuevo elemento al map
    println(mapa1 - 2) // Quitar elemento del map

    val map1 = Map(1 -> "uno", 2 -> "dos")
    val map2 = Map(2 -> "dos", 3 -> "tres")
    println(map1 ++ map2)

    val mapaMutable = mutable.Map( 1-> "uno", 2 -> "dos")
    mapaMutable += ( 3 -> "tres", 4 -> "cuatro")
    println(mapaMutable)
    mapaMutable.put(5, "Cinco")
    println(mapaMutable)

    println("")
    // Forecah y For
    val hadoop = Seq("hdfs", "hive", "impala", "spark", "sqoop", "pig")
    val opiniones = Seq("Robusto", "Moderno", "Veloz", "Antiguo", "Aburrido")

    // Recorre los elementos sin arrojar una salida como el map
    hadoop.foreach( h => println(s"Tecnología: $h"))

    println("")
    // Tipo producto cartesiano, por cada h de hadoop hay un o opiniones
    for( h <- hadoop; o <- opiniones)
      println(s"Tecnología: $h es $o")

    println("")
    // Tipo producto cartesiano, por cada h de hadoop hay un o opiniones
    for( h <- hadoop if h.endsWith("a"); o <- opiniones if o.startsWith("A"))
      println(s"Tecnología: $h es $o")

    for( h <- hadoop.filter( f => f.endsWith("a")); o <- opiniones.filter( f => f.startsWith("A")))
      println(s"Tecnología: $h es $o")

    println("")
    hadoop.foreach( h => opiniones.foreach( o => println(s"Tecnología: $h es $o")))

  }

}
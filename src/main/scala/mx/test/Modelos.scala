
package object Models {

  case class DatosPersonales(
                   nombre: String,
                   apellidos: String,
                   edad: Int,
                   telefono: String,
                   direccion: Domicilio
                   )

  case class Domicilio(
                      calle: String,
                      numero: Int,
                      colonia: String,
                      ciudad: String
                      )
}
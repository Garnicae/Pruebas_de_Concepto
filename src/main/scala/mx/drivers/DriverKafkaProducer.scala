import java.util.Properties
import org.apache.kafka.clients.producer._

object ProducerExample {

  val host = "10.15.191.150"
  val portServer = "9092"
  val topic = "test"

  def main(args: Array[String]): Unit = {
    val  props = new Properties()
    props.put("bootstrap.servers", s"$host:$portServer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    for(i<- 1 to 50){
      val record = new ProducerRecord(topic, "key", s"hello $i")
      producer.send(record)
    }

    val record = new ProducerRecord(topic, "key", "the end "+new java.util.Date)
    producer.send(record)
    producer.close()
  }
}
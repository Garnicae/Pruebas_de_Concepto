import java.util
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._
import java.util.Properties

object ConsumerExample {

  def main(args: Array[String]): Unit = {
    val host = "10.15.191.136"
    val portServer = "9092"
    val topic = "test"

    val  props = new Properties()
    props.put("bootstrap.servers", s"$host:$portServer")

    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "something")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Collections.singletonList(topic))

    while(true){
      val records=consumer.poll(100)
      for (record<-records.asScala){
        println(record)
      }
    }
  }
}
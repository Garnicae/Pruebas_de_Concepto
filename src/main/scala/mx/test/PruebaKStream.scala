import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

import kafka.serializer.StringDecoder

object PruebaKStream{
  def main(args: Array[String]): Unit = {
    println("Hola, kStream")

    val sc = getSparkContext("streamApp")
    val batchDuration = Seconds(2)

    def streamingApp(sc: SparkContext, batchDuration: Duration) = {
      println("streamApp")

      val ssc = new StreamingContext(sc, batchDuration)

      val kafkaParam = Map(
        "zookeeper.connect" -> "10.15.191.136",
        "group.id" -> "cfdi",
        "auto.offset.reset" -> "largest"
      )

      val kStream = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](
        ssc, kafkaParam, Map("test" -> 1), StorageLevel.MEMORY_AND_DISK)
        .map(_._2)

      kStream.print(2)

      ssc
    }
    val ssc = getStreamingContext(streamingApp, sc, batchDuration)
    ssc.start()
    ssc.awaitTermination()
  }


  def getSparkContext(appName: String) = {

    var checkpointDirectory = ""
    val conf = new SparkConf().setAppName(appName).setMaster("local[1]")
    conf.set("spark.testing.memory", "2147480000") // if you have memory error

    val sc = SparkContext.getOrCreate(conf)
    //sc.setCheckpointDir(checkpointDirectory)
    sc
  }

  def getStreamingContext(streamingApp : (SparkContext, Duration) => StreamingContext,
                          sc: SparkContext, duration : Duration) = {
    val creatingFunc = () => streamingApp(sc, duration)
    val ssc = sc.getCheckpointDir match {
      case Some(checkpointDir) => StreamingContext.getActiveOrCreate(checkpointDir, creatingFunc, sc.hadoopConfiguration,
        createOnError = true)
      case None => StreamingContext.getActiveOrCreate(creatingFunc)
    }
    sc.getCheckpointDir.foreach( cp => ssc.checkpoint(cp))
    ssc
  }
}


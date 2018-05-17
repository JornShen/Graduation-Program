import org.apache.hadoop.conf.Configuration
import java.net.URI
import java.util

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.ml.feature.{StringIndexer, StringIndexerModel}
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.evaluation.{BinaryClassificationMetrics, MulticlassMetrics}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.mllib.linalg.{Vectors}
import org.apache.spark.mllib.tree.{DecisionTree, RandomForest}
import org.apache.spark.rdd.RDD

object AliAdClick {

  private val localFile: FileSystem = FileSystem.get(URI.create("file:///home/atmlinux/"),new Configuration())

  private val hdfsFile: FileSystem = FileSystem.get(URI.create("hdfs://localhost:9000/"), new Configuration())

  // 参数: 0: train type, 1: train road, 2: train type:
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("AliAdClick").master("local[2]").getOrCreate()

    // println("train road:" + args(0))
    // println("test road:" + args(1))

    if (args.length != 5) {
      sendMessage("param is not enough")
      System.exit(0)
    }

    if (!testFileExist(args(0).toString)) {
      sendMessage("train file road error")
      System.exit(0)
    }

    if (!testFileExist(args(1).toString)) {
      sendMessage("test file road error")
      System.exit(0)
    }

    val train_file_path: String = args(0)
    val test_file_path: String = args(1)
    val maxBin = args(2).toInt
    val treeDepth = args(3).toInt
    val impurity = args(4)

    // train_file_path = "hdfs://localhost:9000/aliclick/dataset/fin_train.csv"
    // test_file_path = "hdfs://localhost:9000/aliclick/dataset/fin_test.csv"

    // var train_file_path: String = arg(0)
    // var test_file_path: String = arg(1)

    val train_data = spark.sparkContext.textFile(train_file_path)
    val test_data = spark.sparkContext.textFile(test_file_path)

    //case class AliAdClick(userid: Double, adgroupid: Double,pid:String, clk: Double,gender: Double, agelevel: Double,pvaluelevel: Double, shoppinglevel: Double)
    val aliclick = train_data.map(_.split(",")).map(a => Row(a(0).trim.toDouble,
      a(1).trim.toDouble, a(2), a(3).trim.toDouble, a(4).trim.toDouble,
      a(5).trim.toDouble, a(6).trim.toDouble, a(7).trim.toDouble))

    val test_aliclick = test_data.map(_.split(",")).map(a => Row(a(0).trim.toDouble,
      a(1).trim.toDouble, a(2), a(3).trim.toDouble, a(4).trim.toDouble,
      a(5).trim.toDouble, a(6).trim.toDouble, a(7).trim.toDouble))

    val schema =
      StructType(
          StructField("userid", DoubleType, true) ::
          StructField("adgroupid", DoubleType, true) ::
          StructField("pid",StringType,true) ::
          StructField("clk", DoubleType, true) ::
          StructField("gender", DoubleType, true)::
          StructField("agelevel", DoubleType, true) ::
          StructField("pvaluelevel", DoubleType, true)::
          StructField("shoppinglevel", DoubleType, true)::Nil)

    //　转化为 DataFrame
    val trainData = spark.createDataFrame(aliclick, schema)
    val testData = spark.createDataFrame(test_aliclick, schema)

    val indexer = new StringIndexer().setInputCol("pid").setOutputCol("pidIndex")
    val indexModel = indexer.fit(trainData)

    val train = generateData(trainData, indexModel)
    val test = generateData(testData, indexModel)

    // 平衡数据集
   /* val posTrain = train.filter(p => p.label == 1)
    val negTrain = train.filter(p => p.label == 0)

    val newTrain = posTrain.union(negTrain.sample(false, posTrain.count().doubleValue() / negTrain.count(), 1234))*/

    /*for (i <- 0 to args.length - 1) {
       println(args(i))
    }

    val posTest = test.filter(p => p.label == 1)
    val negTest = test.filter(p => p.label == 0)
    val newTest = posTest.union(negTest.sample(false, ))

    // val result = svmTrain(newTrain, test, 100)
    //  val result = decisionTree(newTrain, test)


    val result1 = new Array[Double](100)
    for (i <- 1 to 100) {
      val result = randomForest(newTrain.cache(), test, i, 5, 32)
      result1(i-1) = multiclassMetrics(result)
    }

    for (i <- 2 to 100) {
      println(result1(i-1))
    }

    val result2 = new Array[Double](10)

    for (i <- 1 to 10) {
      val result = randomForest(newTrain.cache(), test, 10, i, 32)
      result2(i-1) = multiclassMetrics(result)
    }

    for (i <- 1 to 10) {
      println("TreeDepth " + i + ":" + result2(i-1))
    }

    // test decisiontree treeDepth
    val result3 = new Array[Double](10)

    for (i <- 1 to 10) {
      val result = decisionTree(newTrain.cache(), test, i, 32)
      result3(i-1) = multiclassMetrics(result)
    }

    // test DecisionTree maxBin
    val result4 = new Array[Double](50)

    for (i <- 2 to 50) {
      val result = decisionTree(newTrain.cache(), test, 4, i)
      result4(i-1) = multiclassMetrics(result)
    }

    for (i <- 1 to 10) {
      println("TreeDepth " + i + ":" + result3(i-1))
    }

    for (i <- 2 to 50) {
      println("maxBin " + i + ":" + result4(i-1))
    } */

    // binaryMetrics(result)
    val result = decisionTree(train, test, treeDepth, maxBin, impurity)
    val precision = multiclassMetrics(result)

    // 创建 kafka 生产者, 用于存放结果返回到 web 端
    sendMessage(precision.toString)
  }

  def sendMessage(msg: String): Unit = {
    val Array(brokers, topic) = Array("localhost:9092", "aliadclick")
    val props = new util.HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    // 创建一个生产者
    val producer = new KafkaProducer[String, String](props)
    val message = new ProducerRecord[String, String](topic, null, msg)
    producer.send(message)
  }

  def testFileExist(fileRoad: String): Boolean = {
      if (fileRoad.startsWith("file")) {
        return localFile.isFile(new Path(fileRoad))
      } else {
        return hdfsFile.isFile(new Path(fileRoad))
      }
  }

  def generateData(data: DataFrame, indexModel: StringIndexerModel): RDD[LabeledPoint] = {
    val indexer1 = indexModel.transform(data)
    val ignored = List("userid", "pid", "clk")
    val featInd = indexer1.columns.diff(ignored).map(indexer1.columns.indexOf(_))
    val targetInd = indexer1.columns.indexOf("clk")
    return  indexer1.rdd.map(r => LabeledPoint(r.getDouble(targetInd), Vectors.dense(featInd.map(r.getDouble(_)).toArray)))
  }

  def svmTrain(trainData: RDD[LabeledPoint], testData: RDD[LabeledPoint], num: Int): RDD[(Double, Double)] = {
    val model = SVMWithSGD.train(trainData.cache(), num)
    model.clearThreshold()
    val scoreAndLabels = testData.map(p => (model.predict(p.features), p.label))
    // print(scoreAndLabels.getClass)
    return scoreAndLabels
  }

  def binaryMetrics(evaluateDate: RDD[(Double, Double)]) = {
    //　二分类问题进行评估
    val metrics = new BinaryClassificationMetrics(evaluateDate)
    println("areaROC:" + metrics.areaUnderROC())
  }

  def multiclassMetrics(evaluateDate: RDD[(Double, Double)]): Double = {
    val metrics = new MulticlassMetrics(evaluateDate)
    println("accuracy: " + metrics.accuracy)
    println("weightedFMeasure:" + metrics.weightedFMeasure)
    return metrics.weightedFMeasure // F-measure 的加权平均值
  }

  def decisionTree(trainData: RDD[LabeledPoint], testData: RDD[LabeledPoint], maxDepth: Int, maxBins: Int, impurity: String): RDD[(Double, Double)] = {

    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]((1, 3), (2, 7), (3, 5), (4, 4), (5, 2))
    // val impurity = "gini"
    // val maxDepth = 4
    // val maxBins = 9
    println("maxDepth:" + maxDepth + " maxBins: " + maxBins + " impurity: " + impurity)
    val model = DecisionTree.trainClassifier(trainData, numClasses, categoricalFeaturesInfo,
      impurity, maxDepth, maxBins)
    val scoreAndLabels = testData.map(p => (model.predict(p.features), p.label))
    // println(model.toDebugString)
    return scoreAndLabels
  }

  def randomForest(trainData: RDD[LabeledPoint], testData: RDD[LabeledPoint], numTrees: Int, maxDepth: Int, maxBins: Int): RDD[(Double, Double)] = {

    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    // val numTrees = 10 // Use more in practice.
    // val maxDepth = 5
    // val maxBins = 32 // maxBins：连续特征离散化时选用的最大分桶个数，并且决定每个节点如何分裂
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"

    val model = RandomForest.trainClassifier(trainData, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)
    return testData.map(p => (model.predict(p.features), p.label))
  }

}

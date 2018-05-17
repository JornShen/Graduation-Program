import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SaveMode, SparkSession}

object PredealData {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("AliAdClick").master("local[2]").getOrCreate()
    val rawData = spark.sparkContext.textFile("file:///home/atmlinux/Documents/data/aliclick/raw_sample.csv")
    val userData = spark.sparkContext.textFile("file:///home/atmlinux/Documents/data/aliclick/user_profile.csv")

    // 去除首行
    /*val rawDataHeader = rawData.first()
    val userDataHeader = userData.first()
    rawData = rawData.filter(_ != rawDataHeader)
    userData = userData.filter(_ != userDataHeader)*/

    // user,time_stamp,adgroup_id,pid,nonclk,clk : raw_sample.csv
    val rawSample = rawData.map(_.split(",")).map(r => Row(r(0).trim.toLong, r(1).trim.toLong,
      r(2).trim.toLong, r(3).trim, r(5).trim.toLong))

    // userid,cms_segid,cms_group_id,final_gender_code,age_level,pvalue_level,shopping_level,occupation,new_user_class_level
    val userProfile = userData.map(_.split(",")).map(r => {
      if (r(5) == "") {
        Row(r(0).trim.toLong, r(3).trim.toLong, r(4).trim.toLong, 4.toLong, r(6).trim.toLong) // pvalue_level 4 表示未知
      } else {
        Row(r(0).trim.toLong, r(3).trim.toLong, r(4).trim.toLong, r(5).trim.toLong, r(6).trim.toLong)
      }
    })

    val rawSampleSchema = StructType(
        StructField("userId", LongType, true) ::
          StructField("time_stamp", LongType, true) ::
          StructField("adgroup_id", LongType,true) ::
          StructField("pid", StringType, true) ::
          StructField("clk", LongType, true) :: Nil)

    val userProfileSchema = StructType(
      StructField("user", LongType, true) ::
        StructField("gender", LongType, true) ::
        StructField("age_level", LongType, true)::
        StructField("pvalue_level", LongType, true) ::
        StructField("shopping_level", LongType, true) :: Nil)

    val rsData = spark.createDataFrame(rawSample, rawSampleSchema)
    val upData = spark.createDataFrame(userProfile, userProfileSchema)

    // join, 通过 userId 将二者进行连接
    val afterJoinData = rsData.join(upData, rsData("userId") === upData("user"))

    val trainData = afterJoinData.filter("time_stamp < 1494604800").select("userId", "adgroup_id","pid", "clk", "gender", "age_level", "pvalue_level", "shopping_level")
    val testData = afterJoinData.filter("time_stamp >= 1494604800").select("userId", "adgroup_id","pid", "clk", "gender", "age_level", "pvalue_level", "shopping_level")

    // 保存到本地文件,　此方法也没法保存到一个文件
    // testData.rdd.coalesce(1,true).saveAsTextFile("file:///home/atmlinux/Documents/data/aliclick/test_testData_use_spark.csv")


    //　写入文件
    // saveDF(testData, "file:///home/atmlinux/Documents/data/aliclick/testData_use_spark.csv")
    // saveDF(trainData.persist(StorageLevel.DISK_ONLY), "file:///home/atmlinux/Documents/data/aliclick/trainData_use_spark.csv")
    val trainDataNum = 500000 // 设置训练集合的大小　100 w
    val testDataNum = 250000 // 设置测试集合的大小  50 w

    val trainDataOptions =  Map("header" -> "false", "path" -> "file:///home/atmlinux/Documents/data/aliclick/trainData")
    val testDataOptions = Map("header" -> "false", "path" -> "file:///home/atmlinux/Documents/data/aliclick/testData")

    val posTrain = trainData.filter("clk = 1")
    val negTrain = trainData.filter("clk = 0")

    val posTrainCount = posTrain.count().doubleValue()
    val negTrainCount = negTrain.count().doubleValue()
    val testDataCount = testData.count().doubleValue()

    // testData.repartition(1).write.format("com.databricks.spark.csv").mode(SaveMode.Overwrite).options(testDataOptions).save()
    // trainData.repartition(1).write.format("com.databricks.spark.csv").mode(SaveMode.Overwrite).options(trainDataOptions).save()

    val unionTrainData = posTrain.sample(false, trainDataNum / posTrainCount, 1234).union(negTrain.sample(false, trainDataNum / negTrainCount, 1234))
    unionTrainData.repartition(1).write.format("com.databricks.spark.csv").mode(SaveMode.Overwrite).options(trainDataOptions).save()

    testData.sample(false, testDataNum / testDataCount, 1234).repartition(1).write.format("com.databricks.spark.csv")
      .mode(SaveMode.Overwrite).options(testDataOptions).save()

  }

  /*def saveDF(data: DataFrame, fileUrl: String) {

    val saveFile = FileSystem.get(new Configuration())
    var save: FSDataOutputStream = null
    /*if (saveFile.isFile(new Path(saveUrl))) {
      println("in save file")*/
    save = saveFile.create(new Path(fileUrl), true)
    /*  println(save.toString)
    }*/

    // var writer = new BufferedWriter(new PrintWriter(save))

    var writer = new PrintWriter(save)
    data.collect().foreach(r => {
      var str = r(0) + "," + r(1) + "," + r(2) + "," + r(3) + "," + r(4) + "," + r(5) + "," + r(6) + "," + r(7) + "\n"
      print(str)
      writer.print(str)
    })
    writer.close()
    save.close()
    saveFile.close()
  }*/


}

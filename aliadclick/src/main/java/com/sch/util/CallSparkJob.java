package com.sch.util;

import org.apache.spark.SparkConf;
import org.apache.spark.deploy.rest.CreateSubmissionResponse;
import org.apache.spark.deploy.rest.RestSubmissionClient;

import scala.collection.immutable.HashMap;



public class CallSparkJob {


    public static boolean sendSparkJob(String trainRoad, String testRoad,
                                       String maxBin, String treeDepth, String impurity) {


        String jarLocation = "file:///home/atmlinux/IdeaProjects/project2/out/artifacts/project2_jar/project2.jar";
        String mainClass = "AliAdClick";
        String[] arg = new String[]{trainRoad, testRoad, maxBin, treeDepth, impurity};
        SparkConf sparkConf = new SparkConf();
            sparkConf.setMaster("spark://localhost:6066")
                .setAppName("carabon" + " " + System.currentTimeMillis())
                .set("spark.executor.cores", "4")
                .set("spark.submit.deployMode", "cluster")
                .set("spark.jars", jarLocation)
                .set("spark.executor.memory", "1g")
                .set("spark.cores.max", "4").set("spark.executor.extraJavaOptions", "-XX:MaxPermSize=1000m")
                // 添加外部的 jar 包
                .set("spark.driver.extraClassPath","file:///usr/local/spark/jars/*:file:///usr/local/spark/jars/kafka/*")
                .set("spark.driver.supervise", "false");

        CreateSubmissionResponse response = null;
        System.getenv();
        try {
            response = (CreateSubmissionResponse) RestSubmissionClient.run(jarLocation, mainClass, arg, sparkConf,
                    new HashMap());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response == null) return false;
        }

        // 返回是否成功
        System.out.println(response.toJson());
        return response.success();
    }



}

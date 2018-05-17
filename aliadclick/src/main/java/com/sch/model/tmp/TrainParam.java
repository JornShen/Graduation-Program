package com.sch.model.tmp;

public class TrainParam {

    private String trainRoad;

    private String testRoad;

    private String impurity;

    private int maxBin;

    private int maxDepth;

    public String getTrainRoad() {
        return trainRoad;
    }

    public void setTrainRoad(String trainRoad) {
        this.trainRoad = trainRoad;
    }

    public String getTestRoad() {
        return testRoad;
    }

    public void setTestRoad(String testRoad) {
        this.testRoad = testRoad;
    }

    public String getImpurity() {
        return impurity;
    }

    public void setImpurity(String impurity) {
        this.impurity = impurity;
    }

    public int getMaxBin() {
        return maxBin;
    }

    public void setMaxBin(int maxBin) {
        this.maxBin = maxBin;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public String toString() {
        return "TrainParam{" +
                "trainRoad='" + trainRoad + '\'' +
                ", testRoad='" + testRoad + '\'' +
                ", impurity='" + impurity + '\'' +
                ", maxBin=" + maxBin +
                ", maxDepth=" + maxDepth +
                '}';
    }
}

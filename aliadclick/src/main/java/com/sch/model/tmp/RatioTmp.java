package com.sch.model.tmp;

public class RatioTmp {

    // 特征 id
    private int futureId;

    // 特征的数量
    private int num;

    public RatioTmp(int futureId, int num) {
        this.futureId = futureId;
        this.num = num;
    }


    public RatioTmp() {
    }

    public int getFutureId() {
        return futureId;
    }

    public void setFutureId(int futureId) {
        this.futureId = futureId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatioTmp ratioTmp = (RatioTmp) o;

        if (futureId != ratioTmp.futureId) return false;
        return num == ratioTmp.num;
    }

    @Override
    public int hashCode() {
        int result = futureId;
        result = 31 * result + num;
        return result;
    }
}

package com.sch.model.tmp;

public class AdRankTmp {

    private int adgroupId;

    private int num;

    public AdRankTmp() {
    }

    public AdRankTmp(int adgroupId, int num) {
        this.adgroupId = adgroupId;
        this.num = num;
    }

    public int getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(int adgroupId) {
        this.adgroupId = adgroupId;
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

        AdRankTmp adRankTmp = (AdRankTmp) o;

        if (adgroupId != adRankTmp.adgroupId) return false;
        return num == adRankTmp.num;
    }

    @Override
    public int hashCode() {
        int result = adgroupId;
        result = 31 * result + num;
        return result;
    }
}

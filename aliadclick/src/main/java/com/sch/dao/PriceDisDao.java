package com.sch.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PriceDisDao {

    List<Integer> getPriceDis(@Param("feature")int feature);

}

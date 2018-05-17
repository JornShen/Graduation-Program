package com.sch.dao;

import com.sch.model.tmp.AdRankTmp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdRankDao {

    /**
     *
     * 根据性别查找排名广告
     *
     * @param id
     * @return
     */
    List<AdRankTmp> getGenderAdRank(@Param("gender")int id);


    /**
     *
     * 根据年龄层次来查找排名广告
     *
     * @param id
     * @return
     */
    List<AdRankTmp> getAgeLevelAdRank(@Param("agelevel")int id);


    /**
     * 根据消费档次查找排名广告
     * @param id
     * @return
     */
    List<AdRankTmp> getpvalueLevelAdRank(@Param("pvalue")int id);


    /**
     * 根据购物深度来查找排名广告
     * @param id
     * @return
     */
    List<AdRankTmp> getShoppingLevelAdRank(@Param("shopping")int id);
}

package com.sch.dao;

import com.sch.model.tmp.RatioTmp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClkRatioDao {

    /**
     * 查找所有男女人数
     * @return
     */
    List<RatioTmp> selectGenderRatioById(@Param("id")int id);

    /**
     * 查找所有男女人数
     * @return
     */
    List<RatioTmp> selectAgeLevelRatioById(@Param("id")int id);

    /**
     * 查找所有男女人数
     * @return
     */
    List<RatioTmp> selectPvalueLevelRatioById(@Param("id")int id);

    /**
     * 查找所有男女人数
     * @return
     */
    List<RatioTmp> selectShoppingLevelRatioById(@Param("id")int id);
}

package com.sch.service;

import com.sch.model.tmp.RatioTmp;

import java.util.List;
import java.util.Map;

public interface RatioSearchService {

    /**
     *  查找所有的比例, 当 adid 为 -1 的时候
     * @return
     */
    public Map<String, List<RatioTmp>> selectAllRatio(int id);


}

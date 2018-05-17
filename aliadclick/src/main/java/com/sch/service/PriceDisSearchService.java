package com.sch.service;

import java.util.List;
import java.util.Map;

public interface PriceDisSearchService {

    /**
     * 根据 feature 来进行
     * @param feature
     * @return
     */
    Map<String, List<Integer>> getPriceDisByFeature(String feature);

}

package com.sch.service;

import com.sch.model.tmp.AdRankTmp;

import java.util.List;
import java.util.Map;

public interface AdRankSearchService {

    Map<String, List<AdRankTmp>> getAdRank(int levelId, String table);

}

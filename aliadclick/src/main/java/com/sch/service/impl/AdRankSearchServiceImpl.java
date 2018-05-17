package com.sch.service.impl;

import com.sch.dao.AdRankDao;
import com.sch.model.tmp.AdRankTmp;
import com.sch.service.AdRankSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("adRankSearchService")
@Transactional
public class AdRankSearchServiceImpl implements AdRankSearchService {

    @Autowired
    private AdRankDao adRankDao;

    @Override
    public Map<String, List<AdRankTmp>> getAdRank(int levelId, String table) {
        Map<String, List<AdRankTmp>> result = new HashMap<>();
        List<AdRankTmp> ranks;
        switch (table) {
            case "gender": ranks = adRankDao.getGenderAdRank(levelId); break;
            case "agelevel": ranks = adRankDao.getAgeLevelAdRank(levelId); break;
            case "pvaluelevel":  ranks = adRankDao.getpvalueLevelAdRank(levelId);break;
            case "shoppinglevel": ranks = adRankDao.getShoppingLevelAdRank(levelId); break;
            default: ranks = null;
        }
        result.put("rankData", ranks);
        return result;
    }
}

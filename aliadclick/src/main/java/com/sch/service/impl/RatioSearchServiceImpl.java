package com.sch.service.impl;

import com.sch.dao.ClkRatioDao;
import com.sch.model.tmp.RatioTmp;
import com.sch.service.RatioSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ratioSearchService")
@Transactional
public class RatioSearchServiceImpl implements RatioSearchService {

    @Autowired
    private ClkRatioDao ClkRatioDao;


    @Override
    public Map<String, List<RatioTmp>> selectAllRatio(int id) {
        Map<String, List<RatioTmp>> result = new HashMap<>();

        List<RatioTmp> gender = ClkRatioDao.selectGenderRatioById(id);
        if (!gender.isEmpty()) result.put("gender", gender);

        List<RatioTmp> agelevel = ClkRatioDao.selectAgeLevelRatioById(id);
        if (!agelevel.isEmpty()) result.put("agelevel", agelevel);

        List<RatioTmp> pvaluelevel = ClkRatioDao.selectPvalueLevelRatioById(id);
        if (!pvaluelevel.isEmpty()) result.put("pvaluelevel", pvaluelevel);

        List<RatioTmp> shoppinglevel = ClkRatioDao.selectShoppingLevelRatioById(id);
        if (!shoppinglevel.isEmpty()) result.put("shoppinglevel", shoppinglevel);

        if (result.size() == 0) result.put("ERROR", new ArrayList<>()); //标记没有此故

        return result;
    }
}

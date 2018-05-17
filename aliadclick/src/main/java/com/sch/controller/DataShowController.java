package com.sch.controller;

import com.sch.model.tmp.AdRankTmp;
import com.sch.model.tmp.RatioTmp;
import com.sch.service.AdRankSearchService;
import com.sch.service.PriceDisSearchService;
import com.sch.service.RatioSearchService;
import com.sch.util.HiveJdbcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/datashow")
public class DataShowController {

    @Autowired
    RatioSearchService ratioSearchService;

    @Autowired
    AdRankSearchService adRankSearchService;

    @Autowired
    PriceDisSearchService priceDisSearchService;

    @Autowired
    HiveJdbcClient hiveJdbcClient;


    /**
     *　根据广告 id 获取各个特征的人数
     * @param adId
     * @return
     */
    @RequestMapping(value = "/getratiobyadid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<RatioTmp>> getRatioByAdId(@RequestParam("adid")int adId) {
        System.out.println("-------- in getratiobyadid --------");
        return ratioSearchService.selectAllRatio(adId);
    }

    /**
     * 根据特征名,　特征值获取广告排名
     * @param levelId  特征的 id
     * @param level 特征: gender, agelevel, pvaluelevel, shoppinglevel
     * @return
     */
    @RequestMapping(value = "/getAdRank", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<AdRankTmp>> getAdRank(@RequestParam("levelId")int levelId, @RequestParam("level")String level) {
        System.out.println("-------- in adrank --------");
        return adRankSearchService.getAdRank(levelId, level);
    }

    /**
     *　根据 feature 获取价格分布
     * @param feature
     * @return
     */
    @RequestMapping(value = "/getPriceDis", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Integer>> getPriceDis(@RequestParam("feature")String feature) {
        System.out.println("-------- in pricedistribution --------");
        return priceDisSearchService.getPriceDisByFeature(feature);
    }


    @RequestMapping(value = "/getGenderPeopleOfShoppinglevel", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Integer>> getGenderPeopleOfShoppinglevel() {
        System.out.println("-------- in GenderPeopleOfShoppinglevel --------");
        Map<String, List<Integer>> res = null;
        try {
             res = hiveJdbcClient.getGenderPeopleGroupByShoppingLevel();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 执行错误
        if (res == null) {
             res = new HashMap<>();
             res.put("ERROR", new ArrayList<>());
        }

        return res;
    }


}

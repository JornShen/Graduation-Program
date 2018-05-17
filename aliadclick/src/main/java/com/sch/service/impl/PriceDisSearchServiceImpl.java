package com.sch.service.impl;

import com.sch.dao.PriceDisDao;
import com.sch.service.PriceDisSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("piceDisSearchService")
@Transactional
public class PriceDisSearchServiceImpl implements PriceDisSearchService {

    @Autowired
    private PriceDisDao priceDisDao;

    @Override
    public Map<String, List<Integer>> getPriceDisByFeature(String feature) {

        Map<String, List<Integer>> result = new HashMap<>();

        switch (feature) {
            case "allpricedis":{

                List<Integer> allPriceDis = priceDisDao.getPriceDis(0);
                result.put("allpricedisData", allPriceDis);
                break;
            }
            case "genderpricedis":{

                List<Integer> male = priceDisDao.getPriceDis(1);
                List<Integer> female = priceDisDao.getPriceDis(2);
                result.put("maleData", male);
                result.put("femaleData", female);
                break;
            }
            case "pvaluepricedis":{

                List<Integer> level1 = priceDisDao.getPriceDis(21);
                List<Integer> level2 = priceDisDao.getPriceDis(22);
                List<Integer> level3 = priceDisDao.getPriceDis(23);
                result.put("level1", level1);
                result.put("level2", level2);
                result.put("level3", level3);
                break;
            }
            case "shoppingpricedis":{
                List<Integer> level1 = priceDisDao.getPriceDis(31);
                List<Integer> level2 = priceDisDao.getPriceDis(32);
                List<Integer> level3 = priceDisDao.getPriceDis(33);
                result.put("level1", level1);
                result.put("level2", level2);
                result.put("level3", level3);
                break;
            }
            default:{
                 result.put("ERROR", new ArrayList<>());
            }
        }

        return result;
    }
}

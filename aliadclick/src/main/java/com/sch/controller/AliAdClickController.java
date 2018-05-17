package com.sch.controller;

import com.sch.model.tmp.TrainParam;
import com.sch.util.CallSparkJob;
import com.sch.util.kafka.KafkaConsume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class AliAdClickController {

    @Autowired
    private KafkaConsume kafkaConsume; // kafka 客户端

    /**
     * 重定向 url "/"
     * @param model
     * @return
     */
    @RequestMapping(value = "/")
    public String toIndex(@ModelAttribute("model")ModelMap model) {
        return "redirect:/toshow";
    }


    /**
     * 进入大数据展示的首页(模型预测)
     * @param model
     * @return
     */
    @RequestMapping(value = "/toshow")
    public String toShow(@ModelAttribute("model")ModelMap model) {
        return "show";
    }

    /**
     * 进入广告点击特征统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toshow/toadclkcount")
    public String toAdCountGender(@ModelAttribute("model")ModelMap model) {
        return "adclkcount";
    }

    /**
     * 进入特征广告排名统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toshow/toadrank")
    public String toAdRank(@ModelAttribute("model")ModelMap model) {
        return "adrank";
    }

    /**
     * 进入点击广告价格分布页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toshow/toclkpricedis")
    public String genderPriceDis(@ModelAttribute("model")ModelMap model) {
        return "clkpricedis";
    }


    /**　
     * 进入性别-价格分布页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toshow/togenderpricedis")
    public String togenderpricedis(@ModelAttribute("model")ModelMap model) {
        return "genderpricedis";
    }


    /**
     * 进入消费档次-价格分布页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toshow/topvaluelevelpricedis")
    public String topvaluelevelpricedis(@ModelAttribute("model")ModelMap model) {
        return "pvaluepricedis";
    }

    /**
     * 进入购物深度价格分布页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toshow/toshoppinglevelpricedis")
    public String toshoppinglevelpricedis(@ModelAttribute("model")ModelMap model) {
        return "shoppingpricedis";
    }


    /**
     * 进入购物深度性别统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/toshow/togenderofshop")
    public String genderOfShop(@ModelAttribute("model")ModelMap model) {
        return "genderofshop";
    }


    /**
     * 传参到后台, 调用 jar 包得到统计的结果
     * @param trainParam
     * @return
     */
    @RequestMapping(value = "/trainDataAndPredict", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> trainDataAndPredict(@RequestBody TrainParam trainParam){
        //　只能用对象跟 json 进行匹配
        // CallSparkJob.sendSparkJob();
        System.out.println(trainParam.toString());
        String result;
        if (CallSparkJob.sendSparkJob(trainParam.getTrainRoad(), trainParam.getTestRoad(),
                trainParam.getMaxBin()+"", trainParam.getMaxDepth()+"", trainParam.getImpurity())) {
            result = kafkaConsume.consume();
        } else {
            result = "提交任务出错, 请检查 Spark!";
        }
        System.out.println(result);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", result);
        return map;
    }

}

var webRoot = getRootPath();

var genderRankConf = null,ageLevelRankConf = null, pvalueLevelRankConf = null, shoppingLevelRankConf = null;
var adRankUrl = "/datashow/getAdRank";

$(function() {

    var genderRankChart = document.getElementById("gender").getContext('2d');
    var agelevelRankChart = document.getElementById("agelevel").getContext('2d');
    var pvaluelevelRankChart = document.getElementById("pvaluelevel").getContext('2d');
    var shoppinglevelRankChart = document.getElementById("shoppinglevel").getContext('2d');

    var opt = {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    };

    genderRankConf = {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: '点击量',
                data: [],
                backgroundColor: 'rgb(255, 99, 132)',
                borderColor: 'rgb(255, 99, 132)',
                borderWidth: 1
            }]
        },
        options: opt
    };

    ageLevelRankConf = {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: '点击量',
                data: [],
                backgroundColor: 'rgb(54, 162, 235)',
                borderColor: 'rgb(54, 162, 235)',
                borderWidth: 1
            }]
        },
        options: opt
    };

    pvalueLevelRankConf = {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: '点击量',
                data: [],
                backgroundColor: 'rgb(153, 102, 255)',
                borderColor: 'rgb(153, 102, 255)',
                borderWidth: 1
            }]
        },
        options: opt
    };

    shoppingLevelRankConf = {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: '点击量',
                data: [],
                backgroundColor: 'rgb(255, 159, 64)',
                borderColor: 'rgb(255, 159, 64)',
                borderWidth: 1
            }]
        },
        options: opt
    };

    window.genderrankpie = new Chart(genderRankChart, genderRankConf);
    window.agelevelrankPie = new Chart(agelevelRankChart, ageLevelRankConf);
    window.pvaluelevelrankPie = new Chart(pvaluelevelRankChart, pvalueLevelRankConf);
    window.shoppinglevelrankPie = new Chart(shoppinglevelRankChart, shoppingLevelRankConf);

    // 设置按钮选择监听事件
    $(".gender_menu_width > li").on('click', function () {
        $("#genderShow").html($(this).text()+"<span class=\"caret\"></span>");
        var genderLabel = $(this).text().trim();
        var gender = 1;
        if (genderLabel == "女") gender = 2;
        showAdRank(adRankUrl+"?level=gender&levelId="+gender, genderLabel+"性点击量", genderRankConf, window.genderrankpie);
    });

    $(".agelevel_menu_width > li").on('click', function () {
        $("#ageLevelShow").html($(this).text()+"<span class=\"caret\"></span>");
        var agelevelLabel = $(this).text().trim();
        showAdRank(adRankUrl+"?level=agelevel&levelId="+agelevelLabel, "level "+agelevelLabel+" 点击量", ageLevelRankConf, window.agelevelrankPie);
    });

    $(".pvalue_menu_width > li").on('click', function () {
        $("#pvalueShow").html($(this).text()+"<span class=\"caret\"></span>");
        var pvaluelevelLabel = $(this).text().trim();
        var pvalue = 1;
        if (pvaluelevelLabel == "中档用户")　pvalue = 2;
        else if (pvaluelevelLabel == "高档用户") pvalue = 3;
        showAdRank(adRankUrl+"?level=pvaluelevel&levelId="+pvalue, pvaluelevelLabel+"点击量", pvalueLevelRankConf, window.pvaluelevelrankPie);
    });

    $(".shopping_menu_width > li").on('click', function () {
        $("#shoppingShow").html($(this).text()+"<span class=\"caret\"></span>");
        var shoppinglevelLabel = $(this).text().trim();
        var shoppinglevel = 1;
        if (shoppinglevelLabel == "中度用户") shoppinglevel = 2;
        else if (shoppinglevelLabel == "深度用户") shoppinglevel = 3;
        showAdRank(adRankUrl+"?level=shoppinglevel&levelId="+shoppinglevel, shoppinglevelLabel+"用户点击量", shoppingLevelRankConf, window.shoppinglevelrankPie);
    });

    //　首次执行
    showAdRank(adRankUrl+"?level=gender&levelId=1", "男性点击量", genderRankConf, window.genderrankpie);
    showAdRank(adRankUrl+"?level=agelevel&levelId=0", "level 0 点击量", ageLevelRankConf, window.agelevelrankPie);
    showAdRank(adRankUrl+"?level=pvaluelevel&levelId=1", "低档用户点击量", pvalueLevelRankConf, window.pvaluelevelrankPie);
    showAdRank(adRankUrl+"?level=shoppinglevel&levelId=1", "浅层用户点击量", shoppingLevelRankConf, window.shoppinglevelrankPie);

});

function showAdRank(url, rankLabel, rankConf, rankPie) {

    $.ajax({
        type: 'get',
        url: webRoot + url,
        success:function (result) {
            console.log(result);
            var adRankData = getRankData(result.rankData);
            rankConf.data.labels = adRankData.labels;
            rankConf.data.datasets[0].data = adRankData.data;
            rankConf.data.datasets[0].label = rankLabel;
            rankPie.update();
        },
        error:function () {
            alert("服务器出错了");
        }
    });

}

function getRankData(dataGroup) {

    var data = [];
    var labels = [];

    for (var i = 0; i < dataGroup.length; i++) {
        labels.push(dataGroup[i].adgroupId);
        data.push(parseInt(dataGroup[i].num));
    }

    return {
        'data': data,
        'labels' : labels
    }

}

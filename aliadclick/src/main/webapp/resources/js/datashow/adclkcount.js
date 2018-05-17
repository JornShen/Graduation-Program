var webRoot = getRootPath();

var genderconf = null,agelevelconf = null, pvaluelevelconf = null, shoppinglevelconf = null;

/*
    red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(201, 203, 207)'
*
* */

$(function() {

    var genderChart = document.getElementById("gender").getContext('2d');
    var agelevelChart = document.getElementById("agelevel").getContext('2d');
    var pvaluelevelChart = document.getElementById("pvaluelevel").getContext('2d');
    var shoppinglevelChart = document.getElementById("shoppinglevel").getContext('2d');

    // 创建饼图的配合对象
    genderconf = {
        type: 'pie',
        data: {
            datasets: [{
                data: [],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)'
                ],
                label: 'Dataset 1'
            }],
            labels: [
                'man',
                'women'
            ]
        },
        options: {
            title: {
                display: true,
                text: '男女比例'
            },
            pieceLabel:{
                render:'percentage',
                precision: 2,
                fontColor:'black',
                position: 'outside',
            }
        }
    };

    agelevelconf = {
        type: 'pie',
        data: {
            datasets: [{
                data: [],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 159, 64)',
                    'rgb(75, 192, 192)',
                    'rgb(153, 102, 255)',
                    'rgb(201, 203, 207)',
                    'rgb(255, 205, 86)'
                ],
                label: 'Dataset 2'
            }],
            labels: [
                'level 0',
                'level 1',
                'level 2',
                'level 3',
                'level 4',
                'level 5',
                'level 6'
            ]
        },
        options: {
            title: {
                display: true,
                text: '年龄层次比例'
            },
            pieceLabel:{
                render:'percentage',
                precision: 2,
                fontColor:'black',
                position: 'outside',
            }
        }
    };

    pvaluelevelconf = {
        type: 'pie',
        data: {
            datasets: [{
                data: [],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 159, 64)',
                    'rgb(153, 102, 255)'
                ],
                label: 'Dataset 3'
            }],
            labels: [
                'unknown',
                '1:低档',
                '2:中档',
                '3:高档'
            ]
        },
        options: {
            title: {
                display: true,
                text: '消费档次比例'
            },
            pieceLabel:{
                render:'percentage',
                precision: 2,
                fontColor:'black',
                position: 'outside',
            }
        }
    };

    shoppinglevelconf = {
        type: 'pie',
        data: {
            datasets: [{
                data: [],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 159, 64)'
                ],
                label: 'Dataset 3'
            }],
            labels: [
                '1:浅层用户',
                '2:中度用户',
                '3:深度用户'
            ]
        },
        options: {
            title: {
                display: true,
                text: '购物深度比例'
            },
            pieceLabel:{
                render:'percentage',
                precision: 2,
                fontColor:'black',
                position: 'outside',
            }
        }
    };　

    // 创建 pie chart
    window.genderpie = new Chart(genderChart, genderconf);
    window.agelevelPie = new Chart(agelevelChart, agelevelconf);
    window.pvaluelevelPie = new Chart(pvaluelevelChart, pvaluelevelconf);
    window.shoppinglevelPie = new Chart(shoppinglevelChart, shoppinglevelconf);

    $("#querybtn").click(queryChartDataByAdId);
    queryChartDataByAdId(); // 开头执行一次
});


function queryChartDataByAdId() {

    var adId = $("#adId").val().trim();
    if (adId == "") {
        adId = -1; //统计所有的比例
    }

    // ajax 传输数据
    $.ajax({
        type: 'get',
        url: webRoot + "/datashow/getratiobyadid?adid=" + adId,
        success:function (result) {
            console.log(result);
            if (result.hasOwnProperty('ERROR')) {
                $("#msgerror").html("不存在该广告 id");
                setTimeout(function(){
                    $("#msgerror").html('');
                },3000);//3秒之后恢复
                return;
            }

            genderconf.data.datasets[0].data = getChartData(result.gender, 2, false);
            agelevelconf.data.datasets[0].data = getChartData(result.agelevel, 6, true);
            pvaluelevelconf.data.datasets[0].data = getChartData(result.pvaluelevel, 3, true);
            shoppinglevelconf.data.datasets[0].data = getChartData(result.shoppinglevel, 3, false);

            window.genderpie.update();
            window.agelevelPie.update();
            window.pvaluelevelPie.update();
            window.shoppinglevelPie.update();

        },
        error:function () {
            alert("服务器出错了");
        }
    });
    
}

function getChartData(priData, length, hasZero) {
    var result = null;
    if (hasZero) result = new Array(length+1).fill(0);
    else result = new Array(length).fill(0);
    if (priData == null) return result;
    for (var i = 0; i < priData.length; i++) {
        if (hasZero) result[priData[i].futureId] = parseInt(priData[i].num);
        else result[priData[i].futureId - 1] = parseInt(priData[i].num);
    }
    return result;
}

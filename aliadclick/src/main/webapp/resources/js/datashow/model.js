var webRoot = getRootPath();

$(function() {

    $("#commitbtn").click(commitClick);

    $("#train_loc_choose").on("click", "li", function(){
        $("#train_location").text($(this).text().trim());
    });

    $("#test_loc_choose").on("click", "li", function(){
        $("#test_location").text($(this).text().trim());
    });

    // 设置验证
    $("#testModel").bootstrapValidator({
        message: '输入值不合法',
        fields:{
            train_road:{
                validators:{
                    notEmpty: {message: '训练地址不能为空'}
                }
            },
            test_road:{
                validators:{
                    notEmpty: {message: '测试地址不能为空'}
                }
            },

            maxBin: {
                validators:{
                    notEmpty: {message: '划分数不能为空'},
                    callback: {
                        message: '不能小于1',
                        callback: function(value, validator) {
                            if (parseInt(value) < 2) return false;
                            return true;
                        }
                    }
                }
            },

            maxDepth:{
                validators:{
                    notEmpty: {message: '最大深度不能为空'},
                    callback: {
                        message: '不能小于0',
                        callback: function(value, validator) {
                            if (parseInt(value) < 0) return false;
                            return true;
                        }
                    }
                }
            }
        }
    });
});


function commitClick() {

    if (!$("#testModel").data('bootstrapValidator').isValid()){
        alert("请正确输入数据");
        return;
    }

    // 获取数据窗口的值
    var train_road = $("#train_road").val().trim();
    var test_road = $("#test_road").val().trim();
    var impurity = $("#impurity").find("option:selected").text();
    var maxBin = $("#maxBin").val().trim();
    var maxDepth = $("#maxDepth").val().trim();
    var train_loc = $("#train_location").text().trim();
    var test_loc = $("#test_location").text().trim();
    var trainRoadTmp = train_road;
    var testRoadTmp = test_road;
    // to test
    // console.log(train_road + "," + test_road + "," + impurity + "," + maxBin + "," + maxDepth);

    // ajax 传送数据到后台
    //　数据处理和准备
    if (train_loc == "local") train_road = "file://" + train_road;
    else train_road = "hdfs://localhost:9000" + train_road;

    if (test_loc == "local") test_road = "file://" + test_road;
    else test_road = "hdfs://localhost:9000" + test_road;

    console.log(train_road + "," + test_road + "," + impurity + "," + maxBin + "," + maxDepth);
    console.log(webRoot);
    var rawData = new Object();
    rawData.trainRoad = train_road;
    rawData.testRoad = test_road;
    rawData.impurity = impurity;
    rawData.maxBin = maxBin;
    rawData.maxDepth = maxDepth;
    $.blockUI({
        message: "正在计算,请等待",
        css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: .5,
            color: '#fff'
        },
        overlayCSS:  {
            backgroundColor: '#000',
            opacity:         0.1,
            cursor:          'wait'
        }
    });

    $.ajax({
        type: 'post',
        url: webRoot + "/trainDataAndPredict",
        dataType: 'json',
        contentType : 'application/json',
        data: /*{
            "trainRoad" : train_road,
            "testRoad" : test_road,
            "impurity" : impurity,
            "maxBin" : maxBin,
            "maxDepth" : maxDepth
        }*/
        JSON.stringify(rawData),
        success:function (result) {
            if (!checkNumber(result.msg)) {
                $("#result").text(result.msg);
            } else {
                $("#result").text("F-Measure:" + result.msg);
                rawData.trainRoad = trainRoadTmp;
                rawData.testRoad = testRoadTmp;
                rawData.prediction = result.msg;
                addTableRow(rawData);
            }

            $.unblockUI();
            $("#myModal").modal('show')
        },
        error:function(XMLHttpRequest, error, errorThrown){
            $.unblockUI();
            console.log(error);
            console.log(errorThrown);
            alert("服务器出错");
        },
    });
};

/**
 * 往历史表格中添加元素
 * @param data
 */
function addTableRow(data) {

    var appendHtml = "<tr>\n" +
        "<td>"+data.trainRoad+"</td>\n" +
        "<td>"+data.testRoad+"</td>\n" +
        "<td>"+data.impurity+"</td>\n" +
        "<td>"+data.maxBin+"</td>\n" +
        "<td>"+data.maxDepth+"</td>\n" +
        "<td>"+data.prediction+"</td>\n" +
        "</tr>";
    $("#hisTableBody").append(appendHtml);
}

/**
 * 判断字符串是否是数字
 * @param data
 * @returns {boolean}
 */
function checkNumber(data){
    var reg = /^[0-9]+.?[0-9]*$/;
    if (reg.test(data)) {
        return true;
    }
    return false;
}



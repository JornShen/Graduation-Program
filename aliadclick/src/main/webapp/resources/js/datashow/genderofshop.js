var webRoot = getRootPath();

var genderOfShopConf = null;

$(function() {

    var genderOfShopChart = document.getElementById("genderofshop").getContext('2d');

    var opt = {
        responsive: true,
        title: {
            display: true,
            text: 'shopping level . gender'
        },
        scales: {
            xAxes: [{
                scaleLabel: {
                    display: true,
                    labelString: '购物档次'
                }
            }],
            yAxes: [{
                scaleLabel: {
                    display: true,
                    labelString: '人数'
                },
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    };


    genderOfShopConf = {
        type: 'bar',
        data: {
            labels:['浅层用户', '中度用户', '深层用户'],
            datasets: [{
                label: '男性',
                backgroundColor: 'rgb(54, 162, 235)',
                borderColor: 'rgb(54, 162, 235)',
                data:[],
                borderWidth: 1
            },{
                label: '女性',
                backgroundColor: 'rgb(255, 99, 132)',
                borderColor: 'rgb(255, 99, 132)',
                data:[],
                borderWidth: 1
            }]
        },
        options: opt
    };

    window.genderofshop = new Chart(genderOfShopChart, genderOfShopConf);
    getGenderOfShopData();

});


function getGenderOfShopData() {

    $.ajax({
        type: 'get',
        url: webRoot + "/datashow/getGenderPeopleOfShoppinglevel",
        success:function (result) {
            console.log(result);
            if (result.hasOwnProperty("ERROR")) {
                alert("无法获得数据");
                return;
            }

            genderOfShopConf.data.datasets[0].data = result.male;
            genderOfShopConf.data.datasets[1].data = result.female;
            window.genderofshop.update();

        },
        error:function () {
            alert("服务器出错了");
        }
    });


}


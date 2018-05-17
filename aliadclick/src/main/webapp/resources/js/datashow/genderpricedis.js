var webRoot = getRootPath();

var genderPriceDisConf = null;

$(function() {

    var genderPriceChart = document.getElementById("genderpricedis").getContext('2d');

    var opt = {
        responsive: true,
        title: {
            display: true,
            text: 'Gender . Price distribution'
        },
        tooltips: {
            mode: 'index',
        },
        hover: {
            mode: 'index',
        },
        scales: {
            xAxes: [{
                scaleLabel: {
                    display: true,
                    labelString: '价格'
                }
            }],
            yAxes: [{
                // stacked: true,
                scaleLabel: {
                    display: true,
                    labelString: '数量'
                }
            }]
        },
        showLines: true  // disable for all datasets
    };


    genderPriceDisConf = {
        type: 'line',
        data: {
            labels:['[1-10)', '[10-50)', '[50-100)', '[100-200)', '[200-500)', '[500-1k)', '[1k-2k)', '[2k-5k)', '[5k-10k)', '[10k-20k)', '[20k-50k)', '[50k-'],
            datasets: [{
                label: '男性',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgb(255, 99, 132)',
                data:[]
            },{
                label: '女性',
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgb(54, 162, 235)',
                data:[]
            }]
        },
        options: opt
    };

    window.genderpricedis = new Chart(genderPriceChart, genderPriceDisConf);
    getGenderPriceDis();

});


function getGenderPriceDis() {

    $.ajax({
        type: 'get',
        url: webRoot + "/datashow/getPriceDis?feature=genderpricedis",
        success:function (result) {
            console.log(result);
            genderPriceDisConf.data.datasets[0].data = result.maleData;
            genderPriceDisConf.data.datasets[1].data = result.femaleData;
            window.genderpricedis.update();
        },
        error:function () {
            alert("服务器出错了");
        }
    });


}
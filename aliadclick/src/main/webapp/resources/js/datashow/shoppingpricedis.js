var webRoot = getRootPath();

var shoppingPriceDisConf = null;

$(function() {

    var shopppingPriceChart = document.getElementById("shoppingpricedis").getContext('2d');

    var opt = {
        responsive: true,
        title: {
            display: true,
            text: 'Shopping level . Price distribution'
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


    shoppingPriceDisConf = {
        type: 'line',
        data: {
            labels:['[1-10)', '[10-50)', '[50-100)', '[100-200)', '[200-500)', '[500-1k)', '[1k-2k)', '[2k-5k)', '[5k-10k)', '[10k-20k)', '[20k-50k)', '[50k-'],
            datasets: [{
                label: '浅层用户',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgb(255, 99, 132)',
                data:[]
            },{
                label: '中度用户',
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgb(54, 162, 235)',
                data:[]
            },{
                label: '深度用户',
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                borderColor: 'rgb(153, 102, 255)',
                data:[]
            }]
        },
        options: opt
    };

    window.shoppingpricedis = new Chart(shopppingPriceChart, shoppingPriceDisConf);
    getShoppingPriceDis();

});


function getShoppingPriceDis() {

    $.ajax({
        type: 'get',
        url: webRoot + "/datashow/getPriceDis?feature=shoppingpricedis",
        success:function (result) {
            console.log(result);
            shoppingPriceDisConf.data.datasets[0].data = result.level1;
            shoppingPriceDisConf.data.datasets[1].data = result.level2;
            shoppingPriceDisConf.data.datasets[2].data = result.level3;
            window.shoppingpricedis.update();
        },
        error:function () {
            alert("服务器出错了");
        }
    });


}
var webRoot = getRootPath();

var priceDisConf = null;
$(function() {

    var priceDisChart = document.getElementById("pricedis").getContext('2d');

    var opt = {
        responsive: true,
        title: {
            display: true,
            text: 'Ad - Price distribution'
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


    priceDisConf = {
        type: 'line',
        data: {
            labels:['[1-10)', '[10-50)', '[50-100)', '[100-200)', '[200-500)', '[500-1k)', '[1k-2k)', '[2k-5k)', '[5k-10k)', '[10k-20k)', '[20k-50k)', '[50k-'],
            datasets: [{
                label: '数量',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgb(255, 99, 132)',
                data:[]
            }]
        },
        options: opt
    };

    window.pricedis = new Chart(priceDisChart, priceDisConf);
    getPriceDis();

});


function getPriceDis() {

    $.ajax({
        type: 'get',
        url: webRoot + "/datashow/getPriceDis?feature=allpricedis",
        success:function (result) {
            console.log(result);
            priceDisConf.data.datasets[0].data = result.allpricedisData;
            window.pricedis.update();
        },
        error:function () {
            alert("服务器出错了");
        }
    });


}
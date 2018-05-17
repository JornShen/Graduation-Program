var webRoot = getRootPath();

var pvaluePriceDisConf = null;

$(function() {

    var pvaluePriceChart = document.getElementById("pvaluepricedis").getContext('2d');

    var opt = {
        responsive: true,
        title: {
            display: true,
            text: 'Pvalue level . Price distribution'
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


    pvaluePriceDisConf = {
        type: 'line',
        data: {
            labels:['[1-10)', '[10-50)', '[50-100)', '[100-200)', '[200-500)', '[500-1k)', '[1k-2k)', '[2k-5k)', '[5k-10k)', '[10k-20k)', '[20k-50k)', '[50k-'],
            datasets: [{
                label: '低档用户',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgb(255, 99, 132)',
                data:[]
            },{
                label: '中档用户',
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgb(54, 162, 235)',
                data:[]
            },{
                label: '高档用户',
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                borderColor: 'rgb(153, 102, 255)',
                data:[]
            }]
        },
        options: opt
    };

    window.pvaluepricedis = new Chart(pvaluePriceChart, pvaluePriceDisConf);
    getPvaluePriceDis();

});


function getPvaluePriceDis() {

    $.ajax({
        type: 'get',
        url: webRoot + "/datashow/getPriceDis?feature=pvaluepricedis",
        success:function (result) {
            console.log(result);
            pvaluePriceDisConf.data.datasets[0].data = result.level1;
            pvaluePriceDisConf.data.datasets[1].data = result.level2;
            pvaluePriceDisConf.data.datasets[2].data = result.level3;
            window.pvaluepricedis.update();
        },
        error:function () {
            alert("服务器出错了");
        }
    });


}
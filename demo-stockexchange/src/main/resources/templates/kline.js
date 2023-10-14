// Initialize ECharts instance
var myChart = echarts.init(document.getElementById('main'));

// Define Finnhub client and API key
const apiKey = "cju3it9r01qr958213c0cju3it9r01qr958213cg"; // Replace with your Finnhub API key
const symbol = "TSLA";
const time = "D";//1,5,15,60,D,M,W
const from = 1390988249;
const to = 1591852249;

const apiUrl = `https://finnhub.io/api/v1/stock/candle?symbol=${symbol}&resolution=${time}&from=${from}&to=${to}&token=${apiKey}`;

function fetchData(){
fetch(apiUrl)
.then(response=>{
  if(!response.ok){
    throw new Error('Network response was not ok');
  }
  return response.json();
})
  .then(data => {
    // Handle the data here
    console.log(data);

    // Extract data
    var categoryData = data.t.map(timestamp => new Date(timestamp * 1000).toLocaleDateString()); // Convert timestamps to date strings
    var values = data.c.map((close, index) => [data.o[index], close, data.l[index], data.h[index]]);
    var vols = data.v;

    var ma10 = calculateMA(10, data);
    var ma50 = calculateMA(50, data);
    var ma100 = calculateMA(100, data);
    var ma250 = calculateMA(250, data);

    // ECharts option
    var option = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross'
        }
      },
      grid: [{
        left: '3%',
        top: '1%',
        height: '58%'
      }, {
        left: '3%',
        right: '10%',
        top: '65%',
        height: '10%'
      }, {
        left: '3%',
        right: '10%',
        top: '78%',
        height: '10%'
      }],
      xAxis: [{
        type: 'category',
        data: categoryData,
        scale: true,
        boundaryGap: false,
        axisLine: {
          onZero: false,
          lineStyle: {
            color: 'red',
          }
        },
        splitLine: {
          show: false
        },
        splitNumber: 20
      }, {
        type: 'category',
        gridIndex: 1,
        data: categoryData,
        axisLabel: {
          show: false
        },
      }, {
        type: 'category',
        gridIndex: 2,
        data: categoryData,
        axisLabel: {
          show: false
        },
      }],
      yAxis: [{
        scale: true,
        splitArea: {
          show: true
        },
        axisLine: {
          lineStyle: {
            color: 'red',
          }
        },
        position: 'right'
      }, {
        gridIndex: 1,
        splitNumber: 3,
        axisLine: {
          onZero: false,
          lineStyle: {
            color: 'red'
          }
        },
        axisTick: {
          show: false
        },
        splitLine: {
          show: false
        },
        axisLabel: {
          show: true
        },
        position: 'right'
      }, {
        gridIndex: 2,
        splitNumber: 4,
        axisLine: {
          onZero: false,
          lineStyle: {
            color: 'red'
          }
        },
        axisTick: {
          show: false
        },
        splitLine: {
          show: false
        },
        axisLabel: {
          show: true
        },
        position: 'right'
      }],
      dataZoom: [{
        type: 'inside',
        start: 100,
        end: 80
      }, {
        show: true,
        type: 'slider',
        y: '90%',
        xAxisIndex: [0, 1],
        start: 50,
        end: 100
      }, {
        show: false,
        xAxisIndex: [0, 2],
        type: 'slider',
        start: 20,
        end: 100
      }],
      series: [{
        name: 'Candlestick',
        type: 'candlestick',
        data: values,
        markPoint: {
          data: [{
            name: 'XX标点'
          }]
        },
        markLine: {
          silent: true,
          data: [{
            yAxis: 2222,
          }]
        }
      }, {
        name: 'MA10',
        type: 'line',
        data: ma10,
        smooth: true,
        lineStyle: {
          normal: {
            opacity: 0.5
          }
        }
      }, {
        name: 'MA50',
        type: 'line',
        data: ma50,
        smooth: true,
        lineStyle: {
          normal: {
            opacity: 0.5
          }
        }
      }, {
        name: 'MA100',
        type: 'line',
        data: ma100,
        smooth: true,
        lineStyle: {
          normal: {
            opacity: 0.5
          }
        }
      }, {
        name: 'MA250',
        type: 'line',
        data: ma250,
        smooth: true,
        lineStyle: {
          normal: {
            opacity: 0.5
          }
        }
      }, {
        name: 'Volume',
        type: 'bar',
        xAxisIndex: 1,
        yAxisIndex: 1,
        data: vols,
        itemStyle: {
          normal: {
            color: function (params) {
              var colorList;
              if (values[params.dataIndex][1] > values[params.dataIndex][0]) {
                colorList = '#ef232a';
              } else {
                colorList = '#14b143';
              }
              return colorList;
            },
          }
        }
      }]
    };

    // Set the option and render the chart
    myChart.setOption(option);
  })
  .catch(error => {
    console.error('Error fetching data:', error);
  });
}

// Set up a timer to call fetchData every, for example, 5 seconds (5000 milliseconds)
const interval = 10000; // 10 seconds
setInterval(fetchData, interval);

// Define calculateMA function
function calculateMA(dayCount, data) {
  var result = [];
  for (var i = 0, len = data.c.length; i < len; i++) {
    if (i < dayCount) {
      result.push('-');
      continue;
    }
    var sum = 0;
    for (var j = 0; j < dayCount; j++) {
      sum += data.c[i - j];
    }
    result.push((sum / dayCount).toFixed(2)); // Rounded to 2 decimal places
  }
  return result;
}

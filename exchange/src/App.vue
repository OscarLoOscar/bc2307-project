<template>
  <div class="container">
    <div class="left-sidebar">
      <h1>Finnhub Kline</h1>
      <div id="main"></div>
    </div>
    <div class="right-sidebar">
      <div class="trade-section">
        <h2>Trade Form</h2>
        <form id="trade-form">
          <div>
            <label for="trade-type">Trade Type:</label>
            <select id="trade-type" name="trade-type">
              <option value="Bid">Bid</option>
              <option value="Ask">Ask</option>
            </select>
          </div>
          <div>
            <label for="stock-code">Stock Code:</label>
            <button id="choose-stock">Choose Stock</button>
            <select id="stock-code" name="stock-code">
              <option value="AAPL">AAPL</option>
              <option value="TSLA">TSLA</option>
              <option value="MSFT">MSFT</option>
            </select>
          </div>
          <div>
            <label for="price">Price:</label>
            <button type="button" id="decrease-price">-</button>
            <input type="number" id="price" name="price" value="100">
            <button type="button" id="increase-price">+</button>
          </div>
          <div>
            <label for="quantity">Quantity:</label>
            <button type="button" id="decrease-quantity">-</button>
            <input type="number" id="quantity" name="quantity" value="100">
            <button type="button" id="increase-quantity">+</button>
          </div>
          <div>
            <button type="submit">Submit</button>
          </div>
        </form>
      </div>
    </div>
    <div class="right-sidebar">
      <div class="list-section">
        <div>
          <ul class="BidList" id="BidList"></ul>
        </div>
        <div>
          <ul class="AskList" id="AskList"></ul>
        </div>
      </div>
    </div>
    <!-- <button class="check-btn" id="check">
      test 1
      <i class="fas fa-paper-plane"></i>
    </button> -->
  </div>
</template>
<!-- <script src="tradeList.js"></script>
<script src="kline.js"></script>
<script src="BidAskTrade.js"></script>
 -->
<script>
import echarts from 'echarts';
export default {
  name: 'App',
  mounted() {
    // Initialize ECharts instance
    let myChart = echarts.init(document.getElementById('main'));

    // Define Finnhub client and API key
    const apiKey = "cju3it9r01qr958213c0cju3it9r01qr958213cg"; // Replace with your Finnhub API key
    const symbol = "AAPL";
    const time = "D"; // 1,5,15,60,D,M,W
    const from = 1390988249;
    const to = 1591852249;

    const apiUrl = `https://finnhub.io/api/v1/stock/candle?symbol=${symbol}&resolution=${time}&from=${from}&to=${to}&token=${apiKey}`;

    function fetchData() {
      fetch(apiUrl)
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          // Handle the data here
          console.log(data);

          // Extract data
          var categoryData = data.t.map(timestamp =>
            new Date(timestamp * 1000).toLocaleDateString()
          ); // Convert timestamps to date strings
          var values = data.c.map((close, index) => [
            data.o[index],
            close,
            data.l[index],
            data.h[index],
          ]);
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
                type: 'cross',
              },
            },
            grid: [
              {
                left: '3%',
                top: '1%',
                height: '58%',
              },
              {
                left: '3%',
                right: '10%',
                top: '65%',
                height: '10%',
              },
              {
                left: '3%',
                right: '10%',
                top: '78%',
                height: '10%',
              },
            ],
            xAxis: [
              {
                type: 'category',
                data: categoryData,
                scale: true,
                boundaryGap: false,
                axisLine: {
                  onZero: false,
                  lineStyle: {
                    color: 'red',
                  },
                },
                splitLine: {
                  show: false,
                },
                splitNumber: 20,
              },
              {
                type: 'category',
                gridIndex: 1,
                data: categoryData,
                axisLabel: {
                  show: false,
                },
              },
              {
                type: 'category',
                gridIndex: 2,
                data: categoryData,
                axisLabel: {
                  show: false,
                },
              },
            ],
            yAxis: [
              {
                scale: true,
                splitArea: {
                  show: true,
                },
                axisLine: {
                  lineStyle: {
                    color: 'red',
                  },
                },
                position: 'right',
              },
              {
                gridIndex: 1,
                splitNumber: 3,
                axisLine: {
                  onZero: false,
                  lineStyle: {
                    color: 'red',
                  },
                },
                axisTick: {
                  show: false,
                },
                splitLine: {
                  show: false,
                },
                axisLabel: {
                  show: true,
                },
                position: 'right',
              },
              {
                gridIndex: 2,
                splitNumber: 4,
                axisLine: {
                  onZero: false,
                  lineStyle: {
                    color: 'red',
                  },
                },
                axisTick: {
                  show: false,
                },
                splitLine: {
                  show: false,
                },
                axisLabel: {
                  show: true,
                },
                position: 'right',
              },
            ],
            dataZoom: [
              {
                type: 'inside',
                start: 100,
                end: 80,
              },
              {
                show: true,
                type: 'slider',
                y: '90%',
                xAxisIndex: [0, 1],
                start: 50,
                end: 100,
              },
              {
                show: false,
                xAxisIndex: [0, 2],
                type: 'slider',
                start: 20,
                end: 100,
              },
            ],
            series: [
              {
                name: 'Candlestick',
                type: 'candlestick',
                data: values,
                itemStyle: {
                  color: '#ec0000',
                  color0: '#00da3c',
                  borderColor: '#8A0000',
                  borderColor0: '#008F28',
                },
                markPoint: {
                  label: {
                    normal: {
                      formatter: function (param) {
                        return param !== null ? Math.round(param.value) : '';
                      },
                    },
                  },
                  data: [
                    {
                      name: 'highest value',
                      type: 'max',
                      valueDim: 'highest',
                    },
                    {
                      name: 'lowest value',
                      type: 'min',
                      valueDim: 'lowest',
                    },
                    {
                      name: 'average value on close',
                      type: 'average',
                      valueDim: 'close',
                    },
                  ],
                  tooltip: {
                    formatter: function (param) {
                      return param.name + '<br>' + (param.data.coord || '');
                    },
                  },
                },
                markLine: {
                  symbol: ['none', 'none'],
                  data: [
                    [
                      {
                        name: 'from lowest to highest',
                        type: 'min',
                        valueDim: 'lowest',
                        symbol: 'circle',
                        symbolSize: 10,
                        label: {
                          show: false,
                        },
                        emphasis: {
                          label: {
                            show: false,
                          },
                        },
                      },
                      {
                        type: 'max',
                        valueDim: 'highest',
                        symbol: 'circle',
                        symbolSize: 10,
                        label: {
                          show: false,
                        },
                        emphasis: {
                          label: {
                            show: false,
                          },
                        },
                      },
                    ],
                    {
                      name: 'max line on close',
                      type: 'max',
                      valueDim: 'close',
                    },
                    {
                      name: 'min line on close',
                      type: 'min',
                      valueDim: 'close',
                    },
                    {
                      name: 'max line on close2',
                      type: 'max',
                      valueDim: 'close',
                    },
                    {
                      name: 'min line on close2',
                      type: 'min',
                      valueDim: 'close',
                    },
                  ],
                },
              },
              {
                name: 'Volume',
                type: 'bar',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: vols,
              },
              {
                name: 'MA10',
                type: 'line',
                data: ma10,
                smooth: true,
                symbolSize: 0,
                lineStyle: {
                  width: 2,
                },
              },
              {
                name: 'MA50',
                type: 'line',
                data: ma50,
                smooth: true,
                symbolSize: 0,
                lineStyle: {
                  width: 2,
                },
              },
              {
                name: 'MA100',
                type: 'line',
                data: ma100,
                smooth: true,
                symbolSize: 0,
                lineStyle: {
                  width: 2,
                },
              },
              {
                name: 'MA250',
                type: 'line',
                data: ma250,
                smooth: true,
                symbolSize: 0,
                lineStyle: {
                  width: 2,
                },
              },
            ],
          };

          // Set the ECharts option
          myChart.setOption(option);
        })
        .catch(error => {
          // Handle errors here
          console.error('There was a problem with the fetch operation:', error);
        });
    }

    // Initial data fetch
    fetchData();

    // Helper function to calculate moving averages
    function calculateMA(dayCount, data) {
      var result = [];
      for (var i = 0, len = data.values.length; i < len; i++) {
        if (i < dayCount) {
          result.push('-');
          continue;
        }
        var sum = 0;
        for (var j = 0; j < dayCount; j++) {
          sum += data.values[i - j][1];
        }
        result.push((sum / dayCount).toFixed(2));
      }
      return result;
    }

    // Update data when the user selects a different stock
    document.getElementById('choose-stock').addEventListener('click', function () {
      var selectedStock = document.getElementById('stock-code').value;
      if (selectedStock !== symbol) {
        // symbol = selectedStock;
        fetchData();
      }
    });

    // Implement other interactive features as needed
  },
};
</script>

<style>
/* Add your CSS styles here if needed */
/* styles.css */

.container {
  display: flex; /* Use flexbox to create a two-column layout */
}

.left-sidebar {
  flex: 1; /* Take up 50% of the container */
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.943);

}


.right-sidebar {
  flex: 1; /* Take up 50% of the container */
  padding: 20px;
  background-color: #e0e0e0;
}

/* Add additional styling for your elements as needed */
/* styles.css */

body {
  font-family: Arial, sans-serif;
  background-color: #f0f0f0;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  min-height: 100vh;
}

h2 {
  margin: 20px 0;
}

button[type="submit"] {
  font-size: 18px; /* Adjust the font size as needed */
  padding: 10px 20px; /* Adjust the padding as needed */
}


form {
  border: 1px solid #ccc;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  width: max-content;
}

label {
  display: block;
  margin-bottom: 5px;
}

select, input {
  margin-bottom: 10px;
}

button {
  margin-right: 10px;
  cursor: pointer;
}

#selected-stock {
  font-weight: bold;
}
/* Global Styling: */
@import url('https://fonts.googleapis.com/css?family=Lato&display=swap');

:root {
  --border-color: #e3e5e4;
  --background-color: #c3c7ca;
  --text-color: #34444f;
}
/* Global Reset: */
* {
  box-sizing: border-box;
}

body {
  background-color: #fff;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  height: 100vh;
  /* margin: 0; */
  font-family: 'Lato', sans-serif;
}
/* BidList Styling: */
.BidList {
  border: 1px solid var(--border-color);
  color: var(--text-color);
  margin-top: 0;
  padding: 0;
  list-style-type: none;
}
/* 外框 */
.BidList li {
  background-color: #ffa07ab0;
  height: 40px ;
  display: flex;
  flex: 1;
  color: black;
  align-items: center;
}
.BidList li:not(:last-of-type) {
  border-bottom: 1px solid var(--border-color);
}

.BidList .number:nth-child(-n+5) {
  background-color: var(--background-color);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px auto;
  height: 40px ;
  width: 40px ;
  background-color: orange;
  color: white;
}

.BidList li.over .tradeListgable {
  background-color: lightskyblue;
}

.BidList .person-name {
  margin: 0 20px 0 0;
}

.BidList li.right .person-name {
  color: #3ae374;
}

.BidList li.wrong .person-name {
  color: #ff3838;
}

/* .tradeListgable {
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px;
  flex: 1;
} */

/* Check Button: */
.check-btn {
  background-color: var(--background-color);
  border: none;
  color: var(--text-color);
  font-size: 16px;
  padding: 10px 20px;
  cursor: pointer;
}

.check-btn:active {
  transform: scale(0.98);
}

.check-btn:focus {
  outline: none;
}
/**/

/* AskList Styling */
.AskList {
  border: 1px solid var(--border-color);
  color: var(--text-color);
  padding: 0;
  list-style-type: none;
}
/* 外框 */
.AskList li {
  background-color: #87cefa86;
  height: 40px ;
  display: flex;
  flex: 1;
  color: black;
}
.AskList li:not(:last-of-type) {
  border-bottom: 1px solid var(--border-color);
}

.AskList .number:nth-child(n+1) {
  background-color: var(--background-color);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px auto;
  height: 40px ;
  width: 40px ;
  background-color: #0073ff;
  color: white;
}

.AskList li.over .tradeListgable {
  background-color: lightskyblue;
}

.AskList .person-name {
  margin: 0 20px 0 0;
}

.AskList li.right .person-name {
  color: #3ae374;
}

.AskList li.wrong .person-name {
  color: #ff3838;
}


</style>

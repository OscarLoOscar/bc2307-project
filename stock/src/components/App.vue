<template>
  <div id="app">
    <h1 class="caption p-4">Stock Order Book</h1>
    <div class="order-book-container">

<div>
        <div>
        <el-row class="button-header">Stock Symbol : </el-row>
        <el-row>
          <div class="custom-input-stock-symbol">
          <el-input v-model="stockSymbol" class="custom-stock-input"></el-input>
            </div>
          </el-row> 
          </div>

      <div class="order-form" >
          <!-- Action -->
          <el-row
            class="button-header"
            :class="{ 'action-row': buySellSelectedOption }"
          >
            Action
          </el-row>
          <el-row>
            <el-col class="button-wrapper">

              <el-button v-for="(option, index) in buySellOptions" :key="index"
            :class="{ 'selected': buySellSelectedOption === index  }"
          @click="buySellSelectOption(index)"
          >
          <span>{{ option }}</span>
          </el-button>

            </el-col>
          </el-row>
          <!-- Order Type -->
          <el-row
            class="button-header"
            :class="{ 'order-type-row': orderTypeSelectedOption }"
          >
            Order Type
          </el-row>
          <el-row>
            <el-col class="button-wrapper">

            <el-button v-for="(option, index) in orderTypeOptions" :key="index"
            :class="{ 'selected': orderTypeSelectedOption === index}"
          @click="orderTypeSelectOption(index)"
          >
          <span>{{ option }}</span>
          </el-button>

            </el-col>
          </el-row>
          <!-- price -->
          <el-row class="button-header">Price</el-row>
          <el-row>
            <div class="custom-input-number">
              <el-input-number v-model="price_input" :min="0" :max="10000" :step="0.1"></el-input-number>
            </div>
          </el-row>
          <!-- shares -->
          <el-row class="button-header">Quantity</el-row>
          <el-row>
            <div class="custom-input-number">
              <el-input-number v-model="quantity_input" :min="0" :max="1000" :step="1"></el-input-number>
            </div>
          </el-row>
          <!-- Total Order value -->
          <el-row class="button-header">Total Order Value</el-row>
          <el-row>
            <div class="custom-input-order-value">
              <el-input-number v-model="total_order_value_input" :min="1" :max="10000000000"
                :controls="false"></el-input-number>
            </div>
          </el-row>
          <el-row>

            <div class="place-order-button">
              <el-button type="primary" @click="placeOrder">Place Order</el-button>
            </div>
        
          </el-row>

          
    </div>
  </div>

<div>
  <div class="order-book-tables" top>
        <div class="order-book-buy"  >
          <el-table :data="buyOrders" :max-height="480">
            <el-table-column label="Quantity" prop="quantity" align="center"></el-table-column>
            <el-table-column label="Buy (Bid)" prop="price" align="center"></el-table-column>
          </el-table>
        </div>
        <div class="order-book-sell"  >
          <el-table :data="sellOrders" :max-height="480">
            <el-table-column label="Sell (Ask)" prop="price" align="center"></el-table-column>
            <el-table-column label="Quantity" prop="quantity" align="center"></el-table-column>
          </el-table>
        </div>
      </div>
      </div>

  <div> 
    <div id="kline-chart" style="width: 600px; height: 600px;" ref="klineChart"></div>
    </div>
</div>


    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';
import 'echarts/theme/macarons'; // Optional: Add a theme for your chart

export default {
  name: 'App',
  setup() {
      const    price_input= ref(1000.0);
      const   quantity_input= ref(100);
      const   total_order_value_input=ref( price_input*quantity_input);
      const stockSymbol = ref('TSLA');

    // Buy Sell Option
    const buySellSelectedOption = ref(0);
    const buySellOptions = ['Buy', 'Sell'];
    const buySellSelectOption = (buySellIndex) => {
      buySellSelectedOption.value = buySellIndex;
    };

    // Order Type Option
    const orderTypeSelectedOption = ref(0);
    const orderTypeOptions = ['Market', 'Limit'];
    const orderTypeSelectOption = (orderTypeIndex) => {
      orderTypeSelectedOption.value = orderTypeIndex;
    };
    
    const buyOrders = ref([]);
    const sellOrders = ref([]);

    const retrieveQueue = async () => {

      try {
        const responseBuy = await axios.get(
          "http://localhost:8085/transactions/bidQueue?stockId=TSLA"
        );
        const responseAsk = await axios.get(
          "http://localhost:8085/transactions/askQueue?stockId=TSLA"
        );
        sellOrders.value = responseAsk.data;
        buyOrders.value = responseBuy.data;
      } catch (err) {
        console.log(err);
      }
    };

    // Function to place an order
    const placeOrder = () => {
  // Access the form data directly from the setup context
   // Ensure that form.price_input and form.quantity_input are valid
   if (isNaN(price_input.value) || isNaN(quantity_input.value) || price_input.value <= 0 || quantity_input.value <= 0) {
        console.error('Invalid price or quantity');
        return;
      }
      const  symbol= stockSymbol.value;
      const  action= buySellOptions[buySellSelectedOption.value]; // Use the selected option index to get the value
      const  orderType= orderTypeOptions[orderTypeSelectedOption.value]; // Use the selected option index to get the value
      const  price= price_input.value;
      const  quantity= quantity_input.value;
    // Include other request data here

  // Construct the URL with the selected parameters
  const url = `http://localhost:8085/sumit/trade/symbol/${symbol}?action=${action}&orderType=${orderType}&price=${price}&quantity=${quantity}`;

  // Create an Axios configuration object
  const config = {
    method: 'post',
    headers: {
      'Content-Type': 'application/json'
    },
  };

  // Send the POST request using Axios
  axios(url, config)
    .then(() => {
      // Handle the response if needed
      console.log('Order placed successfully');
    })
    .catch(error => {
      // Handle any errors
      console.error('Error placing order:', error);
    });
};

const klineChart = ref(null); // Reference to the Kline chart div

    const initKlineChart = () => {
      // Initialize ECharts instance in the kline-chart div
      const myChart = echarts.init(klineChart.value);
    // Define Finnhub client and API key
// const apiKey = "cju3it9r01qr958213c0cju3it9r01qr958213cg"; // Replace with your Finnhub API key
const symbol = stockSymbol.value;
//stockSymbol.value;
const time = "D";//1,5,15,60,D,M,W
const from = "2022-11-01";
const to = "2023-10-01";

// const apiUrl = `https://finnhub.io/api/v1/stock/candle?symbol=${symbol}&resolution=${time}&from=${from}&to=${to}&token=${apiKey}`;
const apiUrl = `http://localhost:8085/api/v1/getCandleData?symbol=${symbol}&resolution=${time}&from=${from}&to=${to}`;

console.log(apiUrl);

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
            opacity: 1
          }
        }
      }, {
        name: 'MA50',
        type: 'line',
        data: ma50,
        smooth: true,
        lineStyle: {
          normal: {
            opacity: 2
          }
        }
      }, {
        name: 'MA100',
        type: 'line',
        data: ma100,
        smooth: true,
        lineStyle: {
          normal: {
            opacity: 3
          }
        }
      }, {
        name: 'MA250',
        type: 'line',
        data: ma250,
        smooth: true,
        lineStyle: {
          normal: {
            opacity: 4
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
const interval = 100000; // 100 seconds
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
    };

    // Fetch data initially
    onMounted(() => {
      retrieveQueue();
      initKlineChart();
    });

    // Fetch data periodically every 2 seconds
    setInterval(() => {
      retrieveQueue();
    }, 2000);

    // Update buyOrders and sellOrders when buys and asks data change
    const updateOrders = () => {
      buyOrders.value = buyOrders.value.map(buy => ({ price: buy.price, share: buy.quantity }));
      sellOrders.value = sellOrders.value.map(ask => ({ price: ask.price, share: ask.quantity }));
    };

    // Watch for changes in buys and asks data and update orders accordingly
    onMounted(() => {
      updateOrders();
    });

    return {
      stockSymbol,
      price_input,
      quantity_input,
      total_order_value_input,
      buyOrders,
      sellOrders,
      buySellSelectedOption,
      buySellOptions,
      buySellSelectOption,
      orderTypeSelectedOption,
      orderTypeOptions,
      orderTypeSelectOption,
      placeOrder,
      updateOrders,
      klineChart
    };
  },
};
</script>

<style>
body::before {
  content: '';
  background-image: url('https://asseco.com/files/public/_processed_/csm_baner_wyniki_small_612a732bf5.jpg');
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  opacity: 0.9;
  /* Adjust the opacity level (0.0 to 1.0) */
  z-index: -1;
}

.caption {
  text-align: center;
  font-size: 33px;
  font-weight: bold;
  color: rgb(237, 230, 230);
  width: 100%;
}
.order-book-container {
  display: flex;
  justify-content: space-between;
  border-radius: 10px;
  border-style: groove;

}

.order-book-tables {
  width: 500px;
  display: flex;
  justify-content: space-between;
  margin-left: -40px;
}

.order-book-buy {
  width: 47%;
}

.order-book-sell {
  width: 47%;
}

/* Styling for the order form and panel */
.order-form {
  width: auto;
  display: flex;
  flex-direction: column;
  align-items: left;
  /* Center content horizontally */
  margin-right: 50px;
  background-color: #f7f7f7;
  /* Light gray background */
  border: 2px solid #b2bfe0;
  border-radius: 5px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  /* Box shadow for depth */
}

.order-panel {
  width: 97%;
  /* Make the panel 100% width within the order-form */
  margin-bottom: 10px;
}

.order-panel label {
  font-weight: bold;
  color: #333;
  /* Dark text color */
}

.order-panel select,
.order-panel input {
  padding: 10px;
  border: 1.8px solid #e46c25;
  border-radius: 10px;
  width: 100%;
  /* Full width input fields */
  margin-bottom: 20px;
}

.button-header {

  color: rgb(92, 99, 99);
  font-size: 20px;
  display: flex;
  justify-content: space-between;
  align-items: top;
}

.button-wrapper .el-button {
  font-size: 15px;
  padding: 28px 60px;
  width: 133px;
  height: 65px;
  border: 1.5px solid #e7dfdf;
  border-radius: 20px;
  text-align: left;
  background-color: #f6f9f7;
  color: #1b439b;
  transition: border-color 0.2s;
}

  .selected {
    border-color: #1b439b;
    border-width: 1.5px;
    color: #1b439b;
  
}

.custom-input-number {
  display: flex;
  align-items: center;
  justify-content: center;
  /* Align items to the right */
  border: 1.5px solid #1b439b;
  border-radius: 25px;

  margin-bottom: 15px;
  margin-top: 15px;

  background-color: #f7f7f7;
  color: #333;
  font-size: 18px;
  /* Increase the font size for a larger input box */
  width: 280px;
  /* Width of the container */
  height: 65px;
  /* Height of the container */
}


.custom-input-number .el-input {
  border: none;
  /* Remove the border from the inner input */
  margin: 0;
  /* Remove any margin */
  padding: 0;
  /* Remove any padding */
}

.custom-input-number .el-input-number__increase {
  background-color: #4771b8;
  color: white;
  border: 1.5px solid #3850b9;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: -50px;
  margin-top: -5px;
  font-size: 20px;
  font-weight: 300;
}

.custom-input-number .el-input-number__decrease {
  background-color: #4771b8;
  color: white;
  border: 1.5px solid #3850b9;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-left: -50px;
  margin-top: -5px;
  font-size: 20px;
  font-weight: bold;
}

.custom-input-order-value {
  width: 200px;
  margin-bottom: 15px;
  margin-top: 15px;
}

.custom-input-order-value .el-input {
  border: none;
  box-shadow: none;
}

.custom-input-order-value .el-input-number__input {
  border: 1.5px solid #1543b7;
  border-radius: 25px;
  background-color: #f7f7f7;
  color: #333;
  font-size: 30px;
  max-width: 600px;
  height: 70px;
  text-align: left;
  /* Align the text input to the left */

}

.place-order-button {
  display: flex;
  justify-content: center;
}

.place-order-button .el-button {
  background-color: #1b439b;
  /* Text color */
  border: none;
  /* Remove the button border */
  border-radius: 25px;
  font-size: 15px;
  width: 280px;
  height: 65px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 5px;
  margin-top: 10px;
}

.place-order-button .el-button:active {
  background-color: #1b439b; /* Change the background color when the button is pressed */
  color: white; /* Change the text color when the button is pressed */
}

.button-wrapper .el-button:active{
  /* Define the style for the "Action" row when clicked */
  background-color: #1b439b;
  color: white;
}

.button-wrapper .el-button {
  font-size: 15px;
  padding: 20px 40px;
  width: auto;
  height: auto;
  border: 1.5px solid #e7dfdf;
  border-radius: 20px;
  text-align: left;
  background-color: #f6f9f7;
  color: #1b439b;
  transition: border-color 0.2s;
}

/* Selected effect for Action buttons */
.button-wrapper .el-button.action.selected {
  border-color: #1b439b;
  border-width: 1.5px;
  color: #1b439b;
}
.button-wrapper .el-button.order-type.selected {
  border-color: #1b439b;
  border-width: 1.5px;
  color: #1b439b;
}

/* Styles for the button on hover */
.button-wrapper .el-button:hover {
  /* Your styles for the hover state (when the mouse is over the button) */
  /* Example styles: */
  background-color: #1b439b;
  color: white;
  border-color: #1b439b;
}

.custom-stock-input {
  /* Control the box size */
  width: 200px; /* Adjust the width as needed */
  height: 40px; /* Adjust the height as needed */
 display: flex;
  align-items: center;
  /* Control the background color */
  background-color: #f7f7f7; /* Background color */
  border: 2px solid #e46c25; /* Border color */
  border-radius: 10px; /* Border radius */
  padding: 10px; /* Padding inside the input */
  font-size: 16px; /* Font size */
  color: #333; /* Text color */
}
</style>
// JavaScript

document.addEventListener("DOMContentLoaded", function() {
  const bidQueue = document.getElementById('BidList');
  const askQueue = document.getElementById('AskList');
  const chart = document.getElementById('Chart');

  const Chart = window.Chart;

  let AAPLBid = [];
  let AAPLAsk = [];

  // Function to create list items and update chat content
  function updateChat(bidOrders, askOrders) {
     // Sort bidOrders and askOrders by price in descending order
    bidOrders.sort((a, b) => b.price - a.price);
    askOrders.sort((a, b) => b.price - a.price);
    // Clear previous data
    bidQueue.innerHTML = "";
    askQueue.innerHTML = "";

    // Update AAPLBid and AAPLAsk with new data
    AAPLBid = bidOrders;
    AAPLAsk = askOrders;

    // Render the updated lists
    createList(bidQueue, AAPLBid);
    createList(askQueue, AAPLAsk);

    // Update the chart
    updateChart(AAPLBid, AAPLAsk);
  }

  // Function to create list items
  function createList(queue, orders) {

    const limitedOrders = orders.slice(0, 10).reverse();

    limitedOrders.forEach((stock) => {
      const listItem = document.createElement('li');

      // 排index
      listItem.innerHTML = `
        <div class="list-group-item">
          <span class="badge bg-success">${stock.quantity}</span>
          <span class="price">$${stock.price.toFixed(3)}</span>
          <span class="time">${stock.localTime}</span>
        </div>
      `;

      queue.appendChild(listItem);
    });
  }

  // Function to update the chart
  function updateChart(bidOrders, askOrders) {
    const chartData = {
      labels: bidOrders.map(order => order.price.toFixed(3)),
      datasets: [
        {
          label: "Bid",
          data: bidOrders.map(order => order.quantity),
          backgroundColor: "green",
        },
        {
          label: "Ask",
          data: askOrders.map(order => order.quantity),
          backgroundColor: "red",
        },
      ],
    };

    const chartConfig = {
      type: "bar",
      data: chartData,
      options: {
        responsive: true,
        maintainAspectRatio: false,
      },
    };

    new Chart(chart, chartConfig);
  }

  // Set up a timer to call fetchData every, for example, 5 seconds (5000 milliseconds)
  const interval = 5000; // 5 seconds

  setInterval(function() {
    // Fetch and display data here
    fetch("http://localhost:8081/transactions/atAuctionOrders?stockId=TSLA")
      .then(response => response.json())
      .then(data => {
        const bidOrders = data.TSLA.bidOrders;
        const askOrders = data.TSLA.askOrders;

        // Update the chat with the extracted data
        updateChat(bidOrders, askOrders);
      })
      .catch(error => {
        console.error("Error fetching data:", error);
      });
  }, interval);

  // Add event listeners and other code as needed
});

const bidQueue = document.getElementById('BidList');
const check = document.getElementById('check');
const askQueue = document.getElementById('AskList');

// Set up a timer to call fetchData every, for example, 5 seconds (5000 milliseconds)
// const interval = 10000; // 10 seconds
// setInterval(fetchData, interval);


const AAPLBid = [
  { price: 18.95, Qty : 50},
  { price: 19.00, Qty : 50},
  { price: 18.55, Qty : 40},
  { price: 18.85, Qty : 50},
  { price: 18.65, Qty : 10},
];

const AAPLAsk = [
  { price: 17.95, Qty : 100},
  { price: 16.55, Qty : 30},
  { price: 16.95, Qty : 20},
  { price: 20.95, Qty : 40},
  { price: 22.95, Qty : 60},
];


// Store listitems
const listItems = [];
// AAPLBid.sort((s1,s2)=>s1.price-s2.price);
// console.table(AAPLBid);

let tradeListStartIndex;

createList();
AskList();

// Insert list items into DOM
function createList() {
  // [...AAPLBid]
  //   .map(a => ({ value: a, sort: Math.random() }))
  //   .sort((a, b) => a.sort - b.sort)
  //   .map(a => a.value)
  //   .forEach((person, index) => {
     AAPLBid.sort((a, b) => b.price - a.price);

    AAPLBid.forEach((stock, index) => {
      const listItem = document.createElement('li');

      listItem.setAttribute('data-index', index);
        // <span class="number">${index + 5}</span>
      listItem.innerHTML = `
        <span class="number">${5 - index }</span>
        <div class="tradeListgable" tradeListgable="true">
        <p class="stock-name">Price: $${stock.price.toFixed(2)} | Qty: ${stock.Qty}</p> 
        <i class="fas fa-grip-lines"></i>
        </div>
      `;

      listItems.push(listItem);

      bidQueue.appendChild(listItem);
    });

  addEventListeners();
}

function AskList() {
  // [...AAPLBid]
  //   .map(a => ({ value: a, sort: Math.random() }))
  //   .sort((a, b) => a.sort - b.sort)
  //   .map(a => a.value)
  //   .forEach((person, index) => {
     AAPLAsk.sort((a, b) => a.price - b.price);

     AAPLAsk.forEach((stock, index) => {
      const listItem = document.createElement('li');

      listItem.setAttribute('data-index', index);
      listItem.innerHTML = `
        <span class="number">${index +1}</span>
        <div class="tradeListgable" tradeListgable="true">
        <p class="stock-name">Price: $${stock.price.toFixed(2)} | Qty: ${stock.Qty}</p> 
        <i class="fas fa-grip-lines"></i>
        </div>
      `;

      listItems.push(listItem);

      askQueue.appendChild(listItem);
    });
}


function tradeListStart() {
  // console.log('Event: ', 'tradeListstart');
  tradeListStartIndex = +this.closest('li').getAttribute('data-index');
}

function tradeListEnter() {
  // console.log('Event: ', 'tradeListenter');
  this.classList.add('over');
}

function tradeListLeave() {
  // console.log('Event: ', 'tradeListleave');
  this.classList.remove('over');
}

function tradeListOver(e) {
  // console.log('Event: ', 'tradeListover');
  e.preventDefault();
}

function tradeListDrop() {
  // console.log('Event: ', 'drop');
  const tradeListEndIndex = +this.getAttribute('data-index');
  swapItems(tradeListStartIndex, tradeListEndIndex);

  this.classList.remove('over');
}

// Swap list items that are tradeList and drop
// function swapItems(fromIndex, toIndex) {
//   const itemOne = listItems[fromIndex].querySelector('.tradeListgable');
//   const itemTwo = listItems[toIndex].querySelector('.tradeListgable');

//   listItems[fromIndex].appendChild(itemTwo);
//   listItems[toIndex].appendChild(itemOne);
// }
function swapItems(fromIndex, toIndex) {
  const itemOne = AAPLBid[fromIndex];
  AAPLBid[fromIndex] = AAPLBid[toIndex];
  AAPLBid[toIndex] = itemOne;
}

// Check the order of list items
// function checkOrder() {
//   listItems.forEach((listItem, index) => {
//     const personName = listItem.querySelector('.tradeListgable').innerText.trim();

//     if (personName !== AAPLBid[index]) {
//       listItem.classList.add('wrong');
//     } else {
//       listItem.classList.remove('wrong');
//       listItem.classList.add('right');
//     }
//   });
// }
function checkOrder() {
  listItems.forEach((listItem, index) => {
    const item = AAPLBid[index];
    const priceElem = listItem.querySelector('.price');
    const qtyElem = listItem.querySelector('.qty');
    const price = parseFloat(priceElem.innerText.split(': ')[1]);
    const qty = parseInt(qtyElem.innerText.split(': ')[1]);

    if (item.price === price && item.Qty === qty) {
      listItem.classList.remove('wrong');
      listItem.classList.add('right');
    } else {
      listItem.classList.add('wrong');
      listItem.classList.remove('right');
    }
  });
}

function addEventListeners() {
  const tradeListgables = document.querySelectorAll('.tradeListgable');
  const tradeListListItems = document.querySelectorAll('.BidList li');

  tradeListgables.forEach(tradeListgable => {
    tradeListgable.addEventListener('tradeListstart', tradeListStart);
  });

  tradeListListItems.forEach(item => {
    item.addEventListener('tradeListover', tradeListOver);
    item.addEventListener('drop', tradeListDrop);
    item.addEventListener('tradeListenter', tradeListEnter);
    item.addEventListener('tradeListleave', tradeListLeave);
  });
}

check.addEventListener('click', checkOrder);


    // Fetch and display data when the page loads
    fetch("http://localhost:8081/transactions/atAuctionOrders?stockId=TSLA")
      .then(response => response.json())
      .then(data => {
        // Extract data from the JSON response
        const bidOrders = data.bidOrders;
        const askOrders = data.askOrders;
  // const bidOrdersPrice = data.bidOrders.price;
  // const bidOrdersQuantity = data.bidOrders.quantity;
  // const askOrdersPrice = data.askOrders.price;
  // const askOrdersQuantity = data.askOrders.quantity;

        // Update the chat with the extracted data
        updateChat(bidOrders, askOrders);
      })
      .catch(error => {
        console.error("Error fetching data:", error);
      });

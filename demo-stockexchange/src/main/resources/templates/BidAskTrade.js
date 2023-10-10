const tradeForm = document.getElementById("trade-form");
const priceInput = document.getElementById("price");
const quantityInput = document.getElementById("quantity");
const increasePriceButton = document.getElementById("increase-price");
const decreasePriceButton = document.getElementById("decrease-price");
const increaseQuantityButton = document.getElementById("increase-quantity");
const decreaseQuantityButton = document.getElementById("decrease-quantity");
const chooseStockButton = document.getElementById("choose-stock");
const selectedStockSpan = document.getElementById("selected-stock");

let selectedStock = null;

document.addEventListener("DOMContentLoaded", () => {
  chooseStockButton.addEventListener("click", () => {
    // You can implement logic to choose a stock here
    selectedStock = "AAPL"; // Replace with your logic to select a stock
    selectedStockSpan.textContent = selectedStock;
  });

  increasePriceButton.addEventListener("click", () => {
    priceInput.value = parseFloat(priceInput.value) + 10;
  });

  decreasePriceButton.addEventListener("click", () => {
    priceInput.value = parseFloat(priceInput.value) - 10;
  });

  increaseQuantityButton.addEventListener("click", () => {
    quantityInput.value = parseInt(quantityInput.value) + 10;
  });

  decreaseQuantityButton.addEventListener("click", () => {
    quantityInput.value = parseInt(quantityInput.value) - 10;
  });

  tradeForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const tradeType = document.getElementById("trade-type").value;
    const stockCode = selectedStock;
    const price = parseFloat(priceInput.value);
    const quantity = parseInt(quantityInput.value);

    // You can use the tradeType, stockCode, price, and quantity for further processing or submission.
    console.log("Trade Type : ", tradeType);
    console.log("Stock Code : ", stockCode);
    console.log("Price : ", price);
    console.log("Quantity : ", quantity);
  });
});

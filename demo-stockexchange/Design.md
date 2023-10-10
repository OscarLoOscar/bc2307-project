1. Narrow down the requirements and define the system

- should be able to trade or place order
- should be able to see latest stock prices
- should be able to see profolio

- highly available 
- highly scalable
- low latency
- Single region

-Wallet


2. Api design
- PlaceOrder ( customerId , exchangeCode , stockTicker , type, quantity , placedAt)
- FetchStock(stockTickers)
- FetchPortfolio(customerId)
- SetTrigger(customerId , exchangeCode , stockerTicker , type , quantity , threshold , condition)


<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JSON Data Presentation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h1 {
            text-align: center;
        }

        .container {
            display: flex;
            justify-content: space-around;
        }

        .stock {
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            margin: 10px;
        }

        .order-type {
            font-weight: bold;
            margin-bottom: 10px;
        }

        .order {
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
    <h1>Stock Exchange Data</h1>
    <p>Data: ${response}</p>
    <div class="container" th:each="stock:${stocks}">
        <div class="stock">
            <h2>${stock.name}</h2>
            <div class="order-type">Ask Orders:</div>
            <div th:each="askOrder:${stock.askOrders}" class="order">
                <strong>Price:</strong> ${askOrder.price}<br>
                <strong>Quantity:</strong> ${askOrder.quantity}
            </div>

            <div class="order-type">Bid Orders:</div>
            <div th:each="bidOrder:${stock.bidOrders}" class="order">
                <strong>Price:</strong> ${bidOrder.price}<br>
                <strong>Quantity:</strong> ${bidOrder.quantity}
            </div>
        </div>
    </div>
</body>
</html>

package com.example.demo.demostockexchange.services.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.demostockexchange.entity.Orders;
import com.example.demo.demostockexchange.infra.tradeType;
import com.example.demo.demostockexchange.model.OrderRequest;
import com.example.demo.demostockexchange.model.OrderResp;
import com.example.demo.demostockexchange.model.BuyerVsSeller.BuyerSellerData;
import com.example.demo.demostockexchange.model.mapper.FinnhubMapper;
import com.example.demo.demostockexchange.repository.StockRepository;
import com.example.demo.demostockexchange.services.OrderBookService;

@Service
public class OrderBookServiceImpl implements OrderBookService {

  @Autowired
  FinnhubMapper finnhubMapper;

  @Autowired
  StockRepository stockRepository;

  @Override
  public List<Orders> getOrderBook() {
    return stockRepository.findAll();
  }

  @Override
  public void addOrder(String symbol, OrderRequest makeOrder) {
    Orders response = finnhubMapper.requestToOrdersEntity(symbol, makeOrder);
    stockRepository.save(response);
  }

  // @Override
  // public List<OrderResp> getBidQueue(String stockId) {
  //   List<Orders> data = this.getOrderBook();
  //   List<Orders> response = new ArrayList<>();
  //   for (Orders order : data) {
  //     if (tradeType.BID.name().toLowerCase()
  //         .equals(order.getType().toLowerCase())
  //         && order.getStockId().equals(stockId)) {
  //       response.add(order);
  //     }
  //   } // Queue<OrderRequest> buyOrders = new PriorityQueue<>(
  //     // (b1, b2) -> Double.compare(b2.getPrice(), b1.getPrice())); // Descending order by price
  //   return finnhubMapper.mapToResp(response);

  // }

  @Override
  public List<OrderResp> getBidQueue(String stockId) {
    List<Orders> data = this.getOrderBook();
    Map<Double, Integer> priceToQuantityMap = new TreeMap<>(Comparator.reverseOrder());

    for (Orders order : data) {
        if (tradeType.BID.name().equalsIgnoreCase(order.getType()) && order.getStockId().equals(stockId)) {
            double price = order.getPrice();
            int quantity = order.getQuantity();
            
            // Check if the price already exists in the map
            if (priceToQuantityMap.containsKey(price)) {
                // If the price exists, add the quantity to the existing value
                int existingQuantity = priceToQuantityMap.get(price);
                priceToQuantityMap.put(price, existingQuantity + quantity);
            } else {
                // If the price is new, simply put it in the map
                priceToQuantityMap.put(price, quantity);
            }
        }
    }

    List<OrderResp> response = new ArrayList<>();
    for (Map.Entry<Double, Integer> entry : priceToQuantityMap.entrySet()) {
        double price = entry.getKey();
        int quantity = entry.getValue();

        // Create an OrderResp object with the price and summed quantity
        OrderResp orderResp = OrderResp.builder()
                .type(tradeType.BID.name())
                .localTime(LocalTime.now().toString())
                .price(price)
                .quantity(quantity)
                .build();

        response.add(orderResp);
    }

    return response;
}

  @Override
  public List<OrderResp> getAskQueue(String stockId) {
    List<Orders> data = this.getOrderBook();
    Map<Double, Integer> priceToQuantityMap = new TreeMap<>();

    for (Orders order : data) {
        if (tradeType.ASK.name().equalsIgnoreCase(order.getType()) && order.getStockId().equals(stockId)) {
            double price = order.getPrice();
            int quantity = order.getQuantity();
            
            // Check if the price already exists in the map
            if (priceToQuantityMap.containsKey(price)) {
                // If the price exists, add the quantity to the existing value
                int existingQuantity = priceToQuantityMap.get(price);
                priceToQuantityMap.put(price, existingQuantity + quantity);
            } else {
                // If the price is new, simply put it in the map
                priceToQuantityMap.put(price, quantity);
            }
        }
    }

    List<OrderResp> response = new ArrayList<>();
    for (Map.Entry<Double, Integer> entry : priceToQuantityMap.entrySet()) {
        double price = entry.getKey();
        int quantity = entry.getValue();

        // Create an OrderResp object with the price and summed quantity
        OrderResp orderResp = OrderResp.builder()
                .type(tradeType.ASK.name())
                .localTime(LocalTime.now().toString())
                .price(price)
                .quantity(quantity)
                .build();

        response.add(orderResp);
    }

    return response;
}

  // @Override
  // public List<OrderResp> getAskQueue(String stockId) {
  //   List<Orders> data = this.getOrderBook();
  //   List<Orders> response = new ArrayList<>();
  //   for (Orders order : data) {
  //     if (tradeType.ASK.name().toLowerCase()
  //         .equals(order.getType().toLowerCase())
  //         && order.getStockId().equals(stockId)) {
  //       response.add(order);
  //     }
  //   } // Queue<OrderRequest> buyOrders = new PriorityQueue<>(
  //     // (b1, b2) -> Double.compare(b2.getPrice(), b1.getPrice())); // Descending order by price
  //   return finnhubMapper.mapToResp(response);

  // }


  // @Override
  // public List<StockExchange> atAuctionOrders(String stockId) {
  // List<StockExchange> response = new ArrayList<>();

  // List<OrderResp> bidQueue = this.getBidQueue(stockId);
  // List<OrderResp> askQueue = this.getAskQueue(stockId);

  // response.get(0).add(bidQueue);
  // return response;

  // }

  @Override
  @Transactional(readOnly = true)
  public BuyerSellerData calculateBuyerSellerIndicator() {
    List<Orders> allTrades = stockRepository.findAll();
    int buyerVolume = 0;
    int sellerVolume = 0;

    for (Orders trade : allTrades) {
      if (trade.getQuantity() > 0 && ("bid".equals(trade.getType()))) {
        buyerVolume += trade.getQuantity();
      } else {
        sellerVolume += Math.abs(trade.getQuantity());
      }
    }

    return new BuyerSellerData(buyerVolume, sellerVolume);
  }

  // @Override
  // public String executeTrades(OrderResp orderResp) {
  // synchronized (this) {
  // for (OrderResp BidOrder : this.getBidQueue(orderResp.getSymbol())) {
  // for (OrderResp AskOrder : this.getAskQueue(orderResp.getSymbol())) {
  // if (BidOrder.getSymbol().equals(AskOrder.getSymbol())
  // && BidOrder.getPrice() >= AskOrder.getPrice()
  // && BidOrder.getQuantity() > 0 && AskOrder.getQuantity() > 0) {
  // int tradedQuantity =
  // Math.min(BidOrder.getQuantity(), AskOrder.getQuantity());
  // double tradePrice = AskOrder.getPrice(); // Use the seller's price
  // System.out
  // .println("Trade executed - Symbol: " + BidOrder.getSymbol() + //
  // ", Price: " + tradePrice + //
  // ", Quantity: " + tradedQuantity);//
  // Integer BidOrderQuantity = BidOrder.getQuantity();
  // Integer AskOrderQuantity = AskOrder.getQuantity();
  // BidOrderQuantity -= tradedQuantity;
  // AskOrderQuantity -= tradedQuantity;
  // }
  // }
  // }
  // }
  // return "Trade executed successfully";
  // }

}



// PriorityQueue<OrderResp> newQueue = new PriorityQueue<>((o1, o2) -> {
// int priceComparison = Double.compare(o2.getPrice(), o1.getPrice());
// if (priceComparison != 0) {
// // If prices are different, prioritize by price
// return priceComparison;
// } else {
// // If prices are equal, prioritize by timestamp (tradeDateTime)
// return o2.getQuantity().compareTo(o1.getQuantity());
// }
// });


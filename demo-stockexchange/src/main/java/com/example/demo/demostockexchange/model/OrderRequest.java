package com.example.demo.demostockexchange.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.demostockexchange.infra.TransactionType;
import com.example.demo.demostockexchange.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@AllArgsConstructor
// @NoArgsConstructor
@Getter
@ToString
@Setter
@Slf4j
public class OrderRequest {

  @Autowired
  TransactionRepository transactionRepository;

  TransactionType action; // 'Buy','Sell'

  String orderType;
  // @JsonFormat(locale = "zh", timezone = "GMT+8",
  // pattern = "yyyy-MM-dd HH:mm:ss")
  // @DateTimeFormat
  // @Column(name = "placedAt")
  // private LocalDate tradeDate = LocalDate.now();
  // String stockId;

  double price;

  private int quantity;

  // private long totalOrderValue ;

  @Builder.Default
  public static Map<Double, Integer> bidOffers =
      new TreeMap<>(Comparator.reverseOrder());
  @Builder.Default
  public static Map<Double, Integer> askOffers = new TreeMap<>();

  synchronized public void onOrder(double price, int quantity, String action) {
    boolean isBidAction = true;
    if (action.equals(TransactionType.BUY.name())) {
      isBidAction = true;
      matchOrder(price, quantity, isBidAction);
      log.info("BUY bidOffers : " + bidOffers.get(price));
      log.info("BUY askOffers : " + askOffers.get(price));

    } else if (action.equals(TransactionType.SELL.name())) {
      isBidAction = false;
      matchOrder(price, quantity, isBidAction);
      log.info("SELL bidOffers : " + bidOffers.get(price));
      log.info("SELL askOffers : " + askOffers.get(price));

    }
  }


  @Transactional
  synchronized private void matchOrder(double price, int quantity,
      boolean isBidAction) {
    Set<Double> bidPrices = new HashSet<>(bidOffers.keySet());
    Set<Double> askPrices = new HashSet<>(askOffers.keySet());

    for (double bidPrice : bidPrices) {
      for (double askPrice : askPrices) {
        if (bidPrice == price && askPrice == price) {
          int askQuantity = askOffers.get(askPrice);
          int bidQuantity = bidOffers.get(bidPrice);

          if (askQuantity >= quantity) {
            // Match the entire offer
            askOffers.put(askPrice, askQuantity - quantity);
            bidOffers.put(bidPrice, bidQuantity + quantity);
            return;
          } else {
            // Match part of the offer
            askOffers.remove(askPrice);
            bidOffers.put(bidPrice, bidQuantity + askQuantity);
            quantity -= askQuantity;
          }
        }
      }
    }

    // If there's remaining quantity, add a resting order
    if (isBidAction) {
      addBidRestingOrder(price, quantity);
    } else {
      addAskRestingOffer(price, quantity);
    }
  }
  // @Transactional
  // synchronized private void matchOrder(double price, int quantity, boolean isBidAction) {
  // Set<Double> bidPrices = new HashSet<>(bidOffers.keySet());
  // Set<Double> askPrices = new HashSet<>(askOffers.keySet());

  // for (double bidPrice : bidPrices) {
  // for (double askPrice : askPrices) {
  // if (bidPrice == price && askPrice == price) {
  // int askQuantity = askOffers.get(askPrice);
  // int bidQuantity = bidOffers.get(bidPrice);

  // if (askQuantity >= quantity) {
  // // Match the entire offer
  // askOffers.put(askPrice, askQuantity - quantity);
  // bidOffers.put(bidPrice, bidQuantity + quantity);
  // return;
  // } else {
  // // Match part of the offer
  // askOffers.remove(askPrice);
  // bidOffers.put(bidPrice, bidQuantity + askQuantity);
  // quantity -= askQuantity;
  // }
  // }
  // }
  // }

  // // If there's remaining quantity, add a resting order
  // if (isBidAction) {
  // addBidRestingOrder(price, quantity);
  // } else {
  // addAskRestingOffer(price, quantity);
  // }
  // }

  // public void onOrder(double price, int quantity, boolean isBidAction) {
  // if (TransactionType.BUY.name().toLowerCase().equals(side)) {
  // this.bidOffers.put(price, quantity);
  // Set<Double> ask_prices = this.askOffers.keySet();
  // List<Double> ask_prices_list = new ArrayList<>(ask_prices);
  // for (double ask_price : ask_prices_list) {
  // if (quantity > 0 && price >= ask_price) {
  // int ask_quantity = this.askOffers.get(ask_price);
  // if (quantity >= ask_quantity) {
  // quantity = quantity - ask_quantity;
  // removeAskOrder(ask_price, ask_quantity);
  // } else {
  // removeAskOrder(ask_price, quantity);
  // quantity = 0;
  // }
  // if (quantity == 0) {
  // break;
  // }
  // }
  // }
  // if (quantity > 0) {
  // addBidRestingOrder(price, quantity);
  // }
  // } else if (TransactionType.SELL.name().toLowerCase().equals(side)) {
  // this.askOffers.put(price, quantity);
  // Set<Double> bid_prices = this.bidOffers.keySet();
  // List<Double> bid_prices_list = new ArrayList<>(bid_prices);
  // for (double bid_price : bid_prices_list) {
  // if (quantity > 0 && price <= bid_price) {
  // int bid_quantity = this.bidOffers.get(bid_price);
  // if (quantity >= bid_quantity) {
  // quantity = quantity - bid_quantity;
  // this.removeBidOrder(bid_price, bid_quantity);
  // } else {
  // this.removeBidOrder(bid_price, quantity);
  // quantity = 0;
  // }
  // if (quantity == 0) {
  // break;
  // }
  // }

  // }
  // if (quantity > 0) {
  // this.addAskRestingOffer(price, quantity);
  // }
  // }
  // }
  synchronized private void removeOrder(double price, int quantity,
      boolean isBidAction) {
    if (isBidAction) {
      // Remove the ask order
      askOffers.remove(price);
      // Implement the necessary logic for your order removal
    } else {
      // Remove the bid order
      bidOffers.remove(price);
      // Implement the necessary logic for your order removal
    }
  }

  synchronized void addBidRestingOrder(double price, int quantity) {
    this.bidOffers.put(price, quantity);
  }

  synchronized void removeBidOrder(double price, int quantity) {
    int lastQuantity = this.bidOffers.get(price);
    if (lastQuantity == quantity) {
      this.bidOffers.remove(price);
    } else {
      this.bidOffers.put(price, lastQuantity - quantity);
    }
  }

  synchronized void addAskRestingOffer(double price, int quantity) {
    this.askOffers.put(price, quantity);
  }

  synchronized void removeAskOrder(double price, int quantity) {
    int lastQuantity = this.askOffers.get(price);
    if (lastQuantity == quantity) {
      this.askOffers.remove(price);
    } else {
      this.askOffers.put(price, lastQuantity - quantity);
    }
  }

  public int getAskLevel() {
    return this.askOffers.size();
  }

  public int getBidLevel() {
    return this.bidOffers.size();
  }

}

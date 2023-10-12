package com.example.demo.demostockexchange.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import com.example.demo.demostockexchange.infra.tradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
public class OrderRequest {
  String type; // 'Bid','Ask'

  // @JsonFormat(locale = "zh", timezone = "GMT+8",
  // pattern = "yyyy-MM-dd HH:mm:ss")
  // @DateTimeFormat
  // @Column(name = "placedAt")
  // private LocalDate tradeDate = LocalDate.now();
  // String stockId;

  Double price;

  private Integer quantity;

  private Map<Double, Integer> bidOffers =
      new TreeMap<>(Comparator.reverseOrder());
  private Map<Double, Integer> askOffers = new TreeMap<>();

  public void onOrder(double price, int quantity, String side) {
    if (tradeType.BID.name().equals(side)) {
      Set<Double> ask_prices = askOffers.keySet();
      List<Double> ask_prices_list = new ArrayList<>(ask_prices);
      for (double ask_price : ask_prices_list) {
        if (quantity > 0 && price >= ask_price) {
          int ask_quantity = askOffers.get(ask_price);
          if (quantity >= ask_quantity) {
            quantity = quantity - ask_quantity;
            removeAskOrder(ask_price, ask_quantity);
          } else {
            removeAskOrder(ask_price, quantity);
            quantity = 0;
          }
          if (quantity == 0) {
            break;
          }
        }
      }
      if (quantity > 0) {
        addBidRestingOrder(price, quantity);
      }
    } else {
      Set<Double> bid_prices = bidOffers.keySet();
      List<Double> bid_prices_list = new ArrayList<>(bid_prices);
      for (double bid_price : bid_prices_list) {
        if (quantity > 0 && price <= bid_price) {
          int bid_quantity = bidOffers.get(bid_price);
          if (quantity >= bid_quantity) {
            quantity = quantity - bid_quantity;
            removeBidOrder(bid_price, bid_quantity);
          } else {
            removeBidOrder(bid_price, quantity);
            quantity = 0;
          }
          if (quantity == 0) {
            break;
          }
        }

      }
      if (quantity > 0) {
        addAskRestingOffer(price, quantity);
      }
    }
  }


  synchronized void addBidRestingOrder(double price, int quantity) {
    bidOffers.put(price, quantity);
  }

  synchronized void removeBidOrder(double price, int quantity) {
    int lastQuantity = bidOffers.get(price);
    if (lastQuantity == quantity) {
      bidOffers.remove(price);
    } else {
      bidOffers.put(price, lastQuantity - quantity);
    }
  }

  synchronized void addAskRestingOffer(double price, int quantity) {
    askOffers.put(price, quantity);
  }

  synchronized void removeAskOrder(double price, int quantity) {
    int lastQuantity = askOffers.get(price);
    if (lastQuantity == quantity) {
      askOffers.remove(price);
    } else {
      askOffers.put(price, lastQuantity - quantity);
    }
  }

  public int getAskLevel() {
    return askOffers.size();
  }

  public int getBidLevel() {
    return bidOffers.size();
  }

}

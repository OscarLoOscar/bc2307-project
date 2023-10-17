package com.example.demo.demostockexchange.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import com.example.demo.demostockexchange.infra.Action;
import com.example.demo.demostockexchange.infra.OrderType;
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
  String action; // 'Bid','Ask'

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

  private Map<Double, Integer> bidOffers =
      new TreeMap<>(Comparator.reverseOrder());
  private Map<Double, Integer> askOffers = new TreeMap<>();

  public void onOrder(double price, int quantity, String side) {
    if (Action.BUY.name().toLowerCase().equals(side)) {
      this.bidOffers.put(price, quantity);
      Set<Double> ask_prices = this.askOffers.keySet();
      List<Double> ask_prices_list = new ArrayList<>(ask_prices);
      for (double ask_price : ask_prices_list) {
        if (quantity > 0 && price >= ask_price) {
          int ask_quantity = this.askOffers.get(ask_price);
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
    } else if (Action.SELL.name().toLowerCase().equals(side)) {
      this.askOffers.put(price, quantity);
      Set<Double> bid_prices = this.bidOffers.keySet();
      List<Double> bid_prices_list = new ArrayList<>(bid_prices);
      for (double bid_price : bid_prices_list) {
        if (quantity > 0 && price <= bid_price) {
          int bid_quantity = this.bidOffers.get(bid_price);
          if (quantity >= bid_quantity) {
            quantity = quantity - bid_quantity;
            this.removeBidOrder(bid_price, bid_quantity);
          } else {
            this.removeBidOrder(bid_price, quantity);
            quantity = 0;
          }
          if (quantity == 0) {
            break;
          }
        }

      }
      if (quantity > 0) {
        this.addAskRestingOffer(price, quantity);
      }
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
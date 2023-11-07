package com.example.demo.demostockexchange.trade;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.demostockexchange.model.Order;
import com.example.demo.demostockexchange.model.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class BuyAndMarket implements Tradable {

  private Order entry;

  @Override
  @Transactional
  public void trade(Order entry) {
      if (entry.getPrice() == null || entry.getShare() == null) {
          throw new IllegalArgumentException("Price and share must not be null.");
      }
      if (entry.getPrice().doubleValue() == 0 || entry.getShare().intValue() == 0) {
          return; // Skip if price or share is zero.
      }
  
      int restToBuy = entry.getShare();
      while (restToBuy > 0) {
          Entry<Double, Integer> head = OrderRequest.askOffers.firstEntry();
          if (head == null || head.getValue() == 0) {
              break; // No more available offers
          }
          
          // Buy as much as possible from the best available price
          int availableQuantity = head.getValue();
          int quantityToBuy = Math.min(availableQuantity, restToBuy);
          
          // Update the quantities in bid and ask offers
          head.setValue(availableQuantity - quantityToBuy);
          OrderRequest.bidOffers.merge(head.getKey(), quantityToBuy, Integer::sum);
          
          // Decrease the remaining quantity to buy
          restToBuy -= quantityToBuy;
      }
      
      cleanup();
  }
  
  private void cleanup() {
    int sellBookSize = OrderRequest.askOffers.size();
    int buyBookSize = OrderRequest.bidOffers.size();
    if (sellBookSize >= 6 && buyBookSize >= 6) {
      // Both sellbook and buybook have sufficient entries, no need to clean up.
      return;
    }

    // Check if sellBook is under the required size
    if (sellBookSize < 6) {
      Double sellLast = OrderRequest.askOffers.lastKey();
      if (sellLast == null) {
        // start with a default price
        sellLast = 50.0;
      }
      fillTheQueue(OrderRequest.askOffers, 6 - sellBookSize, sellLast + 0.01);
    }

    // Check if buyBook is under the required size
    if (buyBookSize < 6) {
      if (!OrderRequest.bidOffers.isEmpty()) {
        Double buyLast = OrderRequest.bidOffers.lastKey();
        if (buyLast == null) {
          // start with a default price
          buyLast = 50.0;
        }
        fillTheQueue(OrderRequest.bidOffers, 6 - buyBookSize, buyLast - 0.01);
      }
    }
  }

  private void fillTheQueue(Map<Double, Integer> book, int size,
      Double lastPrice) {
    double step = book.equals(OrderRequest.bidOffers) ? -0.01 : 0.01;
    while (size > 0) {
      lastPrice += step;
      book.put(lastPrice, 0);
      size--;
    }
  }
}

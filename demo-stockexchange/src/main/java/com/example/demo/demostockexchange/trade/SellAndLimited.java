package com.example.demo.demostockexchange.trade;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import com.example.demo.demostockexchange.model.Order;
import com.example.demo.demostockexchange.model.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SellAndLimited implements Tradable {

  private Order entry;

  @Override
  public void trade(Order entry) {
    if (entry.getPrice() == null || entry.getShare() == null) {
      throw new IllegalArgumentException("Price and share must not be null.");
    }
    if (entry.getPrice().doubleValue() < 0.0d || entry.getShare().intValue() <= 0) {
      return; // Skip if price is negative or share is zero or negative.
    }

    if (OrderRequest.bidOffers.isEmpty()
        || entry.getPrice().compareTo(OrderRequest.askOffers.keySet().stream()
            .min(Comparator.naturalOrder()).orElse(0.0)) < 0) {
      placeBook(entry.getPrice(), entry.getShare());
      cleanup();
      return;
    }

    handleMatchingOrders(entry);

    cleanup();
  }

  private void placeBook(Double price, Integer share) {
    OrderRequest.askOffers.put(price, share);
  }

  private void handleMatchingOrders(Order entry) {
    Iterator<Map.Entry<Double, Integer>> bidIterator = OrderRequest.bidOffers.entrySet().iterator();

    while (bidIterator.hasNext()) {
        Map.Entry<Double, Integer> bidEntry = bidIterator.next();
        Double bidPrice = bidEntry.getKey();
        Integer bidShare = bidEntry.getValue();

        if (entry.getPrice() <= bidPrice) {
            if (entry.getShare() >= bidShare) {
                entry.setShare(entry.getShare() - bidShare);
                bidIterator.remove(); // Remove the matched bid entry

                // Update askOffers
                OrderRequest.askOffers.put(bidPrice, bidShare);

                log.info("Sell And Limit Matched order - Price: " + bidPrice + ", Share: " + bidShare);
            } else {
                // Update bidOffers with the remaining shares
                bidEntry.setValue(bidShare - entry.getShare());

                // Update askOffers
                OrderRequest.askOffers.put(bidPrice, entry.getShare());

                log.info("Sell And Limit Partial Match - Price: " + bidPrice + ", Share: " + entry.getShare());
                entry.setShare(0); // No more shares to sell
                break; // No need to continue checking for more matches
            }
        }
    }
  }

  private void cleanup() {
    int sellBookSize = OrderRequest.askOffers.size();
    int buyBookSize = OrderRequest.bidOffers.size();
    if (sellBookSize >= 6 && buyBookSize >= 6) {
      return; // Both sell book and buy book have sufficient entries, no need to clean up.
    }
    // Check if sell book is under the required size
    if (sellBookSize < 6) {
      // Get the last price from the sell book
      Double sellLast = OrderRequest.askOffers.lastKey();
      if (sellLast == null) {
        // Start with a default price
        sellLast = 50.0;
      }
      // Fill the sell book with entries until it has at least 6 entries
      fillTheQueue(OrderRequest.askOffers, 6 - sellBookSize, sellLast + 0.01);
    }

    // Check if buy book is under the required size
    if (buyBookSize < 6) {
      // Get the last price from the buy book
      Double buyLast = OrderRequest.bidOffers.lastKey();
      if (buyLast == null) {
        // Start with a default price
        buyLast = 50.0;
      }
      // Fill the buy book with entries until it has at least 6 entries
      fillTheQueue(OrderRequest.bidOffers, 6 - buyBookSize, buyLast - 0.01);
    }
  }

  private void fillTheQueue(Map<Double, Integer> book, int size,
      Double lastPrice) {
    double step = book.equals(OrderRequest.askOffers) ? 0.01 : -0.01;
    while (size > 0) {
      lastPrice += step;
      book.put(lastPrice, 0);
      size--;
    }
  }
}

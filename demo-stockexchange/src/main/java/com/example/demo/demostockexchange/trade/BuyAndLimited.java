package com.example.demo.demostockexchange.trade;

import java.util.Map;
import java.util.Map.Entry;
import com.example.demo.demostockexchange.model.Order;
import java.util.Comparator;
import java.util.Iterator;
import com.example.demo.demostockexchange.model.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class BuyAndLimited implements Tradable {

  private Order entry;

  @Override
  public void trade(Order entry) {
    if (entry.getPrice().doubleValue() == 0
        || entry.getShare().intValue() == 0) {
      throw new IllegalArgumentException();
    }
    if (entry.getPrice().doubleValue() < 0.0d
        || entry.getShare().intValue() <= 0) {
      return;
    }

    if (OrderRequest.askOffers.isEmpty()
        || entry.getPrice().compareTo(OrderRequest.askOffers.keySet().stream()
            .min(Comparator.naturalOrder()).orElse(0.0)) >= 0) {
      placeBook(entry.getPrice(), entry.getShare());
      cleanup();
      return;
    }
    handleMatchingOrders(entry);

    cleanup();
  }

  private void placeBook(Double price, Integer share) {
    OrderRequest.bidOffers.put(price, share);
  }

  private void handleMatchingOrders(Order entry) {
    Iterator<Map.Entry<Double, Integer>> askIterator = OrderRequest.askOffers.entrySet().iterator();

    while (askIterator.hasNext()) {
        Map.Entry<Double, Integer> askEntry = askIterator.next();
        Double askPrice = askEntry.getKey();
        Integer askShare = askEntry.getValue();

        if (entry.getPrice() <= askPrice) {
            if (entry.getShare() >= askShare) {
                entry.setShare(entry.getShare() - askShare);
                askIterator.remove(); // Remove the matched bid entry

                // Update askOffers
                OrderRequest.bidOffers.put(askPrice, askShare);

                log.info("Buy And Limit Matched order - Price: " + askPrice + ", Share: " + askShare);
            } else {
                // Update bidOffers with the remaining shares
                askEntry.setValue(askShare - entry.getShare());

                // Update askOffers
                OrderRequest.bidOffers.put(askPrice, entry.getShare());

                log.info("Buy And Limit Partial Match - Price: " + askPrice + ", Share: " + entry.getShare());
                entry.setShare(0); // No more shares to sell
                break; // No need to continue checking for more matches
            }
        }
    }
  }

  private void cleanup() {
    int sellBookSize = OrderRequest.askOffers.size();
    int buyBookSize = OrderRequest.bidOffers.size();
    if (sellBookSize >= 6 && buyBookSize >= 6)
      // Both sellbook and buybook have sufficient entries, no need to clean up.
      return;
    // Check if sellBook is under the required size
    if (sellBookSize < 6) {
      // get the last price from the sellbook
      Double buyLast = OrderRequest.bidOffers.lastKey();
      // if sellLast is null , it means the sellBook is empty
      if (buyLast == null) {
        // start with a default price
        buyLast = 50.0;
      }
      // fill the sellbook with entries it has at least 6 entries
      fillTheQueue(OrderRequest.bidOffers, 6 - buyBookSize, buyBookSize + 0.01);
    }

    // check if buybook is under the required size
    if (sellBookSize < 6) {
      // get the last price from the buybook
      Double sellLast = OrderRequest.askOffers.lastKey();

      // if buyLast is null , it means the buybook is empty
      if (sellLast == null) {
        // start with a default price
        sellLast = 50.0;
      }
      // fill the buybook with entries until it has at least 6 entries
      fillTheQueue(OrderRequest.askOffers, 6 - sellBookSize, sellLast - 0.01);
    }
  }

  private void fillTheQueue(Map<Double, Integer> book, int size,
      Double lastPrice) {
    while (size > 0) {
      lastPrice += (book.equals(OrderRequest.bidOffers) ? 0.01 : -0.01);
      book.put(lastPrice, 0);
      size--;
    }
  }
}

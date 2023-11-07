package com.example.demo.demostockexchange.trade;

import java.util.Map;
import java.util.Map.Entry;
import com.example.demo.demostockexchange.model.Order;
import java.util.Comparator;
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
      placeBook(OrderRequest.bidOffers, entry.getPrice(), entry.getShare());
      cleanup();
      return;
    }
    log.info("BuyandLimit Sell Book");

    Entry<Double, Integer> head =
        OrderRequest.askOffers.entrySet().iterator().next();
    int restToBuy = entry.getShare();

    while (entry.getPrice().compareTo(head.getKey()) >= 0) {
      if (restToBuy >= head.getValue()) {
        OrderRequest.askOffers.remove(head.getKey());
        restToBuy -= head.getValue();
        if (entry.getPrice().compareTo(head.getKey()) == 0) {
          head.setValue(restToBuy);
        } else {
          head.setValue(0);
        }
        OrderRequest.bidOffers.put(head.getKey(), head.getValue());
        log.info("askOffers size: " + OrderRequest.askOffers.size());
        log.info("bidOffers size: " + OrderRequest.bidOffers.size());
      } else {
        log.info("last ");
        head.setValue(head.getValue() - restToBuy);
        restToBuy = 0;
      }
      if (restToBuy <= 0)
        break;
      head = OrderRequest.askOffers.entrySet().iterator().next();
    }
    log.info("end askOffers size: " + OrderRequest.askOffers.size());
    log.info("end bidOffers size: " + OrderRequest.bidOffers.size());
    cleanup();
  }

  // Rest of the BuyAndLimited class remains the same
  // ...

  private void cleanup() {
    int sellBookSize = OrderRequest.askOffers.size();
    int buyBookSize = OrderRequest.bidOffers.size();
    if (sellBookSize >= 6 && buyBookSize >= 6)
      // Both sellbook and buybook have sufficient entries, no need to clean up.
      return;
    // Check if sellBook is under the required size
    if (sellBookSize < 6) {
      // get the last price from the sellbook
      Double sellLast = OrderRequest.askOffers.lastKey();
      // if sellLast is null , it means the sellBook is empty
      if (sellLast == null) {
        // start with a default price
        sellLast = 50.0;
      }
      // fill the sellbook with entries it has at least 6 entries
      fillTheQueue(OrderRequest.askOffers, 6 - sellBookSize, sellLast + 0.01);
    }

    // check if buybook is under the required size
    if (buyBookSize < 6) {
      // get the last price from the buybook
      Double buyLast = OrderRequest.bidOffers.lastKey();

      // if buyLast is null , it means the buybook is empty
      if (buyLast == null) {
        // start with a default price
        buyLast = 50.0;
      }
      // fill the buybook with entries until it has at least 6 entries
      fillTheQueue(OrderRequest.bidOffers, 6 - buyBookSize, buyLast - 0.01);
    }
  }

  private void fillTheQueue(Map<Double, Integer> book, int size,
      Double lastPrice) {
    while (size > 0) {
      lastPrice += (book == OrderRequest.askOffers ? 0.01 : -0.01);
      book.put(lastPrice, 0);
      size--;
    }
  }
}

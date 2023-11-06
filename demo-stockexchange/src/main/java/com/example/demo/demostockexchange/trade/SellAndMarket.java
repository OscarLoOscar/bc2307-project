package com.example.demo.demostockexchange.trade;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import com.example.demo.demostockexchange.model.Order;
import com.example.demo.demostockexchange.model.OrderRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SellAndMarket implements Tradable {

  private Order entry;

  @Override
  public void trade(Order entry) {
    if (entry.getPrice() == null || entry.getShare() == null)
      throw new IllegalArgumentException();
    if (entry.getPrice().doubleValue() == 0 || entry.getShare().intValue() == 0)
      return;

    if (OrderRequest.bidOffers.isEmpty()
        || entry.getPrice().compareTo(OrderRequest.bidOffers.keySet().stream()
            .min(Comparator.naturalOrder()).orElse(0.0)) > 0) {
      placeBook(OrderRequest.askOffers, entry.getPrice(), entry.getShare());
      cleanup();
      return;
    }

    // Market order logic: Sell shares at the best available price in the buybook
    Entry<Double, Integer> head =
        OrderRequest.bidOffers.entrySet().iterator().next();
    int restToSell = entry.getShare();
    while (entry.getPrice().compareTo(head.getKey()) >= 0) {
      if (restToSell >= head.getValue()) {
        OrderRequest.bidOffers.remove(head.getKey());
        restToSell -= head.getValue();
        head.setValue(0);
        OrderRequest.askOffers.put(head.getKey(), head.getValue());
        System.out.println("askOffers size: " + OrderRequest.askOffers.size());
        System.out.println("bidOffers size: " + OrderRequest.bidOffers.size());
      } else {
        System.out.println("last ");
        head.setValue(head.getValue() - restToSell);
        restToSell = 0;
      }
      if (restToSell <= 0)
        break;
      head = OrderRequest.bidOffers.entrySet().iterator().next();
    }

    cleanup();
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
      lastPrice += (book.equals(OrderRequest.askOffers) ? 0.01 : -0.01);
      book.put(lastPrice, 0);
      size--;
    }
  }
}

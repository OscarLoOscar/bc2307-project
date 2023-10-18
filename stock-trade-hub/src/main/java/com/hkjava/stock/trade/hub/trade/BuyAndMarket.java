package com.hkjava.stock.trade.hub.trade;

import java.util.Deque;
import com.hkjava.stock.trade.hub.model.Entry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BuyAndMarket implements Tradable {

  private Deque<Entry> buybook;
  private Deque<Entry> sellbook;

  @Override
  public void trade(Double orderPrice, Integer orderShare) {
    if (orderPrice == null || orderShare == null)
      throw new IllegalArgumentException();
    if (orderPrice.doubleValue() < 0.0d || orderShare.intValue() <= 0)
      return;

    if (this.sellbook.peek() == null
        || orderPrice.compareTo(this.sellbook.peek().getPrice()) < 0) {
      placeBook(this.buybook, orderPrice, orderShare);
      cleanup();
      return;
    }

    // Market order logic: Purchase shares at the best available price in the sellbook
    Entry head = this.sellbook.peek();
    int restToBuy = orderShare;
    while (orderPrice.compareTo(head.getPrice()) >= 0) {
      if (restToBuy >= head.getShare()) {
        head = this.sellbook.poll();
        restToBuy -= head.getShare();
        this.buybook.addFirst(head);
      } else {
        head.deductShare(restToBuy);
        restToBuy = 0;
      }
      if (restToBuy <= 0)
        break;
      head = this.sellbook.peek();
    }

    cleanup();
  }
  private void cleanup() {
    int sellBookSize = this.sellbook.size();
    int buyBookSize = this.buybook.size();
    if (sellBookSize >= 6 && buyBookSize >= 6)
      // Both sellbook and buybook have sufficient entries, no need to clean up.
      return;
    // Check if sellBook is under the required size
    if (sellBookSize < 6) {
      // get the last price from the sellbook
      Entry sellLast = this.sellbook.peekLast();
      // if sellLast is null , it means the sellBook is empty
      if (sellLast == null) {
        // start with a default price
        sellLast = new Entry(50.0, 0);
      }
      // fill the sellbook with entries it has at least 6 entries
      fillTheQueue(this.sellbook, 6 - sellBookSize, sellLast.getPrice() + 0.01);
    }

    // check if buybook is under the required size
    if (buyBookSize < 6) {
      // get the last price from the buybook
      Entry buyLast = this.buybook.peekLast();

      // if buyLast is null , it means the buybook is empty
      if (buyLast == null) {
        // start with a default price
        buyLast = new Entry(50.0, 0);
      }
      // fill the buybook with entries until it has at least 6 entries
      fillTheQueue(this.buybook, 6 - buyBookSize, buyLast.getPrice() - 0.01);
    }
  }

  private void fillTheQueue(Deque<Entry> book, int size, Double lastPrice) {
    // while (size < 6) {
    // lastPrice += 0.01;
    // this.sellbook.add(new Entry(lastPrice, 0));
    // size++;
    // }
    while (size > 0) {
      lastPrice += (book.equals(this.sellbook) ? 0.01 : -0.01);
      book.add(new Entry(lastPrice, 0));
      size--;

    }
  }

}

package com.example.demo.demostockexchange.trade;

import java.util.Deque;
import com.example.demo.demostockexchange.model.Entry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SellAndMarket implements Tradable {

  private Deque<Entry> buybook;
  private Deque<Entry> sellbook;

  @Override
  public void trade(Double orderPrice, Integer orderShare) {
    if (orderPrice == null || orderShare == null)
      throw new IllegalArgumentException();
    if (orderPrice.doubleValue() < 0.0d || orderShare.intValue() <= 0)
      return;

    if (this.buybook.peek() == null
        || orderPrice.compareTo(this.buybook.peek().getPrice()) > 0) {
      placeBook(this.sellbook, orderPrice, orderShare);
      cleanup();
      return;
    }

    // Market order logic: Sell shares at the best available price in the buybook
    Entry head = this.buybook.peek();
    int restToSell = orderShare;
    while (orderPrice.compareTo(head.getPrice()) >= 0) {
      if (restToSell >= head.getShare()) {
        head = this.buybook.poll();
        restToSell -= head.getShare();
        this.sellbook.addFirst(head);
      } else {
        head.deductShare(restToSell);
        restToSell = 0;
      }
      if (restToSell <= 0)
        break;
      head = this.buybook.peek();
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

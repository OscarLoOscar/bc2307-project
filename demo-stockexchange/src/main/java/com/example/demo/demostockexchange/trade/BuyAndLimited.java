package com.example.demo.demostockexchange.trade;

import java.util.Deque;
import com.example.demo.demostockexchange.model.Entry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BuyAndLimited implements Tradable {

  private Deque<Entry> buybook; // descending 51.5 ,51.25 ,51.0

  private Deque<Entry> sellbook; // ascending 51.75 , 52.0 , 52.25

  @Override
  public void trade(Double orderPrice, Integer orderShare) {
    if (orderPrice == null || orderShare == null )
      throw new IllegalArgumentException();
    if (orderPrice.doubleValue() < 0.0d || orderShare.intValue() <= 0)
      return;

    // Order Price not matching existing sell book.
    // Place it to Buy Book and return
    if (this.sellbook.isEmpty()
        || orderPrice.compareTo(this.sellbook.peek().getPrice()) >= 0) {
      placeBook(this.buybook, orderPrice, orderShare);
      cleanup();
      return;
    }
    System.out.println("BuyandLimit Sell Book");
    // loop to check sellbook and add back to buyBook
    Entry head = this.sellbook.peek();// peek head
    int restToBuy = orderShare;
    while (orderPrice.compareTo(head.getPrice()) >= 0) {
      if (restToBuy >= head.getShare()) {
        head = this.sellbook.poll(); // update head
        restToBuy -= head.getShare();
        if (orderPrice.compareTo(head.getPrice()) == 0) {
          head.setShare(restToBuy);
        } else {
          head.clearShare();
        }
        this.buybook.addFirst(head);
        System.out.println("sellbook size : " + this.sellbook.size());
        System.out.println("buybook size : " + this.buybook.size());
      } else {
        System.out.println("last ");
        head.deductShare(restToBuy);
        restToBuy = 0;
      }
      if (restToBuy <= 0)
        break;
      head = this.sellbook.peek();
    }
    System.out.println("end sellbook size : " + this.sellbook);
    System.out.println("end buybook size : " + this.buybook);
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

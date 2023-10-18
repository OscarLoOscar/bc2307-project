package com.hkjava.stock.trade.hub;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.hkjava.stock.trade.hub.enums.Action;
import com.hkjava.stock.trade.hub.enums.OrderType;
import com.hkjava.stock.trade.hub.model.Book;
import com.hkjava.stock.trade.hub.model.Entry;
import com.hkjava.stock.trade.hub.model.Order;
import com.hkjava.stock.trade.hub.model.Stock;

public class TradeFactoryTest {

  Stock stock;

  Book buyBook;

  Book sellBook;

  @BeforeEach
  void init() {
    List<Entry> buyEntries = new LinkedList<>();
    buyEntries.add(new Entry(0.84d, 1000));
    buyEntries.add(new Entry(0.87d, 400));
    buyEntries.add(new Entry(0.85d, 200));
    buyEntries.add(new Entry(0.86d, 0));
    this.buyBook = Book.builder().entries(buyEntries).build();

    List<Entry> sellEntries = new LinkedList<>();
    sellEntries.add(new Entry(0.88d, 0));
    sellEntries.add(new Entry(0.90d, 400));
    sellEntries.add(new Entry(0.91d, 200));
    sellEntries.add(new Entry(0.89d, 0));
    this.sellBook = Book.builder().entries(sellEntries).build();

    stock = Stock.builder()//
        .symbol("TSLA")//
        .buyBook(buyBook)//
        .sellBook(sellBook)//
        .build();
  }

  @Test
  void testDequeOrdering() {
    Deque<Entry> buyDqeue = this.stock.getBuyBook().toDescDeque();
    assertThat(buyDqeue.poll(), equalTo(new Entry(0.87d, 400)));
    assertThat(buyDqeue.poll(), equalTo(new Entry(0.86d, 0)));
    assertThat(buyDqeue.poll(), equalTo(new Entry(0.85d, 200)));
    assertThat(buyDqeue.poll(), equalTo(new Entry(0.84d, 1000)));
    Deque<Entry> sellDeque = this.stock.getSellBook().toAscDeque();
    assertThat(sellDeque.poll(), equalTo(new Entry(0.88d, 0)));
    assertThat(sellDeque.poll(), equalTo(new Entry(0.89d, 0)));
    assertThat(sellDeque.poll(), equalTo(new Entry(0.90d, 400)));
    assertThat(sellDeque.poll(), equalTo(new Entry(0.91d, 200)));
  }

  // @Test
  void testTradeBuyAndLimit1() {
    // Prepare order to place
    Order order = Order.of("test", Action.BUY, OrderType.LIMIT, 0.91d, 500);
    // test
    this.stock.processOrder(order);
    // Assert
    assertThat(this.stock.getBuyBook().getEntries().size(), equalTo(7));
    assertThat(this.stock.getSellBook().getEntries().size(), equalTo(6));
    assertThat(this.stock.getSellBook().toAscDeque().peek().getPrice(),
        equalTo(0.91d));
    assertThat(this.stock.getSellBook().toAscDeque().peek().getShare(),
        equalTo(100));
  }
 // @Test
 void testTradeBuyAndLimit2() {
  // Prepare order to place
  Order order =
      Order.of("vincentlau", Action.BUY, OrderType.LIMIT, 0.88d, 700);
  // Test place order
  this.stock.processOrder(order);
  // Assert
  assertThat(this.stock.getBuyBook().getEntries().size(), equalTo(5));
  assertThat(this.stock.getSellBook().getEntries().size(), equalTo(6));
  assertThat(this.stock.getBuyBook().toDescDeque().peek().getPrice(),
      equalTo(0.88d));
  assertThat(this.stock.getBuyBook().toDescDeque().peek().getShare(),
      equalTo(700));
  assertThat(this.stock.getSellBook().toAscDeque().peek().getPrice(),
      equalTo(0.89d));
  assertThat(this.stock.getSellBook().toAscDeque().peek().getShare(),
      equalTo(0));
}

// @Test
void testTradeBuyAndLimit3() {
  // Prepare order to place
  Order order =
      Order.of("vincentlau", Action.BUY, OrderType.LIMIT, 0.85d, 1300);
  // Test place order
  this.stock.processOrder(order);
  // Assert
  assertThat(this.stock.getBuyBook().getEntries().size(), equalTo(4));
  assertThat(this.stock.getSellBook().getEntries().size(), equalTo(6));
  assertThat(this.stock.getBuyBook().getEntries(),
      hasItem(new Entry(0.84, 1000)));
  assertThat(this.stock.getBuyBook().getEntries(),
      hasItem(new Entry(0.85, 1500)));
  assertThat(this.stock.getBuyBook().getEntries(),
      hasItem(new Entry(0.86, 0)));
  assertThat(this.stock.getBuyBook().getEntries(),
      hasItem(new Entry(0.87, 400)));
}
}

package com.hkjava.stock.trade.hub;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.hkjava.stock.trade.hub.model.Book;
import com.hkjava.stock.trade.hub.model.Entry;
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

  
}

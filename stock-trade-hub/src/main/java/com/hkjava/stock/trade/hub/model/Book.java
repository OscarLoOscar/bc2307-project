package com.hkjava.stock.trade.hub.model;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Book {
  private List<Entry> entries;

  public void add(Entry entry) {
    entries.add(entry);
  }

  private Comparator<Entry> sortedByPrice() {
    return (e1, e2) -> e1.getPrice() < e2.getPrice() ? -1 : 1;
  }

  private Comparator<Entry> reversedByPrice() {
    return (e1, e2) -> e1.getPrice() > e2.getPrice() ? -1 : 1;
  }

  public List<Entry> sorted() {
    return this.entries.stream()//
        .sorted(sortedByPrice())//
        .collect(Collectors.toList());
  }

  public List<Entry> reversed() {
    return this.entries.stream()//
        .sorted(reversedByPrice())//
        .collect(Collectors.toList());
  }

  public Deque<Entry> toAscDeque() {
    return this.entries.stream()//
        .sorted(sortedByPrice())//
        .collect(Collectors.toCollection(LinkedList::new));
  }

  public Deque<Entry> toDescDeque() {
    return this.entries.stream()//
        .sorted(reversedByPrice())//
        .collect(Collectors.toCollection(LinkedList::new));
  }

  public void print() {
    entries.stream()//
        .forEach(e -> {
          System.out.println(
              "price : " + e.getPrice() + " , share : " + e.getShare());
        });
  }
}

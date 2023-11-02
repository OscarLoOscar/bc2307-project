package com.example.demo.demostockexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.demostockexchange.entity.Stocks;

public interface StockRepository extends JpaRepository<Stocks, Long> {
  // @Query("SELECT s.type from Orders s WHERE type = 'Bid'")
  // List<Orders> getBidType();

  // @Query("SELECT s.type from Orders s WHERE type = 'Ask'")
  // List<Orders> getAskType();



}

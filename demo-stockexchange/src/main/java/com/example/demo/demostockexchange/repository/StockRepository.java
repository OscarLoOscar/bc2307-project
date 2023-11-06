package com.example.demo.demostockexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.demostockexchange.entity.Stocks;

@Transactional
public interface StockRepository extends JpaRepository<Stocks, Long> {
  // @Query("SELECT s.type from Orders s WHERE type = 'Bid'")
  // List<Orders> getBidType();

  // @Query("SELECT s.type from Orders s WHERE type = 'Ask'")
  // List<Orders> getAskType();



}

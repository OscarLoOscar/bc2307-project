package com.hkjava.demo.demofinnhubdb.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hkjava.demo.demofinnhubdb.entity.StockPrice;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
  // List<StockPrice> getDayHighDayLowDayOpenPrevDayCloseByStockId(
  // @Param(value = "id") Long id);

  List<StockPrice> getAllClosePriceByStockId(Long stockId);

  double getDayHighByStockId(@Param(value = "id") Long id);

  double getDayLowByStockId(@Param(value = "id") Long id);

  double getDayOpenByStockId(@Param(value = "id") Long id);

  double getDayCloseByStockId(@Param(value = "id") Long id);

}

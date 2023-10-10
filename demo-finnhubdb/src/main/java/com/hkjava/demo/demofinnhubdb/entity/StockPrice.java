package com.hkjava.demo.demofinnhubdb.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "FINNHUB_STOCK_PRICE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StockPrice implements Serializable {// Candlestick

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "datetime")
  @JsonFormat(locale = "zh", timezone = "GMT+8",
      pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat
  private LocalDateTime datetime;

  @Column(name = "current_price", columnDefinition = "NUMERIC(15,2)")
  private double currentPrice;

  @Column(name = "day_high", columnDefinition = "NUMERIC(15,2)")
  private double dayHigh;

  @Column(name = "day_low", columnDefinition = "NUMERIC(15,2)")
  private double dayLow;

  @Column(name = "day_open", columnDefinition = "NUMERIC(15,2)")
  private double dayOpen;

  @Column(name = "prev_day_close", columnDefinition = "NUMERIC(15,2)")
  private double prevDayClose;

  @ManyToOne
  @JoinColumn(name = "stock_id", nullable = false)
  private Stock stock;
}

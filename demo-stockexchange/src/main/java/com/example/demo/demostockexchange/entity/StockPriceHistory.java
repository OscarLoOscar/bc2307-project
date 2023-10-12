package com.example.demo.demostockexchange.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "StockPriceHistory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StockPriceHistory implements Serializable {
  @Id
  private String stockId;

  @Id
  @JsonFormat(locale = "zh", timezone = "GMT+8",
      pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat
  private LocalDateTime updatedAt;

  @Column(name = "dayhigh")
  // dayhigh
  private BigDecimal high;

  @Column(name = "daylow")
  private BigDecimal low;

  @Column(name = "open")
  private BigDecimal open;

  @Column(name = "end")
  private BigDecimal end;

}

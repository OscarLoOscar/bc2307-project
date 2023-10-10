package com.example.demo.demostockexchange.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Triggers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Triggers implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "customer_id")
  private Integer customerId;

  @Column(name = "stock_id")
  private String stockId;

  @Column(name = "type")
  private String type;// 'Buy , Sell'

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "threshold")
  private Double threshold;

  @Column(name = "\"condition\"") // Use double quotes to escape "condition"
  private String condition;

}

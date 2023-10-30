package com.example.demo.demostockexchange.entity;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "Stocks")
public class Stocks {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long stockId;
  private String stockSymbol;
  private String companyName;
  private double currentPrice;
}

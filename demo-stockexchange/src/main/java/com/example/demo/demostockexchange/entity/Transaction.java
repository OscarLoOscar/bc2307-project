package com.example.demo.demostockexchange.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.demo.demostockexchange.infra.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "Transaction")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long transactionId;
  private Long userId;
  private String stockSymbol;
  private int quantity;
  private double price;
  private TransactionType transactionType;

  @JsonFormat(locale = "zh", timezone = "GMT+8",
      pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat
  // @Column(name = "placedAt")
  private LocalDateTime transactionDate;

}

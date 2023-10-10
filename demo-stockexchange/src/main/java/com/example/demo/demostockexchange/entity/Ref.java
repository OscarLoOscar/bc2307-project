package com.example.demo.demostockexchange.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nonnull;
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

@Table(name = "Ref")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Ref implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Nonnull
  private String stockNum; // Renamed from stockNum

  @Nonnull
  private String stockName; // Renamed from stockName

  @Nonnull
  private String stockSector; // Renamed from stockSector

  @Nonnull
  @JsonFormat(locale = "zh", timezone = "GMT+8",
      pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat
  private Date sellTime; // Renamed from buyTime

  @Nonnull
  private Float price;

  @Nonnull
  private Double volume; // Renamed from volume

  @Nonnull
  private Double askPrice; // Renamed from bidPrice

  private String sellDetail; // Renamed from buyDetail

  @Nonnull
  private String accountNumber; // Renamed from accountNumber

  @Nonnull
  private String accountName; // Renamed from accountName

  @Nonnull
  @Column(name = "Approve_Status", columnDefinition = "VARCHAR(1)") // "A","I"
  private Character approve;

  @Nonnull
  @Column(name = "Paid_Status", columnDefinition = "VARCHAR(1)") // "Y","N"
  private Character isPaid; // Renamed from ispay

  @JsonFormat(locale = "zh", timezone = "GMT+8",
      pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat
  private Date addtime;

}


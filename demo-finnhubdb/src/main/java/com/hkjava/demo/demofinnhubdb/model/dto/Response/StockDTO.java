package com.hkjava.demo.demofinnhubdb.model.dto.Response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockDTO {
  private String symbol;

  private String localdate;

  private CompanyProfileDTO companyProfile;

  private double currentPrice;

  private double dayHigh;

  private double dayLow;

  private double dayOpen;

  private double prevDayClose;

}

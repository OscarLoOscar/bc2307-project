package com.hkjava.demo.demofinnhub
.model.dto.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileDTO {
  
  private String country;

  private String companyName;

  private LocalDateTime ipoDate;

  private String logo;

  private double marketCap;

  private String currency;

}

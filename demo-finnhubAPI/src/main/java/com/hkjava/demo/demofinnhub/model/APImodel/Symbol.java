package com.hkjava.demo.demofinnhub
.model.APImodel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Symbol {
  public String currency;
  @JsonProperty("description")
  public String desc;
  public String displaySymbol;
  public String figi;
  public String isin;
  public String mic;
  public String shareClassFIGI;
  public String symbol;
  public String symbol2;
  public String type;
}

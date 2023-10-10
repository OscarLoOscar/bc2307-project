package com.hkjava.demo.demofinnhubdb
.model.dto.Response;
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

public class CandleStickDTO {
      //数据模型 : [time0 ,open1, close2, min3, max4 ,vol5,tag6 ,macd7,dif8 ,dea9]
LocalDateTime localDateTime;
double open;
double close;
double min;
double max;
double vol;
double tag;
double macd;
double dif;
double dea;


}

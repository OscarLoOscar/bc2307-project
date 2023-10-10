package com.hkjava.demo.demofinnhub
;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkjava.demo.demofinnhub
.model.APImodel.CompanyProfile2DTO;
import java.time.LocalDate;

@SpringBootTest
public class DeserializationForRestTemplate {

    private ObjectMapper objectMapper;

    @BeforeAll // call一次
    static void init() {

    }

    @Test
    void testDeserializationForRestTemplate() throws JsonProcessingException {
        // JSON -> Object
        CompanyProfile2DTO companyProfile = CompanyProfile2DTO.builder()//
                .companyName("APPL Company")//
                .country("US")//
                .currency("USD")//
                .estimateCurrency("USD")//
                .exchange("XYZ")//
                .finnhubIndustry("IJK")//
                .ipoDate(LocalDate.of(1988, 12, 31).toString())//
                .marketCap(3000.12)//
                .logo("/abc.png")//
                .phone("1243546789")//
                .shareOutstanding(23.90)//
                .ticker("AAPL")//
                .build();
        // test Serialization
        String mockedResponseInJson =
                objectMapper.writeValueAsString(companyProfile);
        System.out.println("json=" + mockedResponseInJson);
        // json={"country":"US","currency":"USD","estimateCurrency":"USD",
        // "exchange":"XYZ","finnhubIndustry":"IJK","logo":"/abc.png",
        // "phone":"123456789","shareOutstanding":23.9,"ticker":"APPL",
        // "weburl":null,"ipo":"1988-12-31","marketCapitalization":3000.12,
        // "name":"APPL Company"}
        JsonNode jsonNode = objectMapper.readTree(mockedResponseInJson);
        assertThat(jsonNode.path("country").asText(), is("US"));
        assertThat(jsonNode.path("ipo").asText(), is("1988-12-31"));
        assertThat(jsonNode.path("marketCapitalization").asDouble(),
                is(3000.12));
        // test Deserilaizationn (main code -> automation)
        CompanyProfile2DTO afterCompanyProfile = objectMapper
                .readValue(mockedResponseInJson, CompanyProfile2DTO.class);
        assertEquals(true, afterCompanyProfile.getIpoDate()
                .equals(companyProfile.getIpoDate()));
        assertEquals(true, afterCompanyProfile.getMarketCap() == companyProfile
                .getMarketCap());
        assertEquals(true, afterCompanyProfile.getCountry()
                .equals(companyProfile.getCountry()));
    }
}

package com.hkjava.demo.demofinnhubdb.repository;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hkjava.demo.demofinnhubdb.entity.Stock;
import com.hkjava.demo.demofinnhubdb.entity.StockSymbol;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // 1.select * from table where company_name = "";
    List<Stock> findByCompanyName(String companyName);

    boolean existsById(Long id);

    List<Stock> findByCountry(String country);

    Stock findByCountryAndMarketCapGreaterThan(String country,
            double marketCap);

    // update stock set field = x where field = ? (findById() -> save())
    // solution : (Put/Patch) findById() -> set() ->save()
    // void updateCountryAndid(Long id, String country);

    // replace JpaRepository findAllById
    // @Query("SELECT s FROM Stock s WHERE s.id = :id") -> get all column
    // 2. Native Query
    @Query(value = "SELECT s.id,s.country,s.company_name,s.ipo_date,s.logo,s.market_cap,s.currency FROM FINNHUB_STOCK s WHERE s.id = :id", //
            nativeQuery = true) // JPQL
    List<Stock> findAllById2(@Param("id") Long id);

    // 3. JPQL , (Java Persistence quesy language)
    @Query(value = " SELECT s FROM Stock s where s.id = :id ")
    Stock findAllById3(@Param(value = "id") Long id);

    // 唔係primary key，就應該用list 接-> 會唔止一個


    Optional<Stock> findByStockSymbol(StockSymbol stockSymbol);

    // select * from stocks where country = ? and market_cap > ?;
    List<Stock> findFirst3ByCountryAndMarketCapGreaterThanEqualOrderByIdDesc(
            String country, double marketCap);
}

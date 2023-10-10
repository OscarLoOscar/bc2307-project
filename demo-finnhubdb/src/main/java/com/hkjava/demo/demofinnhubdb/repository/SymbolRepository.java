package com.hkjava.demo.demofinnhubdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hkjava.demo.demofinnhubdb.entity.StockSymbol;

// @Repository
public interface SymbolRepository extends JpaRepository<StockSymbol, Long> {

}

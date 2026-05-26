package com.nkm.stockflow.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Stock {

    @Id
    private String stockCode;

    private String stockMarket;

    private String marketType;

    public Stock(String stockCode, String stockMarket, String marketType) {
        this.stockCode = stockCode;
        this.stockMarket = stockMarket;
        this.marketType = marketType;
    }
}
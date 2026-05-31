package com.nkm.stockflow.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate baseDate;

    private int openPrice; // 시가

    private int highPrice; // 고가

    private int lowPrice; // 저가

    private int closePrice; // 종가

    private long volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="stock_code")
    private Stock stock;
}
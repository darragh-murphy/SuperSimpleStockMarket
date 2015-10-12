package com.darraghmurphy.stockmarket;

public interface StockInterface {

    String getSymbol();

    Double getParValue();

    double dividendYield(double price);

    double priceEarningsRatio(double price);
}

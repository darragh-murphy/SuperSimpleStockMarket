package com.darraghmurphy.stockmarket.api;

import java.util.Calendar;

/**
 * Stock Market Interface
 */
public interface StockMarketInterface {

    /**
     * Record BUY trade in market.
     *
     * @param numberOfShares number of shares
     * @param stockSymbol    stock symbol
     * @param price          stock price
     * @param timestamp      trade timestamp
     */
    void recordBuyTrade(int numberOfShares, String stockSymbol, double price, Calendar timestamp);

    /**
     * Record SELL trade in market.
     *
     * @param numberOfShares number of shares
     * @param stockSymbol    stock symbol
     * @param price          stock price
     * @param timestamp      trade timestamp
     */
    void recordSellTrade(int numberOfShares, String stockSymbol, double price, Calendar timestamp);

    /**
     * @param symbol        stock symbol
     * @param fixedDividend fixed dividend
     * @param parValue      par value
     */
    void recordPreferredStock(String symbol, Double fixedDividend, Double parValue);

    /**
     * @param symbol       stock symbol
     * @param lastDividend last dividend
     * @param parValue     par value
     */
    void recordCommonStock(String symbol, Double lastDividend, Double parValue);

    /**
     * Calculate dividend yield
     *
     * @param stockSymbol stock symbol
     * @param price       stock price
     * @return dividend yield
     */
    double dividendYield(String stockSymbol, double price);

    /**
     * Calculate price per earnings ratio
     *
     * @param stockSymbol stock symbol
     * @param price       stock price
     * @return price per earnings ratio
     */
    double priceEarningsRatio(String stockSymbol, double price);

    /**
     * Calculate volume weighted stock price
     *
     * @param symbol stock symbol
     * @return volume weighted stock price
     */
    double volumeWeightedStockPrice(String symbol);

    /**
     * Calculate volume weighted stock price
     *
     * @return geometric mean
     */
    double geometricMean();
}

package com.darraghmurphy.stockmarket.impl;

import com.darraghmurphy.stockmarket.api.StockInterface;
import com.darraghmurphy.stockmarket.api.TradeInterface;

import java.util.Calendar;

/**
 * Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price.
 */
public class Trade implements TradeInterface {

    /**
     * Share price in pennies.
     * <p/>
     * A share price is the price of a single share of a number of saleable stocks of a company, derivative or other
     * financial asset.
     */
    private final Double price;

    /**
     * Calendar is used to store the timestamp as it is better for date arithmetic and also handles localization.
     */
    private final Calendar timestamp;

    /**
     * Number of shares.
     */
    private final int numberOfShares;

    /**
     * Trade status
     */
    private final TradeInterface.TradeStatus tradeStatus;

    /**
     * Stock interface.
     */
    private final StockInterface stock;

    /**
     * Constructor.
     *
     * @param tradeStatus    trade status
     * @param numberOfShares number of shares
     * @param stock          stock symbol
     * @param price          stock price
     * @param timestamp      timestamp
     */
    public Trade(TradeStatus tradeStatus, int numberOfShares, StockInterface stock, double price, Calendar timestamp) {

        this.timestamp = timestamp;
        this.tradeStatus = tradeStatus;
        this.numberOfShares = numberOfShares;
        this.stock = stock;
        this.price = price;
    }


    /**
     *
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     */
    public StockInterface getStock() {
        return stock;
    }

    /**
     *
     */
    public Calendar getTimestamp() {
        return timestamp;
    }

    /**
     *
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     *
     */
    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }
}

package com.darraghmurphy.stockmarket;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price.
 */
public class Trade implements TradeInterface {

    public Trade(TradeStatus tradeStatus, int numberOfShares, StockInterface stock, double price) {

        /** For simplicity, I'll set the timestamp at object creation for now */
        timestamp = new GregorianCalendar();

        this.tradeStatus = tradeStatus;
        this.numberOfShares = numberOfShares;
        this.stock = stock;
        this.price = price;
    }

    /**
     * Share price in pennies.
     * <p/>
     * A share price is the price of a single share of a number of saleable stocks of a company, derivative or other
     * financial asset.
     */
    private Double price;

    /**
     * Calendar is used to store the timestamp as it is better for date arithmetic and also handles localization.
     */
    private Calendar timestamp;


    private int numberOfShares;

    private TradeStatus tradeStatus;

    private StockInterface stock;

    public Double getPrice() {
        return price;
    }

    public StockInterface getStock() {
        return stock;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }


    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }
}

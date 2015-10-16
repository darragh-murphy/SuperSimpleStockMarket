package com.darraghmurphy.stockmarket.impl;

import com.darraghmurphy.stockmarket.api.StockInterface;

import java.math.BigDecimal;

/**
 * Common Stock.
 */
public class CommonStock extends AbstractStock implements StockInterface {


    /**
     * A dividend is a payment made by a corporation to its shareholders, usually as a distribution of profits.
     * <p/>
     * This is a percentage of the common dividend
     */
    private final BigDecimal lastDividend;

    /**
     * Constructor.
     *
     * @param symbol       stock symbol
     * @param lastDividend last dividend
     * @param parValue     par value
     */
    public CommonStock(String symbol, Double lastDividend, Double parValue) {
        this.symbol = symbol;
        this.lastDividend = BigDecimal.valueOf(lastDividend);
        this.parValue = BigDecimal.valueOf(parValue);
    }

    /**
     *
     */
    public double priceEarningsRatio(double price) {

        return new BigDecimal(price).divide(lastDividend).doubleValue();
    }

    /**
     *
     */
    public double dividendYield(double price) {

        if (price <= 0) throw new IllegalArgumentException(String.format("Invalid parameter %f", price));

        /** TODO Requirements are unclear. Discussion required. */
        if (lastDividend.signum() != 1) return 0d;

        return lastDividend.divide(BigDecimal.valueOf(price)).doubleValue();

    }
}
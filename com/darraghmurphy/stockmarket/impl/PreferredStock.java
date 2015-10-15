package com.darraghmurphy.stockmarket.impl;

import com.darraghmurphy.stockmarket.api.StockInterface;

/**
 * Preferred Stock.
 */
public class PreferredStock extends AbstractStock implements StockInterface {

    /**
     * A “fixed” dividend is known as a “fixed rate” preferred share. For example, a $25 preferred share with
     * a 5% dividend would pay $.3125 quarterly (.05 x $25/4=. 3125). This is why preferred shares
     * are usually thought of as “fixed income” investments.
     */
    private final Double fixedDividend;

    /**
     * Constructor.
     *
     * @param symbol        stock symbol
     * @param parValue      par value
     * @param fixedDividend fixed dividend
     */
    public PreferredStock(String symbol, Double parValue, Double fixedDividend) {
        this.symbol = symbol;
        this.parValue = parValue;
        this.fixedDividend = fixedDividend;
    }

    /**
     *
     */
    public double dividendYield(double price) {
        return fixedDividend * getParValue() / price;

    }

    /**
     *
     */
    public double priceEarningsRatio(double price) {

        return price / fixedDividend;
    }


}

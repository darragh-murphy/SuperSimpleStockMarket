package com.darraghmurphy.stockmarket.impl;

import com.darraghmurphy.stockmarket.api.StockInterface;

import java.math.BigDecimal;

/**
 * Preferred Stock.
 */
public class PreferredStock extends AbstractStock implements StockInterface {

    /**
     * A “fixed” dividend is known as a “fixed rate” preferred share. For example, a $25 preferred share with
     * a 5% dividend would pay $.3125 quarterly (.05 x $25/4=. 3125). This is why preferred shares
     * are usually thought of as “fixed income” investments.
     */
    private final BigDecimal fixedDividend;

    /**
     * Constructor.
     *
     * @param symbol        stock symbol
     * @param parValue      par value
     * @param fixedDividend fixed dividend
     */
    public PreferredStock(String symbol, Double parValue, Double fixedDividend) {
        this.symbol = symbol;
        this.parValue = BigDecimal.valueOf(parValue);
        this.fixedDividend = BigDecimal.valueOf(fixedDividend);
    }

    /**
     *
     */
    public double dividendYield(double price) {
        return fixedDividend.multiply(parValue).divide(BigDecimal.valueOf(price)).doubleValue();

    }

    /**
     *
     */
    public double priceEarningsRatio(double price) {

        return BigDecimal.valueOf(price).divide(fixedDividend).doubleValue();
    }


}

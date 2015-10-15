package com.darraghmurphy.stockmarket.impl;

import com.darraghmurphy.stockmarket.api.StockInterface;

/**
 * Abstract Stock.
 */
public abstract class AbstractStock implements StockInterface {

    /**
     * A ticker symbol or stock symbol is an abbreviation used to uniquely identify publicly traded shares of a
     * particular stock on a particular stock market. A stock symbol may consist of letters, numbers or a combination of both.
     */
    String symbol;

    /**
     * Par value for a share refers to the stock value stated in the corporate charter. *./
     */
    Double parValue;

    /**
     *
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     *
     */
    public Double getParValue() {
        return parValue;
    }
}

package com.darraghmurphy.stockmarket.api;

import java.util.Calendar;

/**
 * TradeInterface
 */
public interface TradeInterface {

    /**
     * Get stock symbol
     *
     * @return stock symbol
     */
    StockInterface getStock();

    /**
     * Get stock price
     *
     * @return stock price
     */
    Double getPrice();

    /**
     * Get trade timestamp
     *
     * @return trade timestamp
     */
    Calendar getTimestamp();

    /**
     * Get number of shares
     *
     * @return number of shares
     */
    int getNumberOfShares();

    /**
     * Trade status.
     */
    enum TradeStatus {
        BUY, SELL
    }

}

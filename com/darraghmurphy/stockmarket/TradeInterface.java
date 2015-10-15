package com.darraghmurphy.stockmarket;

import java.util.Calendar;

public interface TradeInterface {

    enum TradeStatus {BUY, SELL}

    StockInterface getStock();

    Double getPrice();

    Calendar getTimestamp();

    int getNumberOfShares();


}

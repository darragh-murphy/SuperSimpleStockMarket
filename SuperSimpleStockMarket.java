package com.darraghmurphy.stockmarket;

import com.darraghmurphy.stockmarket.TradeInterface.TradeStatus;

import java.util.*;

/**
 * <p/>
 * <h1></h12>Super Simple Stock Market</h1>
 * <p/>
 * <h3>Requirements</h3>
 * Provide working source code that will :- For a given stock
 * <br>
 * <ol>
 * <li>Given any price as input, calculate the dividend yield</li>
 * <li>Given any price as input,  calculate the P/E Ratio</li>
 * <li>Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price</li>
 * <li>Calculate Volume Weighted Stock Price based on trades in past 15 minutes</li>
 * </ol>
 * <br>
 * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
 * <p/>
 * <p/>
 * <h3>Constraints & Notes</h3>
 * Written in one of these languages : Java, C#, C++, Python
 * No database or GUI is required, all data need only be held in memory
 * No prior knowledge of stock markets or trading is required – all formulas are provided below.
 * <p/>
 * <i>Table1. Sample data from the Global Beverage Corporation Exchange</i>
 * <pre>
 * +----------------+-------------+---------------+----------------+-----------+
 * | STOCK SYMBOL   | TYPE        | LAST DIVIDEND | FIXED DIVIDEND | PAR VALUE |
 * |                |             |               |                |           |
 * +----------------+-------------+---------------+----------------+-----------+
 * |                |             |               |                |           |
 * |   TEA          |   Common    |   0           |                |   100     |
 * |                |             |               |                |           |
 * +----------------+-------------+---------------+----------------+-----------+
 * |                |             |               |                |           |
 * |   POP          |   Common    |   8           |                |   100     |
 * |                |             |               |                |           |
 * +----------------+-------------+---------------+----------------+-----------+
 * |                |             |               |                |           |
 * |   ALE          |   Common    |   23          |                |   60      |
 * |                |             |               |                |           |
 * +----------------+-------------+---------------+----------------+-----------+
 * |                |             |               |                |           |
 * |   GIN          |   Preferred |   8           |   2%           |   100     |
 * |                |             |               |                |           |
 * +----------------+-------------+---------------+----------------+-----------+
 * |                |             |               |                |           |
 * |   JOE          |   Common    |   13          |                |   250     |
 * |                |             |               |                |           |
 * +----------------+-------------+---------------+----------------+-----------+
 * </pre>
 */
public class SuperSimpleStockMarket {

    /**
     * This comparator is used to ensure that trades are stored in order of their timestamp
     */
    private class TimestampComparator implements Comparator<TradeInterface> {

        public int compare(TradeInterface m1, TradeInterface m2) {

            /**
             * Comparator returns 1 instead of 0 in case of equal elements. Hence in the case of equal elements
             * the TreeSet with this Comparator will not overwrite the duplicate and will just sort it.
             */
            if (m1.getTimestamp().equals(m2.getTimestamp()))
                return 1;
            else
                return m1.getTimestamp().compareTo(m2.getTimestamp());
        }
    }

    /**
     * Store of all trades active within the Stock market, sorted by timestamp.
     * A sorted set automatically sorts the collection at insertion, meaning that it does the sorting while you
     * add elements into the collection. It also means you don't need to manually sort it.
     * <p/>
     * The set will be sorted according to the comparator provided.
     * time, which will improve access time when wish to get trades by timestamp.
     * <p/>
     */
    private TreeSet<TradeInterface> tradeSet = new TreeSet(new
            TimestampComparator());

    /**
     * Store of all trades active within the Stock market, sorted by timestamp.
     * <p/>
     * The map will be sorted according to the comparator provided.
     * time, which will improve access time when wish to get trades by timestamp.
     * <p/>
     */
    private Map<String, StockInterface> stocks = new HashMap<>();


    /**
     * For a given stock, given any price as input, calculate the dividend yield.
     */
    public double dividendYield(String stockSymbol, double price) {
        return stocks.get(stockSymbol).dividendYield(price);
    }

    /**
     * For a given stock, given any price as input, calculate the dividend yield.
     */
    public double priceEarningsRatio(String stockSymbol, double price) {
        return stocks.get(stockSymbol).priceEarningsRatio(price);
    }


    /**
     * Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price.
     */
    public void recordBuyTrade(int numberOfShares, String stockSymbol, double price) {

        StockInterface stock = stocks.get(stockSymbol);
        TradeInterface trade = new Trade(TradeStatus.BUY, numberOfShares, stock, price);

        recordTrade(trade);
    }


    /**
     * Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price.
     */
    public void recordSellTrade(int numberOfShares, String stockSymbol, double price) {

        StockInterface stock = stocks.get(stockSymbol);
        TradeInterface trade = new Trade(TradeStatus.SELL, numberOfShares, stock, price);

        recordTrade(trade);
    }


    /**
     * Record a trade.
     */
    private void recordTrade(TradeInterface trade) {

        /** Store a list of stocks by symbol */
        if (!stocks.keySet().contains(trade.getStock().getSymbol())) {
            stocks.put(trade.getStock().getSymbol(), trade.getStock());
        }

        tradeSet.add(trade);
    }

    /**
     * Record preferred stock.
     */
    public void recordPreferredStock(String symbol, Double lastDividend, Double
            fixedDividend, Double parValue) {

        PreferredStock ps = new PreferredStock(symbol, lastDividend, parValue, fixedDividend);
        stocks.put(symbol, ps);
    }


    /**
     * Record preferred stock.
     */
    public void recordCommonStock(String symbol, Double lastDividend, Double parValue) {

        CommonStock cs = new CommonStock(symbol, lastDividend, parValue);
        stocks.put(symbol, cs);
    }

    /**
     * Calculate Volume Weighted Stock Price based on trades in past 15 minutes.
     *
     * @return volume Weighted Stock Price based on trades in past 15 minutes.
     */
    public double volumeWeightedStockPrice(String symbol) {
        Calendar historicTime = Calendar.getInstance();
        historicTime.add(Calendar.MINUTE, -15);

        double sumTradedPriceByQuantity = 0;
        double sumQuantity = 0;

        for (TradeInterface trade : tradeSet) {

            /** As the trade map is sorted by date, we need look no further */
            if (trade.getTimestamp().after(historicTime.getTime())) break;

            if (trade.getStock().getSymbol().equals(symbol)) {

                double tradedPrice = trade.getPrice();
                double quantity = trade.getNumberOfShares();

                double tradedPriceByQuantity = tradedPrice * quantity;

                sumTradedPriceByQuantity += tradedPriceByQuantity;
                sumQuantity += quantity;
            }
        }

        /** Calculate Volume Weighted Stock Price */

        return sumTradedPriceByQuantity / sumQuantity;
    }

    /**
     * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks.
     * <p/>
     * i.e Calculate portfolio performance.
     *
     * @return geometric mean of prices for all stocks
     */
    public double geometricMean() {

        /**
         * In mathematics, the geometric mean is a type of mean or average, which indicates the central tendency or
         * typical value of a set of numbers by using the product of their values (as opposed to the arithmetic mean
         * which uses their sum).
         *
         * The geometric mean is defined as the nth root of the product of n numbers.
         * */
        double product = 0;
        for (TradeInterface trade : tradeSet) {

            if (product == 0) product = 1;
            product *= trade.getPrice();
        }

        return Math.pow(product, 1.0 / tradeSet.size());
    }


}




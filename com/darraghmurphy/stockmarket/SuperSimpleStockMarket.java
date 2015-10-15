package com.darraghmurphy.stockmarket;

import com.darraghmurphy.stockmarket.api.StockInterface;
import com.darraghmurphy.stockmarket.api.StockMarketInterface;
import com.darraghmurphy.stockmarket.api.TradeInterface;
import com.darraghmurphy.stockmarket.impl.CommonStock;
import com.darraghmurphy.stockmarket.impl.PreferredStock;
import com.darraghmurphy.stockmarket.impl.Trade;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Super Simple Stock Market.
 */
public class SuperSimpleStockMarket implements StockMarketInterface {

    /**
     * Store of all trades active within the Stock market, sorted by timestamp.
     * <p/>
     * A sorted set automatically sorts the collection at insertion, meaning that it does the sorting while you
     * add elements into the collection. It also means you don't need to manually sort it.
     * <p/>
     * The set will be sorted according to the comparator provided.
     * time, which will improve access time when wish to get trades by timestamp.
     * <p/>
     * The elements are ordered using a custom comparator @TimestampComparator
     */
    private final ConcurrentSkipListSet<TradeInterface> tradeSet = new ConcurrentSkipListSet(new TimestampComparator());

    /**
     * Business date and time.
     * <p/>
     * All calculates related to trade timestamps will be relative to this date.
     */
    private final Calendar businessDate;
    /**
     * Store of all trades active within the Stock market, sorted by timestamp.
     * <p/>
     * The map will be sorted according to the comparator provided.
     * time, which will improve access time when wish to get trades by timestamp.
     * <p/>
     */
    private final ConcurrentHashMap<String, StockInterface> stocks = new ConcurrentHashMap<>();


    /**
     * Constructor.
     *
     * @param businessDate business date
     */
    public SuperSimpleStockMarket(Calendar businessDate) {
        this.businessDate = businessDate;
    }

    /**
     * For a given stock, given any price as input, calculate the dividend yield.
     */
    @Override
    public double dividendYield(String stockSymbol, double price) {
        return stocks.get(stockSymbol).dividendYield(price);
    }

    /**
     * For a given stock, given any price as input, calculate the dividend yield.
     */
    @Override
    public double priceEarningsRatio(String stockSymbol, double price) {
        return stocks.get(stockSymbol).priceEarningsRatio(price);
    }

    /**
     * Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price.
     */
    @Override
    public void recordBuyTrade(int numberOfShares, String stockSymbol, double price, Calendar timestamp) {

        StockInterface stock = stocks.get(stockSymbol);
        TradeInterface trade = new Trade(TradeInterface.TradeStatus.BUY, numberOfShares, stock, price, timestamp);

        recordTrade(trade);
    }

    /**
     * Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price.
     */
    @Override
    public void recordSellTrade(int numberOfShares, String stockSymbol, double price, Calendar timestamp) {

        StockInterface stock = stocks.get(stockSymbol);
        TradeInterface trade = new Trade(TradeInterface.TradeStatus.SELL, numberOfShares, stock, price, timestamp);

        recordTrade(trade);
    }

    /**
     * Record a trade.
     */
    private void recordTrade(TradeInterface trade) {

        /** Store a list of stocks by symbol */
        synchronized (tradeSet) {
            if (!stocks.keySet().contains(trade.getStock().getSymbol())) {
                stocks.put(trade.getStock().getSymbol(), trade.getStock());
            }
            tradeSet.add(trade);
        }
    }

    /**
     * Record preferred stock.
     */
    @Override
    public void recordPreferredStock(String symbol, Double fixedDividend, Double parValue) {

        PreferredStock ps = new PreferredStock(symbol, parValue, fixedDividend);
        stocks.put(symbol, ps);
    }

    /**
     * Record preferred stock.
     */
    @Override
    public void recordCommonStock(String symbol, Double lastDividend, Double parValue) {

        CommonStock cs = new CommonStock(symbol, lastDividend, parValue);
        stocks.put(symbol, cs);
    }

    /**
     * Calculate Volume Weighted Stock Price based on trades in past 15 minutes.
     *
     * @return volume Weighted Stock Price based on trades in past 15 minutes.
     */
    @Override
    public double volumeWeightedStockPrice(String symbol) {

        /** Set the cutoff time to be 15 minutes in the past. */
        Calendar cutOffTime = Calendar.getInstance(businessDate.getTimeZone());
        cutOffTime.setTime(businessDate.getTime());
        cutOffTime.add(Calendar.MINUTE, -15);

        double quantity = 0;
        double sumTradedPriceByQuantity = 0;

        /**
         * It is imperative that we manually synchronize on map when before iterating.
         * Failure to follow this advice may result in non-deterministic behavior.
         */
        synchronized (tradeSet) {
            for (TradeInterface trade : tradeSet) {

                /** As the trade map is sorted by date, we need look no further once we reach the cutoff time */
                if (trade.getTimestamp().before(cutOffTime)) {
                    break;
                }

                if (trade.getStock().getSymbol().equals(symbol)) {

                    double tradedPriceByQuantity = trade.getPrice() * (double) trade.getNumberOfShares();

                    sumTradedPriceByQuantity += tradedPriceByQuantity;

                    quantity += (double) trade.getNumberOfShares();
                }
            }
        }

        /** Calculate Volume Weighted Stock Price */
        if (sumTradedPriceByQuantity == 0 || quantity == 0) {
            return 0;
        }

        return sumTradedPriceByQuantity / quantity;
    }

    /**
     * Calculate the GBCE All Share Index using the geometric mean of prices for all stocks.
     * <p/>
     * i.e Calculate portfolio performance.
     *
     * @return geometric mean of prices for all stocks
     */
    @Override
    public double geometricMean() {

        /**
         * In mathematics, the geometric mean is a type of mean or average, which indicates the central tendency or
         * typical value of a set of numbers by using the product of their values (as opposed to the arithmetic mean
         * which uses their sum).
         *
         * The geometric mean is defined as the nth root of the product of n numbers.
         * */
        double product = 0;
        synchronized (tradeSet) {
            for (TradeInterface trade : tradeSet) {

                if (product == 0) product = 1;
                product *= trade.getPrice();
            }
        }
        return Math.pow(product, 1.0 / tradeSet.size());
    }

    /**
     * This comparator is used to ensure that trades are stored in order of their timestamp
     */
    private class TimestampComparator implements Comparator<TradeInterface> {

        public int compare(TradeInterface m1, TradeInterface m2) {

            /**
             * Comparator returns 1 instead of 0 in case of equal elements. Hence in the case of equal elements
             * the TreeSet with this Comparator will not overwrite the duplicate and will just sort it.
             */
            if (m1.getTimestamp().equals(m2.getTimestamp())) return 1;
            else return m1.getTimestamp().compareTo(m2.getTimestamp());
        }
    }


}




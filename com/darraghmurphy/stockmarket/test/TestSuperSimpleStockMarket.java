package com.darraghmurphy.stockmarket.test;

import com.darraghmurphy.stockmarket.SuperSimpleStockMarket;
import com.darraghmurphy.stockmarket.api.TradeInterface;
import com.darraghmurphy.stockmarket.api.TradeInterface.TradeStatus;
import com.darraghmurphy.stockmarket.impl.CommonStock;
import com.darraghmurphy.stockmarket.impl.PreferredStock;
import com.darraghmurphy.stockmarket.impl.Trade;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Simple test class.
 */
public class TestSuperSimpleStockMarket {

    /**
     * Default test business date.
     **/
    private static final GregorianCalendar DEFAULT_BUSINESS_DATA = new GregorianCalendar(2015, 0, 1, 13, 0, 0);

    /**
     * Add test stock data.
     */
    private static void addStockDate(SuperSimpleStockMarket market) {
        market.recordCommonStock("TEA", 0d, 100d);
        market.recordCommonStock("POP", 8d, 100d);
        market.recordCommonStock("ALE", 23d, 60d);
        market.recordPreferredStock("GIN", 8d, 0.02d);
        market.recordCommonStock("JOE", 13d, 250d);
    }

    /**
     * Add test trade data.
     */
    private static void addTradeData(Calendar businessDate, SuperSimpleStockMarket market) {
        market.recordBuyTrade(10, "TEA", 20d, businessDate);
        market.recordSellTrade(20, "POP", 10d, businessDate);
        market.recordSellTrade(30, "ALE", 30d, businessDate);
        market.recordSellTrade(40, "GIN", 40d, businessDate);
        market.recordSellTrade(50, "JOE", 50d, businessDate);
    }

    /**
     * TestSuperSimpleStockMarket dividend Yield.
     *
     * @throws Exception Error occurred.
     */
    private static void testStock() throws Exception {

        CommonStock cs1 = new CommonStock("AAA", 10d, 10d);
        assertEquals(cs1.dividendYield(10), 1d);
        assertEquals(cs1.getSymbol(), "AAA");
        assertEquals(cs1.getParValue(), 10);

        CommonStock cs2 = new CommonStock("AAA", 10d, 10d);
        assertEquals(cs2.priceEarningsRatio(10), 1d);

        PreferredStock ps1 = new PreferredStock("AAA", 10d, 10d);
        assertEquals(ps1.getParValue(), 10);
        assertEquals(ps1.dividendYield(10), 10);
        assertEquals(ps1.priceEarningsRatio(10), 1);
    }

    /**
     * TestSuperSimpleStockMarket dividend Yield.
     *
     * @throws Exception Error occurred.
     */
    private static void testDividendYield() throws Exception {

        SuperSimpleStockMarket market = new SuperSimpleStockMarket(new GregorianCalendar(2015, 0, 1, 13, 0, 0));

        addStockDate(market);

        /**
         * Check dividend Yield for common stock (last dividend / price ) = 23 / 1
         */
        assertEquals(market.dividendYield("ALE", 1), 23);

        /**
         * Check dividend Yield. for common stock (last dividend / price ) = 23 / 2
         */
        assertEquals(market.dividendYield("ALE", 2), 11.5);

        /**
         * Check dividend Yield for preferred stock (fixed dividend * par value ) / price = (2 * 100) / 1 = 200
         */
        assertEquals(market.dividendYield("GIN", 100), 0.001600d);

    }

    /**
     * TestSuperSimpleStockMarket geometric mean.
     *
     * @throws Exception Error occurred.
     */
    private static void testGeometricMean() throws Exception {

        SuperSimpleStockMarket market = new SuperSimpleStockMarket(DEFAULT_BUSINESS_DATA);

        /** Populate test data */
        addStockDate(market);

        /** Check geometric mean is ZERO before any trades are added */
        assertEquals(market.geometricMean(), 0);

        /** Check geometric mean after trades have been added */
        addTradeData(DEFAULT_BUSINESS_DATA, market);
        assertEquals(market.geometricMean(), Math.pow(10d * 20d * 30d * 40d * 50d, 1.0 / 5.0));

        /**
         * Check geometric mean after more trades have been added.
         * As the number of stock types and prices is constant the result should be the same.
         * */
        addTradeData(DEFAULT_BUSINESS_DATA, market);
        assertEquals(market.geometricMean(), Math.pow(10d * 20d * 30d * 40d * 50d, 1.0 / 5.0));
    }

    /**
     * Calculate Volume Weighted Stock Price based on future trades.
     *
     * @throws Exception Error occurred.
     */
    private static void testVolumeWeightedStockPrice() throws Exception {

        /** TestSuperSimpleStockMarket trade at business date */
        volumeWeightedStockPrice(DEFAULT_BUSINESS_DATA, 0, 20);

        /** TestSuperSimpleStockMarket trade at business date + 15 minutes */
        volumeWeightedStockPrice(DEFAULT_BUSINESS_DATA, +15, 20);

        /** TestSuperSimpleStockMarket trade at business date - 15 minutes */
        volumeWeightedStockPrice(DEFAULT_BUSINESS_DATA, -15, 20);

        /** TestSuperSimpleStockMarket trade at business date + 16 minutes */
        volumeWeightedStockPrice(DEFAULT_BUSINESS_DATA, +16, 20);

        /** TestSuperSimpleStockMarket trade at business date - 16 minutes */
        volumeWeightedStockPrice(DEFAULT_BUSINESS_DATA, -16, 0);
    }


    /**
     * TestSuperSimpleStockMarket that the NavigableSet & Comparator does sort all trade entries by timestamp.
     */
    private static void testConcurrentSkipListSet() throws Exception {

        NavigableSet<TradeInterface> set = new ConcurrentSkipListSet(new Comparator<TradeInterface>() {

            public int compare(TradeInterface m1, TradeInterface m2) {

                /**
                 * Comparator returns 1 instead of 0 in case of equal elements. Hence in the case of equal elements
                 * the TreeSet with this Comparator will not overwrite the duplicate and will just sort it.
                 */
                if (m1.getTimestamp().equals(m2.getTimestamp())) return 1;
                else return m1.getTimestamp().compareTo(m2.getTimestamp());
            }

        });

        CommonStock stock = new CommonStock("AAA", 1d, 1d);

        /** Unsorted trades with timestamps varying by YEAR */
        set.add(new Trade(TradeStatus.SELL, 1, stock, 2, new GregorianCalendar(2011, 0, 1, 13, 0, 0)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 1, new GregorianCalendar(2010, 0, 1, 13, 0, 0)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 3, new GregorianCalendar(2012, 0, 1, 13, 0, 0)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 5, new GregorianCalendar(2014, 0, 1, 13, 0, 0)));
        set.add(new Trade(TradeStatus.SELL, 1, stock, 4, new GregorianCalendar(2013, 0, 1, 13, 0, 0)));
        assertEquals(set.size(), 5);

        Iterator<TradeInterface> it1 = set.iterator();
        for (int i = 1; i <= set.size(); i++) {
            assertEquals(it1.next().getPrice(), i);
        }

        /** Unsorted trades with timestamps varying by SECONDS */
        set.clear();
        set.add(new Trade(TradeStatus.SELL, 1, stock, 2, new GregorianCalendar(2010, 0, 1, 13, 0, 1)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 1, new GregorianCalendar(2010, 0, 1, 13, 0, 0)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 3, new GregorianCalendar(2010, 0, 1, 13, 0, 2)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 5, new GregorianCalendar(2010, 0, 1, 13, 0, 4)));
        set.add(new Trade(TradeStatus.SELL, 1, stock, 4, new GregorianCalendar(2010, 0, 1, 13, 0, 3)));
        assertEquals(set.size(), 5);

        Iterator<TradeInterface> it2 = set.iterator();
        for (int i = 1; i <= set.size(); i++) {
            assertEquals(it2.next().getPrice(), i);
        }

        /** Ensure the trades with duplicate timestamps are allowed. */
        set.clear();
        set.add(new Trade(TradeStatus.SELL, 1, stock, 2, new GregorianCalendar(2010, 0, 1, 13, 0, 1)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 1, new GregorianCalendar(2010, 0, 1, 13, 0, 0)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 3, new GregorianCalendar(2010, 0, 1, 13, 0, 2)));
        set.add(new Trade(TradeStatus.BUY, 1, stock, 5, new GregorianCalendar(2010, 0, 1, 13, 0, 4)));
        set.add(new Trade(TradeStatus.SELL, 1, stock, 4, new GregorianCalendar(2010, 0, 1, 13, 0, 3)));
        set.add(new Trade(TradeStatus.SELL, 1, stock, 6, new GregorianCalendar(2010, 0, 1, 13, 0, 3)));
        assertEquals(set.size(), 6);

    }

    /**
     * Calculate Volume Weighted Stock Price based on future trades.
     *
     * @param businessDate        active business date
     * @param tradeTimeDifference trade time difference in minutes
     * @param expectedResult      expected volume weights stock price
     * @throws Exception Error occurred.
     */
    private static void volumeWeightedStockPrice(Calendar businessDate, int tradeTimeDifference, int expectedResult) throws Exception {

        SuperSimpleStockMarket market = new SuperSimpleStockMarket(businessDate);

        Calendar dateInPast = Calendar.getInstance(businessDate.getTimeZone());
        dateInPast.setTime(businessDate.getTime());
        dateInPast.add(Calendar.MINUTE, tradeTimeDifference);

        addStockDate(market);
        addTradeData(dateInPast, market);

        assertEquals(market.volumeWeightedStockPrice("TEA"), expectedResult);
    }

    /**
     * TestSuperSimpleStockMarket if parameters are equal.
     *
     * @param actual   actual result.
     * @param expected expected result
     * @throws Exception TestSuperSimpleStockMarket failed.
     */
    private static void assertEquals(double actual, double expected) throws Exception {
        if (actual != expected) {
            throw new TestFailedException(String.format("TestSuperSimpleStockMarket failed %f != %f", actual, expected));
        }
    }

    /**
     * TestSuperSimpleStockMarket if parameters are equal.
     *
     * @param actual   actual result.
     * @param expected expected result
     * @throws Exception TestSuperSimpleStockMarket failed.
     */
    private static void assertEquals(String actual, String expected) throws Exception {
        if (!actual.equals(expected)) {
            throw new TestFailedException(String.format("TestSuperSimpleStockMarket failed %f != %f", actual, expected));
        }
    }

    /**
     * Main class.
     *
     * @throws Exception Error occurred.
     */
    public static void main(String[] args) throws Exception {

        testDividendYield();
        testGeometricMean();
        testVolumeWeightedStockPrice();
        testStock();
        testConcurrentSkipListSet();
    }

}

/**
 * TestSuperSimpleStockMarket failure exception.
 */
class TestFailedException extends Exception {
    public TestFailedException(String message) {
        super(message);
    }
}

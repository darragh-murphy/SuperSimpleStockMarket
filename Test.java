package com.darraghmurphy.stockmarket;

public class Test {

    /**
     * Main class.
     */
    public static void main(String[] args) throws Exception {

        SuperSimpleStockMarket market = new SuperSimpleStockMarket();
        market.recordCommonStock("TEA", 0d, 100d);
        market.recordCommonStock("POP", 8d, 100d);
        market.recordCommonStock("ALE", 23d, 60d);
        market.recordPreferredStock("GIN", 8d, 0.02d, 100d);
        market.recordCommonStock("JOE", 13d, 250d);

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
        assertEquals(market.dividendYield("GIN", 100), 0.020000);


        /** Check geometric mean is ZERO before any trades are added */
        double gm1 = market.geometricMean();
        assertEquals(gm1, 0);

        market.recordBuyTrade(10, "TEA", 20d);
        market.recordSellTrade(20, "POP", 10d);
        market.recordSellTrade(30, "ALE", 30d);
        market.recordSellTrade(40, "GIN", 40d);
        market.recordSellTrade(50, "JOE", 50d);

        /** Check geometric mean after trades have been added */
        double gm2 = market.geometricMean();
        assertEquals(gm2, Math.pow(10d * 20d * 30d * 40d * 50d, 1.0 / 5.0));

        market.recordBuyTrade(10, "TEA", 20d);
        market.recordSellTrade(20, "POP", 10d);
        market.recordSellTrade(30, "ALE", 30d);
        market.recordSellTrade(40, "GIN", 40d);
        market.recordSellTrade(50, "JOE", 50d);

        /** Check geometric mean after trades have been added, where the number of stock types and prices stays
         * constent */
        double gm3 = market.geometricMean();
        assertEquals(gm2, Math.pow(10d * 20d * 30d * 40d * 50d, 1.0 / 5.0));

        double vwsp = market.volumeWeightedStockPrice("TEA");
        assertEquals(vwsp, 20);
    }

    private static void assertEquals(double actual, double expected) throws Exception {
        if (actual != expected) {
            throw new Exception(String.format("Test failed %f != %f", actual, expected));
        }
    }

}

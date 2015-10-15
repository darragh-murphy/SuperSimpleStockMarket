package com.darraghmurphy.stockmarket;

public class CommonStock implements StockInterface {

    public CommonStock(String symbol, Double lastDividend, Double parValue) {
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    /**
     * A ticker symbol or stock symbol is an abbreviation used to uniquely identify publicly traded shares of a
     * particular stock on a particular stock market. A stock symbol may consist of letters, numbers or a combination of both.
     */
    private String symbol;

    /**
     * A dividend is a payment made by a corporation to its shareholders, usually as a distribution of profits.
     * <p/>
     * This is a percentage of the common dividend
     */
    private Double lastDividend;

    /**
     * Par value for a share refers to the stock value stated in the corporate charter.  *./
     */
    private Double parValue;

    public String getSymbol() {
        return symbol;
    }

    /**
     * The Price-to-Earnings Ratio or P/E ratio is a ratio for valuing a company that measures its current share
     * price relative to its per-share earnings.
     * <p/>
     * The price-earnings ratio can be calculated as <pre>Market Value per Share / Earnings per Share</pre>
     * <p/>
     * Earnings per share (basic formula) <pre>Profit - Perferred Dividends / Weighted average common shares</pre>
     */
    public double priceEarningsRatio(double price) {

        return price / lastDividend;
    }

    public Double getParValue() {
        return parValue;
    }

    public double dividendYield(double price) {

        if (price <= 0) throw new IllegalArgumentException(String.format("Invalid parameter %f", price));

        /** TODO Requirements are unclear. Discussion required. */
        if (lastDividend == 0d) return 0d;

        return lastDividend / price;

    }
}
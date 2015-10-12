package com.darraghmurphy.stockmarket;

public class PreferredStock extends CommonStock implements StockInterface {

    public PreferredStock(String symbol, Double lastDividend, Double parValue, Double fixedDividend) {
        super(symbol, lastDividend, parValue);
        this.fixedDividend = fixedDividend;
    }

    /**
     * A “fixed” dividend is known as a “fixed rate” preferred share. For example, a $25 preferred share with
     * a 5% dividend would pay $.3125 quarterly (.05 x $25/4=. 3125). This is why preferred shares
     * are usually thought of as “fixed income” investments.
     */
    private Double fixedDividend;

    public double dividendYield(double price) {
        return (fixedDividend * getParValue()) / price;

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

        return price / fixedDividend;
    }


}

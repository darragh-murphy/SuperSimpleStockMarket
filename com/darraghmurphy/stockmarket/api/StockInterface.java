package com.darraghmurphy.stockmarket.api;

/**
 * Stock interface.
 */
public interface StockInterface {

    String getSymbol();

    /**
     * Get Par Value.
     *
     * @return par value
     */
    double getParValue();

    /**
     * Dividend Yield.
     *
     * @param price stock price
     * @return dividend yield
     */
    double dividendYield(double price);

    /**
     * The Price-to-Earnings Ratio or P/E ratio is a ratio for valuing a company that measures its current share
     * price relative to its per-share earnings.
     * <p/>
     * The price-earnings ratio can be calculated as <pre>Market Value per Share / Earnings per Share</pre>
     * <p/>
     * Earnings per share (basic formula) <pre>Profit - Perferred Dividends / Weighted average common shares</pre>
     *
     * @param price stock price
     * @return price Earnings Ratio
     */
    double priceEarningsRatio(double price);
}

 * <p/>
 * <h3>Requirements</h3>
 * Provide working source code that will :- For a given stock
 * <br>
 * <ol>
 * <li>Given any price as input, calculate the dividend yield</li>
 * <li>Given any price as input, calculate the P/E Ratio</li>
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
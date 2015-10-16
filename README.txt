Example Assignment – Super Simple Stock Market
==============================================

Requirements
============
Provide working source code that will :-

A) For a given stock

    1) Given any price as input, calculate the dividend yield
    2) Given any price as input, calculate the P/E Ratio
    3) Record a trade, with timestamp, quantity of shares, buy or sell indicator and traded price
    4) Calculate Volume Weighted Stock Price based on trades in past 15 minutes

B) Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

Constraints & Notes
===================
1) Written in one of these languages : Java, C#, C++, Python
2) No database or GUI is required, all data need only be held in memory
3) No prior knowledge of stock markets or trading is required – all formulas are provided below.

Table1. Sample data from the Global Beverage Corporation Exchange
=================================================================

+----------------+-------------+---------------+----------------+-----------+
| STOCK SYMBOL   | TYPE        | LAST DIVIDEND | FIXED DIVIDEND | PAR VALUE |
|                |             |               |                |           |
+----------------+-------------+---------------+----------------+-----------+
|                |             |               |                |           |
|   TEA          |   Common    |   0           |                |   100     |
|                |             |               |                |           |
+----------------+-------------+---------------+----------------+-----------+
|                |             |               |                |           |
|   POP          |   Common    |   8           |                |   100     |
|                |             |               |                |           |
+----------------+-------------+---------------+----------------+-----------+
|                |             |               |                |           |
|   ALE          |   Common    |   23          |                |   60      |
|                |             |               |                |           |
+----------------+-------------+---------------+----------------+-----------+
|                |             |               |                |           |
|   GIN          |   Preferred |   8           |   2%           |   100     |
|                |             |               |                |           |
+----------------+-------------+---------------+----------------+-----------+
|                |             |               |                |           |
|   JOE          |   Common    |   13          |                |   250     |
|                |             |               |                |           |
+----------------+-------------+---------------+----------------+-----------+

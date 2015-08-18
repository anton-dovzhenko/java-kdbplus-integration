package com.gammadevs.kdb.dao;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by anton on 7/8/15.
 */
public class StockDataDaoImpl implements StockDataDao {

    @Override
    public List<HistoricalQuote> getHistory(String symbol, Date start, Date end) throws IOException {
        Stock stock = YahooFinance.get(symbol);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        return stock.getHistory(startCalendar, endCalendar, Interval.DAILY);
    }

}

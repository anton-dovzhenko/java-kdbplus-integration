package com.gammadevs.kdb.dao;

import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by anton on 7/8/15.
 */
public interface StockDataDao {

    List<HistoricalQuote> getHistory(String symbol, Date start, Date end) throws IOException;

}

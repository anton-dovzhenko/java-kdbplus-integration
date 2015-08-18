package com.gammadevs.kdb.dao;

import kx.c;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by anton on 7/8/15.
 */
public interface KdbDao {

    void save(List<HistoricalQuote> quotes) throws IOException, c.KException;
    Map<String, Double> getVwaps(Date start, Date end, String ... symbols) throws IOException, c.KException;

}

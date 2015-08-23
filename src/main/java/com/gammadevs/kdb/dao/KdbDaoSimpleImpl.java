package com.gammadevs.kdb.dao;

import kx.c;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anton on 7/8/15.
 */
public class KdbDaoSimpleImpl implements KdbDao {

    private final String kdbDateFormat = "yyyy.MM.dd";
    private final c kdbConnection;
    private final String[] historicalQuoteColNames =
            {"date", "symbol", "open", "low", "high", "close", "adjClose", "volume"};

    public KdbDaoSimpleImpl(c kdbConnection) {
        this.kdbConnection = kdbConnection;
    }

    @Override
    public void save(List<HistoricalQuote> quotes) throws IOException, c.KException {
        int size = quotes.size();
        String[] symbol = new String[size];
        java.sql.Date[] date = new java.sql.Date[size];
        double[] open = new double[size];
        double[] low = new double[size];
        double[] high = new double[size];
        double[] close = new double[size];
        double[] adjClose = new double[size];
        long[] volume = new long[size];

        for (int i = 0; i < size; i++) {
            HistoricalQuote quote = quotes.get(i);
            symbol[i] = quote.getSymbol();
            date[i] = new java.sql.Date(quote.getDate().getTime().getTime());
            open[i] = quote.getOpen().doubleValue();
            low[i] = quote.getLow().doubleValue();
            high[i] = quote.getHigh().doubleValue();
            close[i] = quote.getClose().doubleValue();
            adjClose[i] = quote.getAdjClose().doubleValue();
            volume[i] = quote.getVolume();
        }
        Object[] data = new Object[] {date, symbol, open, low, high, close, adjClose, volume};
        kdbConnection.k("insert", "trades", data);
    }

    @Override
    public Map<String, Double> getVwaps(Date start, Date end, String ... symbols)
            throws IOException, c.KException {
        String query = "select volume wavg adjClose by symbol from trades where 1=1 ";
        query += getDateClause(start, end);
        query += getSymbolClause(symbols);
        c.Dict resultSet = (c.Dict) kdbConnection.k(query);
        String[] smbls = (String[]) ((c.Flip) resultSet.x).y[0];
        double[] vwaps = (double[]) ((c.Flip) resultSet.y).y[0];
        assert smbls.length == vwaps.length;
        Map<String, Double> result = new HashMap<>();
        for (int i = 0; i < smbls.length; i++) {
            result.put(smbls[i], vwaps[i]);
        }
        return result;
    }

    @Override
    public Map<String, Double> getMaxAdjClose(Date start, Date end, String ... symbols)
            throws IOException, c.KException {
        String query = "select max(adjClose) by symbol from trades where 1=1 ";
        query += getDateClause(start, end);
        query += getSymbolClause(symbols);
        c.Dict resultSet = (c.Dict) kdbConnection.k(query);
        String[] smbls = (String[]) ((c.Flip) resultSet.x).y[0];
        double[] maxAdjCloses = (double[]) ((c.Flip) resultSet.y).y[0];
        assert smbls.length == maxAdjCloses.length;
        Map<String, Double> result = new HashMap<>();
        for (int i = 0; i < smbls.length; i++) {
            result.put(smbls[i], maxAdjCloses[i]);
        }
        return result;
    }

    protected String getSymbolClause(String ... symbols) {
        String query = "";
        if (symbols.length > 0) {
            query += " & (symbol in ";
            for (String symbol : symbols) {
                query += "`" + symbol;
            }
            query += ")";
        }
        return query;
    }

    protected String getDateClause(Date start, Date end) {
        String query = "";
        query += " & (date >= " + getKdbDate(start) + ")";
        query += " & (date <= " + getKdbDate(end) + ")";
        return query;
    }


    protected String getKdbDate(Date date) {
        return new SimpleDateFormat(kdbDateFormat).format(date);
    }

}

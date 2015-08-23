package com.gammadevs.kdb.dao;

import com.gammadevs.kdb.model.TradeTable;
import info.michaelwittig.javaq.connector.QConnectorError;
import info.michaelwittig.javaq.connector.QConnectorException;
import info.michaelwittig.javaq.connector.QConnectorSync;
import info.michaelwittig.javaq.query.Result;
import info.michaelwittig.javaq.query.Select;
import info.michaelwittig.javaq.query.TableRow;
import info.michaelwittig.javaq.query.type.impl.TypeDate;
import info.michaelwittig.javaq.query.value.Value;
import info.michaelwittig.javaq.query.value.impl.DateValue;
import info.michaelwittig.javaq.query.value.impl.SymbolValue;
import kx.c;
import yahoofinance.histquotes.HistoricalQuote;

import javax.swing.text.TableView;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KdbDaoJavaQImpl implements KdbDao {

    private final QConnectorSync kx;

    public KdbDaoJavaQImpl(QConnectorSync kx) {
        this.kx = kx;
    }

    @Override
    public void save(List<HistoricalQuote> quotes) throws IOException, c.KException {

    }

    @Override
    public Map<String, Double> getVwaps(Date start, Date end, String... symbols) throws IOException, c.KException {
        throw new UnsupportedOperationException("Don't know how-to implement this with java-q");
    }

    @Override
    public Map<String, Double> getMaxAdjClose(Date start, Date end, String... symbols) throws QConnectorException {
        TradeTable trade = TradeTable.getInstance();
        Select select = TradeTable.getInstance().select()
                .column(trade.adjClose.max())
                .group(trade.symbol.group())
                .filter(trade.symbol.filterIn(SymbolValue.froms(symbols)))
                .filter(trade.date.filterGreaterOrEqualThan(new DateValue(new java.sql.Date(start.getTime()), TypeDate.get())))
                .filter(trade.date.filterSmallerOrEqualThan(new DateValue(new java.sql.Date(end.getTime()), TypeDate.get())))
                .build();

        Result resultSet = kx.select(select);
        Map<String, Double> result = new HashMap<>();
        for (TableRow row : trade.read(resultSet)) {
           result.put(row.get(trade.symbol).get(), row.get(trade.adjClose).get().doubleValue());
        }
        return result;
    }

}

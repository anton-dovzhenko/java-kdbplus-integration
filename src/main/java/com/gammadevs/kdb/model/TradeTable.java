package com.gammadevs.kdb.model;

import info.michaelwittig.javaq.query.column.impl.DateColumn;
import info.michaelwittig.javaq.query.column.impl.FloatColumn;
import info.michaelwittig.javaq.query.column.impl.LongColumn;
import info.michaelwittig.javaq.query.column.impl.SymbolColumn;
import info.michaelwittig.javaq.query.impl.ATable;

public class TradeTable extends ATable {

    private static final TradeTable instance = new TradeTable();

    public final DateColumn date = new DateColumn("date");
    public final SymbolColumn symbol = new SymbolColumn("symbol");
    public final FloatColumn open = new FloatColumn("open");
    public final FloatColumn low = new FloatColumn("low");
    public final FloatColumn high = new FloatColumn("high");
    public final FloatColumn close = new FloatColumn("close");
    public final FloatColumn adjClose = new FloatColumn("adjClose");
    public final LongColumn volume = new LongColumn("volume");

    private TradeTable() {
        super("trades");
        addColumn(date);
        addColumn(symbol);
        addColumn(open);
        addColumn(low);
        addColumn(high);
        addColumn(close);
        addColumn(adjClose);
        addColumn(volume);
    }

    public static TradeTable getInstance() {
        return instance;
    }

}

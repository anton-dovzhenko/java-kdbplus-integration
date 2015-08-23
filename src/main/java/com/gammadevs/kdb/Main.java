package com.gammadevs.kdb;

import com.gammadevs.kdb.dao.KdbDao;
import com.gammadevs.kdb.dao.KdbDaoSimpleImpl;
import com.gammadevs.kdb.dao.StockDataDao;
import com.gammadevs.kdb.dao.StockDataDaoImpl;
import kx.c;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by anton on 7/8/15.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger("Main");

    public static void main(String[] args) throws IOException, c.KException {

        System.setProperty("user.timezone", "GMT");
        TimeZone.setDefault(null);

        final String[] symbols = {"AAPL", "EPAM", "GOOG", "LXFT", "MSFT", "ORCL"};
        final Date start = new Date(113, 0, 1);
        final Date end = new Date(115, 6, 1);
        final String host = "localhost";
        final int port = 5555;

        c kdbConnection = null;
        try {
            kdbConnection = new c(host, port);
            KdbDao kdbDao = new KdbDaoSimpleImpl(kdbConnection);
            StockDataDao stockDataDao = new StockDataDaoImpl();
            for (String symbol : symbols) {
                kdbDao.save(stockDataDao.getHistory(symbol, start, end));
            }
        } finally {
            if (kdbConnection != null) {
                kdbConnection.close();
            }
        }

    }

}

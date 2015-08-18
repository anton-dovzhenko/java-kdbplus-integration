package com.gammadevs.kdb.dao;

import kx.c;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by anton on 18/8/15.
 */
public class KdbDaoImplTestInt {

    private static final String host = "localhost";
    private static final int port = 5555;
    private static c kdbConnection;
    private static KdbDao kdbDao;

    private final double error = 10e-12;

    @BeforeClass
    public static void init() throws IOException, c.KException {
        kdbConnection = new c(host, port);
        kdbDao = new KdbDaoImpl(kdbConnection);
    }

    @AfterClass
    public static void destroy() throws IOException {
        if (kdbConnection != null) {
            kdbConnection.close();
        }
    }

    @Test
    public void testGetVwapsOneSymbolSingleDay() throws Exception {
        Map<String, Double> vwaps = kdbDao.getVwaps(new Date(114, 6, 1), new Date(114, 6, 1), "AAPL");
        assertEquals(1, vwaps.size());
        assertEquals(91.493074, vwaps.get("AAPL"), error);
    }

    @Test
    public void testGetVwapsTwoSymbolsSingleDay() throws Exception {
        Map<String, Double> vwaps = kdbDao.getVwaps(new Date(114, 6, 1), new Date(114, 6, 1), "AAPL", "LXFT");
        assertEquals(2, vwaps.size());
        assertEquals(91.493074, vwaps.get("AAPL"), error);
        assertEquals(36.189999, vwaps.get("LXFT"), error);
    }


    @Test
    public void testGetVwapsOneSymbol() throws Exception {
        Map<String, Double> vwaps = kdbDao.getVwaps(new Date(114, 0, 1), new Date(114, 6, 1), "AAPL");
        assertEquals(1, vwaps.size());
        assertEquals(77.98673691618444, vwaps.get("AAPL"), error);
    }

    @Test
    public void testGetVwapsTwoSymbols() throws Exception {
        Map<String, Double> vwaps = kdbDao.getVwaps(new Date(114, 0, 1), new Date(114, 6, 1), "AAPL", "LXFT");
        assertEquals(2, vwaps.size());
        assertEquals(77.98673691618444, vwaps.get("AAPL"), error);
        assertEquals(32.743101835674636, vwaps.get("LXFT"), error);
    }

}
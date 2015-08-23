package com.gammadevs.kdb.dao;

import com.gammadevs.kdb.TestConstants;
import kx.c;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by anton on 18/8/15.
 */
public class KdbDaoSimpleImplTestInt {

    private static c kdbConnection;
    private static KdbDao kdbDao;

    @BeforeClass
    public static void init() throws IOException, c.KException {
        kdbConnection = new c(TestConstants.host, TestConstants.port);
        kdbDao = new KdbDaoSimpleImpl(kdbConnection);
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
        assertEquals(91.493074, vwaps.get("AAPL"), TestConstants.error);
    }

    @Test
    public void testGetVwapsTwoSymbolsSingleDay() throws Exception {
        Map<String, Double> vwaps = kdbDao.getVwaps(new Date(114, 6, 1), new Date(114, 6, 1), "AAPL", "LXFT");
        assertEquals(2, vwaps.size());
        assertEquals(91.493074, vwaps.get("AAPL"), TestConstants.error);
        assertEquals(36.189999, vwaps.get("LXFT"), TestConstants.error);
    }


    @Test
    public void testGetVwapsOneSymbol() throws Exception {
        Map<String, Double> vwaps = kdbDao.getVwaps(new Date(114, 0, 1), new Date(114, 6, 1), "AAPL");
        assertEquals(1, vwaps.size());
        assertEquals(77.98673691618444, vwaps.get("AAPL"), TestConstants.error);
    }

    @Test
    public void testGetVwapsTwoSymbols() throws Exception {
        Map<String, Double> vwaps = kdbDao.getVwaps(new Date(114, 0, 1), new Date(114, 6, 1), "AAPL", "LXFT");
        assertEquals(2, vwaps.size());
        assertEquals(77.98673691618444, vwaps.get("AAPL"), TestConstants.error);
        assertEquals(32.743101835674636, vwaps.get("LXFT"), TestConstants.error);
    }

    @Test
    public void testGetMaxAdjCloseOneSymbolSingleDay() throws Exception {
        Map<String, Double> result = kdbDao.getMaxAdjClose(new Date(114, 5, 4), new Date(114, 5, 4), "AAPL");
        assertEquals(1, result.size());
        assertEquals(90.120624, result.get("AAPL"), TestConstants.error);
    }

    @Test
    public void testGetMaxAdjCloseTwoSymbolsSingleDay() throws Exception {
        Map<String, Double> result = kdbDao.getMaxAdjClose(new Date(114, 5, 4), new Date(114, 5, 4), "AAPL", "LXFT");
        assertEquals(2, result.size());
        assertEquals(90.120624, result.get("AAPL"), TestConstants.error);
        assertEquals(32.16, result.get("LXFT"), TestConstants.error);
    }

    @Test
    public void testGetMaxAdjCloseOneSymbolForYear() throws Exception {
        Map<String, Double> result = kdbDao.getMaxAdjClose(new Date(114, 0, 1), new Date(114, 11, 31), "AAPL");
        assertEquals(1, result.size());
        assertEquals(117.507253, result.get("AAPL"), TestConstants.error);
    }

    @Test
    public void testGetMaxAdjCloseTwoSymbolForYear() throws Exception {
        Map<String, Double> result = kdbDao.getMaxAdjClose(new Date(114, 0, 1), new Date(114, 11, 31), "AAPL", "LXFT");
        assertEquals(2, result.size());
        assertEquals(117.507253, result.get("AAPL"), TestConstants.error);
        assertEquals(43.52, result.get("LXFT"), TestConstants.error);
    }

}
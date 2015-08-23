package com.gammadevs.kdb.dao;

import com.gammadevs.kdb.TestConstants;
import info.michaelwittig.javaq.connector.QConnectorError;
import info.michaelwittig.javaq.connector.QConnectorException;
import info.michaelwittig.javaq.connector.QConnectorSync;
import info.michaelwittig.javaq.connector.impl.QConnectorFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by anton on 18/8/15.
 */
public class KdbDaoJavaQImplTestInt {

    private static KdbDao kdbDao;
    private static QConnectorSync kx;

    @BeforeClass
    public static void init() throws QConnectorException, QConnectorError {
        kx = QConnectorFactory.create(TestConstants.host, TestConstants.port);
        kx.connect();
        kdbDao = new KdbDaoJavaQImpl(kx);
    }

    @AfterClass
    public static void destroy() throws QConnectorError {
        if (kx.isConnected()) {
            kx.disconnect();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetVwaps() throws Exception {
        kdbDao.getVwaps(new Date(114, 6, 1), new Date(114, 6, 1), "AAPL");
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
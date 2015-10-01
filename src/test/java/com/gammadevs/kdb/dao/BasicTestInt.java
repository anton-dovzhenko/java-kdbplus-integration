package com.gammadevs.kdb.dao;

import com.gammadevs.kdb.TestConstants;
import kx.c;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by anton on 18/8/15.
 */
public class BasicTestInt {

    private static c kdbConnection;

    @BeforeClass
    public static void init() throws IOException, c.KException {
        kdbConnection = new c(TestConstants.host, TestConstants.port);
    }


    @Test()
    public void getDictionaryTest() throws Exception {
        c.Dict dict = (c.Dict) kdbConnection.k("`a`b`c ! (1; `qqq; enlist(`aaa;`bbb;`ccc))");
        assertEquals("a", ((String[]) dict.x)[0]);
        assertEquals("b", ((String[]) dict.x)[1]);
        assertEquals("c", ((String[]) dict.x)[2]);
        assertEquals(1L, ((Object[]) dict.y)[0]);
        assertEquals("qqq", ((Object[]) dict.y)[1]);
        assertArrayEquals(new String[] {"aaa", "bbb", "ccc"}
                , (String[]) ((Object[])((Object[]) dict.y)[2])[0]);
        System.out.println(dict);
    }

}
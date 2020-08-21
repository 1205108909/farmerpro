package com.phenix.math;

import com.phenix.farmer.TestUtil;
import com.phenix.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MathUtilTest {
    private double[]d;
    private final static double DEFAULT_DELTA = 1e-4;
    private Path folder;

    @Before
    public void setUp() {
        folder = Paths.get(TestUtil.getDir(MathUtilTest.class).toString());
        //d = new double[]{0.2760, 0.6797, 0.6551, 0.1626, 0.1190, 0.4984, 0.9597, 0.3404, 0.5853, 0.2238};
    }

    @Test
    public void testL1filter() throws Exception {
        Path p = Paths.get(folder.toString() + "/l1data.csv");
        double []d = Util.readDouble(p, 0);
        double []dFilteredE = Util.readDouble(p, 1);
        double []dFiltered = MathUtil.l1filter(d, 0.035);

        assertArrayEquals(dFilteredE, dFiltered, DEFAULT_DELTA);
    }

    @Test
    public void testDiff() {
        Path p = Paths.get(folder.toString() + "/diffData.csv");
        Path pe1 = Paths.get(folder.toString() + "/diff1.csv");
        Path pe2 = Paths.get(folder.toString() + "/diff2.csv");
        double []d = Util.readDouble(p, 0);
        double []d1 = MathUtil.diff(d, 1);
        double []d2 = MathUtil.diff(d, 2);

        double []d1E = Util.readDouble(pe1, 0);
        assertArrayEquals(d1E, d1, DEFAULT_DELTA);

        double []d2E = Util.readDouble(pe2, 0);
        assertArrayEquals(d2E, d2, DEFAULT_DELTA);
    }

    @Test
    public void testComputeDtw() {
        double expected = 3.051001368000001;
        Path p1 = Paths.get(folder.toString() + "/diff1.csv");
        Path p2 = Paths.get(folder.toString() + "/diff2.csv");
        double []d1 = Util.readDouble(p1, 0);
        double []d2 = Util.readDouble(p2, 0);
        double dtw = MathUtil.computeDtw(d1, d2);

        assertEquals(expected, dtw, DEFAULT_DELTA);
    }
}
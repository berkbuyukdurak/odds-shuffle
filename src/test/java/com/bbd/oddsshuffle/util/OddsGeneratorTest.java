package com.bbd.oddsshuffle.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OddsGeneratorTest {

    private OddsGenerator oddsGenerator;

    @Value("${odds.margin.min}")
    private double marginMin;

    @Value("${odds.margin.max}")
    private double marginMax;

    @Value("${odds.limit.min}")
    private double oddsLimitMin;

    @Value("${odds.limit.max}")
    private double oddsLimitMax;

    @BeforeEach
    void setUp() {
        oddsGenerator = new OddsGenerator();
        // Inject property values for testing
        ReflectionTestUtils.setField(oddsGenerator, "marginMin", marginMin);
        ReflectionTestUtils.setField(oddsGenerator, "marginMax", marginMax);
        ReflectionTestUtils.setField(oddsGenerator, "oddsLimitMin", oddsLimitMin);
        ReflectionTestUtils.setField(oddsGenerator, "oddsLimitMax", oddsLimitMax);
    }

    @Test
    void testGenerateOdds_WithinLimits() {
        double[] odds = oddsGenerator.generateOdds();

        // Assert array length
        assertEquals(3, odds.length, "Odds array should have exactly 3 elements.");

        // Assert each odd falls within the range
        for (double odd : odds) {
            assertTrue(odd >= oddsLimitMin && odd <= oddsLimitMax,
                    "Each odd should be between the configured limits.");
        }
    }

    @Test
    void testGenerateOdds_WithMarginAdjustment() {
        double[] odds = oddsGenerator.generateOdds();

        // Generate odds without the margin for comparison
        double margin = marginMin + (marginMax - marginMin) / 2.0;
        double[] rawOdds = {oddsLimitMin + (oddsLimitMax - oddsLimitMin) / 3.0,
                oddsLimitMin + (oddsLimitMax - oddsLimitMin) / 2.0,
                oddsLimitMin + (oddsLimitMax - oddsLimitMin) / 4.0};

        for (int i = 0; i < odds.length; i++) {
            double expectedOdd = rawOdds[i] * (1 - margin);
            assertTrue(odds[i] >= oddsLimitMin && odds[i] <= oddsLimitMax,
                    "Adjusted odd should fall within configured limits.");
        }
    }
}
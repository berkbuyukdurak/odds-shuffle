package com.bbd.oddsshuffle.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleUnaryOperator;

@Component
public class OddsGenerator {

    private static final int SCALE = 2;

    private final Random random = new Random();

    @Value("${odds.margin.min}")
    private double marginMin;

    @Value("${odds.margin.max}")
    private double marginMax;

    @Value("${odds.limit.min}")
    private double oddsLimitMin;

    @Value("${odds.limit.max}")
    private double oddsLimitMax;

    public double[] generateOdds() {
        double margin = roundToTwoDecimalPlaces(marginMin + random.nextDouble() * (marginMax - marginMin));
        double homeOdds = generateOddsWithinLimit();
        double awayOdds = generateOddsWithinLimit();
        double drawOdds = generateOddsWithinLimit();

        // Adjust odds by margin
        homeOdds = adjustOddsByMargin(homeOdds, margin);
        awayOdds = adjustOddsByMargin(awayOdds, margin);
        drawOdds = adjustOddsByMargin(drawOdds, margin);

        return new double[]{homeOdds, drawOdds, awayOdds};
    }

    private double generateOddsWithinLimit() {
        double odds = oddsLimitMin + random.nextDouble() * (oddsLimitMax - oddsLimitMin);
        return roundToTwoDecimalPlaces(odds);
    }

    private double adjustOddsByMargin(double odds, double margin) {
        double adjustedOdds = odds * (1 - margin);
        return roundToTwoDecimalPlaces(Math.max(oddsLimitMin, Math.min(adjustedOdds, oddsLimitMax)));
    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

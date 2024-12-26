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
    private double minMargin;

    @Value("${odds.margin.max}")
    private double maxMargin;

    @Value("${odds.limit.min}")
    private double minOddsLimit;

    @Value("${odds.limit.max}")
    private double maxOddsLimit;

    public double[] generateOdds() {
        double[] fairOdds = generateFairOdds();
        double margin = calculateMargin();

        // Apply margin and clamp odds
        DoubleUnaryOperator applyMarginAndClamp = fairOdd ->
                clamp(margin / fairOdd, minOddsLimit, maxOddsLimit);

        return roundOdds(
                applyMarginAndClamp.applyAsDouble(fairOdds[0]),
                applyMarginAndClamp.applyAsDouble(fairOdds[1]),
                applyMarginAndClamp.applyAsDouble(fairOdds[2])
        );
    }

    private double[] generateFairOdds() {
        double homeWinProb = ThreadLocalRandom.current().nextDouble();
        double drawProb = ThreadLocalRandom.current().nextDouble();
        double awayWinProb = ThreadLocalRandom.current().nextDouble();

        double totalProb = homeWinProb + drawProb + awayWinProb;
        return new double[]{
                homeWinProb / totalProb,
                drawProb / totalProb,
                awayWinProb / totalProb
        };
    }

    private double calculateMargin() {
        return 1 + ThreadLocalRandom.current().nextDouble(minMargin, maxMargin);
    }

    private double[] roundOdds(double... odds) {
        return new double[]{
                round(odds[0]),
                round(odds[1]),
                round(odds[2])
        };
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

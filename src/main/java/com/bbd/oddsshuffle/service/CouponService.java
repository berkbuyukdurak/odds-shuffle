package com.bbd.oddsshuffle.service;

import com.bbd.oddsshuffle.constants.BetStatus;
import com.bbd.oddsshuffle.model.entity.Bet;
import com.bbd.oddsshuffle.model.entity.Coupon;
import com.bbd.oddsshuffle.model.entity.Match;
import com.bbd.oddsshuffle.repository.CouponRepository;
import com.bbd.oddsshuffle.repository.MatchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {

    private static final int MAX_COUPONS_PER_MATCH = 500;

    private final CouponRepository couponRepository;
    private final MatchRepository matchRepository;
    private final TaskExecutor taskExecutor;
    private final CouponExpirationService couponExpirationService;

    @Value("${oddsshuffle.coupon.timeout}")
    private int couponTimeout; // Timeout in seconds

    public CouponService(CouponRepository couponRepository, MatchRepository matchRepository, TaskExecutor taskExecutor, CouponExpirationService couponExpirationService) {
        this.couponRepository = couponRepository;
        this.matchRepository = matchRepository;
        this.taskExecutor = taskExecutor;
        this.couponExpirationService = couponExpirationService;
    }

    @Transactional
    public Coupon createCoupon(List<Bet> bets) throws Exception {
        // Validate each bet and associate it with matches
        for (Bet bet : bets) {
            Match match = matchRepository.findById(bet.getMatch().getMatchId())
                    .orElseThrow(() -> new Exception("Match not found: " + bet.getMatch().getMatchId()));

            // Enforce maximum coupon limit for the match
            if (couponRepository.countByBetsMatch(match) >= MAX_COUPONS_PER_MATCH) {
                throw new Exception("Maximum coupon limit reached for match: " + match.getMatchId());
            }

            // Set the odds at the time of placement
            bet.setOddsAtPlacement(getOddsAtPlacement(match, bet));
            bet.setStatus(BetStatus.PENDING); // Mark bet as pending
        }

        // Create and save the coupon
        Coupon coupon = new Coupon();
        coupon.setCouponTime(LocalDateTime.now());
        coupon.setBets(bets);

        for (Bet bet : bets) {
            bet.setCoupon(coupon);
        }

        Coupon savedCoupon = couponRepository.save(coupon);

        // Schedule the timeout for the coupon
        scheduleCouponTimeout(savedCoupon);

        return savedCoupon;
    }

    public Coupon getCouponById(UUID couponId) throws Exception {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new Exception("Coupon not found with ID: " + couponId));
    }

    private double getOddsAtPlacement(Match match, Bet bet) {
        switch (bet.getBetType()) {
            case HOME_WIN:
                return match.getHomeOdds();
            case DRAW:
                return match.getDrawOdds();
            case AWAY_WIN:
                return match.getAwayOdds();
            default:
                throw new IllegalArgumentException("Unknown bet type: " + bet.getBetType());
        }
    }

    public void scheduleCouponTimeout(Coupon coupon) {
        taskExecutor.execute(() -> {
            try {
                Thread.sleep(couponTimeout * 1000L); // Sleep for timeout duration
                couponExpirationService.markCouponAsExpired(coupon.getCouponId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Coupon timeout interrupted: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error during coupon expiration: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
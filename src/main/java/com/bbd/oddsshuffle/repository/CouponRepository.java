package com.bbd.oddsshuffle.repository;

import com.bbd.oddsshuffle.model.entity.Coupon;
import com.bbd.oddsshuffle.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    long countByBetsMatch(Match match);

    @Query("SELECT COUNT(c) >= 500 FROM Coupon c JOIN c.bets b WHERE b.match.matchId = :matchId")
    boolean isMaxCouponLimitReached(UUID matchId);
}
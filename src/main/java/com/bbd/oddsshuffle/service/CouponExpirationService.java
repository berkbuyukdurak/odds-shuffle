package com.bbd.oddsshuffle.service;

import com.bbd.oddsshuffle.constants.BetStatus;
import com.bbd.oddsshuffle.model.entity.Coupon;
import com.bbd.oddsshuffle.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CouponExpirationService {

    private final CouponRepository couponRepository;

    public CouponExpirationService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markCouponAsExpired(UUID couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalStateException("Coupon not found for expiration: " + couponId));

        coupon.getBets().forEach(bet -> {
            bet.setStatus(BetStatus.EXPIRED);
        });
        coupon.setExpired(true);

        couponRepository.save(coupon);
    }
}
package com.bbd.oddsshuffle.controller;

import com.bbd.oddsshuffle.model.dto.request.CouponRequestDTO;
import com.bbd.oddsshuffle.model.dto.response.BetResponseDTO;
import com.bbd.oddsshuffle.model.dto.response.CouponResponseDTO;
import com.bbd.oddsshuffle.model.entity.Bet;
import com.bbd.oddsshuffle.model.entity.Coupon;
import com.bbd.oddsshuffle.service.CouponService;
import com.bbd.oddsshuffle.util.GenericResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<Object> createCoupon(@RequestBody CouponRequestDTO request) {
        try {
            // Convert request DTO to Bet entity
            List<Bet> bets = request.getBets().stream()
                    .map(betDTO -> {
                        Bet bet = new Bet();
                        bet.setMatch(betDTO.getMatch());
                        bet.setBetType(betDTO.getBetType());
                        return bet;
                    }).collect(Collectors.toList());

            // Call service to create the coupon
            Coupon createdCoupon = couponService.createCoupon(bets);

            // Map coupon to response DTO
            CouponResponseDTO response = new CouponResponseDTO();
            response.setCouponId(createdCoupon.getCouponId());
            response.setCouponTime(createdCoupon.getCouponTime());
            response.setExpired(createdCoupon.isExpired());
            response.setBets(createdCoupon.getBets().stream().map(bet -> {
                BetResponseDTO betResponse = new BetResponseDTO();
                betResponse.setBetId(bet.getBetId());
                betResponse.setMatchId(bet.getMatch().getMatchId());
                betResponse.setOddsAtPlacement(bet.getOddsAtPlacement());
                betResponse.setBetType(bet.getBetType());
                betResponse.setStatus(bet.getStatus());
                return betResponse;
            }).collect(Collectors.toList()));

            if (ObjectUtils.isEmpty(response)) {
                return GenericResponseHandler.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
            }
            return GenericResponseHandler.successResponse(HttpStatus.CREATED, response);
        } catch (Exception e) {
            System.err.println("Error creating coupon: " + e.getMessage());
            return GenericResponseHandler.errorResponse(HttpStatus.BAD_REQUEST, "Error creating coupon");
        }
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<Object> getCoupon(@PathVariable("couponId") String couponId) {
        try {
            // Fetch coupon by ID
            Coupon coupon = couponService.getCouponById(UUID.fromString(couponId));

            // Map coupon to response DTO
            CouponResponseDTO response = new CouponResponseDTO();
            response.setCouponId(coupon.getCouponId());
            response.setCouponTime(coupon.getCouponTime());
            response.setExpired(coupon.isExpired());
            response.setBets(coupon.getBets().stream().map(bet -> {
                BetResponseDTO betResponse = new BetResponseDTO();
                betResponse.setBetId(bet.getBetId());
                betResponse.setMatchId(bet.getMatch().getMatchId());
                betResponse.setOddsAtPlacement(bet.getOddsAtPlacement());
                betResponse.setBetType(bet.getBetType());
                betResponse.setStatus(bet.getStatus());
                return betResponse;
            }).collect(Collectors.toList()));

            if (ObjectUtils.isEmpty(response)) {
                return GenericResponseHandler.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
            }
            return GenericResponseHandler.successResponse(HttpStatus.OK, response);
        } catch (Exception e) {
            return GenericResponseHandler.errorResponse(HttpStatus.NOT_FOUND, "Coupon not found");
        }
    }
}
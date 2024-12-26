package com.bbd.oddsshuffle.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CouponResponseDTO {
    private UUID couponId;
    private LocalDateTime couponTime;
    private boolean isExpired;
    private List<BetResponseDTO> bets;
}

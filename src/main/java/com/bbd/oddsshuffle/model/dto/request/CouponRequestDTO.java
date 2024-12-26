package com.bbd.oddsshuffle.model.dto.request;

import com.bbd.oddsshuffle.constants.BetType;
import com.bbd.oddsshuffle.model.entity.Match;
import lombok.Data;

import java.util.List;

@Data
public class CouponRequestDTO {
    private List<BetDTO> bets;

    @Data
    public static class BetDTO {
        private Match match;
        private BetType betType;
    }
}
